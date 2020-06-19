//package com.seemmo.airecheck.imgstore.service.impl;
//
//import cn.hutool.core.codec.Base64Encoder;
//import com.alibaba.druid.util.StringUtils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.seemmo.aitraffic.commons.model.EnumRole;
//import com.seemmo.aitraffic.commons.model.IImageInfo;
//import com.seemmo.aitraffic.commons.task.AsynTaskScheduler;
//import com.seemmo.aitraffic.commons.task.TimesetFilter;
//import com.seemmo.aitraffic.config.entity.ConfigImageStore;
//import com.seemmo.aitraffic.config.entity.ConfigInstesv;
//import com.seemmo.aitraffic.constant.BaseConstant;
//import com.seemmo.aitraffic.datagate.entity.RecogConst;
//import com.seemmo.aitraffic.datagate.entity.RecogResponseCarRect;
//import com.seemmo.aitraffic.datagate.module.BusinessException;
//import com.seemmo.aitraffic.imgstore.dao.ImagePointMapper;
//import com.seemmo.aitraffic.imgstore.entity.EnumsSplitImage;
//import com.seemmo.aitraffic.imgstore.entity.ImageAndPointEntity;
//import com.seemmo.aitraffic.imgstore.entity.ImageEntity;
//import com.seemmo.aitraffic.imgstore.entity.ImageSourceInfo;
//import com.seemmo.aitraffic.imgstore.service.ImageStore;
//import com.seemmo.aitraffic.imgstore.service.ImageStoreHelper;
//import com.seemmo.aitraffic.imgstore.service.ImageStoreHelper.IdInfo;
//import com.seemmo.aitraffic.platform.model.TbDeviceMark;
//import com.seemmo.aitraffic.platform.model.TbDeviceMarkConfigPattern;
//import com.seemmo.aitraffic.platform.service.TbDeviceMarkService;
//import com.seemmo.aitraffic.utils.*;
//import org.apache.commons.io.FileUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Base64Utils;
//
//import javax.annotation.PreDestroy;
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.util.*;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 图片存储服务
// *
// * @author maoying
// */
//@Service
//public class ImageStoreImpl implements ImageStore {
//    private static final int MAX_IMAGE_SIZE = 10241024 * 30;
//    private static final String STARPP = "\\*";
//    Logger logger = LoggerFactory.getLogger(ImageStoreImpl.class);
//    private Map<String, WeakReference<ImageEntity>> allCache =
//            new ConcurrentHashMap<String, WeakReference<ImageEntity>>(ConfigImageStore.getMaxCacheNum());
//    private TimesetFilter<ImageEntity> timesetFilter;
//    private AsynTaskScheduler<ImageEntity, String> asynTaskScheuler;
//    private Object lock = new Object();
//
//    @Autowired
//    private ImagePointMapper imagePointMapper;
//
//
//    @Override
//    public void init() {
//        timesetFilter = new TimesetFilter<ImageEntity>(2, ConfigImageStore.getMaxCacheNum());
//        asynTaskScheuler = new AsynTaskScheduler<ImageEntity, String>(ConfigImageStore.getCoreThreadNum(),
//                ConfigImageStore.getMaxThreadNum(), ConfigImageStore.getMaxWaitTaskNum(), ConfigImageStore.getMaxCacheNum(),
//                ImageStoreImpl.class.getSimpleName()) {
//            @Override
//            public String execute(ImageEntity t) {
//                return null;
//            }
//        };
//        asynTaskScheuler.init();
//    }
//
//    @Override
//    @PreDestroy
//    public void onDestroy() {
//        if (asynTaskScheuler != null) {
//            asynTaskScheuler.destory();
//        }
//    }
//
//    /**
//     * 通过违法id获取一组序列图，用来动态展示
//     *
//     * @param imageInfo     图片详情
//     * @param combinedInfo  合成图模式
//     * @param compressRate  缩放比例
//     * @param boxPoint      是否显示车框
//     * @param redLightPoint 是否显示红灯框
//     * @return 序列图
//     */
//    @Override
//    public List<ImageAndPointEntity> getDynamicImage(IImageInfo imageInfo, TbDeviceMarkConfigPattern combinedInfo, float compressRate, boolean boxPoint,
//                                                     boolean redLightPoint) {
//        if (imageInfo == null) {
//            return Collections.EMPTY_LIST;
//        }
//        List<ImageAndPointEntity> imageAndPointEntities;
//
//        // 是否存在序列图
//        boolean hasSequence = isHasSequence(imageInfo);
//        if (hasSequence) {
//            // 如果存在序列图
//            imageAndPointEntities = getSequenceImageAndPointEntities(imageInfo, boxPoint, redLightPoint, compressRate);
//        } else if (StringUtil.isNotBlank(imageInfo.getCombinedPicId())) {
//            // 如果存在合成图，根据违法记录id获取合成图模式
//            if (combinedInfo == null) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("合成图模式不存在");
//                }
//                return null;
//            }
//
//            imageAndPointEntities =
//                    getCombinedImageAndPointEntities(imageInfo, boxPoint, redLightPoint, compressRate, combinedInfo);
//        } else {
//            // 如果都不存在
//            imageAndPointEntities = Collections.EMPTY_LIST;
//        }
//
//        return imageAndPointEntities;
//    }
//
//    /**
//     * 将切图模式转换成具体参数
//     *
//     * @param pattern
//     * @param newCompositePattern
//     * @return
//     */
//    @Override
//    public boolean calImageSeq(TbDeviceMarkConfigPattern pattern, String newCompositePattern) {
//        List<Integer> recogImageIdx = Lists.newArrayList();
//        pattern.setCompositePattern(newCompositePattern);
//        boolean flag = splitPattern(pattern);
//
//        // 如果合成图模式填写出错，直接返回
//        if (!flag) {
//            return false;
//        }
//        if (CollectionUtil.isNotEmpty(pattern.getRecogImageIdx())) {
//            return true;
//        }
//
//        // 多少行
//        int rows = pattern.getRows();
//        // 每行多少个图片
//        int cols = pattern.getCols();
//
//        pattern.setRows(rows);
//        pattern.setCols(cols);
//        // 如果是 2*3 的模式的话，图片 3/6 应跳过。特写图 应为 第5张，序列图为1,2,4
//        if (BaseConstant.PATTERN_2_MULX_3.equals(pattern.getCompositePattern())
//                || BaseConstant.PATTERN_2_MUL_3.equals(pattern.getCompositePattern())) {
//            recogImageIdx.add(1);
//            recogImageIdx.add(2);
//            recogImageIdx.add(4);
//            pattern.setFeatureImageIdx(BaseConstant.CONST5);
//
//        } else {
//            if (pattern.getOp().equals(BaseConstant.OP_MUL_X) || pattern.getOp().equals(BaseConstant.OP_MUL)) {
//                for (int i = 0; i < rows * cols; i++) {
//                    if ((i + 1) == pattern.getFeatureImageIdx()) {
//                        continue;
//                    }
//                    recogImageIdx.add(i + 1);
//                }
//            } else if (pattern.getOp().equals(BaseConstant.OP_ADD)) {
//                for (int i = 0; i < rows + cols; i++) {
//                    if ((i + 1) == pattern.getFeatureImageIdx()) {
//                        continue;
//                    }
//                    recogImageIdx.add(i + 1);
//                }
//            }
//        }
//        pattern.setRecogImageIdx(recogImageIdx);
//        return true;
//    }
//
//    private boolean splitPattern(TbDeviceMarkConfigPattern pattern) {
//        String[] str = null;
//        String newCompositePattern = pattern.getCompositePattern();
//        if (newCompositePattern.contains(BaseConstant.OP_MUL_X)) {
//            str = newCompositePattern.split(BaseConstant.OP_MUL_X);
//            pattern.setOp(BaseConstant.OP_MUL_X);
//        } else if (newCompositePattern.contains(BaseConstant.OP_ADD)) {
//            str = newCompositePattern.split("\\+");
//            pattern.setOp(BaseConstant.OP_ADD);
//        } else if (newCompositePattern.contains(BaseConstant.OP_MUL)) {
//            str = newCompositePattern.split("\\*");
//            pattern.setOp(BaseConstant.OP_MUL);
//        }
//        // 如果合成图模式填写出错，直接返回
//        if (str == null || str.length != BaseConstant.CONST2) {
//            return false;
//        }
//
//        // 多少行
//        pattern.setRows(Integer.parseInt(str[0]));
//        // 每行多少个图片
//        pattern.setCols(Integer.parseInt(str[1]));
//        return true;
//    }
//
//    /**
//     * 将切图模式转换成具体参数
//     *
//     * @param pattern
//     * @param newCompositePattern
//     * @return
//     */
//    @Override
//    public boolean fillPattern(TbDeviceMarkConfigPattern pattern, String newCompositePattern) {
//        if (!newCompositePattern.equals(pattern.getCompositePattern())) {
//            // 如果是 2*3 的模式的话，图片 3/6 应跳过。特写图 应为 第5张，序列图为1,2,4
//            if (BaseConstant.PATTERN_2_MULX_3.equals(pattern.getCompositePattern())
//                    || BaseConstant.PATTERN_2_MUL_3.equals(pattern.getCompositePattern())) {
//                pattern.setFeatureImageIdx(BaseConstant.CONST5);
//                pattern.setMarkImageIdx(pattern.getMarkImageIdx() == 3 ? pattern.getMarkImageIdx() + 1 : pattern.getMarkImageIdx());
//            }
//        }
//        pattern.setCompositePattern(newCompositePattern);
//        return splitPattern(pattern);
//    }
//
//    @Override
//    public String storeSrcToTarg(String srcfilePath, String targetPath, boolean isDeleteSrc) throws Exception {
//        File srcFile = new File(srcfilePath);
//        if (!srcFile.exists() || !srcFile.isFile()) {
//            throw new Exception("image file not exist,imagepath" + srcFile.getCanonicalPath());
//        }
//        File tarFile = new File(targetPath);
//        if (!tarFile.getParentFile().exists()) {
//            tarFile.getParentFile().mkdirs();
//        }
//        if (tarFile.exists()) {
//            return targetPath;
//        }
//        targetPath = asynTaskScheuler.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                if (isDeleteSrc) {
//                    boolean flag = srcFile.renameTo(tarFile);
//                    if (!flag) {
//                        FileUtils.moveFile(srcFile, tarFile);
//                    }
//                    srcFile.delete();
//                } else {
//                    FileUtils.copyFile(srcFile, tarFile);
//                }
//                return tarFile.getPath();
//            }
//
//        }).get();
//        return targetPath;
//    }
//
//    @Override
//    public String storeSrcToTarg(byte[] bytes, String targetPath) {
//        File f = new File(targetPath);
//        if (!f.getParentFile().exists()) {
//            f.getParentFile().mkdirs();
//        }
//        FileUtil.writeNioBytesToFile(f.getAbsolutePath(), bytes);
//        return targetPath;
//    }
//
//    /**
//     * 根据imageInfo从合成图中获取序列图以及序列图坐标
//     *
//     * @param imageInfo     图片信息
//     * @param boxPoint      是否需要车辆坐标
//     * @param redLightPoint 是否需要信号灯坐标
//     * @param compressRate  缩放比例
//     * @param pattern       合成图模式相关信息
//     * @return 图片以及坐标集合
//     */
//    private List<ImageAndPointEntity> getCombinedImageAndPointEntities(IImageInfo imageInfo, boolean boxPoint,
//                                                                       boolean redLightPoint, float compressRate, TbDeviceMarkConfigPattern pattern) {
//        // 获取合成图
//        ImageEntity combinedEntity = null;
//        try {
//            combinedEntity = getImage(imageInfo.getCombinedPicId());
//        } catch (Exception e) {
//            logger.info(e.getMessage(), e);
//            return new ArrayList();
//        }
//        if (combinedEntity == null || combinedEntity.getImageData() == null) {
//            logger.info("get Image failed,invalid image id,imageId=" + imageInfo.getCombinedPicId());
//            return new ArrayList();
//        }
//
//        // 合成图模式
//        String compositedPattern = pattern.getCompositePattern();
//        // 如果合成图模式填写出错，直接返回
//        if (!fillPattern(pattern, compositedPattern)) {
//            logger.warn("invalid composited pattern,can not split image!");
//            return Collections.emptyList();
//        }
//        boolean flag = false;
//
//        // 如果是合成图，先判断 合成图的大小 与 标注图片的 比例范围
//        RecogResponseCarRect rect = ImageStoreHelper.getImageSize(combinedEntity.getImageData());
//
//        // 计算合成图与标注图的（水印）比例
//        calPattern(rect, pattern, imageInfo.getCombinedPicId());
//        // 切割合成图
//        List<byte[]> images;
//        try {
//            images = splitImageById(combinedEntity, pattern).getImageBytes();
//        } catch (Exception e) {
//            logger.error("split image fail,imageId is" + imageInfo.getCombinedPicId(), e);
//            return Collections.emptyList();
//        }
//        // 如果没有图片则返回空集合
//        if (images == null || images.size() == 0) {
//            return Collections.emptyList();
//        }
//
//        if (flag) {
//            rect = ImageStoreHelper.getImageSize(images.get(0));
//            pattern.setHeight(rect.getH());
//            pattern.setWidth(rect.getW());
//        }
//
//        // 查询坐标
//        ImageAndPointEntity imageAndPointEntity =
//                imagePointMapper.selectImagePointByImageId(imageInfo.getCombinedPicId());
//        List<Integer> featureIndexList = new ArrayList<>(BaseConstant.CONST3);
//
//        // 如果没有坐标信息
//        if (BaseConstant.PATTERN_2_MUL_3.equals(pattern.getCompositePattern())
//                || BaseConstant.PATTERN_2_MULX_3.equals(pattern.getCompositePattern())) {
//            if (images.size() == pattern.getCols() * pattern.getRows()) {
//                featureIndexList.add(BaseConstant.CONST2);
//                featureIndexList.add(BaseConstant.CONST4);
//                featureIndexList.add(BaseConstant.CONST5);
//            } else {
//                return Collections.emptyList();
//            }
//        } else {
//            if (pattern.getFeatureImageIdx() != 0) {
//                featureIndexList.add(pattern.getFeatureImageIdx() - 1);
//            }
//        }
//        List<ImageAndPointEntity> entities = new ArrayList<>();
//        if (imageAndPointEntity == null) {
//            for (int i = 0; i < images.size(); i++) {
//                byte[] imageByte = images.get(i);
//                ImageAndPointEntity entity = new ImageAndPointEntity();
//                // 如果是特写图，不显示
//                if (featureIndexList.contains(i)) {
//                    continue;
//                }
//                try {
//                    // 缩放小图
//                    byte[] smallImage = ImageUtil.imageCompress(imageByte, compressRate);
//                    entity.setImageData(Base64Encoder.encode(smallImage));
//                } catch (IOException e) {
//                    logger.warn("image compressRate fail,imageId is {},index is {}", imageInfo.getCombinedPicId(), i);
//                }
//                entities.add(entity);
//            }
//        } else {
//            // 如果有坐标点
//
//            String redLights = imageAndPointEntity.getRedLightPoint();
//            String boxPoints = imageAndPointEntity.getBoxPoint();
//
//            // 遍历小图
//            for (int i = 0; i < images.size(); i++) {
//                byte[] imageByte = images.get(i);
//                ImageAndPointEntity entity =
//                        new ImageAndPointEntity().setBoxPoint(boxPoints).setRedLightPoint(redLights);
//                // 如果是特写图，不显示
//                if (featureIndexList.contains(i)) {
//                    continue;
//                }
//
//                // 计算坐标
//                entity = convertPoint(entity, i, pattern);
//                // 如果entity为null说明时特写图，不显示
//                if (entity == null) {
//                    continue;
//                }
//                try {
//                    // 缩放小图
//                    byte[] smallImage = ImageUtil.imageCompress(imageByte, compressRate);
//                    entity.setImageData(Base64Encoder.encode(smallImage));
//                    // 缩放坐标
//                    compressPoint(entity, compressRate);
//                } catch (IOException e) {
//                    logger.warn("image compressRate fail,imageId is {},index is {}", imageInfo.getCombinedPicId(), i);
//                }
//                if (!boxPoint) {
//                    entity.setBoxPoint(null);
//                }
//                if (!redLightPoint) {
//                    entity.setRedLightPoint(null);
//                }
//                entities.add(entity);
//            }
//        }
//
//        // 返回
//        return entities;
//    }
//
//    /**
//     * 转换合成图坐标为单个图片坐标
//     *
//     * @param entity  小图坐标
//     * @param pattern 合同图模式
//     * @param index   当前图片下标，从1开始
//     */
//    private ImageAndPointEntity convertRect(ImageAndPointEntity entity, List<RecogResponseCarRect> carRects, int index,
//                                            TbDeviceMarkConfigPattern pattern) {
//
//        // 获取特写图数量
//        int featureImageCount = pattern.getFeatureImageIdx() <= 0 ? 0 : 1;
//        int rectSize = carRects.size();
//        // 如果是 2*3
//        int imageCount = 0;
//
//        boolean hasLight = false;
//        boolean hasCarRect = false;
//        if (BaseConstant.PATTERN_2_MULX_3.equals(pattern.getCompositePattern())
//                || BaseConstant.PATTERN_2_MUL_3.equals(pattern.getCompositePattern())) {
//            hasCarRect = carRects != null && carRects.size() == BaseConstant.CONST3;
//            // 3 张序列图
//            imageCount = 3;
//            // 弥补几个空白选项,5为特写图，3，6为卡口过车图片，可不进行识别。目前暂时认定写死
//            carRects.add(2, new RecogResponseCarRect());
//            carRects.add(new RecogResponseCarRect());
//            carRects.add(new RecogResponseCarRect());
//        } else if (BaseConstant.PATTERN_1_ADD_2.equals(pattern.getCompositePattern())) {
//            // 3 张序列图
//            // 特写图数量，featureIndex为0时特写图为0张，否则特写图有1张
//            imageCount = pattern.getRows() + pattern.getCols() - featureImageCount;
//            if (rectSize > 0 && featureImageCount > 0) {
//                carRects.add(pattern.getFeatureImageIdx() - 1, new RecogResponseCarRect());
//            }
//        } else {
//            // 是否有可用车辆坐标
//            hasCarRect =
//                    carRects != null && carRects.size() == pattern.getRows() * pattern.getCols() - featureImageCount;
//
//            imageCount = pattern.getRows() * pattern.getCols() - featureImageCount;
//            if (rectSize > 0 && featureImageCount > 0) {
//                carRects.add(pattern.getFeatureImageIdx() - 1, new RecogResponseCarRect());
//            }
//        }
//        // 坐标点在坐标列表中的真实下标，从0开始
//        int realIndex = index < pattern.getFeatureImageIdx() ? index : index - featureImageCount;
//        if (hasCarRect) {
//            if (realIndex < carRects.size()) {
//                RecogResponseCarRect carRect = carRects.get(realIndex);
//                if (carRect == null) {
//                    entity.setBoxPoint(null);
//                    return entity;
//                }
//                int x = carRect.getX();
//                int y = carRect.getY();
//                // 计算相对于单张图的宽高
//                carRect.setX(x - ((index % pattern.getCols()) * pattern.getWidth()));
//                carRect.setY(y - pattern.getWatermarkUp() - ((index / pattern.getRows()) * pattern.getHeight()));
//                entity.setBoxPoint(JSON.toJSONString(Collections.singletonList(carRect)));
//            } else {
//                entity.setBoxPoint(null);
//            }
//        }
//        return entity;
//    }
//
//    /**
//     * 转换合成图坐标为单个图片坐标
//     *
//     * @param entity  小图坐标
//     * @param pattern 合同图模式
//     * @param index   当前图片下标，从1开始
//     */
//    private ImageAndPointEntity convertPoint(ImageAndPointEntity entity, int index, TbDeviceMarkConfigPattern pattern) {
//
//        // 获取特写图数量
//        int featureImageCount = pattern.getFeatureImageIdx() <= 0 ? 0 : 1;
//
//        // 获取坐标信息
//        List<String> redLights = JSON.parseArray(entity.getRedLightPoint(), String.class);
//        List<RecogResponseCarRect> carRects = JSON.parseArray(entity.getBoxPoint(), RecogResponseCarRect.class);
//        int rectSize = carRects.size();
//        boolean hasLight = false;
//        boolean hasCarRect = false;
//        if (BaseConstant.PATTERN_2_MULX_3.equals(pattern.getCompositePattern())
//                || BaseConstant.PATTERN_2_MUL_3.equals(pattern.getCompositePattern())) {
//            hasLight = redLights != null && redLights.size() == BaseConstant.CONST3;
//            hasCarRect = carRects != null && carRects.size() == BaseConstant.CONST3;
//            carRects.add(BaseConstant.CONST2, new RecogResponseCarRect());
//        } else {
//            hasLight =
//                    redLights != null && redLights.size() == pattern.getRows() * pattern.getCols() - featureImageCount;
//            // 是否有可用车辆坐标
//            hasCarRect =
//                    carRects != null && carRects.size() == pattern.getRows() * pattern.getCols() - featureImageCount;
//        }
//        // 坐标点在坐标列表中的真实下标，从0开始
//        int realIndex = index < pattern.getFeatureImageIdx() - 1 ? index : index - featureImageCount;
//        if (hasLight) {
//            if (realIndex < redLights.size()) {
//                String redLight = redLights.get(realIndex);
//                List<RecogResponseCarRect> redLightPoints = JSON.parseArray(redLight, RecogResponseCarRect.class);
//                if (redLightPoints != null && redLightPoints.size() > 0) {
//                    redLightPoints.forEach(e -> {
//                        int x = e.getX();
//                        int y = e.getY();
//                        // 计算相对于单张图的宽高
//                        e.setX(x - index % pattern.getCols() * pattern.getWidth());
//                        e.setY(y - pattern.getWatermarkUp() - index / pattern.getRows() * pattern.getHeight());
//                    });
//                    entity.setRedLightPoint(JSON.toJSONString(redLightPoints));
//                } else {
//                    entity.setRedLightPoint(null);
//                }
//            } else {
//                entity.setRedLightPoint(null);
//            }
//
//        }
//        if (hasCarRect) {
//            if (realIndex < carRects.size()) {
//                RecogResponseCarRect carRect = carRects.get(realIndex);
//                if (carRect == null) {
//                    entity.setBoxPoint(null);
//                    return entity;
//                }
//                int x = carRect.getX();
//                int y = carRect.getY();
//                // 计算相对于单张图的宽高. 只有当长宽比大于1 时，才需要用比例来挪移
//                if (pattern.getCols() > 1) {
//                    carRect.setX(x - ((index % pattern.getCols()) * pattern.getWidth()));
//                } else {
//                    carRect.setX(x);
//                }
//                if (pattern.getRows() > 1) {
//                    carRect.setY(y - pattern.getWatermarkUp() - ((index / pattern.getRows()) * pattern.getHeight()));
//                } else {
//                    carRect.setY(y - pattern.getWatermarkUp());
//                }
//                entity.setBoxPoint(JSON.toJSONString(Collections.singletonList(carRect)));
//            } else {
//                entity.setBoxPoint(null);
//            }
//        }
//        return entity;
//    }
//
//    /**
//     * 根据imageInfo获取序列图以及序列图坐标
//     *
//     * @param imageInfo     图片信息
//     * @param boxPoint      是否需要车辆坐标
//     * @param redLightPoint 是否需要信号灯坐标
//     * @param compressRate
//     * @return 图片以及坐标集合
//     */
//    private List<ImageAndPointEntity> getSequenceImageAndPointEntities(IImageInfo imageInfo, boolean boxPoint,
//                                                                       boolean redLightPoint, float compressRate) {
//        List<ImageAndPointEntity> imageAndPointEntities;
//        List<String> imageIds = Arrays.asList(imageInfo.getCarImg1Id(), imageInfo.getCarImg2Id(),
//                imageInfo.getCarImg3Id(), imageInfo.getCarImg4Id(), imageInfo.getCarImg5Id());
//
//        Stream<ImageAndPointEntity> stream = imageIds.stream().filter(StringUtil::isNotBlank)
//                // 获取序列图
//                .map(this::mapToImageEntity).filter(Objects::nonNull)
//                // 获取坐标,并缩放图片
//                .map(e -> mapToImageAndPoint(e, compressRate));
//        // 如果不需要车框坐标
//        if (!boxPoint) {
//            stream = stream.peek(e -> e.setBoxPoint(null));
//        }
//        // 如果不需要红绿灯坐标
//        if (!redLightPoint) {
//            stream = stream.peek(e -> e.setRedLightPoint(null));
//        }
//        imageAndPointEntities = stream.collect(Collectors.toList());
//        return imageAndPointEntities;
//    }
//
//    /**
//     * 获取图片中相关坐标
//     *
//     * @param imageEntity
//     * @param compressRate
//     * @return
//     */
//    private ImageAndPointEntity mapToImageAndPoint(ImageEntity imageEntity, float compressRate) {
//        ImageAndPointEntity entity = imagePointMapper.selectImagePointByImageId(imageEntity.getImageId());
//        byte[] imageData = imageEntity.getImageData();
//        // 缩放图片
//        try {
//            imageData = ImageUtil.imageCompress(imageData, compressRate);
//            // 缩放图片坐标
//            if (entity == null) {
//                entity = new ImageAndPointEntity();
//            } else {
//                compressPoint(entity, compressRate);
//            }
//        } catch (IOException e) {
//            logger.error("compress image fail,image id is {}", entity.getImageId());
//        }
//        entity.setImageData(Base64Utils.encodeToString(imageData));
//        return entity;
//    }
//
//    public ImageAndPointEntity test(String imageId, float compressRate) {
//        ImageEntity entity = new ImageEntity();
//        entity.setImageId(imageId);
//        return mapToImageAndPoint(entity, compressRate);
//    }
//
//    /**
//     * 判断是否存在序列图
//     *
//     * @param imageInfo
//     * @return
//     */
//    private boolean isHasSequence(IImageInfo imageInfo) {
//        return StringUtil.isNotBlank(imageInfo.getCarImg1Id()) || StringUtil.isNotBlank(imageInfo.getCarImg2Id())
//                || StringUtil.isNotBlank(imageInfo.getCarImg3Id()) || StringUtil.isNotBlank(imageInfo.getCarImg4Id())
//                || StringUtil.isNotBlank(imageInfo.getCarImg5Id());
//    }
//
//    @Override
//    public String store(File image, String type) throws Exception {
//        return store(image, type, true);
//    }
//
//    @Override
//    public String store(File image, String type, boolean isDelete) throws Exception {
//        if (!image.exists() || !image.isFile()) {
//            throw new Exception("image file not exist,imagepath" + image.getCanonicalPath());
//        }
//        IdInfo idInfo = ImageStoreHelper.generateIdInfo(type, ConfigImageStore.getHostCode());
//        String filepath = idInfo.generateFilePath();
//        File f = new File(ConfigImageStore.getRootDir() + File.separator + filepath);
//        if (f.exists()) {
//            Thread.sleep(100);
//            return store(image, type);
//        }
//        ImageEntity entity = asynTaskScheuler.submit(new Callable<ImageEntity>() {
//            @Override
//            public ImageEntity call() throws Exception {
//                if (isDelete) {
//                    boolean flag = image.renameTo(f);
//                    if (!flag) {
//                        FileUtils.moveFile(image, f);
//                    }
//                    image.delete();
//                } else {
//                    FileUtils.copyFile(image, f);
//                }
//                return new ImageEntity().setImageId(ImageStoreHelper.generateId(idInfo)).setFilepath(filepath);
//            }
//
//        }).get();
//        return entity.getImageId();
//    }
//
//    /**
//     * 将 一个已知的图片 复制/移动 到指定 类型目录下，并且返回一个 图片id
//     *
//     * @param imageId  系统中的图片id
//     * @param type     指定类型目录
//     * @param isDelete true-移动，false-复制
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public String store(String imageId, String type, boolean isDelete) throws Exception {
//        // 解析图片的真实路径
//        String filePath = getFilePath(imageId);
//        File file = new File(filePath);
//        // 如果图片真实存在，则将其保存到 指定的路径下
//        if (file.exists() && file.isFile()) {
//            return store(file, type, isDelete);
//        }
//        return null;
//    }
//
//    @Override
//    public String store(byte[] imageData, String type) throws Exception {
//        try {
//            IdInfo idInfo = ImageStoreHelper.generateIdInfo(type, ConfigImageStore.getHostCode());
//            String filepath = idInfo.generateFilePath();
//
//            File f = new File(ConfigImageStore.getRootDir() + File.separator + filepath);
//            if (!f.getParentFile().exists()) {
//                f.getParentFile().mkdirs();
//            }
//            if (!f.exists()) {
//                f.createNewFile();
//            }
//            FileUtil.writeNioBytesToFile(f.getAbsolutePath(), imageData);
//            String id = ImageStoreHelper.generateId(idInfo);
//            return id;
//        } finally {
//        }
//    }
//
//    @Override
//    public ImageEntity getImage(String imageId) throws InterruptedException, ExecutionException {
//        ImageEntity ent = null;
//        try {
//            IdInfo idInfo = ImageStoreHelper.parseId(imageId);
//            if (idInfo == null) {
//                return null;
//            }
//            ImageEntity entity = new ImageEntity();
//            entity.setFilepath(ConfigImageStore.getRootDir() + File.separator + idInfo.generateFilePath());
//            entity.setImageId(imageId);
//            entity.setStoreTime(idInfo.getTimeStamp());
//            entity.setType(idInfo.getType());
//            entity.setFileurl(entity.getFilepath());
//            ent = asynTaskScheuler.submit(new Callable<ImageEntity>() {
//                @Override
//                public ImageEntity call() throws Exception {
//                    byte[] buffer;
//                    File file = null;
//                    try {
//                        file = new File(entity.getFilepath());
//                        if (!file.exists() || file.isDirectory()) {
//                            logger.warn("image file is not exists for image id:" + imageId);
//                            return null;
//                        }
//                        buffer = FileUtils.readFileToByteArray(file);
//                        entity.setImageData(buffer);
//                        return entity;
//                    } catch (IOException e) {
//                        logger.error("get image data failed");
//                        logger.error(e.getMessage(), e);
//                    }
//                    return entity;
//                }
//            }).get();
//            return ent;
//        } finally {
//        }
//    }
//
//    private ImageEntity mapToImageEntity(String imageId) {
//        try {
//            return getImage(imageId);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public String getFilePath(String imageId) {
//        IdInfo entity = ImageStoreHelper.parseId(imageId);
//        if (entity == null) {
//            logger.warn("invalid image id,can not parse it");
//            return null;
//        }
//        return ConfigImageStore.getRootDir() + File.separator + entity.generateFilePath();
//    }
//
//    @Override
//    public boolean delete(String imageId) {
//        if (StringUtil.isBlank(imageId)) {
//            return false;
//        }
//        IdInfo entity = ImageStoreHelper.parseId(imageId);
//        if (entity == null) {
//            logger.warn("image id is not exists,can not delete the file");
//            return false;
//        }
//        String filePath = entity.generateFilePath();
//        File f = new File(ConfigImageStore.getRootDir() + File.separator + filePath);
//        if (f.exists() && f.isFile()) {
//            allCache.remove(imageId);
//            return f.delete();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean remove(String type, String day, String hour) {
//        asynTaskScheuler.submit(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                try {
//                    String filePath = ImageStoreHelper.genStorePath(ConfigImageStore.getHostCode(), type, day, hour);
//                    File f = new File(filePath);
//                    if (f.exists() && f.isDirectory()) {
//                        synchronized (lock) {
//                            timesetFilter.clear();
//                            allCache.clear();
//                        }
//                        boolean flag = f.delete();
//                        if (!flag) {
//                            Thread.sleep(500);
//                            remove(type, day, hour);
//                        }
//                    }
//                    return false;
//                } catch (Exception e) {
//                    logger.warn(e.getMessage(), e);
//                }
//                return true;
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public String update(byte[] imageData, String type, String imageId) throws Exception {
//        ImageEntity imageEntity = this.getImage(imageId);
//        if (imageEntity == null || imageEntity.getImageData() == null) {
//            throw new BusinessException("imageId is not exist,update failed,imageId=" + imageId);
//        }
//        IdInfo idInfo = ImageStoreHelper.parseId(imageId);
//        if (idInfo == null) {
//            throw new BusinessException("imageId is not exist,update failed,imageId=" + imageId);
//        }
//        String filepath = idInfo.generateFilePath();
//        File f = new File(ConfigImageStore.getRootDir() + File.separator + filepath);
//        if (f.exists()) {
//            Thread.sleep(100);
//            return update(imageData, type, imageId);
//        }
//        imageEntity = asynTaskScheuler.submit(new Callable<ImageEntity>() {
//            @Override
//            public ImageEntity call() throws Exception {
//                FileUtils.writeByteArrayToFile(f, imageData);
//                return new ImageEntity().setImageId(ImageStoreHelper.generateId(idInfo)).setFilepath(filepath);
//            }
//        }).get();
//        return imageEntity == null ? null : imageEntity.getImageId();
//    }
//
//    @Override
//    public String getImageBase64(String imageId) throws BusinessException {
//        ImageEntity entity;
//        try {
//            entity = this.getImage(imageId);
//            if (entity == null || entity.getImageData() == null) {
//                logger.info("get Image failed,invalid image id,imageId=" + imageId);
//                throw new BusinessException("get Image failed,invalid image id,imageId=" + imageId);
//            }
//            return Base64.getEncoder().encodeToString(entity.getImageData());
//        } catch (InterruptedException | ExecutionException e) {
//            throw new BusinessException(e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public ImageEntity getImage(String imageId, float compressRate)
//            throws InterruptedException, ExecutionException, IOException {
//        ImageEntity entity = this.getImage(imageId);
//        if (compressRate >= BaseConstant.CONST1F || compressRate <= BaseConstant.CONST0) {
//            return entity;
//        }
//        if (entity == null || entity.getImageData() == null) {
//            logger.info("get Image failed,invalid image id,imageId=" + imageId);
//            return entity;
//        }
//        entity = new ImageEntity(entity);
//        entity.setImageData(ImageUtil.imageCompress(entity.getImageData(), compressRate));
//        return entity;
//    }
//
//    /**
//     * 根据算法画框来进行切图
//     *
//     * @param imageId
//     * @param compositePatter 图片组合模式,格式为rows*cols
//     * @param boxes
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    @Override
//    public ImageSourceInfo splitImageById(String imageId, String compositePatter, List<RecogResponseCarRect> boxes)
//            throws IOException, InterruptedException, ExecutionException, BusinessException {
//        // 读取原始图片
//        ImageEntity entity = this.getImage(imageId);
//        if (entity == null || entity.getImageData() == null) {
//            logger.info("get Image failed,invalid image id,imageId=" + imageId);
//            return new ImageSourceInfo().setImageBytes(new ArrayList());
//        }
//        List<byte[]> images = splitImageById(entity.getImageData(), boxes);
//        return new ImageSourceInfo().setImageBytes(images);
//    }
//
//    private List<byte[]> splitImageById(byte[] imageData, List<RecogResponseCarRect> boxes) {
//        List<byte[]> images = new ArrayList<>();
//        // 根据坐标中图片上切图
//        if (boxes != null && !boxes.isEmpty()) {
//            for (int i = 0; i < boxes.size(); i++) {
//                RecogResponseCarRect box = boxes.get(i);
//                images.add(ImageStoreHelper.splitSubImage(imageData, box.getX(), box.getY(), box.getW(), box.getH()));
//            }
//        }
//        return images;
//    }
//
//    /**
//     * @param imageId
//     * @param compositedPattern 图片组合模式,格式为rows*cols或rows x cols
//     * @param watermarkUp       图片上边水印高度
//     * @param watermarkDown     图片下边水印高度
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    @Override
//    public ImageSourceInfo splitImageById(Integer deviceId, String imageId, String compositedPattern, int watermarkUp,
//                                          int watermarkDown) throws IOException, InterruptedException, ExecutionException, BusinessException {
//
//        TbDeviceMarkConfigPattern pattern = new TbDeviceMarkConfigPattern();
//        pattern.setCompositePattern(compositedPattern);
//        pattern.setWatermarkUp(watermarkUp);
//        pattern.setWatermarkDown(watermarkDown);
//        // 拆分合成图模式
//        if (!fillPattern(pattern, compositedPattern)) {
//            logger.info("get Image failed,invalid compositedPattern,compositedPattern={}", compositedPattern);
//            return new ImageSourceInfo().setImageBytes(new ArrayList());
//        }
//
//        ImageEntity entity = this.getImage(imageId);
//        if (entity == null || entity.getImageData() == null) {
//            logger.info("get Image failed,invalid image id,imageId={}", imageId);
//            return new ImageSourceInfo().setImageBytes(new ArrayList());
//        }
//
//        // 如果切图模式是 2*2 ，且 需要与标注图 进行一次对比
//        if (deviceId != null && (BaseConstant.PATTERN_2_MULX_2.equals(compositedPattern)
//                || BaseConstant.PATTERN_2_MUL_2.equals(compositedPattern))) {
//            // 如果是合成图，先判断 合成图的大小 与 标注图片的 比例范围
//            RecogResponseCarRect rect = ImageStoreHelper.getImageSize(entity.getImageData());
//            TbDeviceMarkService markService = SpringContextHolder.getBean(TbDeviceMarkService.class);
//            TbDeviceMark mark = markService.findByDeviceId(deviceId);
//            if (mark != null && mark.getMarkInfo() != null) {
//                JSONObject object = JSON.parseObject(mark.getMarkInfo());
//                // 把标注图片的高、宽 放在切图模式对象中
//                if (object.containsKey(RecogConst.JSONKEY_WIDTH)) {
//                    pattern.setWidth(object.getInteger(RecogConst.JSONKEY_WIDTH));
//                }
//                if (object.containsKey(RecogConst.JSONKEY_HEIGHT)) {
//                    pattern.setHeight(object.getInteger(RecogConst.JSONKEY_HEIGHT));
//                }
//                // 计算原图 与 标注图的（水印） 比例大小
//                calPattern(rect, pattern, imageId);
//            }
//        }
//        return splitImageById(entity, pattern);
//    }
//
//    /**
//     * @param imageId
//     * @param compositedPattern 图片组合模式,格式为rows*cols或rows x cols
//     * @param watermarkUp       图片上边水印高度
//     * @param watermarkDown     图片下边水印高度
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    @Override
//    public ImageSourceInfo splitImageById(String imageId, String compositedPattern, int watermarkUp, int watermarkDown)
//            throws IOException, InterruptedException, ExecutionException, BusinessException {
//
//        return splitImageById(null, imageId, compositedPattern, watermarkUp, watermarkDown);
//    }
//
//    /**
//     * @param imageId
//     * @param pattern 切图模式
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    @Override
//    public ImageSourceInfo splitImageById(String imageId, TbDeviceMarkConfigPattern pattern)
//            throws IOException, InterruptedException, ExecutionException, BusinessException {
//
//        if (pattern == null || StringUtil.isBlank(pattern.getCompositePattern())) {
//            throw new BusinessException("invalid composited pattern,can not split image!");
//        }
//        ImageEntity entity = this.getImage(imageId);
//        if (entity == null || entity.getImageData() == null) {
//            logger.info("get Image failed,invalid image id,imageId={}", imageId);
//            return new ImageSourceInfo().setImageBytes(new ArrayList());
//        }
//        // 如果是采用算法的切图模式
//        if (pattern.getType() == EnumsSplitImage.EnumSplitImageType.SUANFA.getCode()) {
//            List<byte[]> images = splitImageById(entity.getImageData(), pattern.getConvertImageRects());
//            return new ImageSourceInfo().setImageBytes(images);
//        } else {
//            // 如果上下水印高度都小于等于0，则不切割水印，直接进行切割
//            if (pattern.getWatermarkUp() <= BaseConstant.CONST0 && pattern.getWatermarkDown() <= BaseConstant.CONST0) {
//                return ImageStoreHelper.splitImage(entity.getImageData(), pattern.getRows(), pattern.getCols(),
//                        pattern.getOp());
//            }
//            // 切割水印
//            byte[] subImage = ImageStoreHelper.splitImage(entity.getImageData(), pattern.getWatermarkUp(),
//                    pattern.getWatermarkDown(), BaseConstant.CONST0, BaseConstant.CONST0);
//            if (subImage == null) {
//                return new ImageSourceInfo().setImageBytes(new ArrayList());
//            }
//            return ImageStoreHelper.splitImage(subImage, pattern.getRows(), pattern.getCols(), pattern.getOp());
//        }
//    }
//
//    /**
//     * @param imageEntity 合成图
//     * @param pattern     切图模式
//     * @throws IOException
//     * @throws InterruptedException
//     * @throws ExecutionException
//     * @throws BusinessException
//     */
//    @Override
//    public ImageSourceInfo splitImageById(ImageEntity imageEntity, TbDeviceMarkConfigPattern pattern)
//            throws IOException, InterruptedException, ExecutionException, BusinessException {
//
//        if (imageEntity == null || pattern == null || StringUtil.isBlank(pattern.getCompositePattern())) {
//            logger.warn("invalid composited pattern,can not split image!");
//            return null;
//        }
//        // 如果是采用算法的切图模式
//        if (pattern.getType() == EnumsSplitImage.EnumSplitImageType.SUANFA.getCode()) {
//            List<byte[]> images = splitImageById(imageEntity.getImageData(), pattern.getConvertImageRects());
//            return new ImageSourceInfo().setImageBytes(images);
//        } else {
//
//            // 如果上下水印高度都小于等于0，则不切割水印，直接进行切割
//            if (pattern.getWatermarkUp() <= BaseConstant.CONST0 && pattern.getWatermarkDown() <= BaseConstant.CONST0) {
//                return ImageStoreHelper.splitImage(imageEntity.getImageData(), pattern.getRows(), pattern.getCols(),
//                        pattern.getOp());
//            }
//            // 切割水印
//            byte[] subImage = ImageStoreHelper.splitImage(imageEntity.getImageData(), pattern.getWatermarkUp(),
//                    pattern.getWatermarkDown(), BaseConstant.CONST0, BaseConstant.CONST0);
//            if (subImage == null) {
//                return new ImageSourceInfo().setImageBytes(new ArrayList<byte[]>());
//            }
//            return ImageStoreHelper.splitImage(subImage, pattern.getRows(), pattern.getCols(), pattern.getOp());
//        }
//    }
//
//    @Override
//    public List<String> splitImageForBase64(String imageData, String compositedPattern)
//            throws IOException, BusinessException {
//        if (StringUtils.isEmpty(compositedPattern)
//                || (!compositedPattern.contains(BaseConstant.STAR) && !compositedPattern.contains(BaseConstant.LOWERX))) {
//            throw new BusinessException("invalid composited pattern,can not split image!");
//        }
//        String[] str = compositedPattern.contains(BaseConstant.STAR) ? compositedPattern.split(STARPP)
//                : compositedPattern.split(BaseConstant.LOWERX);
//        byte[] buffer = Base64.getDecoder().decode(imageData);
//        List<byte[]> list = ImageStoreHelper
//                .splitImage(buffer, Integer.parseInt(str[BaseConstant.CONST0]), Integer.parseInt(str[BaseConstant.CONST1]))
//                .getImageBytes();
//        List<String> retList = new ArrayList<String>();
//        for (byte[] b : list) {
//            retList.add(Base64.getEncoder().encodeToString(b));
//        }
//        return retList;
//    }
//
//    /**
//     * 根据图片id获取图片上车辆和红绿灯坐标点信息
//     *
//     * @param imageId 图片id
//     */
//    @Override
//    public ImageAndPointEntity getPointByImageId(String imageId) {
//        ImageAndPointEntity imageAndPointEntity = imagePointMapper.selectImagePointByImageId(imageId);
//        if (imageAndPointEntity == null) {
//            imageAndPointEntity = new ImageAndPointEntity().setBoxPoint(BaseConstant.DOUBLEMIDDLEBRACKETS)
//                    .setRedLightPoint(BaseConstant.DOUBLEMIDDLEBRACKETS);
//        }
//        return imageAndPointEntity;
//    }
//
//    @Override
//    public void deleteImageAndPoint(String... imageIds) {
//        String images = Arrays.stream(imageIds).peek(this::delete).collect(Collectors.joining("','", "'", "'"));
//        imagePointMapper.deleteImagePointByImageIds(images);
//    }
//
//    @Override
//    public void deleteImageAndPoint(List<String> imageIds) {
//        String images = imageIds.stream().peek(this::delete).collect(Collectors.joining("','", "'", "'"));
//        imagePointMapper.deleteImagePointByImageIds(images);
//    }
//
//    /**
//     * 根据图片imageInfo 和一些其他辅助条件获取图片byte数组
//     *
//     * @param imageInfo       图片信息
//     * @param showReturnImage 是否显示算法返回的图片
//     * @param useImageId
//     * @param request
//     * @return 图片二进制数组
//     */
//    @Override
//    public byte[] getImageDataById(IImageInfo imageInfo, boolean showReturnImage, AtomicReference<String> useImageId,
//                                   HttpServletRequest request) {
//        // 判断是否需要算法返回的图片
//        if (showReturnImage && isSuanfaUser(request)) {
//            String returnImgId = imageInfo.getReturnImgId();
//            try {
//                ImageEntity image = this.getImage(returnImgId);
//                byte[] imageData = image.getImageData();
//                if (image != null && imageData != null && imageData.length > 0) {
//                    useImageId.set(returnImgId);
//                    return imageData;
//                }
//            } catch (Exception e) {
//                logger.warn(e.getMessage(), e);
//            }
//        }
//
//        if (org.apache.commons.lang3.StringUtils.isNotBlank(imageInfo.getCombinedPicId())) {
//            ImageEntity image = null;
//            try {
//                image = this.getImage(imageInfo.getCombinedPicId());
//            } catch (Exception e) {
//                logger.error(
//                        "getImageDataById=>getCombinedPicId[{}] error:{}", imageInfo.getCombinedPicId(), e.getMessage(),
//                        e);
//            }
//            if (image != null && image.getImageData() != null) {
//                return image.getImageData();
//            }
//        }
//
//        for (int i = BaseConstant.CONST1; i <= BaseConstant.CONST5; i++) {
//            String carImgIdName = "carImg" + i + "Id";
//            String carImgIdValue =
//                    Objects.toString(Reflections.getFieldValue(imageInfo, carImgIdName), BaseConstant.EMPTYSTR);
//            if (org.apache.commons.lang3.StringUtils.isNotBlank(carImgIdValue)) {
//                ImageEntity image = null;
//                try {
//                    image = this.getImage(carImgIdValue);
//                } catch (Exception e) {
//                    logger.error("getImageDataById=>carImgId[{}] error:{}", carImgIdValue, e.getMessage(), e);
//                }
//                if (image != null && image.getImageData() != null) {
//                    return image.getImageData();
//                }
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 将坐标点进行缩放
//     *
//     * @param imageAndPointEntity 封装坐标点的实体类
//     * @param compressRate        缩放比例
//     */
//    @Override
//    public void compressPoint(ImageAndPointEntity imageAndPointEntity, float compressRate) {
//        JSONArray boxArray = JSONObject.parseArray(imageAndPointEntity.getBoxPoint());
//        if (boxArray == null || boxArray.size() == 0) {
//            return;
//        }
//        compressPointArgument(compressRate, boxArray);
//        JSONArray array = new JSONArray();
//        // 过滤空坐标
//        boxArray.stream().filter(e -> e != null).forEach(e -> array.add(e));
//        imageAndPointEntity.setBoxPoint(array.toJSONString());
//
//        JSONArray redLightArray = JSONObject.parseArray(imageAndPointEntity.getRedLightPoint());
//        if (redLightArray == null || redLightArray.size() == 0) {
//            return;
//        }
//        // compressPointArgument(compressRate, redLightArray);
//        imageAndPointEntity.setRedLightPoint(redLightArray.toJSONString());
//    }
//
//    @Override
//    public String getSubImage(String imageId, String rectJson) {
//        RecogResponseCarRect carRect = JSON.parseObject(rectJson, RecogResponseCarRect.class);
//        try {
//            ImageEntity image = getImage(imageId);
//            byte[] imageData = image.getImageData();
//            byte[] subImageData =
//                    ImageStoreHelper.subImage(imageData, carRect.getX(), carRect.getY(), carRect.getW(), carRect.getH());
//            return Base64Utils.encodeToString(subImageData);
//        } catch (Exception e) {
//            logger.warn("get image fail", e);
//        }
//        return null;
//    }
//
//    /**
//     * 缩小啊jsonObject里面的值，传入jsonObject，压缩率和需要压缩的值对应的key，如果key对应的value为null则不处理
//     *
//     * @param compressRate 压缩率
//     * @param jsonObject   目标jsonObject
//     * @param valueNames   需要缩小的值对应的键,可以为多个
//     */
//    private void compressValueInJsonObject(float compressRate, JSONObject jsonObject, String... valueNames) {
//        for (String valueName : valueNames) {
//            Integer x = jsonObject.getIntValue(valueName);
//            if (x != null && x != 0) {
//                jsonObject.put(valueName, x * compressRate);
//            }
//        }
//    }
//
//    /**
//     * 将array中的每个元素的x y w h 进行缩放
//     *
//     * @param compressRate
//     * @param array
//     */
//    private void compressPointArgument(float compressRate, JSONArray array) {
//
//        for (int i = 0; i < array.size(); i++) {
//            JSONObject jsonObject = array.getJSONObject(i);
//            if (jsonObject != null) {
//                compressValueInJsonObject(compressRate, jsonObject, "x", "y", "w", "h");
//            }
//        }
//    }
//
//    /**
//     * 判断是否是算法指定的用户
//     *
//     * @param request
//     * @return 如果是返回true，否则返回false
//     */
//    private boolean isSuanfaUser(HttpServletRequest request) {
//        return SessionUtils.isRole(request, EnumRole.SUANFA_USER);
//    }
//
//
//    /**
//     * 计算切图模式
//     *
//     * @param rect
//     * @param pattern
//     * @param imageId
//     * @return
//     */
//    @Override
//    public boolean calPattern(RecogResponseCarRect rect, TbDeviceMarkConfigPattern pattern, String imageId) {
//        boolean flag = false;
//        // 根据宽度来获取图片的比例
//        float ratio = (float) rect.getW() / (pattern.getWidth() * pattern.getCols());
//        float ratio2 = (float) rect.getH() / (pattern.getHeight() * pattern.getRows()
//                + pattern.getWatermarkDown()
//                + pattern.getWatermarkUp());
//        float waterRatio = 1f;
//        // 如果计算比例之后，误差在 可容忍范围内。 则认为比例相同
//        if (Math.abs(ratio2 - ratio) * 100 <= ConfigInstesv.getSplitImageHeightError()) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("imageId[{}] [{}]合成图与标注图符合缩放比例，ratio={} ", pattern.getCompositePattern(), imageId, ratio);
//            }
//        } else {
//            ratio = (float) rect.getW() / (pattern.getWidth() * BaseConstant.CONST3);
//            // 用于标识是否匹配上
//            boolean f = false;
//            for (float uprow : BaseConstant.UPROWS) {
//                ratio2 = (float) rect.getH() / (pattern.getHeight() * BaseConstant.CONST2
//                        + pattern.getWatermarkDown() * uprow
//                        + pattern.getWatermarkUp() * uprow);
//                if (Math.abs(ratio2 - ratio) * 100 <= ConfigInstesv.getSplitImageHeightError()) {
//                    // 用 2* 3 试试
//                    fillPattern(pattern, BaseConstant.PATTERN_2_MULX_3);
//                    waterRatio = uprow;
//                    f = true;
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("imageId[{}] [2*3]合成图与标注图符合缩放比例，ratio={} ,waterRatio={}", imageId, ratio, waterRatio);
//                    }
//                    break;
//                }
//            }
//            if (!f) {
//                ratio = 1.0f;
//                logger.warn("imageId[{}] 合成图与切图模式 不匹配. 合成图h={} w={},标注图 h={} w={}.",
//                        imageId, rect.getH(), rect.getW(), pattern.getHeight(),
//                        pattern.getWidth());
//                flag = true;
//            }
//            // 重设序列图的宽和高
//            if (!flag) {
//                pattern.setWatermarkUp(Math.round(pattern.getWatermarkUp() * ratio * waterRatio));
//                pattern.setWatermarkDown(Math.round(pattern.getWatermarkDown() * ratio * waterRatio));
//                pattern.setHeight(Math.round(pattern.getHeight() * ratio));
//                pattern.setWidth(Math.round(pattern.getWidth() * ratio));
//            }
//        }
//        return flag;
//    }
//}
