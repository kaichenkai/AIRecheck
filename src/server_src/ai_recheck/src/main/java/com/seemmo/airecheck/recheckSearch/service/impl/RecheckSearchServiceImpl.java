package com.seemmo.airecheck.recheckSearch.service.impl;

import com.seemmo.airecheck.constant.BaseConstant;
import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
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
    public Response astrictSearchTime(RecheckSearchDto recheckSearchDto) {
        Long accessStartTime = recheckSearchDto.getAccessStartTime();
        Long accessEndTime = recheckSearchDto.getAccessEndTime();
        Long apartDays = (accessEndTime - accessStartTime) / DateUtils.DAY_MILLISECONDS;
        if (apartDays > BaseConstant.CONST31) {
            return ResponseGenerator.genFailResp(ExceptionInfo.SEARCH_TIME_INTERVAL_OUT);
        } else {
            return ResponseGenerator.genSuccessResp();
        }
    }

    @Override
    public List<RecheckSearchData> recheckSearch(RecheckSearchDto recheckSearchDto) {
        return trafficWfRecordSearchMapper.recheckSearch(recheckSearchDto);
    }
}
