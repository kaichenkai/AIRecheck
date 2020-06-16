package com.seemmo.airecheck.dataGate.model.dto;

import lombok.Data;
import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class TrafficIllegalRecordCreateDto implements Serializable {
    /**
     * 创建时间
     */
    @JSONField(name = "createTime")
    protected Long createTime;

    /**
     * 更新时间
     */
    @JSONField(name = "update_time")
    protected Long updateTime;

    /**
     * 唯一记录ID
     */
    @NotBlank(message = "recordId不能为空")
    @Length(max = 50, message = "recordId长度过长")
    @JSONField(name = "recordId")
    protected String recordId;

    /**
     * 设备代码，违法图片拍摄的设备代码
     */
    @NotBlank(message = "deviceCode不能为空")
    @Length(max = 32, message = "deviceCode长度过长")
    @JSONField(name = "deviceCode")
    protected String deviceCode;

    /**
     * 车牌号码
     */
    @NotBlank(message = "carPlateNumber不能为空")
    @Length(max = 15, message = "carPlateNumber长度过长")
    @JSONField(name = "carPlateNumber")
    protected String carPlateNumber;

    /**
     * 车牌类型编码
     */
    @NotBlank(message = "carPlateType不能为空")
    @Length(max = 2, message = "arPlateType长度过长")
    @JSONField(name = "carPlateType")
    protected String carPlateType;

    /**
     * 违法时间。 unix毫秒时间戳，例如“1490409584000”
     */
    @Min(value = 153002100L, message = "illegalTime为无效值")
    @NotBlank(message = "illegalTime不能为空")
    protected Long illegalTime;

    /**
     * 违法类型编码
     */
    @NotBlank(message = "illegalCode不能为空")
    @Length(max = 10, message = "illegalCode长度过长")
    @JSONField(name = "illegalCode")
    protected String illegalCode;

    /**
     * 违法地址
     */
    @JSONField(name = "illegalAddr")
    @Length(max = 128, message = "illegalAddr长度过长")
    protected String illegalAddr;

    /**
     * 采集机关编码
     */
    @NotBlank(message = "collectSectorCode不能为空")
    @Length(max = 12, message = "collectSectorCode长度过长")
    @JSONField(name = "collectSectorCode")
    protected String collectSectorCode;

    /**
     * 采集机关名称
     */
    @NotBlank(message = "collectSectorName不能为空")
    @Length(max = 128, message = "collectSectorName长度过长")
    @JSONField(name = "collectSectorName")
    protected String collectSectorName;

    /**
     * 录入人
     */
    @NotBlank(message = "entryPerson不能为空")
    @Length(max = 30, message = "entryPerson长度过长")
    @JSONField(name = "entryPerson")
    protected String entryPerson;

    /**
     * 图片url
     */
    @NotBlank(message = "imgUrl不能为空")
    @Length(max = 256, message = "imgUrl长度过长")
    @JSONField(name = "imgUrl")
    protected String imgUrl;

    /**
     * 图片路径 （本地绝对路径）
     */
    @JSONField(name = "imgPath")
    private String imgPath;

    /**
     * 备用字段1
     */
    @JSONField(name = "remark1")
    protected String remark1;

    /**
     * 备用字段2
     */
    @JSONField(name = "remark2")
    protected String remark2;

    /**
     * 备用字段3
     */
    @JSONField(name = "remark3")
    protected String remark3;

    // getter and setter
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarPlateType() {
        return carPlateType;
    }

    public void setCarPlateType(String carPlateType) {
        this.carPlateType = carPlateType;
    }

    public Long getIllegalTime() {
        return illegalTime;
    }

    public void setIllegalTime(Long illegalTime) {
        this.illegalTime = illegalTime;
    }

    public String getIllegalCode() {
        return illegalCode;
    }

    public void setIllegalCode(String illegalCode) {
        this.illegalCode = illegalCode;
    }

    public String getIllegalAddr() {
        return illegalAddr;
    }

    public void setIllegalAddr(String illegalAddr) {
        this.illegalAddr = illegalAddr;
    }

    public String getCollectSectorCode() {
        return collectSectorCode;
    }

    public void setCollectSectorCode(String collectSectorCode) {
        this.collectSectorCode = collectSectorCode;
    }

    public String getCollectSectorName() {
        return collectSectorName;
    }

    public void setCollectSectorName(String collectSectorName) {
        this.collectSectorName = collectSectorName;
    }

    public String getEntryPerson() {
        return entryPerson;
    }

    public void setEntryPerson(String entryPerson) {
        this.entryPerson = entryPerson;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }
}
