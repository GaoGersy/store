package com.piesat.project.datahandle;

import java.util.ArrayList;
import java.util.List;

public class StoreResult {
    private int successCout;// 成功的个数

    private int failedCout;// 成功的个数

    private List<Object> datas;

    public int getSuccessCout() {
        return successCout;
    }

    public void setSuccessCout(int successCout) {
        this.successCout = successCout;
    }

    public int getFailedCout() {
        return failedCout;
    }

    public void setFailedCout(int failedCout) {
        this.failedCout = failedCout;
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    public void add(Object obj) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(obj);
    }

    public void plusSuccessCout(int i) {
        successCout += i;
    }

    public void plusFailedCout(int i) {
        failedCout += i;
    }

    @Override
    public String toString() {
        return "StoreResult{" +
                "successCout=" + successCout +
                ", failedCout=" + failedCout +
                ", datas=" + datas +
                '}';
    }
}
