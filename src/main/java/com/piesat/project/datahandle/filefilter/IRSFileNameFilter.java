package com.piesat.project.datahandle.filefilter;

public class IRSFileNameFilter implements FileNameFilter {
    @Override
    public boolean isFilter(String fileName) {
        if (fileName.contains("IRS")&&fileName.endsWith(".tiff")){
            return true;
        }
        return false;
    }
}
