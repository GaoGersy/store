package com.piesat.project.datahandle.filefilter;

public class GZIPFilter implements FileNameFilter{
    @Override
    public boolean isFilter(String fileName) {
        if (fileName.endsWith("tar.gz")){
            return true;
        }
        return false;
    }
}
