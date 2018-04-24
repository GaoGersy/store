package com.piesat.project.datahandle.step.IRS;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class StepIRSTransferGzipFiles extends BaseStep {

    public StepIRSTransferGzipFiles(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start) {
            return false;
        }
        StepIRSTranferFile stepIRSTranferFile = (StepIRSTranferFile) getStep();
        StepIRSDeleteUntarFiles stepIRSDeleteUntarFiles = (StepIRSDeleteUntarFiles) stepIRSTranferFile.getStep();
        StepIRSPreHande step = (StepIRSPreHande) stepIRSDeleteUntarFiles.getStep();
        StepIRSUnTar stepB1UnTar = (StepIRSUnTar) step.getStep();
        String distPath = PieOrthorParameterHelper.PROPERTIES_MAP.get("DIST_PATH");
        File file = stepB1UnTar.getFile();
        boolean renameTo = renameTo(file, distPath);
        SuperLogger.e("转移压缩文件完成");
        if (renameTo) {
            return true;
        }
        saveLog("转移压缩文件失败");
        return false;
    }
}
