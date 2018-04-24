package com.piesat.project.datahandle.handle;

import com.piesat.project.common.utils.SuperLogger;
import com.piesat.project.datahandle.StoreResult;
import com.piesat.project.datahandle.factory.DataStoreFactory;
import com.piesat.project.datahandle.filefilter.GZIPFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class BaseMultiDataTypeStoreHandler implements IMultiDataTypeStoreHandler {

    private static ExecutorService mExecutor;
    private final ConcurrentHashMap<String, TestThread> runnables = new ConcurrentHashMap<>();

    public BaseMultiDataTypeStoreHandler() {
        mExecutor = new ThreadPoolExecutor(1,
                1,
                30,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(1)
        );
    }

    @Override
    public StoreResult onStore(String filePath) {
        List<File> files = scanFiles(null,filePath);
        for (File file : files) {
            add(file);
        }

        Set<Map.Entry<String, TestThread>> entries = runnables.entrySet();
        Iterator<Map.Entry<String, TestThread>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, TestThread> next = iterator.next();
            mExecutor.execute(next.getValue());
        }
        return null;
    }

    public void add(File file) {
        String fileTypeName = getFileTypeName(file);
        TestThread runnable = getRunnable(fileTypeName);
        runnable.onRun(new WorkThread(fileTypeName, file));
    }

    private TestThread getRunnable(String fileTypeName) {
        TestThread runnable = runnables.get(fileTypeName);
        if (runnable == null) {
            TestThread testThread = new TestThread();
            runnables.put(fileTypeName, testThread);
            return testThread;
        }
        return runnable;
    }

    public static class WorkThread implements Callable {
        private final IDataStoreHandler mDataStoreHandler;
        private File mFile;

        public WorkThread(String fileTypeName, File file) {
            mDataStoreHandler = DataStoreFactory.get(fileTypeName);
            mFile = file;
        }

        @Override
        public Object call() throws Exception {
            StoreResult storeResult = mDataStoreHandler.onStore(mFile);
            SuperLogger.e(storeResult);
            return storeResult;
        }
    }

    public static class TestThread implements Runnable {
        private final ExecutorService executor;
        private List<Callable<StoreResult>> mRunnables = new ArrayList<>();

        public TestThread() {
            executor = new ThreadPoolExecutor(1,
                    1,
                    0,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<Runnable>());

        }

        @Override
        public void run() {
            try {
                List<Future<StoreResult>> futures = executor.invokeAll(mRunnables);
                for (int i = 0; i < futures.size(); i++) {
                    Future<StoreResult> storeResultFuture = futures.get(i);
                    try {
                        StoreResult storeResult = storeResultFuture.get();
                        SuperLogger.e(storeResult);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void onRun(Callable<StoreResult> runnable) {
            mRunnables.add(runnable);
        }
    }

    private List<File> scanFiles(List<File> fileList, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            SuperLogger.e("文件不存在");
            return null;
        }
        GZIPFilter filter = new GZIPFilter();
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    scanFiles(fileList,files[i].getAbsolutePath());
                } else {
                    File file1 = files[i];
                    if (filter.isFilter(file1.getName())) {
                        fileList.add(file1);
                    }
                }
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }

    public abstract String getFileTypeName(File file);

}
