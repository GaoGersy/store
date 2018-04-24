package com.piesat.project.datahandle.step;

import com.piesat.project.common.aop.exception.annotation.HandleException;
import com.piesat.project.common.config.ApplicationContextRegister;
import com.piesat.project.model.SysLog;
import com.piesat.project.service.system.SysLogService;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public abstract class BaseStep implements IStep {
    private IStep mStep;
    protected SysLogService mSysLogService;

    public BaseStep(IStep step) {
        mStep = step;
        mSysLogService = ApplicationContextRegister.getBean(SysLogService.class);
    }

    @Override
    @HandleException
    public boolean start() {
        if (mStep!=null) {
            boolean success = mStep.start();
            if (!success){
                return false;
            }
        }
        return true;
    }

    public void saveLog(String content){
        SysLog sysLog = new SysLog();
        sysLog.setContent(content);
        sysLog.setCreateTime(new Date());
        mSysLogService.insert(sysLog);
    }

    public IStep getStep() {
        return mStep;
    }

    public void setStep(IStep step) {
        mStep = step;
    }

    protected boolean transferTo(String sourcePath, String distPath) {
        try {
            File file = new File(sourcePath);
            FileUtils.copyFile(file, new File(distPath));
            return file.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean transferTo(File sourceFile, String distPath) {
        try {
            FileUtils.copyFile(sourceFile, new File(distPath));
            return sourceFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean renameTo(String sourcePath, String distPath) {
        File file = new File(sourcePath);
        return renameTo(file, distPath);
    }

    protected boolean renameTo(File file, String distPath) {
        File folder = new File(distPath);
        if (!folder.exists()){
            folder.mkdirs();
        }
        File destFile = new File(folder,file.getName());
        if (destFile.exists()){
            saveLog("已经存在了同名文件，删除旧文件");
            destFile.delete();
        }
        return file.renameTo(destFile);
    }

    protected boolean rename(String sourcePath, String newName) {
        File file = new File(sourcePath);
        return rename(file, newName);
    }

    protected boolean rename(File file, String newName) {
        String name = file.getName();
        int index = name.lastIndexOf(".");
        String lastName = name.substring(index);
        String distPath = file.getParent() + File.separator + newName + lastName;
        return file.renameTo(new File(distPath));
    }
}
