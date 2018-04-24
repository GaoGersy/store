package com.piesat.project.datahandle.step.B1;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.IOException;

public class StepB1PreHande extends BaseStep {

    public StepB1PreHande(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start){
           return false;
        }
        StepB1UnTar step = (StepB1UnTar) getStep();
        String tiffPath = step.getTiffPath();
        String createProject=PieOrthorParameterHelper.getB1ProjectParameter(tiffPath);
        String preHandle=PieOrthorParameterHelper.getB1HandleParameter(tiffPath);
        try {
            Runtime.getRuntime().exec(createProject).waitFor();
            Runtime.getRuntime().exec(preHandle).waitFor();
            SuperLogger.e("预处理完成");
            return true;
        } catch (InterruptedException e) {
            saveLog("预处理失败");
            e.printStackTrace();
        } catch (IOException e) {
            saveLog("预处理失败");
            e.printStackTrace();
        }
        return false;
    }
}
