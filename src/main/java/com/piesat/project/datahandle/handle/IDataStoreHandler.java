package com.piesat.project.datahandle.handle;

import com.piesat.project.datahandle.StoreResult;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 将解析后的数据存入数据库
 */
public interface IDataStoreHandler {

    StoreResult onStore(List<File> fileList);

    StoreResult onStore(String filePath);

    StoreResult onStore(File file);

    StoreResult onStore(MultipartFile multipartFile,String distPath);

}
