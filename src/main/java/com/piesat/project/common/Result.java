package com.piesat.project.common;

import com.piesat.project.common.utils.SuperLogger;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    /**
     * 成功
     */
    public static final int SUCCESS_CODE = 200;
    /**
     * 没有注册
     */
    public static final int UNREGISTER_CODE = 300;
    /**
     * 服务器内部错误
     */
    public static final int SERVER_ERROR_CODE = 500;
    /**
     * 通用错误码
     */
    public static final int COMMON_ERROR_CODE = 400;
    /**
     * 没有查到相应的数据
     */
    public static final int DATA_NOT_FOUND = 999;


    private int result;// 是否成功标志 0:失败 1：成功

    private Object data;// 成功时返回的数据

    private String error;// 错误信息

    private int code;//区别不同的错误类型

    private Result() {
        result = SUCCESS;
        data = "操作成功";
    }

    // 成功时的构造器
    public Result(int result, Object data) {
        this.result = result;
        this.data = data;
    }

    // 错误时的构造器
    public Result(int success, String error) {
        this.result = success;
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "JsonResult [success=" + result + "code=" + code + ", data=" + data + ", e=" + error + "]";
    }

    public static Result getInstance() {
        return new Result();
    }

    public static Result error() {
        return error("操作失败", COMMON_ERROR_CODE);
    }

    public static Result error(String msg) {
        return error(msg, SERVER_ERROR_CODE);
    }

    public static Result error(String msg, int errorCode) {
        Result result = new Result(ERROR, msg);
        result.setCode(errorCode);
        return result;
    }

    public static Result success(String msg) {
        return new Result(SUCCESS, msg);
    }

    public static Result success() {
        Result result = new Result();
        result.error="";
        return result;
    }

    public static Result noData() {
        return error("没有找到相应的数据", DATA_NOT_FOUND);
    }

    public Result success(Object data) {
        this.result = SUCCESS;
        this.error = "";
        this.data = data;
        this.code = SUCCESS_CODE;
        return this;
    }

    public Result exception(Exception e) {
        e.printStackTrace();
        this.result = ERROR;
        this.code = SERVER_ERROR_CODE;
//        if (e instanceof NullPointerException) {
//            this.error = "空指针异常";
//        } else if (e instanceof JsonSyntaxException) {
//            this.error = "Json语法错误";
//        } else if (e instanceof IllegalStateException) {
//            this.error = "服务器非法状态 错误";
//        } else if (e instanceof IOException) {
//            this.error = "服务器IO异常";
//        } else {
//            this.error = "服务器未知错误";
//        }
        SuperLogger.e(e.getMessage());
        return this;
    }

}
