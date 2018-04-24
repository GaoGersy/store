package com.piesat.project.datahandle;

import com.piesat.project.datahandle.handle.BaseDataStoreHandler;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class MyDataSotreHandler extends BaseDataStoreHandler {
    @Override
    protected IStep getStep(File file) {
        return null;
    }
}
