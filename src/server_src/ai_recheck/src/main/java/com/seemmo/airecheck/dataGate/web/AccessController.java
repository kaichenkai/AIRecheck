package com.seemmo.airecheck.dataGate.web;

import com.seemmo.airecheck.core.Result;
import com.seemmo.airecheck.core.ResultGenerator;
import com.seemmo.airecheck.core.ExceptionCode;
import com.seemmo.airecheck.dataGate.web.dto.TrafficIllegalRecordCreateDto;
import com.seemmo.airecheck.dataGate.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: kaichenkai
 * @create: 6/1/2020 16:15
 *
 * 数据接入控制器类
 */
@RestController
@RequestMapping("/client/access")
public class AccessController {
    static Logger logger = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private AccessService accessService;

    @PostMapping("illegallogic")
    public Result<?> add(@RequestBody TrafficIllegalRecordCreateDto trafficIllegalRecordCreateDto) {
        try {
            Result result = accessService.checkArgs(trafficIllegalRecordCreateDto);
            if (result.success()) {
                if(logger.isDebugEnabled()){
                    logger.debug("CHECKRECORDID:illegallogic,stored,recordId:{}", trafficIllegalRecordCreateDto.getRecordId());
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultGenerator.genFailResult(ExceptionCode.Unknown.code, ExceptionCode.Unknown.message);
        }
    }
}
