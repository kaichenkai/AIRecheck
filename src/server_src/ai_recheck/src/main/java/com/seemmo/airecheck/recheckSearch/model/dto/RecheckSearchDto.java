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
    @Min(value = 153002100L, message = "accessStartTime为无效值")
    @NotBlank(message = "accessStartTime不能为空")
    private Long accessStartTime;

    @Min(value = 153002100L, message = "accessEndTime为无效值")
    @NotBlank(message = "accessEndTime不能为空")
    private Long accessEndTime;

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

    public Long getAccessStartTime() {
        return accessStartTime;
    }

    public void setAccessStartTime(Long accessStartTime) {
        this.accessStartTime = accessStartTime;
    }

    public Long getAccessEndTime() {
        return accessEndTime;
    }

    public void setAccessEndTime(Long accessEndTime) {
        this.accessEndTime = accessEndTime;
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
