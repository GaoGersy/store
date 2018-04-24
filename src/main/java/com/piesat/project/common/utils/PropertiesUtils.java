package com.piesat.project.common.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    public static void getProperties(Map<String,String> map, String propertiesName) throws IOException {
        Resource resource = new ClassPathResource(propertiesName);
        File file = resource.getFile();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            Properties props = new Properties();
            props.load(fis);
            for (String name : props.stringPropertyNames()) {
                map.put(name, props.getProperty(name));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("resource文件夹中没有发现"+propertiesName);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
