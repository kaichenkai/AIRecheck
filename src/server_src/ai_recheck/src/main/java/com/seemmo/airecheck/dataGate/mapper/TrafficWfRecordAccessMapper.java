package com.seemmo.airecheck.dataGate.mapper;

import com.seemmo.airecheck.dataGate.model.RecordData;
import com.seemmo.airecheck.dataGate.web.dto.TrafficIllegalRecordCreateDto;
import com.sun.prism.impl.Disposer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
