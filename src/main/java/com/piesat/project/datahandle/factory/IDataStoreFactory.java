package com.piesat.project.datahandle.factory;

import com.piesat.project.datahandle.handle.IDataStoreHandler;

public interface IDataStoreFactory {

    IDataStoreHandler createDataStoreHandler();
}