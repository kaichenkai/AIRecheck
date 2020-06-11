package com.seemmo.airecheck.dataGate.entity;

import javax.persistence.Id;
import javax.persistence.Column;

/**
 * @author: kaichenkai
 * @create: 6/1/2020 16:51
 * 原始记录信息
 */
public class TrafficWfRecord {
    /**
     * ID
     */
    @Id
    private Integer id;

    /**
     * //创建时间(UNIX时间戳)
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * //更新时间(UNIX时间戳)
     */
    @Column(name = "update_time")
    private Long updateTime;

    /**
     * 唯一记录ID
     */
    @Column(name = "record_id")
    private String recordId;

    /**
     * 设备编码, 违法图片拍摄的设备编码
     */
    @Column(name = "device_code")
    private String deviceCode;

    /**
     * 车牌号码
     */
    @Column(name = "car_plate_number")
    private String carPlateNumber;

    /**
     * 车牌类型
     */
    @Column(name = "car_plate_type")
    private String carPlateType;

    /**
     * 违法时间(UNIX时间戳) 例如“1490409584000”
     */
    @Column(name = "illegal_time")
    private Long illegalTime;

    /**
     * 违法类型编码
     */
    @Column(name = "illegal_code")
    private String illegalCode;

    /**
     * 违法地址
     */
    @Column(name = "illegal_addr")
    private String illegalAddr;

    /**
     * 采集机关编码
     */
    @Column(name = "collect_sector_code")
    private String collectSectorCode;

    /**
     * 采集机关名称
     */
    @Column(name = "collect_sector_name")
    private String collectSectorName;

    /**
     * 录入人
     */
    @Column(name = "entry_person")
    private String entryPerson;

    /**
     * 手动复核状态, 0未复审，1复审有效，2复审无效
     */
    @Column(name = "manual_check_status")
    private Byte manualCheckStatus;

    /**
     * 图片url
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 图片本地路径(相对)
     */
    @Column(name = "img_path")
    private String imgPath;

    /**
     * 数据上报状态, 0:未上报， 1:成功， 2:失败
     */
    @Column(name = "report_status")
    private Byte reportStatus;

    /**
     * 数据上报时间
     */
    @Column(name = "report_time")
    private Long reportTime;

    /**
     * 识别状态, 0未识别，1识别中，2识别成功 ,3识别异常, 4没有图片
     */
    @Column(name = "recog_status")
    private Byte recogStatus;

    /**
     * 识别时间(UNIX时间戳)
     */
    @Column(name = "recog_time")
    private Long recogTime;

    /**
     * 识别数据
     */
    @Column(name = "recog_data")
    private String recogData;

    /**
     * 识别号牌号码
     */
    @Column(name = "sdk_car_plate_number")
    private String sdkCarPlateNumber;

    /**
     * 识别号牌类型
     */
    @Column(name = "sdk_car_plate_type")
    private String sdkCarPlateType;

    /**
     * 识别结果代码: 1:车牌更正, 2:"疑似", 3:"模糊", 4:"遮挡", 5:"未匹配到车"]
     */
    @Column(name = "sdk_reason_code")
    private Byte sdkReasonCode;

    /**
     * 识别车牌坐标
     */
    @Column(name = "sdk_plate_rect")
    private String sdkPlateRect;

    /**
     * 车牌识别首字得分
     */
    @Column(name = "sdk_plate_head_score")
    private Byte sdkPlateHeadScore;

    /**
     * 备用字段 1
     */
    @Column(name = "remark1")
    private String remark1;

    /**
     * 备用字段 2
     */
    @Column(name = "remark2")
    private String remark2;

    /**
     * 备用字段 3
     */
    @Column(name = "remark3")
    private String remark3;

    // getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Byte getManualCheckStatus() {
        return manualCheckStatus;
    }

    public void setManualCheckStatus(Byte manualCheckStatus) {
        this.manualCheckStatus = manualCheckStatus;
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

    public Byte getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Byte reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }

    public Byte getRecogStatus() {
        return recogStatus;
    }

    public void setRecogStatus(Byte recogStatus) {
        this.recogStatus = recogStatus;
    }

    public String getRecogData() {
        return recogData;
    }

    public void setRecogData(String recogData) {
        this.recogData = recogData;
    }

    public Long getRecogTime() {
        return recogTime;
    }

    public void setRecogTime(Long recogTime) {
        this.recogTime = recogTime;
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

    public Byte getSdkReasonCode() {
        return sdkReasonCode;
    }

    public void setSdkReasonCode(Byte sdkReasonCode) {
        this.sdkReasonCode = sdkReasonCode;
    }

    public String getSdkPlateRect() {
        return sdkPlateRect;
    }

    public void setSdkPlateRect(String sdkPlateRect) {
        this.sdkPlateRect = sdkPlateRect;
    }

    public Byte getSdkPlateHeadScore() {
        return sdkPlateHeadScore;
    }

    public void setSdkPlateHeadScore(Byte sdkPlateHeadScore) {
        this.sdkPlateHeadScore = sdkPlateHeadScore;
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
