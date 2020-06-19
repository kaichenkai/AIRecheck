//package com.seemmo.airecheck.imgstore.service;
//
//import com.seemmo.aitraffic.commons.model.IImageInfo;
//import com.seemmo.aitraffic.datagate.entity.RecogResponseCarRect;
//import com.seemmo.aitraffic.datagate.module.BusinessException;
//import com.seemmo.aitraffic.imgstore.entity.ImageAndPointEntity;
//import com.seemmo.aitraffic.imgstore.entity.ImageEntity;
//import com.seemmo.aitraffic.imgstore.entity.ImageSourceInfo;
//import com.seemmo.aitraffic.platform.model.TbDeviceMarkConfigPattern;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicReference;
//
//public interface ImageStore {
//    /**
//     * 启动时初始化方法
//     */
//    void init();
//
//    /**
//     * 计算切图模式
//     *
//     * @param rect
//     * @param pattern
//     * @param imageId
//     * @return
//     */
//    boolean calPattern(RecogResponseCarRect rect, TbDeviceMarkConfigPattern pattern, String imageId);
//
//    /**
//     * @param image 图片的临时文件
//     * @return 文件的相对路径/id
//     * @throws Exception
//     */
//    String store(File image, String type) throws Exception;
//
//    /**
//     * @param image 图片的临时文件
//     * @return 文件的相对路径/id
//     * @throws Exception
//     */
//    String store(File image, String type, boolean isDelete) throws Exception;
//
//    /**
//     * 将一张图片复制到另一个位置
//     *
//     * @param imageId 系统中的图片id
//     * @return 文件的相对路径/id
//     * @throws Exception
//     */
//    String store(String imageId, String type, boolean isDelete) throws Exception;
//
//    /**
//     * @param imageData
//     * @param type
//     * @param imageId
//     * @return
//     * @throws Exception
//     */
//    String update(byte[] imageData, String type, String imageId) throws Exception;
//
//    /**
//     * @param imageData 图片的二进制数据
//     * @return 文件的相对路径/id
//     * @throws Exception
//     */
//    String store(byte[] imageData, String type) throws Exception;
//
//    /**
//     * @param imageid
//     * @return 图片对象
//     * @throws ExecutionException
//     * @throws InterruptedException
//     */
//    ImageEntity getImage(String imageid) throws InterruptedException, ExecutionException;
//
//
//    /**
//     * @param imageid
//     * @return 图片对象
//     * @throws ExecutionException
//     * @throws InterruptedException
//     * @throws BusinessException
//     */
//    String getImageBase64(String imageid) throws BusinessException;
//
//    /**
//     * @param imageid
//     * @return 图片路径
//     */
//    String getFilePath(String imageid);
//
//    /**
//     * @param imageid
//     * @return 图片路径
//     */
//    boolean delete(String imageid);
//
//
//    void deleteImageAndPoint(List<String> imageIds);
//
//    /**
//     * @param type 图片类型
//     * @param day  yyyyMMdd
//     * @param hour HH
//     * @return
//     */
//    boolean remove(String type, String day, String hour);
//
//    /**
//     * @param imageId
//     * @param compressRate 压缩比例
//     * @return
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws IOException
//     */
//    ImageEntity getImage(String imageId, float compressRate) throws InterruptedException, ExecutionException, IOException;
//
//    /**
//     * @param imageId
//     * @param compositePatter 图片组合模式,格式为rows*cols
//     * @param boxes
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    ImageSourceInfo splitImageById(String imageId, String compositePatter, List<RecogResponseCarRect> boxes) throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//    /**
//     * @param imageId
//     * @param compositePatter 图片组合模式,格式为rows*cols
//     * @return watermarkDown 图片下边水印高度
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    ImageSourceInfo splitImageById(String imageId, String compositePatter, int watermarkUp, int watermarkDown) throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//    /**
//     * 对于2*3 的切图进行一定处理
//     *
//     * @param deviceId
//     * @param imageId
//     * @param compositePatter 图片组合模式,格式为rows*cols
//     * @return watermarkDown 图片下边水印高度
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    ImageSourceInfo splitImageById(Integer deviceId, String imageId, String compositePatter, int watermarkUp, int watermarkDown) throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//
//    ImageSourceInfo splitImageById(ImageEntity imageEntity, TbDeviceMarkConfigPattern pattern)
//            throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//    /**
//     * 切图的方法扩展
//     *
//     * @param imageId
//     * @param pattern
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    ImageSourceInfo splitImageById(String imageId, TbDeviceMarkConfigPattern pattern)
//            throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//    /**
//     * @param imageData       base64
//     * @param compositePatter 图片组合模式,格式为rows*cols
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    List<String> splitImageForBase64(String imageData, String compositePatter) throws IOException, InterruptedException, ExecutionException, BusinessException;
//
//    /**
//     * 根据图片id获取图片上车辆和红绿灯坐标点信息
//     *
//     * @param imageId 图片id
//     */
//    ImageAndPointEntity getPointByImageId(String imageId);
//
//    /**
//     * 删除图片和图片对应的车框以及信号灯框
//     *
//     * @param imageIds 图片id
//     */
//    void deleteImageAndPoint(String... imageIds);
//
//    void onDestroy();
//
//    /**
//     * 通过违法id获取一组序列图，用来动态展示
//     *
//     * @param imageInfo     违法信息对象
//     * @param pattern       合成图切图模式
//     * @param compressRate  缩放比例
//     * @param boxPoint      是否显示车框
//     * @param redLightPoint 是否显示红灯框
//     * @return 序列图
//     */
//    List<ImageAndPointEntity> getDynamicImage(IImageInfo imageInfo, TbDeviceMarkConfigPattern pattern, float compressRate, boolean boxPoint, boolean redLightPoint);
//
//    byte[] getImageDataById(IImageInfo imageInfo, boolean showReturnImage, AtomicReference<String> useImageId, HttpServletRequest request);
//
//    /**
//     * 将坐标点进行缩放
//     *
//     * @param imageAndPointEntity 封装坐标点的实体类
//     * @param compressRate        缩放比例
//     */
//    void compressPoint(ImageAndPointEntity imageAndPointEntity, float compressRate);
//
//    /**
//     * 截取图片
//     *
//     * @param imageId  图片id
//     * @param rectJson 截取的坐标json
//     */
//    String getSubImage(String imageId, String rectJson);
//
//
//    boolean calImageSeq(TbDeviceMarkConfigPattern pattern, String newCompositePattern);
//
//    /**
//     * 将切图模式 转换成具体的参数
//     *
//     * @param pattern
//     * @param newCompositePattern
//     * @return
//     */
//    boolean fillPattern(TbDeviceMarkConfigPattern pattern, String newCompositePattern);
//
//    /**
//     * 将原始图片拷贝至指定地址，
//     *
//     * @param srcfilePath
//     * @param targetPath
//     * @param isDeleteSrc
//     * @return
//     * @throws Exception
//     */
//    String storeSrcToTarg(String srcfilePath, String targetPath, boolean isDeleteSrc) throws Exception;
//
//    /**
//     * 将图片保存至指定地址，
//     *
//     * @param srcBytes
//     * @param targetPath
//     * @return
//     */
//    String storeSrcToTarg(byte[] srcBytes, String targetPath);
//}
