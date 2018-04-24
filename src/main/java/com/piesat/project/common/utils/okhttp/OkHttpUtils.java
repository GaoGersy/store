package com.piesat.project.common.utils.okhttp;



import com.piesat.project.common.utils.JSONUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";
    private static final long DEFAULT_MILLISECONDS = 60000;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM_DATA= MediaType.parse("multipart/form-data; charset=utf-8");

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpUtils() {
        initOkHttp("gersy");
    }

    protected void initOkHttp(String tag) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(tag);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.SEVERE);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        mOkHttpClient = builder.build();
    }

    /**
     * 通过单例模式构造对象
     *
     * @return OkHttpUtils
     */
    private synchronized static OkHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    public static void get(String url, ResultCallback callback) {
        getInstance().getRequest(url, callback);
    }

    public static String getSync(String url) {
        return getInstance().getRequest(url);
    }

    public static void post(String url, List<Param> params, ResultCallback callback) {
        getInstance().postRequest(url, callback, params);
    }

    public static String postSync(String url, List<Param> params) {
        return getInstance().postRequest(url, params);
    }

    public static void postJson(String url, Object object,ResultCallback callback) {
        String json = JSONUtils.beanToJson(object);
        getInstance().postJsonRequest(url,callback, json);
    }

    public static String postJsonSync(String url, Object object){
        String json = JSONUtils.beanToJson(object);
        return getInstance().postJsonRequest(url, json);
    }

    /**
     * 构造Get请求
     *
     * @param url 请求的url
     * @param callback 结果回调的方法
     */
    private void getRequest(String url, final ResultCallback callback) {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    private String getRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return syncResult(request);
    }

    /**
     * 构造post 请求
     *
     * @param url 请求的url
     * @param callback 结果回调的方法
     * @param params 请求参数
     */
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    private String postRequest(String url, List<Param> params) {
        Request request = buildPostRequest(url, params);
        return syncResult(request);
    }

    private void postJsonRequest(String url, final ResultCallback callback, String jsonContent) {
        RequestBody requestBody = RequestBody.create(JSON,jsonContent);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        deliveryResult(callback, request);
    }

    private String postJsonRequest(String url, String jsonContent) {
        RequestBody requestBody = RequestBody.create(JSON,jsonContent);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return syncResult(request);
    }

    public String syncResult(Request request) {
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    /**
     * 处理请求结果的回调
     */
    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    sendSuccessCallBack(callback, str);
                } catch (final Exception e) {
                    sendFailCallback(callback, e);
                }
            }

        });
    }

    /**
     * 发送失败的回调
     */
    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        if (callback != null) {
            callback.onFailure(e);
        }
    }

    /**
     * 发送成功的调
     */
    private void sendSuccessCallBack(final ResultCallback callback, String obj) {
        if (callback != null) {
            callback.onSuccess(obj);
        }
    }

    /**
     * 构造post请求
     *
     * @param url 请求url
     * @param params 请求的参数
     * @return 返回 Request
     */
    private Request buildPostRequest(String url, List<Param> params) {
//        FormEncodingBuilder builder = new FormEncodingBuilder();
        RequestBody requestBody = RequestBody.create(FORM_DATA,"");
//        for (Param param : params) {
//            builder.add(param.key, param.value);
//        }
//        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**
     * http请求回调类,回调方法在UI线程中执行
     */
    public static abstract class ResultCallback {
        /**
         * 请求成功回调
         */
        public abstract void onSuccess(String result);

        /**
         * 请求失败回调
         */
        public abstract void onFailure(Exception e);
    }


    public static String uploadMultiFile(String url, File file) {
        RequestBody requestBody = RequestBody.create(FORM_DATA,file);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return getInstance().syncResult(request);
    }

    /**
     * post请求参数类
     */
    public static class Param {

        String key;//请求的参数
        String value;//参数的值

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    public static class ObjectParam {

        String key;//请求的参数
        Object value;//参数的值

        public ObjectParam() {
        }

        public ObjectParam(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
