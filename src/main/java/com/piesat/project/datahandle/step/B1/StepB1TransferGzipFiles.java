package com.piesat.project.datahandle.step.B1;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class StepB1TransferGzipFiles extends BaseStep {

    public StepB1TransferGzipFiles(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start){
            return false;
        }
        StepB1TranferFile stepB1TranferFile = (StepB1TranferFile) getStep();
        StepB1DeleteUntarFiles stepB1DeleteUntarFiles = (StepB1DeleteUntarFiles) stepB1TranferFile.getStep();
        StepB1PreHande step = (StepB1PreHande) stepB1DeleteUntarFiles.getStep();
        StepB1UnTar stepB1UnTar = (StepB1UnTar) step.getStep();
        String distPath = PieOrthorParameterHelper.PROPERTIES_MAP.get("DIST_PATH");
        File file = stepB1UnTar.getFile();
        boolean renameTo = renameTo(file, distPath);
        SuperLogger.e("转移压缩文件完成");
        if (renameTo){
            return true;
        }
        saveLog("转移压缩文件失败");
        return false;
    }
}
