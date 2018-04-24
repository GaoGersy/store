package com.piesat.project.datahandle;

import com.piesat.project.datahandle.factory.IDataStoreFactory;
import com.piesat.project.datahandle.handle.IDataStoreHandler;

public class MyDataStoreFactory implements IDataStoreFactory {

    @Override
    public IDataStoreHandler createDataStoreHandler() {
        return new MyDataSotreHandler();
    }
}
