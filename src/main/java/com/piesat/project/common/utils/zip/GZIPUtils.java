package com.piesat.project.common.utils.zip;

import com.piesat.project.datahandle.filefilter.FileNameFilter;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GZIPUtils {
    public static String unCompressTar(String finalName, String distPath, FileNameFilter fileFilter) {

        File file = new File(finalName);
        TarArchiveInputStream tais = null;
        List<BufferedOutputStream> list = new ArrayList<>();
        String folderName = finalName.substring(finalName.lastIndexOf(File.separator) + 1, finalName.lastIndexOf("."));
        String filePath = null;
        try {
            tais = new TarArchiveInputStream(new FileInputStream(file));
            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                String parent = distPath + File.separator + folderName;
                File tarFile = new File(parent, name);
                if (fileFilter != null && fileFilter.isFilter(name)) {
                    filePath = tarFile.getAbsolutePath();
                }
                if (!tarFile.getParentFile().exists()) {
                    tarFile.getParentFile().mkdirs();
                }

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));

                int read = -1;
                byte[] buffer = new byte[1024];
                while ((read = tais.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.close();
                list.add(bos);
            }
            tais.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                for (int i = 0; i < list.size(); i++) {
                    BufferedOutputStream bos = list.get(i);
                    if (bos != null) {
                        bos.close();
                    }
                }
                if (tais != null) {
                    tais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file.delete();//删除tar文件
        return filePath;
    }

    public static String unCompressArchiveGz(File file, String distPath, FileNameFilter fileFilter) {
        BufferedInputStream bis = null;
        GzipCompressorInputStream gcis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            String fileName =
                    file.getName().substring(0, file.getName().lastIndexOf("."));

            String finalName = file.getParent() + File.separator + fileName;
            bos = new BufferedOutputStream(new FileOutputStream(finalName));

            gcis = new GzipCompressorInputStream(bis);

            byte[] buffer = new byte[1024];
            int read = -1;
            while ((read = gcis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bis.close();
            bos.close();
            gcis.close();
            return unCompressTar(finalName, distPath, fileFilter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (gcis != null) {
                    gcis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
