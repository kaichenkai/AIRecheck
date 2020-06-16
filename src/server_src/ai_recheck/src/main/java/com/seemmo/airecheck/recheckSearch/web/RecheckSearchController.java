package com.seemmo.airecheck.recheckSearch.web;

import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;
import com.seemmo.airecheck.recheckSearch.service.RecheckSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: kaichenkai
 * @create: 6/9/2020 17:24
 * 复核查询控制器类
 */
@RestController
@RequestMapping("/main")
public class RecheckSearchController {
    private Logger logger = LoggerFactory.getLogger(RecheckSearchController.class);

    @Autowired
    private RecheckSearchService recheckSearchService;

    @PostMapping(value = "recheckSearch", name = "复核结果查询")
    public Response<?> recheckSearch(@RequestBody RecheckSearchDto recheckSearchDto) {
        //限制接入查询时间
        Response response = recheckSearchService.astrictSearchTime(recheckSearchDto);
        if (response.success()) {
            //搜索数据
            try {
                List<RecheckSearchData> recheckSearchDataList = recheckSearchService.recheckSearch(recheckSearchDto);
            } catch (Exception e) {
                logger.error(String.format("database operation error: %s", e));
                return ResponseGenerator.genFailResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
            }
        } else {
            return response;
        }
//        System.out.println(recheckSearchDto.getAccessEndTime());
        return ResponseGenerator.genSuccessResp();
    }
}
