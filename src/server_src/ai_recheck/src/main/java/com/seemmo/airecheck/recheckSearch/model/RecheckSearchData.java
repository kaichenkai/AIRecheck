package com.seemmo.airecheck.recheckSearch.model;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 20:03
 */
public class RecheckSearchData {
    private Long createTime;
    private Integer id;
    private String recordId;
    private String carPlateType;
    private String carPlateNumber;
    private String illegalCode;
    private String imgUrl;
    private String imgPath;
    private Integer manualCheckStatus;
    private String entryPerson;
    private Integer reportStatus;
    private Long reportTime;
    private Long sdkRecogTime;
    private Integer sdkRecogStatus;
    private String sdkRecogData;
    private Integer sdkRecheckStatus;
    private String sdkCarPlateNumber;
    private String sdkCarPlateType;
    private String sdkPlateRect;

    //getter and setter
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCarPlateType() {
        return carPlateType;
    }

    public void setCarPlateType(String carPlateType) {
        this.carPlateType = carPlateType;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getIllegalCode() {
        return illegalCode;
    }

    public void setIllegalCode(String illegalCode) {
        this.illegalCode = illegalCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getManualCheckStatus() {
        return manualCheckStatus;
    }

    public void setManualCheckStatus(Integer manualCheckStatus) {
        this.manualCheckStatus = manualCheckStatus;
    }

    public String getEntryPerson() {
        return entryPerson;
    }

    public void setEntryPerson(String entryPerson) {
        this.entryPerson = entryPerson;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public Long getSdkRecogTime() {
        return sdkRecogTime;
    }

    public void setSdkRecogTime(Long sdkRecogTime) {
        this.sdkRecogTime = sdkRecogTime;
    }

    public Integer getSdkRecogStatus() {
        return sdkRecogStatus;
    }

    public void setSdkRecogStatus(Integer sdkRecogStatus) {
        this.sdkRecogStatus = sdkRecogStatus;
    }

    public String getSdkRecogData() {
        return sdkRecogData;
    }

    public void setSdkRecogData(String sdkRecogData) {
        this.sdkRecogData = sdkRecogData;
    }

    public Integer getSdkRecheckStatus() {
        return sdkRecheckStatus;
    }

    public void setSdkRecheckStatus(Integer sdkRecheckStatus) {
        this.sdkRecheckStatus = sdkRecheckStatus;
    }

    public String getSdkCarPlateNumber() {
        return sdkCarPlateNumber;
    }

    public void setSdkCarPlateNumber(String sdkCarPlateNumber) {
        this.sdkCarPlateNumber = sdkCarPlateNumber;
    }

    public String getSdkCarPlateType() {
        return sdkCarPlateType;
    }

    public void setSdkCarPlateType(String sdkCarPlateType) {
        this.sdkCarPlateType = sdkCarPlateType;
    }

    public String getSdkPlateRect() {
        return sdkPlateRect;
    }

    public void setSdkPlateRect(String sdkPlateRect) {
        this.sdkPlateRect = sdkPlateRect;
    }
}
