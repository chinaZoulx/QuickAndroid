package org.chris.quick.tools.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/5/10.
 */
public class FileUtils {

    private static FileUtils instance;

    public static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    /**
     * 写入文件
     *
     * @param inputStream
     * @param filePathDir   文件路径
     * @param fileName      文件名
     * @param isRewriteFile 是否覆盖
     * @return
     */
    public boolean writeFile(InputStream inputStream, String filePathDir, String fileName, boolean isRewriteFile) {
        if (inputStream != null && filePathDir != null && fileName != null) {
            try {
                File e = new File(filePathDir);
                if (!e.exists()) {
                    e.mkdirs();
                }

                String filePath = filePathDir + File.separatorChar + fileName;
                File file = new File(filePath);
                FileOutputStream fileOutputStream;
                byte[] buffer;
                boolean count;
                int count1;
                if (file.exists() && file.isFile()) {
                    if (!isRewriteFile) {
                        inputStream.close();
                        return false;
                    } else {
                        file.delete();
                        fileOutputStream = new FileOutputStream(filePath);
                        buffer = new byte[1024];
                        count = false;

                        while ((count1 = inputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count1);
                        }

                        fileOutputStream.close();
                        inputStream.close();
                        return true;
                    }
                } else {
                    fileOutputStream = new FileOutputStream(filePath);
                    buffer = new byte[1024];
                    count = false;

                    while ((count1 = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count1);
                    }

                    fileOutputStream.close();
                    inputStream.close();
                    return true;
                }
            } catch (Exception var11) {
                var11.printStackTrace();
                return false;
            }
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 从流读取文件
     *
     * @param ins
     * @param file
     * @return
     * @throws IOException
     */
    public File inputstreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }
}
