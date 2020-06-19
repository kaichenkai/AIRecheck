//package com.seemmo.airecheck.imgstore.web;
//
//import com.seemmo.airecheck.commons.model.IImageInfo;
//import com.seemmo.airecheck.commons.service.impl.TrafficWfAndTestRecordService;
//import com.seemmo.airecheck.config.entity.ConfigImageStore;
//import com.seemmo.airecheck.constant.BaseConstant;
//import com.seemmo.airecheck.constant.BusinessConstant;
//import com.seemmo.airecheck.core.Result;
//import com.seemmo.airecheck.core.ResultGenerator;
//import com.seemmo.airecheck.datagate.entity.RecogResponseCarRect;
//import com.seemmo.airecheck.datagate.entity.RecogViolationRequest;
//import com.seemmo.airecheck.datagate.service.ImagePointService;
//import com.seemmo.airecheck.imgstore.entity.*;
//import com.seemmo.airecheck.imgstore.service.ImageStore;
//import com.seemmo.airecheck.platform.model.TbDeviceMarkConfigPattern;
//import com.seemmo.airecheck.utils.ImageUtil;
//import com.seemmo.airecheck.utils.Reflections;
//import com.seemmo.airecheck.utils.StringUtil;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Base64Utils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicReference;
//
//@RestController
//@RequestMapping("/business/image")
//public class ImageStoreController {
//    private static final Logger logger = LoggerFactory.getLogger(ImageStoreController.class);
//    @Resource
//    private ImageStore imageStore;
//    @Autowired
//    private ImagePointService imagePointService;
//    @Autowired
//    private TrafficWfAndTestRecordService wfAndTestRecordService;
//
//    @RequestMapping("/store")
//    public Result add(@RequestParam("imageFile") MultipartFile file, @RequestParam String type) {
//        if (file.isEmpty()) {
//            logger.warn("upload file is empty,upload failed!!!");
//            return ResultGenerator.genFailResult("upload file is empty,upload failed!!!");
//        }
//        // 获取文件名
//        String fileName = file.getOriginalFilename();
//        logger.info("upload file name is：" + fileName);
//        // 获取文件的后缀名
//        String suffixName = fileName.substring(fileName.lastIndexOf(BaseConstant.SPOT));
//        logger.info("uploaded file name is：" + suffixName);
//        // 解决中文问题，liunx下中文路径，图片显示问题
//        fileName = UUID.randomUUID() + suffixName;
//        File dest = new File(ConfigImageStore.getTempDir() + File.separator + fileName);
//        // 检测是否存在目录
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(dest);
//            String imageId = imageStore.store(dest, type);
//            if (StringUtils.isNotEmpty(imageId)) {
//                Map<String, String> map = new HashMap(BaseConstant.CONST2);
//                map.put("imageId", imageId);
//                return ResultGenerator.genSuccessResult(map);
//            } else {
//                return ResultGenerator.genFailResult("upload failed!!!");
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return ResultGenerator.genFailResult("upload failed!!!" + e.getMessage());
//        }
//    }
//
//    @RequestMapping("/get")
//    public Result getImage(HttpServletRequest request, HttpServletResponse response,
//                           @RequestParam(name = "imageId") String imageId,
//                           @RequestParam(required = false, name = "compressRate", defaultValue = "1.0") float compressRate,
//                           @RequestParam(required = false, name = "boxPoint", defaultValue = "false") boolean boxPoint,
//                           @RequestParam(required = false, name = "redLightPoint", defaultValue = "false") boolean redLightPoint) {
//
//        // 验证图片id非空
//        if (StringUtils.isEmpty(imageId)) {
//            return ResultGenerator.genFailResult("get Image failed, imageId " + imageId + "is not exists");
//        }
//
//        // 根据imageId获取图片
//        ImageEntity entity = null;
//        try {
//            entity = imageStore.getImage(imageId, compressRate);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        if (entity == null || entity.getImageData() == null) {
//            return ResultGenerator.genFailResult("get Image failed, imageId " + imageId + "is not exists");
//        }
//
//        Result result = ResultGenerator.genSuccessResult();
//        if (boxPoint || redLightPoint) {
//            // 如果需要图片中车辆坐标或红绿灯坐标
//            byte[] imageData = entity.getImageData();
//            ImageAndPointEntity imageAndPointEntity =
//                    getImageAndPointEntity(imageId, boxPoint, redLightPoint, imageData);
//            imageStore.compressPoint(imageAndPointEntity, compressRate);
//            result.setData(imageAndPointEntity);
//        } else {
//            // 不需要返回相关坐标
//            try (ServletOutputStream outputStream = response.getOutputStream()) {
//                outputStream.write(entity.getImageData());
//                outputStream.flush();
//                return null;
//            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
//                result = ResultGenerator.genFailResult("write image data fail");
//            }
//        }
//
//        return result;
//    }
//
//    @RequestMapping("getDynamicImage")
//    public Result
//    getDynamicImage(@Validated(GetImageParameter.GetImage.class) @ModelAttribute GetImageParameter parameter) {
//        // 获取违法记录
//        IImageInfo imageInfo = wfAndTestRecordService.getImageInfo(parameter.getId(), parameter.getType());
//        if (imageInfo == null) {
//            return ResultGenerator.genFailResult("记录不存在");
//        }
//        TbDeviceMarkConfigPattern pattern = null;
//        if (StringUtil.isNotBlank(imageInfo.getCombinedPicId())) {
//            // 如果存在合成图，根据违法记录id获取合成图模式
//            pattern = wfAndTestRecordService.getPattern(imageInfo, parameter.getType());
//
//        }
//        List<ImageAndPointEntity> imageAndPointEntities = imageStore.getDynamicImage(imageInfo,
//                pattern, parameter.getCompressRate(), parameter.isBoxPoint(), parameter.isRedLightPoint());
//        if (imageAndPointEntities == null) {
//            return ResultGenerator.genFailResult("设备未标注或切图模式未配置");
//        }
//        return ResultGenerator.genSuccessResult(imageAndPointEntities);
//    }
//
//    @RequestMapping("/getImage")
//    public Result getImageById(HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam(name = "id") int id, @RequestParam(name = "type") int type,
//                               @RequestParam(required = false, name = "compressRate", defaultValue = "1.0") float compressRate,
//                               @RequestParam(required = false, name = "boxPoint", defaultValue = "false") boolean boxPoint,
//                               @RequestParam(required = false, name = "redLightPoint", defaultValue = "false") boolean redLightPoint,
//                               @RequestParam(required = false, name = "showReturnImage", defaultValue = "false") boolean showReturnImage) {
//
//        // 参数校验
//        if (id <= BaseConstant.CONST0) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("get Image failed, id [" + id + "]is invalid");
//            }
//            return ResultGenerator.genFailResult("记录Id[" + id + "] 不存在");
//        }
//        if (type != BaseConstant.CONST1 && type != BaseConstant.CONST2) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("get Image failed, type [" + type + "]is invalid");
//            }
//            return ResultGenerator.genFailResult("记录类型[" + type + "] 不存在");
//        }
//
//        // 根据违法id和type获取图片信息
//        IImageInfo imageInfo = wfAndTestRecordService.getImageInfo(id, type);
//        if (imageInfo == null) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("get Image failed,id [" + id + "] ,type [" + type + "] not found imageInfo");
//            }
//            return ResultGenerator.genFailResult("记录Id[" + id + "],类型[" + type + "] 未找到违法记录");
//        }
//        // 根据图片信息获取图片并设置使用的图片的id
//        AtomicReference<String> useImageId = new AtomicReference();
//        byte[] imageDatas = imageStore.getImageDataById(imageInfo, showReturnImage, useImageId, request);
//        // 如果图片不存在则根据url下载
//        if (imageDatas == null) {
//            imageDatas = getImageDataByUrl(imageInfo, useImageId);
//        }
//        if (imageDatas == null || imageDatas.length == BaseConstant.CONST0) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("get Image failed,id [" + id + "] ,type [" + type + "] not found imageDatas");
//            }
//            return ResultGenerator.genFailResult("记录Id[" + id + "],类型[" + type + "] 获取图片失败");
//        }
//        // 缩放图片
//        try {
//            imageDatas = ImageUtil.imageCompress(imageDatas, compressRate);
//        } catch (IOException e1) {
//            logger.error("get Image failed,id [" + id + "] ,type [" + type + "],imageLength[" + imageDatas.length
//                    + "] error:" + e1.getMessage(), e1);
//        }
//
//        if (boxPoint || redLightPoint) {
//            // 如果需要车辆或信号灯信息
//            // 获取图片id，根据图片id获取坐标
//            ImageAndPointEntity imageAndPointEntity =
//                    getImageAndPointEntity(useImageId.get(), boxPoint, redLightPoint, imageDatas);
//            imageStore.compressPoint(imageAndPointEntity, compressRate);
//            return ResultGenerator.genSuccessResult(imageAndPointEntity);
//        } else {
//            // 如果只需要图片
//            try (ServletOutputStream outputStream = response.getOutputStream()) {
//                IOUtils.write(imageDatas, outputStream);
//                outputStream.flush();
//                return null;
//            } catch (IOException e) {
//                logger.error("get Image failed,id [" + id + "] ,type [" + type + "] error:" + e.getMessage(), e);
//                return ResultGenerator.genFailResult("记录Id[" + id + "],类型[" + type + "] 获取图片出错：" + e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * 根据imageData
//     *
//     * @param imageId       图片id
//     * @param boxPoint      是否需要车辆坐标
//     * @param redLightPoint 是否需要红绿灯坐标
//     * @param imageData     图片二进制
//     * @return 图片base64和坐标点封装的实体类
//     */
//    private ImageAndPointEntity getImageAndPointEntity(String imageId, boolean boxPoint, boolean redLightPoint,
//                                                       byte[] imageData) {
//        ImageAndPointEntity imageAndPointEntity = imageStore.getPointByImageId(imageId);
//        imageAndPointEntity.setImageData(Base64Utils.encodeToString(imageData));
//
//        if (!boxPoint) {
//            imageAndPointEntity.setBoxPoint(null);
//        }
//        if (!redLightPoint) {
//            imageAndPointEntity.setRedLightPoint(null);
//        }
//        return imageAndPointEntity;
//    }
//
//    @RequestMapping(value = "/splitImage", method = RequestMethod.POST)
//    public Result splitImage(@Validated @RequestBody SplitImageParam imageParam) {
//        try {
//            List<byte[]> images = null;
//            /**
//             * 如果是算法切图，采用算法切图的方法
//             */
//            if (imageParam.getType() == EnumsSplitImage.EnumSplitImageType.SUANFA.getCode()) {
//                if (imageParam.getBoxes() == null) {
//                    return ResultGenerator.genFailResult("参数有误");
//                }
//                List<RecogResponseCarRect> carRects = new ArrayList<>();
//                for (Integer[] i : imageParam.getBoxes()) {
//                    RecogResponseCarRect carRect = new RecogResponseCarRect(i);
//                    carRects.add(carRect);
//                }
//                images = imageStore
//                        .splitImageById(imageParam.getImageId(), imageParam.getCompositePatter(), carRects)
//                        .getImageBytes();
//            } else {
//                // 不是算法切图，还是采用原有的切图方法
//                images =
//                        imageStore
//                                .splitImageById(imageParam.getDeviceId(), imageParam.getImageId(),
//                                        imageParam.getCompositePatter(), imageParam.getWatermarkUp(), imageParam.getWatermarkDown())
//                                .getImageBytes();
//            }
//            if (images == null || images.size() <= 0) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("splitImage imageId [" + imageParam.getImageId() + "] compositePatter ["
//                            + imageParam.getCompositePatter() + "] fail");
//                }
//                return ResultGenerator.genFailResult(
//                        "图片[" + imageParam.getImageId() + "],切割方式[" + imageParam.getCompositePatter() + "] 不存在");
//            }
//            List<byte[]> processImageList = new ArrayList<>();
//            List<ImgageInfo> returnList = new ArrayList<>();
//            if (imageParam.getIndex() == null || imageParam.getIndex() <= 0
//                    || imageParam.getIndex() > (images.size())) {
//                processImageList = new ArrayList<>(images);
//            } else {
//                processImageList.add(images.get(imageParam.getIndex() - 1));
//            }
//            for (byte[] imageData : processImageList) {
//                try {
//                    ImgageInfo imgageInfo = generateImageInfo(imageData, imageParam);
//                    returnList.add(imgageInfo);
//                } catch (Exception e) {
//                    logger.error("splitImage imageId [" + imageParam.getImageId() + "] compositePatter ["
//                            + imageParam.getCompositePatter() + "] index[" + images.size() + "(" + imageParam.getIndex()
//                            + ")],error:" + e.getMessage(), e);
//                    return ResultGenerator.genFailResult("图片[" + imageParam.getImageId() + "],切割方式["
//                            + imageParam.getCompositePatter() + "] 下标[" + imageParam.getIndex() + "] 获取图片数据发生错误");
//                }
//            }
//            return ResultGenerator.genSuccessResult(returnList);
//        } catch (Exception e) {
//            logger.error("splitImage failed,id [" + imageParam.getImageId() + "] ,compositePatter ["
//                            + imageParam.getCompositePatter() + "] ,type [" + imageParam.getIndex() + "] error:" + e.getMessage(),
//                    e);
//            return ResultGenerator.genFailResult("图片[" + imageParam.getImageId() + "],类型["
//                    + imageParam.getCompositePatter() + "] 切割图片出错：" + e.getMessage());
//        }
//    }
//
//    @RequestMapping(value = "/splitImage2", method = RequestMethod.POST)
//    public Result splitImage2(@Validated @RequestBody SplitImageParam imageParam) {
//        try {
//            ImageSourceInfo imageInfo = imageStore.splitImageById(imageParam.getDeviceId(), imageParam.getImageId(),
//                    imageParam.getCompositePatter(), imageParam.getWatermarkUp(), imageParam.getWatermarkDown());
//
//            List<byte[]> images = imageInfo.getImageBytes();
//            if (images == null || images.size() <= 0) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("splitImage imageId [" + imageParam.getImageId() + "] compositePatter ["
//                            + imageParam.getCompositePatter() + "] fail");
//                }
//                return ResultGenerator.genFailResult(
//                        "图片[" + imageParam.getImageId() + "],切割方式[" + imageParam.getCompositePatter() + "] 不存在");
//            }
//            List<byte[]> processImageList = new ArrayList<>();
//            List<ImgageInfo> returnList = new ArrayList<>();
//            if (imageParam.getIndex() == null || imageParam.getIndex() <= 0
//                    || imageParam.getIndex() > (images.size())) {
//                processImageList = new ArrayList<>(images);
//            } else {
//                processImageList.add(images.get(imageParam.getIndex() - 1));
//            }
//            for (byte[] imageData : processImageList) {
//                try {
//                    ImgageInfo imgageInfo = generateImageInfo(imageData, imageParam);
//                    returnList.add(imgageInfo);
//                } catch (Exception e) {
//                    logger.error("splitImage imageId [" + imageParam.getImageId() + "] compositePatter ["
//                            + imageParam.getCompositePatter() + "] index[" + images.size() + "(" + imageParam.getIndex()
//                            + ")],error:" + e.getMessage(), e);
//                    return ResultGenerator.genFailResult("图片[" + imageParam.getImageId() + "],切割方式["
//                            + imageParam.getCompositePatter() + "] 下标[" + imageParam.getIndex() + "] 获取图片数据发生错误");
//                }
//            }
//            imageInfo.setReturnList(returnList);
//            imageInfo.setImageBytes(null);
//            return ResultGenerator.genSuccessResult(imageInfo);
//        } catch (Exception e) {
//            logger.error("splitImage failed,id [" + imageParam.getImageId() + "] ,compositePatter ["
//                            + imageParam.getCompositePatter() + "] ,type [" + imageParam.getIndex() + "] error:" + e.getMessage(),
//                    e);
//            return ResultGenerator.genFailResult("图片[" + imageParam.getImageId() + "],类型["
//                    + imageParam.getCompositePatter() + "] 切割图片出错：" + e.getMessage());
//        }
//    }
//
//    private ImgageInfo generateImageInfo(byte[] imageData, SplitImageParam imageParam) throws Exception {
//        if (imageData == null || imageData.length <= 0) {
//            throw new Exception("图片[" + imageParam.getImageId() + "],切割方式[" + imageParam.getCompositePatter() + "] 下标["
//                    + imageParam.getIndex() + "] 获取图片数据为空");
//        }
//        ImgageInfo imgageInfo = new ImgageInfo();
//        imgageInfo.setImageBase64(Base64.getEncoder().encodeToString(imageData));
//        if (imageParam.getModel() != null && imageParam.getModel() == 1) {
//            imgageInfo.setImageId(imageStore.store(imageData, BusinessConstant.IMAGE_SPLIT_IMAGE));
//        }
//        return imgageInfo;
//
//    }
//
//    private byte[] getImageDataByUrl(IImageInfo imageInfo, AtomicReference<String> useImageId) {
//        // 下载合成图片
//        if (StringUtils.isNotBlank(imageInfo.getCombinedPicUrl())) {
//            byte[] imageDatas = null;
//            try {
//                imageDatas = ImageUtil.downImage(imageInfo.getCombinedPicUrl());
//            } catch (Exception e) {
//                logger.error(
//                        "getImageDataByUrl=>getCombinedPicId[" + imageInfo.getCombinedPicId() + "] error:" + e.getMessage(),
//                        e);
//            }
//            if (imageDatas != null) {
//                // 填充使用的imageId
//                useImageId.set(imageInfo.getCombinedPicId());
//                return imageDatas;
//            }
//        }
//
//        // 下载序列图片1
//
//        for (int i = BaseConstant.CONST1; i <= BaseConstant.CONST5; i++) {
//            String carImgUrlName = "carImg" + i + "Url";
//            String carImgUrlValue =
//                    Objects.toString(Reflections.getFieldValue(imageInfo, carImgUrlName), BaseConstant.EMPTYSTR);
//            if (StringUtils.isNotBlank(carImgUrlValue)) {
//                byte[] imageDatas = null;
//                try {
//                    imageDatas = ImageUtil.downImage(carImgUrlValue);
//                } catch (Exception e) {
//                    logger.error("getImageDataByUrl=>getCombinedPicId[" + imageInfo.getCombinedPicId() + "] error:"
//                            + e.getMessage(), e);
//                }
//                if (imageDatas != null) {
//                    // 填充使用的imageId
//                    String imageIdName = carImgUrlName.replace("Url", "Id");
//                    String imageId = Objects.toString(Reflections.getFieldValue(imageInfo, imageIdName), null);
//                    useImageId.set(imageId);
//                    return imageDatas;
//                }
//            }
//
//        }
//        return null;
//    }
//
//    @RequestMapping("/delete")
//    public Result delete(@RequestParam String imageId) {
//        boolean flag = imageStore.delete(imageId);
//        if (flag) {
//            return ResultGenerator.genSuccessResult();
//        } else {
//            return ResultGenerator.genFailResult("delete image file failed for imageid:" + imageId);
//        }
//    }
//
//    @GetMapping("car_plate")
//    public void carPlate(HttpServletResponse response, @RequestParam Integer id, @RequestParam Integer type) {
//
//        // 获取违法记录
//        IImageInfo trafficRecord = wfAndTestRecordService.getImageInfo(id, type);
//        if (trafficRecord == null) {
//            logger.warn("id为{}的记录不存在", id);
//            return;
//        }
//        ImagePointService.IllegalImage illegalImage =
//                new ImagePointService.IllegalImage().setCombinedPicId(trafficRecord.getCombinedPicId())
//                        .setFeatureId(trafficRecord.getCarNumPicId()).setImage1Id(trafficRecord.getCarImg1Id())
//                        .setImage2Id(trafficRecord.getCarImg2Id()).setImage3Id(trafficRecord.getCarImg3Id())
//                        .setImage4Id(trafficRecord.getCarImg4Id()).setImage5Id(trafficRecord.getCarImg5Id());
//        String plateImage =
//                imagePointService.getPlateImage(illegalImage, id, RecogViolationRequest.RecordType.pares(type));
//        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            if (StringUtils.isBlank(plateImage)) {
//                return;
//            }
//            outputStream.write(Base64Utils.decodeFromString(plateImage));
//            outputStream.flush();
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//    }
//}
