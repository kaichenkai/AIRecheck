package com.seemmo.airecheck.imgstore.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @program: ai_traffic
 * @author: 张乐
 * @create: 2018-12-20 11:15
 **/
public class ImageAndPointEntity {

    private String imageId;
    @JSONField(serialize = false)
    private String boxPoint;
    @JSONField(name = "boxPoint")
    private JSONArray boxPointOjb;
    @JSONField(serialize = false)
    private String redLightPoint;
    @JSONField(name = "redLightPoint")
    private JSONArray redLightPointOjb;
    private String imageData;

    public String getBoxPoint() {
        return boxPoint;
    }

    public JSONArray getBoxPointOjb() {
        return JSONObject.parseArray(boxPoint);
    }

    public JSONArray getRedLightPointOjb() {
        return JSONObject.parseArray(redLightPoint);
    }

    public ImageAndPointEntity setBoxPoint(String boxPoint) {
        this.boxPoint = boxPoint;
        return this;
    }

    public String getRedLightPoint() {
        return redLightPoint;
    }

    public ImageAndPointEntity setRedLightPoint(String redLightPoint) {
        this.redLightPoint = redLightPoint;
        return this;
    }

    public String getImageData() {
        return imageData;
    }

    public ImageAndPointEntity setImageData(String imageData) {
        this.imageData = imageData;
        return this;
    }

    public String getImageId() {
        return imageId;
    }

    public ImageAndPointEntity setImageId(String imageId) {
        this.imageId = imageId;
        return this;
    }
}