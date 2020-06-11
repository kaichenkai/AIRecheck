package com.seemmo.airecheck.recheckSearch.mapper;

import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;
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
public interface TrafficWfRecordSearchMapper {
    List<RecheckSearchData> recheckSearch(RecheckSearchDto recheckSearchDto);
}
