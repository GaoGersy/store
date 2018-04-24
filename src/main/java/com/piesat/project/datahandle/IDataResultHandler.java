package com.piesat.project.datahandle;

/**
 * 数据解析完后将结果返回给调用者
 * @param <T> 返回的数据类型
 */
public interface IDataResultHandler<T>{
    /**
     * 数据解析
     */
    void onDataParse();

    /**
     * 将解析后的数据返回
     */
    T onDataResult();
}
