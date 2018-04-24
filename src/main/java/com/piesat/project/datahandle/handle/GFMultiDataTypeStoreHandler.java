package com.piesat.project.datahandle.handle;

import java.io.File;

public class GFMultiDataTypeStoreHandler extends BaseMultiDataTypeStoreHandler {
    private static final GFMultiDataTypeStoreHandler INSTANCE = new GFMultiDataTypeStoreHandler();

    private GFMultiDataTypeStoreHandler() {
        super();
    }

    public static GFMultiDataTypeStoreHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public String getFileTypeName(File file) {
        if (file.getName().contains("B1")) {
            return "B1";
        } else {
            return "IRS";
        }
    }
}
