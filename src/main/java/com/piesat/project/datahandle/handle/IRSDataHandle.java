package com.piesat.project.datahandle.handle;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.IRS.StepIRSTransferGzipFiles;
import com.piesat.project.datahandle.step.IStep;
import com.piesat.project.datahandle.step.IRS.StepIRSDeleteUntarFiles;
import com.piesat.project.datahandle.step.IRS.StepIRSUnTar;
import com.piesat.project.datahandle.step.IRS.StepIRSPreHande;
import com.piesat.project.datahandle.step.IRS.StepIRSTranferFile;

import java.io.File;

public class IRSDataHandle extends BaseDataStoreHandler {
    @Override
    protected IStep getStep(File file) {
        SuperLogger.e("IRSDataHandle 开始工作");
        StepIRSUnTar stepIRSUnTar = new StepIRSUnTar(null);
        stepIRSUnTar.setFile(file);
        StepIRSPreHande preHande = new StepIRSPreHande(stepIRSUnTar);
        StepIRSDeleteUntarFiles deleteUntarFiles = new StepIRSDeleteUntarFiles(preHande);
        StepIRSTranferFile tranferFile = new StepIRSTranferFile(deleteUntarFiles);
        StepIRSTransferGzipFiles transferGzipFiles = new StepIRSTransferGzipFiles(tranferFile);
        return transferGzipFiles;
    }
}
