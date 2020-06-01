package com.seemmo.airecheck.dataGate.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kaichenkai
 * @create: 6/1/2020 16:15
 *
 * 数据接入类
 */
@RestController
@RequestMapping("/client/access")
public class AccessController {
    @RequestMapping(value = "/illegallogic", method = RequestMethod.GET)
    public String illegallogic(){
        return "test";
    }
}
