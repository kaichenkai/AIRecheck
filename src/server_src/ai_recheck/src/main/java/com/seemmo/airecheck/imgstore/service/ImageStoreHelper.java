//package com.seemmo.airecheck.imgstore.service;
//
//import cn.hutool.core.util.HashUtil;
//import com.seemmo.aitraffic.constant.BaseConstant;
//import com.seemmo.aitraffic.datagate.entity.RecogResponseCarRect;
//import com.seemmo.aitraffic.imgstore.entity.ImageSourceInfo;
//import com.seemmo.aitraffic.utils.DateUtils;
//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.Thumbnails.Builder;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * @author maoying
// * @date 2018/5/29 0029.
// */
//public class ImageStoreHelper {
//    private static Logger logger = LoggerFactory.getLogger(ImageStoreHelper.class);
//    private static final String FILEDIR_FORMAT = "yyyyMMdd/HH/";
//    private static final int FIRST_MAX_RANDOM = 300;
//    private static final long MAX_TIME_STAMP = 1 << 17;
//    private static final long MAX_INCREATE = 1 << 18;
//    private static final long MAX_INDEX = 1 << 20;
//    private static final long TIME_HOUR = 1000 * 60 * 60;
//    private static final long MAX_IMAGENUM_SINGLEDIR = 1024;
//    private static final String DEFAULT_TYPE = "aitraffic";
//    private static final String ID_SEPERATOR = "_";
//    private static long BASE_TIME_STAMP = 1514736000000L;
//    private static Random random = new Random(System.currentTimeMillis());
//    private volatile static AtomicLong recordNumber;
//    private volatile static AtomicLong imageNumber = new AtomicLong();
//    private volatile static String timeInteval = null;
//
//    static {
//        int firstRecord = random.nextInt(FIRST_MAX_RANDOM);
//        recordNumber = new AtomicLong(firstRecord);
//        timeInteval = DateUtils.formatTimeToStr(System.currentTimeMillis(), FILEDIR_FORMAT);
//    }
//
//
//    /**
//     * 格式化Id数据
//     * |-符号位-|---------时间戳（小时17）----------|---------自动增长值（18）----------|----图片类别(7)----|----索引编号(10)----|-机器码（4）-|-随机数（7）-|
//     *
//     * @param imageId id值
//     * @return
//     */
//    public static IdInfo parseId(String imageId) {
//        if (StringUtils.isBlank(imageId) || imageId.lastIndexOf(ID_SEPERATOR) <= 0) {
//            return null;
//        }
//        String type = imageId.substring(0, imageId.lastIndexOf(ID_SEPERATOR));
//        long id = Long.parseLong(imageId.substring(imageId.lastIndexOf(ID_SEPERATOR) + 1, imageId.length()));
//        long timeStamp = ((id >> 46) & 0x1ffff);
//        timeStamp = timeStamp * TIME_HOUR + BASE_TIME_STAMP;
//        long increateNumber = ((id >> 28) & 0x3ffff);
//        long imageIndex = ((id >> 11) & 0x3ff) * MAX_IMAGENUM_SINGLEDIR;
//        long hostCode = (id >> 7) & 0xf;
//        long randNum = (id) & 0x7f;
//        return new IdInfo().setHost(hostCode).setNumber(increateNumber).setTimeStamp(timeStamp).setRand(randNum).setType(type).setImageIndex(imageIndex);
//    }
//
//    /**
//     * 获取自动增长值
//     *
//     * @return
//     */
//    public static long getiIncreateNumber() {
//        return recordNumber.getAndIncrement();
//    }
//
//    public static IdInfo generateIdInfo(String type, int hostCode) {
//        if (StringUtils.isEmpty(type)) {
//            type = DEFAULT_TYPE;
//        }
//        long now = System.currentTimeMillis();
//        IdInfo idInfo = new IdInfo();
//        long timeStamp = ((now - BASE_TIME_STAMP) / TIME_HOUR) % (MAX_TIME_STAMP);
//        timeStamp = timeStamp * TIME_HOUR + BASE_TIME_STAMP;
//        String dateTime = DateUtils.formatTimeToStr(timeStamp, FILEDIR_FORMAT);
//        idInfo.setHost(hostCode).setTimeStamp(timeStamp).setType(type).setImageIndex(getImageIndex(dateTime) % MAX_INDEX / MAX_IMAGENUM_SINGLEDIR);
//        idInfo.setNumber(getiIncreateNumber()).setRand(random.nextInt(0x40));
//        return idInfo;
//    }
//
//    private static long getImageIndex(String dateTime) {
//        if (!timeInteval.equals(dateTime)) {
//            synchronized (timeInteval) {
//                if (!timeInteval.equals(dateTime)) {
//                    timeInteval = dateTime;
//                    imageNumber.set(random.nextInt(FIRST_MAX_RANDOM));
//                }
//                return imageNumber.incrementAndGet();
//            }
//        }
//        return imageNumber.incrementAndGet();
//    }
//
//    /**
//     * 生成Id
//     * |-1(sign)-|-----------------------17(timestamp base on 2018:01:01 00:00:00) |------18(revert of increate number)---------------------|
//     * |------21(offset of file)---------------------|--------4(host code)-----|--------3(rand)-----|
//     *
//     * @param info 信息
//     * @return
//     */
//    public static String generateId(IdInfo info) {
//        return generateId(info.getType(), info.getImageIndex(), info.getNumber(), info.getTimeStamp(), info.getHost(), info.getRand());
//    }
//
//    public static String generateId(IdInfo info, String type) {
//        return generateId(type, info.getImageIndex(), info.getNumber(), info.getTimeStamp(), info.getHost(), info.getRand());
//    }
//
//    /**
//     * 生成对象Id
//     *
//     * @param imageIndex     在文件中偏移量
//     * @param increateNumber 自动增涨数
//     * @param intervalTime   数据时间
//     * @param hostCode       主机代码
//     * @param randNum        随机数
//     * @return
//     */
//    public static String generateId(String type, long imageIndex, long increateNumber, long intervalTime, long hostCode, long randNum) {
//        increateNumber = increateNumber % (MAX_INCREATE);
//        imageIndex = imageIndex % (MAX_INDEX) / MAX_IMAGENUM_SINGLEDIR;
//        long _type = HashUtil.javaDefaultHash(type);
//        long intervalTimeStamp = ((intervalTime - BASE_TIME_STAMP) / TIME_HOUR) % (MAX_TIME_STAMP);
//        long id = ((intervalTimeStamp & 0x1ffff) << 46) | ((increateNumber & 0x3ffff) << 28) |
//                ((_type & 0x7f) << 21) | ((imageIndex & 0x3ff) << 11) | ((hostCode & 0xf) << 7) | ((randNum & 0x7f));
//        return type + ID_SEPERATOR + id;
//    }
//
//    /**
//     * 根据自动增长及随机数生成文件名称  increateNumber(18)|randNum(3)
//     *
//     * @param imageIndex
//     * @param randNum
//     * @return
//     */
//    public static int generateFileName(long imageIndex, long randNum) {
//        int imageIndexInt = (int) ((imageIndex) % (MAX_INDEX));
//        int fileName = ((imageIndexInt & 0x3ffff) << 7) | ((int) randNum & 0x7f);
//        return fileName;
//    }
//
//
//    public static String getLocalFilePath(String filePath) {
//        return filePath.replace('/', '_').replace(File.separator, "_");
//    }
//
//    //byte 数组与 long 的相互转换
//    public static byte[] longToBytes(long x) {
//        ByteBuffer buffer = ByteBuffer.allocate(8);
//        buffer.putLong(0, x);
//        byte[] data = buffer.array();
//        buffer.clear();
//        return data;
//    }
//
//    public static long bytesToLong(byte[] bytes) {
//        ByteBuffer buffer = ByteBuffer.allocate(BaseConstant.CONST8);
//        buffer.put(bytes, BaseConstant.CONST0, bytes.length);
//        buffer.flip();//need flip
//        long data = buffer.getLong();
//        buffer.clear();
//        return data;
//    }
//
//    public static byte[] LongToBytes(long values) {
//        byte[] buffer = new byte[BaseConstant.CONST8];
//        for (int i = BaseConstant.CONST0; i < BaseConstant.CONST8; i++) {
//            int offset = BaseConstant.CONST64 - (i + BaseConstant.CONST1) * BaseConstant.CONST8;
//            buffer[i] = (byte) ((values >> offset) & 0xff);
//        }
//        return buffer;
//    }
//
//    public static long BytesToLong(byte[] buffer) {
//        long values = BaseConstant.CONST0;
//        for (int i = BaseConstant.CONST0; i < BaseConstant.CONST8; i++) {
//            values <<= BaseConstant.CONST8;
//            values |= (buffer[i] & 0xff);
//        }
//        return values;
//    }
//
//    public static String genTempPath(int type, String tempDir) {
//        return tempDir + File.separator + type + "_" + System.currentTimeMillis() + "_" + random.nextInt(0x40);
//    }
//
//
//    public static class IdInfo {
//        private static final String FILEDATE_FORMAT = "yyyyMMdd/HH/";
//        private long rand;
//        private long timeStamp;
//        private long host;
//        private long number;
//        private String type;
//        private long imageIndex;
//
//        public String getType() {
//            return type;
//        }
//
//        public IdInfo setType(String type) {
//            this.type = type;
//            return this;
//        }
//
//        public long getImageIndex() {
//            return imageIndex;
//        }
//
//        public IdInfo setImageIndex(long imageIndex) {
//            this.imageIndex = imageIndex;
//            return this;
//        }
//
//        public long getRand() {
//            return rand;
//        }
//
//        public IdInfo setRand(long rand) {
//            this.rand = rand;
//            return this;
//        }
//
//        public long getTimeStamp() {
//            return timeStamp;
//        }
//
//        public IdInfo setTimeStamp(long timeStamp) {
//            this.timeStamp = timeStamp;
//            return this;
//        }
//
//        public long getHost() {
//            return host;
//        }
//
//        public IdInfo setHost(long host) {
//            this.host = host;
//            return this;
//        }
//
//        public long getNumber() {
//            return number;
//
//        }
//
//        public IdInfo setNumber(long number) {
//            this.number = number;
//            return this;
//        }
//
//        /**
//         * 生成文件路径
//         *
//         * @return host/yyyyMM/hh/(increate(18位)|rand(3位))
//         */
//        public String generateFilePath() {
//            StringBuilder sb = new StringBuilder();
//            String hexStr = "00" + Long.toHexString(this.imageIndex % MAX_INDEX / MAX_IMAGENUM_SINGLEDIR);
//            sb.append(this.host).append(File.separator).append(this.type).append(File.separator)
//                    .append(DateUtils.formatTimeToStr(this.timeStamp, FILEDATE_FORMAT))
//                    .append(hexStr.substring(hexStr.length() - 2)).append(File.separator);
//            int fileName = ImageStoreHelper.generateFileName(this.number, rand);
//            sb.append(fileName);
//            return sb.toString();
//        }
//
//
//        @Override
//        public String toString() {
//            return "IdInfo{" +
//                    "rand=" + rand +
//                    ", timeStamp=" + timeStamp +
//                    ", host=" + host +
//                    ", number=" + number +
//                    ", type=" + type +
//                    ", imageIndex=" + imageIndex +
//                    '}';
//        }
//    }
//
//
//    public static String genStorePath(int hostCode, String type, String day, String hour) {
//        String path = "";
//        if (hostCode >= 0) {
//            path += hostCode;
//        }
//        if (StringUtils.isNotEmpty(type)) {
//            path += type;
//        }
//        if (StringUtils.isNotEmpty(day)) {
//            path += day;
//        }
//        if (StringUtils.isNotEmpty(hour)) {
//            path += hour;
//        }
//        return path;
//    }
//
//    public static ImageSourceInfo splitImage(byte[] imageData, int rows, int cols) throws IOException {
//        ImageSourceInfo imageInfo = new ImageSourceInfo();
//        if (imageData == null) {
//            return imageInfo.setImageBytes(new ArrayList<byte[]>());
//        }
//        ImageReader imageReader = ImageIO.getImageReaders(ImageIO.createImageInputStream(new ByteArrayInputStream(imageData))).next();
//        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
//        imageInfo.setSourceHeight(image.getWidth()).setSourceWidth(image.getHeight());
//        int chunks = rows * cols;
//        int chunkWidth = image.getWidth() / cols;
//        int chunkHeight = image.getHeight() / rows;
//        int count = 0;
//        byte[][] imgs = new byte[chunks][];
//        Builder<BufferedImage> builder = Thumbnails.of(image);
//        for (int x = 0; x < rows; x++) {
//            for (int y = 0; y < cols; y++) {
//                ByteArrayOutputStream bout = new ByteArrayOutputStream();
//                BufferedImage img = new BufferedImage(chunkWidth, chunkHeight, image.getType());
//                //写入图像内容
//                Graphics2D gr = img.createGraphics();
//                gr.drawImage(image, 0, 0,
//                        chunkWidth, chunkHeight,
//                        chunkWidth * y, chunkHeight * x,
//                        chunkWidth * y + chunkWidth,
//                        chunkHeight * x + chunkHeight, null);
//                gr.dispose();
//                ImageIO.write(img, imageReader.getFormatName(), bout);
//                imgs[count++] = bout.toByteArray();
//                bout.flush();
//                bout.close();
//            }
//        }
//
//        return imageInfo.setImageBytes(Arrays.asList(imgs));
//    }
//
//
//    /**
//     * 从图片中截取一部分出来
//     *
//     * @param imageBytes 图片二进制
//     * @param x          图片左上角x坐标
//     * @param y          图片左上角y坐标
//     * @param w          截取的图片宽
//     * @param h          截取的图片高
//     * @return 截取出来的图片二进制
//     */
//    public static byte[] subImage(byte[] imageBytes, int x, int y, int w, int h) {
//
//        ImageReader imageReader = null;
//        try {
//            imageReader = ImageIO.getImageReaders(ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes))).next();
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
//
//            ByteArrayOutputStream bout = new ByteArrayOutputStream();
//            BufferedImage img = new BufferedImage(w, h, image.getType());
//            //写入图像内容
//            Graphics2D gr = img.createGraphics();
//            gr.drawImage(image, 0, 0,
//                    w, h,
//                    x, y, w + x, h + y, null);
//            gr.dispose();
//            ImageIO.write(img, imageReader.getFormatName(), bout);
//            bout.flush();
//            bout.close();
//
//            return bout.toByteArray();
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return null;
//    }
//
//
//    /**
//     * 可以用来表示 + 的切图方法
//     * 支持的切图方法为 1+2 (表示2行数据，上面1张，下面2张）, 1*2 , 1*4 , 2*2 ,2*3 之类
//     *
//     * @param imageData
//     * @param rows
//     * @param cols
//     * @param op
//     * @return
//     * @throws IOException
//     */
//    public static ImageSourceInfo splitImage(byte[] imageData, int rows, int cols, String op) throws IOException {
//        ImageSourceInfo imageInfo = new ImageSourceInfo();
//        if (imageData == null) {
//            return imageInfo.setImageBytes(new ArrayList<byte[]>());
//        }
//        switch (op) {
//            case BaseConstant.OP_ADD: {
//                // 比如 1+2 表示有3张图片，上一 下二（对分）
//                if (BaseConstant.PATTERN_1_ADD_2.equals(rows + op + cols)) {
//                    List<byte[]> imgs = new ArrayList<>(BaseConstant.CONST3);
//                    // 先当 2*1 进行切图
//                    List<byte[]> tmpimgs = splitImage(imageData, 2, 1, BaseConstant.OP_MUL_X).getImageBytes();
//                    if (tmpimgs.size() < 2) {
//                        if (logger.isDebugEnabled()) {
//                            logger.debug("图片切割张数不足");
//                        }
//                        break;
//                    }
//                    imgs.add(tmpimgs.get(0));
//                    // 先当 1*2 进行切图
//                    List<byte[]> tmpimgs2 = splitImage(tmpimgs.get(1), 1, 2, BaseConstant.OP_MUL_X).getImageBytes();
//                    if (tmpimgs2.size() < 2) {
//                        if (logger.isDebugEnabled()) {
//                            logger.debug("图片切割张数不足");
//                        }
//                        break;
//                    }
//                    imgs.addAll(tmpimgs2);
//                    return imageInfo.setImageBytes(imgs);
//                }
//                break;
//            }
//            case BaseConstant.OP_MUL:
//            case BaseConstant.OP_MUL_X: // *
//            {
//                return splitImage(imageData, rows, cols);
//            }
//            default:
//                break;
//        }
//        return imageInfo.setImageBytes(new ArrayList<>(BaseConstant.CONST0));
//    }
//
//    public static byte[] splitImage(byte[] img, int top, int bottom, int left, int right) {
//        BufferedImage image;
//        try {
//            image = ImageIO.read(new ByteArrayInputStream(img));
//        } catch (IOException e) {
//            return null;
//        }
//        int height = image.getHeight();
//        int width = image.getWidth();
//        image = image.getSubimage(left, top, width - right - left, height - bottom - top);
//
//        byte[] subImage = null;
//        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
//            ImageReader imageReader = ImageIO.getImageReaders(ImageIO.createImageInputStream(new ByteArrayInputStream(img))).next();
//            ImageIO.write(image, imageReader.getFormatName(), bout);
//            subImage = bout.toByteArray();
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return subImage;
//    }
//
//    /**
//     * 切割图片
//     * @param img  原图
//     * @param x  矩形框左上角坐标x
//     * @param y  矩形框左上角坐标y
//     * @param w  w为框的宽,
//     * @param h   h为框的高
//     * @return
//     */
//    public static byte[] splitSubImage(byte[] img, int x, int y, int w, int h) {
//        BufferedImage image;
//        try {
//            image = ImageIO.read(new ByteArrayInputStream(img));
//        } catch (IOException e) {
//            return null;
//        }
//        image = image.getSubimage(x, y, w, h);
//
//        byte[] subImage = null;
//        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
//            ImageReader imageReader = ImageIO.getImageReaders(ImageIO.createImageInputStream(new ByteArrayInputStream(img))).next();
//            ImageIO.write(image, imageReader.getFormatName(), bout);
//            subImage = bout.toByteArray();
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return subImage;
//    }
//
//    /**
//     * 获取图片宽高
//     *
//     * @param bytes 图片二进制
//     * @return 对象的w 和 h为图片的宽和高，其余属性都为null
//     */
//    public static RecogResponseCarRect getImageSize(byte[] bytes) {
//        RecogResponseCarRect rect = new RecogResponseCarRect();
//        try {
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
//            rect.setW(image.getWidth());
//            rect.setH(image.getHeight());
//        } catch (IOException e) {
//            logger.error("get image w and h fail", e);
//        }
//        return rect;
//    }
//}
