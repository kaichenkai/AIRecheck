package com.seemmo.airecheck.utils;

import com.seemmo.airecheck.constant.BaseConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 文件操作工具类
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {
    private static Logger logger = LoggerFactory.getLogger(com.seemmo.airecheck.utils.FileUtil.class);

    public static File[] ls(String path) {
        return cn.hutool.core.io.FileUtil.ls(path);
    }

    /**
     * 创建目录
     *
     * @param destDirName 目标目录名
     * @return 目录创建成功返回true，否则返回false
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            File myDelFile = new File(filePathAndName);
            myDelFile.delete();

        } catch (Exception e) {
            logger.error("删除{}文件操作出错",filePathAndName);

        }

    }

    /**
     * 读取到字节数组0
     *
     * @param filePath //路径
     * @throws IOException
     */
    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }

    /**
     * 读取到字节数组1
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filePath) throws IOException {

        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * 读取到字节数组2
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) throws IOException {

        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException(filePath);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray3(String filePath) throws IOException {

        FileChannel fc = null;
        RandomAccessFile rf = null;
        try {
            rf = new RandomAccessFile(filePath, "r");
            fc = rf.getChannel();
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
                    fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                rf.close();
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.write(new File(fileName), content, "utf-8", append);
            if (logger.isDebugEnabled()) {
                logger.debug("文件 " + fileName + " 写入成功!");
            }
        } catch (IOException e) {
            logger.error("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     */
    public static void writeByteArrayToFile(String fileName, String content, boolean append) {
        try {
            FileUtils.writeByteArrayToFile(new File(fileName), content.getBytes(), append);
            if (logger.isDebugEnabled()) {
                logger.debug("文件 " + fileName + " 写入成功!");
            }
        } catch (IOException e) {
            logger.error("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }


    /**
     * 获取文件扩展名(返回小写)
     *
     * @param fileName 文件名
     * @return 例如：test.jpg  返回：  jpg
     */
    public static String getFileExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(BaseConstant.SPOT) == BaseConstant.CONST_1)
                || (fileName.lastIndexOf(BaseConstant.SPOT) == fileName.length() - BaseConstant.CONST1)) {
            return null;
        }
        return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(BaseConstant.SPOT) + BaseConstant.CONST1));
    }


    /**
     * @param folderPath 文件路径 (只删除此路径的最末路径下所有文件和文件夹)
     */
    public static void delFolder(String folderPath) {
        try {
            FileUtils.deleteDirectory(new File(folderPath));
        } catch (IOException e) {
            logger.error(folderPath + " 文件删除失败!msg=" + e.getMessage());
        }
    }

    public static File mkdir(String distDir) {
        return cn.hutool.core.io.FileUtil.mkdir(distDir);
    }

    public static void move(File f, File file, boolean b) {
        cn.hutool.core.io.FileUtil.move(f, file, b);
    }

    public static void writeNioBytesToFile(String filePath, byte[] data) {
        //映射文件输出必须用RandomAccessFile
        RandomAccessFile os = null;
        FileChannel fo = null;
        try {
            os = new RandomAccessFile(filePath, "rw");
            fo = os.getChannel();
            ByteBuffer oIb = fo.map(MapMode.READ_WRITE, BaseConstant.CONST0, data.length);
            oIb.put(data);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            try {
                Thread.sleep(BaseConstant.CONST100);
                writeNioBytesToFile(filePath, data);
            } catch (InterruptedException e1) {
                logger.error(e.getMessage(), e);
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fo != null) {
                    fo.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static byte[] readNioFileToBytes(String filePath) {
        FileInputStream is = null;
        FileChannel fi = null;
        try {
            long startTime = System.currentTimeMillis();
            is = new FileInputStream(filePath);
            fi = is.getChannel();
            ByteBuffer iIb = fi.map(MapMode.READ_ONLY, BaseConstant.CONST0, fi.size());
            return iIb.array();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fi != null) {
                    fi.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static Future<Integer> writeAioBytestoFile(String filePath, byte[] bytes) {
        AsynchronousFileChannel channel;
        try {
            channel = AsynchronousFileChannel.open(Paths.get(filePath), StandardOpenOption.WRITE);
            return channel.write(ByteBuffer.wrap(bytes), BaseConstant.CONST0);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static boolean checkFileExist(String filePath) {

        if (StringUtil.isBlank(filePath)) {
            return false;
        }
        File file = new File(filePath.trim());
        return file.isFile() && file.exists();
    }

    public static long fileSizeWithMB(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            return file.length() / BaseConstant.CONST1024 / BaseConstant.CONST1024;
        }
        return BaseConstant.CONST0;
    }

    public static List<File> findFileByDir(String dir){
        return doFileFile(new File(dir));
    }

    private static List<File> doFileFile(File dir){
        List<File> result=new ArrayList<File>();
        if(dir.isDirectory()){
            File[] fs=dir.listFiles();
            if(!Arrays.isNullOrEmpty(fs)){
                for(int i=0;i<fs.length;i++){
                    if(fs[i].isDirectory()){
                        result.addAll(doFileFile(fs[i]));
                    }else {
                        result.add(fs[i]);
                    }
                }
            }
            return result;
        }else{
            result.add(dir);
            return result;
        }
    }

}