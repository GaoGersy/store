package com.piesat.project.datahandle.factory;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.handle.IDataStoreHandler;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class DataStoreFactory{

    private DataStoreFactory(){}
    private static Map<String, IDataStoreHandler> handlerPool = new HashMap<>();
    
    private static final String PACKAGE_NAME="com.piesat.project.datahandle.handle.";

    public static int getFileTypeSize(){
        return handlerPool.size();
    }

    private static IDataStoreHandler createDataStoreHandler(String clazzName) {

        if (!clazzName.startsWith("com")){
            clazzName = PACKAGE_NAME +clazzName;
        }
        Class<?> clazz = null;
        IDataStoreHandler dataStoreHandler = null;
        try {
            clazz = Class.forName(clazzName);
            dataStoreHandler = (IDataStoreHandler) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            SuperLogger.e("class 不存在");
            e.printStackTrace();
        }catch (InstantiationException e) {
            SuperLogger.e(e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            SuperLogger.e(e.getMessage());
            e.printStackTrace();
        }
        return dataStoreHandler;
    }

    public static void initPool() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Resource resource = new ClassPathResource("config/datahandler.properties");
        File file = resource.getFile();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Properties props = new Properties();
            props.load(fis);
            for (String name : props.stringPropertyNames()) {
                handlerPool.put(name, createDataStoreHandler(props.getProperty(name)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("resource文件夹中没有发现datahandler.properties");
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public static IDataStoreHandler get(String typeName){
        IDataStoreHandler dataStoreHandler = handlerPool.get(typeName);
//        if (dataStoreHandler ==null){
//            dataStoreHandler = createDataStoreHandler(clazzName);
//            handlerPool.put(clazzName,dataStoreHandler);
//            SuperLogger.e("还没有"+clazzName+"的对象，现在创建一个");
//        }
        return dataStoreHandler;
    }
}
