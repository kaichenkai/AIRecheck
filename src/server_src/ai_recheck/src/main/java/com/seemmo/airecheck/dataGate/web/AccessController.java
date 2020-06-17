package com.seemmo.airecheck.dataGate.web;

import com.seemmo.airecheck.core.Response;
import com.seemmo.airecheck.core.ResponseGenerator;
import com.seemmo.airecheck.core.ExceptionInfo;
import com.seemmo.airecheck.dataGate.model.dto.TrafficIllegalRecordCreateDto;
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
    public Response<?> add(@RequestBody TrafficIllegalRecordCreateDto trafficIllegalRecordCreateDto) {
        try {
            Response response = accessService.checkArgs(trafficIllegalRecordCreateDto);
            if (response.success()) {
                if(logger.isDebugEnabled()){
                    logger.debug("CHECKRECORDID:illegallogic,stored,recordId:{}", trafficIllegalRecordCreateDto.getRecordId());
                }
            }
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseGenerator.genErrorResp(ExceptionInfo.SERVER_INTERNAL_ERROR);
        }
    }
}
