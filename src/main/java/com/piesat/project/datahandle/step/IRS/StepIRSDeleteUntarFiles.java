package com.piesat.project.datahandle.step.IRS;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class StepIRSDeleteUntarFiles extends BaseStep {

    public StepIRSDeleteUntarFiles(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start){
            return false;
        }
        StepIRSPreHande step = (StepIRSPreHande) getStep();
        StepIRSUnTar stepIRSUnTar = (StepIRSUnTar) step.getStep();
        String tiffPath = stepIRSUnTar.getTiffPath();
        File file = new File(tiffPath);
        String parent = file.getParent();
        File folder = new File(parent);
        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                file1.delete();
            }
        }else {
            SuperLogger.e("没有找到任何解压出来的文件");
            saveLog("没有找到任何解压出来的文件");
            return false;
        }
        boolean delete = folder.delete();
        SuperLogger.e("删除解压文件完成");
        if (delete){
            return true;
        }
        return false;
    }
}
