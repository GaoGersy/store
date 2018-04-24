package com.piesat.project.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PieOrthorParameterHelper {
    private static final int OUT_PROJECTION = 1;
    private static final int NEED_RPC = 0;
    private static final int IRS_GF_IMAGE_RESOLUTION = 400;
    private static final int IRS_MULTISPECTRAL_IMAGE_RESOLUTION = 400;
    private static final int B1_GF_IMAGE_RESOLUTION = 50;
    private static final int B1_MULTISPECTRAL_IMAGE_RESOLUTION = 50;
    private static final String FORMAT_STRING = ".tif";
    public static final Map<String,String> PROPERTIES_MAP = new HashMap<>();
    private static final String FILE_PATH = "config/gf.properties";
    public static void init() throws IOException {
        PropertiesUtils.getProperties(PROPERTIES_MAP,FILE_PATH);
        String output_path = PROPERTIES_MAP.get("OUTPUT_PATH");
        String untar_path = PROPERTIES_MAP.get("UNTAR_PATH");
        String dist_path = PROPERTIES_MAP.get("DIST_PATH");
        String source_path = PROPERTIES_MAP.get("SOURCE_PATH");
        createFolder(output_path);
        createFolder(untar_path);
        createFolder(dist_path);
        createFolder(source_path);
    }

    private static void createFolder(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static String getIRSProjectParameter(String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append(PROPERTIES_MAP.get("CREATEPROJEXE4_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("PROJECT_PATH")).append("IRS.prj").append("  ");
        sb.append("\"\"").append("  ");
        sb.append(filePath).append("  ");
        sb.append(PROPERTIES_MAP.get("REFERENCE_IMAGE_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("DEM_PATH")).append("  ");
        sb.append(IRS_GF_IMAGE_RESOLUTION).append("  ");
        sb.append(IRS_MULTISPECTRAL_IMAGE_RESOLUTION).append("  ");
        sb.append(OUT_PROJECTION);
        return sb.toString();
    }

    public static String getB1ProjectParameter(String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append(PROPERTIES_MAP.get("CREATEPROJEXE4_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("PROJECT_PATH")).append("B1.prj").append("  ");
        sb.append("\" \"").append("  ");
        sb.append(filePath).append("  ");
        sb.append(PROPERTIES_MAP.get("REFERENCE_IMAGE_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("DEM_PATH")).append("  ");
        sb.append(B1_GF_IMAGE_RESOLUTION).append("  ");
        sb.append(B1_MULTISPECTRAL_IMAGE_RESOLUTION).append("  ");
        sb.append(OUT_PROJECTION);
        return sb.toString();
    }

    public static String getB1HandleParameter(String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append(PROPERTIES_MAP.get("DOMGENERATE_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("PROJECT_PATH")).append("B1.prj").append("  ");
        sb.append(filePath).append("  ");
        sb.append(NEED_RPC).append("  ");
        sb.append(FORMAT_STRING).append("  ");
        sb.append("\"\"");
        return sb.toString();
    }

    public static String getIRSHandleParameter(String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append(PROPERTIES_MAP.get("DOMGENERATE_PATH")).append("  ");
        sb.append(PROPERTIES_MAP.get("PROJECT_PATH")).append("IRS.prj").append("  ");
        sb.append(filePath).append("  ");
        sb.append(NEED_RPC).append("  ");
        sb.append(FORMAT_STRING).append("  ");
        sb.append("\"\"");
        return sb.toString();
    }
}
