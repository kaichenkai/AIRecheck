package com.seemmo.airecheck.recheckSearch.web;

import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.recheckSearch.model.RecheckResultExportData;
import com.seemmo.airecheck.recheckSearch.model.RecheckSearchData;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckResultExportDto;
import com.seemmo.airecheck.recheckSearch.model.dto.RecheckSearchDto;
import com.seemmo.airecheck.recheckSearch.service.RecheckResultExportService;
import com.seemmo.airecheck.recheckSearch.service.RecheckSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private RecheckResultExportService recheckResultExportService;

    /**
     * 复核查询
     * @param recheckSearchDto
     * @return
     */
    @PostMapping(value = "recheckSearch", name = "复核结果查询")
    public Response<?> recheckSearch(@RequestBody RecheckSearchDto recheckSearchDto) {
        //限制查询时间区间
        Response response = recheckSearchService.astrictSearchTime(recheckSearchDto);
        if (!response.success()) {
            return response;
        }
        Map<String, Object> dataMap = new HashMap<>();
        try {
            //搜索数据
            List<RecheckSearchData> recheckDataList = recheckSearchService.search(recheckSearchDto);
            dataMap.put("recheckDataList", recheckDataList);
            dataMap.put("totalNum", recheckDataList.size());
            response.setData(dataMap);
            return response;
        } catch (Exception e) {
            logger.error(String.format("database operation error: %s", e));
            return ResponseGenerator.genErrorResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
        }
    }

    /**
     * 复核结果导出
     * @param recheckResultExportDto
     * @return
     */
    @PostMapping(value = "export/recheckResult", name = "复核结果导出")
    public Response<?> export(@RequestBody RecheckResultExportDto recheckResultExportDto) {
        Response response = recheckResultExportService.executePacking(recheckResultExportDto);


//        //限制查询时间区间
//        Response response = recheckResultExportService.astrictSearchTime(recheckResultExportDto);
//        if (!response.success()) {
//            return response;
//        }
//        //搜索数据
//        try {
//            List<RecheckResultExportData> recheckDataList = recheckResultExportService.search(recheckResultExportDto);
//        } catch (Exception e) {
//            logger.error(String.format("database operation error: %s", e));
//            return ResponseGenerator.genErrorResp(ExceptionInfo.DATABASE_OPERATION_ERROR);
//        }
//        //打包数据

        return ResponseGenerator.genSuccessResp();
    }
}
