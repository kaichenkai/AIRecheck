package com.seemmo.airecheck.dataGate.mapper;

import com.seemmo.airecheck.dataGate.model.dto.TrafficIllegalRecordCreateDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: kaichenkai
 * @create: 6/2/2020 17:10
 *
 * TrafficWfRecord 操作方法
 */
@Repository
@Mapper
public interface TrafficWfRecordAccessMapper {
    int create(TrafficIllegalRecordCreateDto trafficIllegalRecordCreateDto);

    String exist(String recordId);
}
