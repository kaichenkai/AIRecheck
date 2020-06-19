package com.seemmo.airecheck.imgstore.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;

/**
 * @program: ai_traffic
 * @description: 获取图片时传入的参数
 * @author: 张乐
 * @create: 2019-01-10 15:51
 **/
public class GetImageParameter {

    @DecimalMin(value = "0" ,message = "无效的id",groups = GetImage.class)
    private int id;
    @NotBlank(message = "imageId不能为空",groups = Get.class)
    private String imageId;
    @Range(min = 1, max = 2, message = "type不能为空",groups = GetImage.class)
    private int type;
    private float compressRate = 1.0f;
    private boolean boxPoint;
    private boolean redLightPoint;
    private boolean showReturnImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getCompressRate() {
        return compressRate;
    }

    public void setCompressRate(float compressRate) {
        this.compressRate = compressRate;
    }

    public boolean isBoxPoint() {
        return boxPoint;
    }

    public void setBoxPoint(boolean boxPoint) {
        this.boxPoint = boxPoint;
    }

    public boolean isRedLightPoint() {
        return redLightPoint;
    }

    public void setRedLightPoint(boolean redLightPoint) {
        this.redLightPoint = redLightPoint;
    }

    public boolean isShowReturnImage() {
        return showReturnImage;
    }

    public void setShowReturnImage(boolean showReturnImage) {
        this.showReturnImage = showReturnImage;
    }

    /**
     * 在参数校验时进行分组，通过违法id获取图片使用getImage接口，用当前接口标记
     */
    public static interface GetImage{}

    /**
     * 在参数校验时进行分组，通过图片id获取图片使用get接口，用当前接口标记
     */
    public static interface Get{}
}
