package com.seemmo.airecheck.recheckSearch.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/18/2020 14:29
 * 导出复核结果请求参数
 */
public class RecheckResultExportDto {
    /**
     * 接入开始/结束时间, unix毫秒时间戳
     */
    @Min(value = 153002100L, message = "entryStartTime为无效值")
    @NotBlank(message = "entryStartTime不能为空")
    private Long entryStartTime;

    @Min(value = 153002100L, message = "entryEndTime为无效值")
    @NotBlank(message = "entryEndTime不能为空")
    private Long entryEndTime;

    /**
     * 识别开始/结束时间
     */
    @Min(value = 153002100L, message = "recogStartTime为无效值")
    private Long recogStartTime;

    @Min(value = 153002100L, message = "recogEndTime为无效值")
    private Long recogEndTime;

    /**
     * 复核结果代码
     */
    private List<Integer> recheckCodeList;

    /**
     * 手动复核状态
     */
    private Integer manualCheckStatus;

    /**
     * 违法类型编码
     */
    private String illegalCode;

    /**
     * 已勾选条目的 id 数组
     */
    private List<Integer> recordIds;

    /**
     * 导出 excel 表格, 1:只导出表格, 0:导出表格+图片数据
     */
    private Boolean onlyExcel;

    //getter and setter
    public Long getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(Long entryStartTime) {
        this.entryStartTime = entryStartTime;
    }

    public Long getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryEndTime(Long entryEndTime) {
        this.entryEndTime = entryEndTime;
    }

    public Long getRecogStartTime() {
        return recogStartTime;
    }

    public void setRecogStartTime(Long recogStartTime) {
        this.recogStartTime = recogStartTime;
    }

    public Long getRecogEndTime() {
        return recogEndTime;
    }

    public void setRecogEndTime(Long recogEndTime) {
        this.recogEndTime = recogEndTime;
    }

    public List<Integer> getRecheckCodeList() {
        return recheckCodeList;
    }

    public void setRecheckCodeList(List<Integer> recheckCodeList) {
        this.recheckCodeList = recheckCodeList;
    }

    public Integer getManualCheckStatus() {
        return manualCheckStatus;
    }

    public void setManualCheckStatus(Integer manualCheckStatus) {
        this.manualCheckStatus = manualCheckStatus;
    }

    public String getIllegalCode() {
        return illegalCode;
    }

    public void setIllegalCode(String illegalCode) {
        this.illegalCode = illegalCode;
    }

    public List<Integer> getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(List<Integer> recordIds) {
        this.recordIds = recordIds;
    }

    public Boolean getOnlyExcel() {
        return onlyExcel;
    }

    public void setOnlyExcel(Boolean onlyExcel) {
        this.onlyExcel = onlyExcel;
    }

    @Override
    public String toString() {
        return "ExportRecheckResultDto{" +
                "entryStartTime=" + entryStartTime +
                ", entryEndTime=" + entryEndTime +
                ", recogStartTime=" + recogStartTime +
                ", recogEndTime=" + recogEndTime +
                ", recheckCodeList=" + recheckCodeList +
                ", manualCheckStatus=" + manualCheckStatus +
                ", illegalCode='" + illegalCode + '\'' +
                ", recordIds=" + recordIds +
                ", onlyExcel=" + onlyExcel +
                '}';
    }
}
