package com.piesat.project.datahandle.handle;

import com.piesat.project.datahandle.StoreResult;

/**
 * 将解析后的数据存入数据库
 */
public interface IMultiDataTypeStoreHandler {

    StoreResult onStore(String filePath);

}
