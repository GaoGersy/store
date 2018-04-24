package com.piesat.project.datahandle.step.IRS;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.common.utils.zip.GZIPUtils;
import com.piesat.project.datahandle.filefilter.IRSFileNameFilter;
import com.piesat.project.datahandle.step.BaseStep;
import com.piesat.project.datahandle.step.IStep;

import java.io.File;

public class StepIRSUnTar extends BaseStep {
    private File mFile;
    private String mTiffPath;

    public StepIRSUnTar(IStep step) {
        super(step);
    }

    @Override
    public boolean start() {
        boolean start = super.start();
        if (!start){
            return false;
        }
        String untarPath = PieOrthorParameterHelper.PROPERTIES_MAP.get("UNTAR_PATH");
        mTiffPath = GZIPUtils.unCompressArchiveGz(mFile, untarPath, new IRSFileNameFilter());
        if (mTiffPath==null){
            SuperLogger.e("没有找到IRS文件");
            saveLog("没有找到IRS文件");
            return false;
        }
        SuperLogger.e("解压完成");
        return true;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        mFile = file;
    }

    public String getTiffPath() {
        return mTiffPath;
    }

    public void setTiffPath(String tiffPath) {
        mTiffPath = tiffPath;
    }
}
