package com.piesat.project.datahandle.filefilter;

public class B1FileNameFilter implements FileNameFilter {
    @Override
    public boolean isFilter(String fileName) {
        if (fileName.contains("B1")&&fileName.endsWith(".tiff")){
            return true;
        }
        return false;
    }
}
