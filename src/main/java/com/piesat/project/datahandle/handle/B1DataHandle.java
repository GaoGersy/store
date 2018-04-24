package com.piesat.project.datahandle.handle;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.B1.StepB1DeleteUntarFiles;
import com.piesat.project.datahandle.step.B1.StepB1PreHande;
import com.piesat.project.datahandle.step.B1.StepB1TranferFile;
import com.piesat.project.datahandle.step.B1.StepB1TransferGzipFiles;
import com.piesat.project.datahandle.step.B1.StepB1UnTar;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class B1DataHandle extends BaseDataStoreHandler {
    @Override
    protected IStep getStep(File file) {
        SuperLogger.e("B1DataHandle 开始工作");
        StepB1UnTar stepIRSUnTar = new StepB1UnTar(null);
        stepIRSUnTar.setFile(file);
        StepB1PreHande preHande = new StepB1PreHande(stepIRSUnTar);
        StepB1DeleteUntarFiles deleteUntarFiles = new StepB1DeleteUntarFiles(preHande);
        StepB1TranferFile tranferFile = new StepB1TranferFile(deleteUntarFiles);
        StepB1TransferGzipFiles transferGzipFiles = new StepB1TransferGzipFiles(tranferFile);
        return transferGzipFiles;
    }
}
