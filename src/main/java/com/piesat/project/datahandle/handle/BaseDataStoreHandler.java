package com.piesat.project.datahandle.handle;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.StoreResult;
import com.piesat.project.datahandle.step.IStep;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataStoreHandler implements IDataStoreHandler {

    @Override
    public StoreResult onStore(String filePath) {
        List<File> files = scanFiles(filePath);
        return onStore(files);
    }

    @Override
    public StoreResult onStore(List<File> fileList) {
        if (fileList == null) {
            SuperLogger.e("文件列表为空");
            return null;
        }
        int size = fileList.size();
        StoreResult storeResult = new StoreResult();
        for (int i = 0; i < size; i++) {
            getStoreResult(fileList.get(i), storeResult);
        }
        return storeResult;
    }

    @Override
    public StoreResult onStore(File file) {
        StoreResult storeResult = new StoreResult();
        getStoreResult(file, storeResult);
        return storeResult;
    }

    @Override
    public StoreResult onStore(MultipartFile multipartFile, String distPath) {
        StoreResult storeResult = null;
        try {
            String name = multipartFile.getName();
            File f = new File(distPath + name);
            multipartFile.transferTo(f);
            storeResult = onStore(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeResult;
    }

    private void getStoreResult(File file, StoreResult storeResult) {
        Object result = onHandle(file);
        if (result != null) {
            storeResult.add(result);
            storeResult.plusSuccessCout(1);
        } else {
            storeResult.plusFailedCout(1);
        }
    }

    private Object onHandle(File file) {
        IStep step = getStep(file);
        step.start();
        return null;
    }

    protected abstract IStep getStep(File file);

    private List<File> scanFiles(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            SuperLogger.e("文件不存在");
            return null;
        }
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    scanFiles(files[i].getAbsolutePath());
                } else {
                    fileList.add(files[i]);
                }
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }



}
