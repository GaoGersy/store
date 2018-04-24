package com.piesat.project.datahandle.step.IRS;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class StepIRSTranferFile extends BaseStep {

    public StepIRSTranferFile(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start){
            return false;
        }
        String projectPath = PieOrthorParameterHelper.PROPERTIES_MAP.get("PROJECT_PATH")+"IRS";
        String distPath = PieOrthorParameterHelper.PROPERTIES_MAP.get("OUTPUT_PATH");
        String sourcePath = projectPath+"\\DOM\\mult";
        File file = new File(sourcePath);
        if (file.exists()){
            File[] files = file.listFiles();
            if (files.length<=0){
                SuperLogger.e("预处理失败，没有找到任何预处理后的文件");
                saveLog("预处理失败，没有找到任何预处理后的文件");
                return false;
            }
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                boolean success = renameTo(file1, distPath);
                if (!success){
                    SuperLogger.e("转移文件失败");
                    saveLog("转移预处理后的文件失败");
                    return false;
                }
            }
        }
        SuperLogger.e("转移文件完成");
        return true;
    }
}
