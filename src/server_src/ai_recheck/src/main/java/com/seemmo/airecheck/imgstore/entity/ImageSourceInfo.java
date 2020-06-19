package com.seemmo.airecheck.imgstore.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 用来表示 在切割处理过程中 产生的 与图片有关的中间值
 *
 * @author panie
 * @date 2019/08/21
 */
public class ImageSourceInfo {

    /**
     * 切除上下水印后的二进制
     */
    @JSONField(serialize = false)
    private List<byte[]> imageBytes;

    /**
     * 源图片id
     */
    @JSONField(serialize = false)
    private String sourceImageId;

    /**
     * 源图片高
     */
    @JSONField(name = "source_height")
    private int sourceHeight;

    /**
     * 源图片宽
     */
    @JSONField(name = "source_width")
    private int sourceWidth;

    /**
     * 切割之后返回的图片流
     */
    @JSONField(name = "imagelist")
    private List<ImgageInfo> returnList;

    public List<byte[]> getImageBytes() {
        return imageBytes;
    }

    public ImageSourceInfo setImageBytes(List<byte[]> imageBytes) {
        this.imageBytes = imageBytes;
        return this;
    }

    public String getSourceImageId() {
        return sourceImageId;
    }

    public ImageSourceInfo setSourceImageId(String sourceImageId) {
        this.sourceImageId = sourceImageId;
        return this;
    }

    public int getSourceHeight() {
        return sourceHeight;
    }

    public ImageSourceInfo setSourceHeight(int sourceHeight) {
        this.sourceHeight = sourceHeight;
        return this;
    }

    public int getSourceWidth() {
        return sourceWidth;
    }

    public ImageSourceInfo setSourceWidth(int sourceWidth) {
        this.sourceWidth = sourceWidth;
        return this;
    }

    public List<ImgageInfo> getReturnList() {
        return returnList;
    }

    public ImageSourceInfo setReturnList(List<ImgageInfo> returnList) {
        this.returnList = returnList;
        return this;
    }
}
