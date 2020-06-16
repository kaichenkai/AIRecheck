package com.seemmo.airecheck.dataGate.service;

import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.dataGate.model.dto.TrafficIllegalRecordCreateDto;


/**
 * @author: kaichenkai
 * @create: 6/2/2020 17:11
 */
public interface AccessService {
    /**
     * @param recordObj 接入的数据对象
     * @return 是否校验通过
     *
     */
    Response checkArgs(TrafficIllegalRecordCreateDto recordObj);

    int create(TrafficIllegalRecordCreateDto data);
}
