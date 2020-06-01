package com.seemmo.airecheck.dataGate.entity;

import javax.persistence.Id;
import javax.persistence.Column;

/**
 * @author: kaichenkai
 * @create: 6/1/2020 16:51
 * 原始记录信息
 */
public class SrcRecordInfo {
    @Id//ID
    private Integer id;
    @Column(name = "record_id")//设备编码
    private String recordId;
    @Column(name = "car_plate_number")//车牌号码
    private String carPlateNumber;
    @Column(name = "car")

}
