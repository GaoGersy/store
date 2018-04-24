package com.piesat.project;

import com.piesat.project.common.utils.PieOrthorParameterHelper;
import com.piesat.project.datahandle.factory.DataStoreFactory;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StoreHandle {
    public StoreHandle(){
        try {
            DataStoreFactory.initPool();
            PieOrthorParameterHelper.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
