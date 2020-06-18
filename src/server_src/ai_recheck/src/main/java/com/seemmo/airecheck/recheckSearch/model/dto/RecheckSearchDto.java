package com.seemmo.airecheck.recheckSearch.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 17:34
 * 复核查询搜索参数
 */
public class RecheckSearchDto {
    /**
     *页面大小
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

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
    private List<Integer> recheckStatusList;

    /**
     * 手动复核状态
     */
    private Integer manualRecheckStatus;

    /**
     * 违法类型编码
     */
    private String illegalCode;

    //getter and setter
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Long getEntryStartTime() {
        return entryStartTime;
    }

    public void setEntryStartTime(Long accessStartTime) {
        this.entryStartTime = accessStartTime;
    }

    public Long getEntryEndTime() {
        return entryEndTime;
    }

    public void setEntryEndTime(Long accessEndTime) {
        this.entryEndTime = accessEndTime;
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

    public List<Integer> getRecheckStatusList() {
        return recheckStatusList;
    }

    public void setRecheckStatusList(List<Integer> recheckStatusList) {
        this.recheckStatusList = recheckStatusList;
    }

    public Integer getManualRecheckStatus() {
        return manualRecheckStatus;
    }

    public void setManualRecheckStatus(Integer manualRecheckStatus) {
        this.manualRecheckStatus = manualRecheckStatus;
    }

    public String getIllegalCode() {
        return illegalCode;
    }

    public void setIllegalCode(String illegalCode) {
        this.illegalCode = illegalCode;
    }
}
