package com.seemmo.airecheck.imgstore.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 图片切割参数
 */
public class SplitImageParam {
    /**
     * 原图片Id
     */
    @NotNull(message = "不能为空")
    private String imageId;
    /**
     * 图片组合模式,格式为rows*cols.  1x1,2x2
     */
    @NotNull(message = "不能为空")
    @JSONField(name = "composite_pattern")
    private String compositePatter;
    /**
     * 需要获取图片下标,如果不传或是传的超出范围将返回全部切割好的图片
     */
    @JSONField(name = "mark_image_idx")
    private Integer index;
    /**
     * 上水印高度
     */
    @JSONField(name = "watermark_up")
    private Integer watermarkUp = 0;
    /**
     * 下水印高度
     */
    @JSONField(name = "watermark_down")
    private Integer watermarkDown = 0;

    // 识别后的坐标
    //private List<RecogResponseCarRect> boxes;

    private List<Integer[]> boxes;

    // 切图模式类型.
    private int type;

    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 图片方式，0结果中不返回imageId，1结果中返回imageId（保存模式）
     */
    private Integer model;

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCompositePatter() {
        return compositePatter;
    }

    public void setCompositePatter(String compositePatter) {
        this.compositePatter = compositePatter;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getWatermarkUp() {
        return watermarkUp;
    }

    public void setWatermarkUp(Integer watermarkUp) {
        this.watermarkUp = watermarkUp;
    }

    public Integer getWatermarkDown() {
        return watermarkDown;
    }

    public void setWatermarkDown(Integer watermarkDown) {
        this.watermarkDown = watermarkDown;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

//    public List<RecogResponseCarRect> getBoxes() {
//        return boxes;
//    }
//
//    public void setBoxes(List<RecogResponseCarRect> boxes) {
//        this.boxes = boxes;
//    }


    public List<Integer[]> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Integer[]> boxes) {
        this.boxes = boxes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
