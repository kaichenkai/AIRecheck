package com.seemmo.airecheck.recheckSearch.service.impl;

import com.seemmo.airecheck.constant.BaseConstant;
import com.seemmo.airecheck.core.ExceptionCode;
import com.seemmo.airecheck.core.Result;
import com.seemmo.airecheck.core.ResultGenerator;
import com.seemmo.airecheck.recheckSearch.mapper.TrafficWfRecordSearchMapper;
import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;
import com.seemmo.airecheck.recheckSearch.service.RecheckSearchService;
import com.seemmo.airecheck.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 17:30
 */
@Service("recheckSearch")
public class RecheckSearchServiceImpl implements RecheckSearchService {
    @Autowired
    private TrafficWfRecordSearchMapper trafficWfRecordSearchMapper;

    @Override
    public Result astrictSearchTime(RecheckSearchDto recheckSearchDto) {
        Long accessStartTime = recheckSearchDto.getAccessStartTime();
        Long accessEndTime = recheckSearchDto.getAccessEndTime();
        Long apartDays = (accessEndTime - accessStartTime) / DateUtils.DAY_MILLISECONDS;
        if (apartDays > BaseConstant.CONST31) {
            return ResultGenerator.genFailResult(ExceptionCode.SEARCH_TIME_INTERVAL_OUT.code, ExceptionCode.SEARCH_TIME_INTERVAL_OUT.message);
        } else {
            return ResultGenerator.genSuccessResult();
        }
    }

    @Override
    public List<RecheckSearchData> recheckSearch(RecheckSearchDto recheckSearchDto) {
        return trafficWfRecordSearchMapper.recheckSearch(recheckSearchDto);
    }
}
