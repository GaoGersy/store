package com.piesat.project.datahandle;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.datahandle.handle.GFMultiDataTypeStoreHandler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GFPreHandle implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String filePath = PieOrthorParameterHelper.PROPERTIES_MAP.get("SOURCE_PATH");
        GFMultiDataTypeStoreHandler dataTypeStoreHandler = GFMultiDataTypeStoreHandler.getInstance();
        dataTypeStoreHandler.onStore(filePath);
    }
}
