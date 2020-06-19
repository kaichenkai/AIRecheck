package com.seemmo.airecheck.config.constant;

/**
 * 配置项目枚举描述
 */
public interface ConfigResEnum {
    String GROUP_SERVER = "server";
    String GROUPDESC_SERVER = "服务器配置";

    String GROUP_IMAGESTORE = "imageStore";
    String GROUPDESC_IMAGESTORE = "图片存储配置";

    String GROUP_CRONTASK = "crontask";
    String GROUPDESC_CRONTASK = "定时任务配置";

    String GROUP_INSTESV = "instesv";
    String GROUPDESC_INSTESV = "接入配置";

    String GROUP_DEVICE_MANAGER = "device_manager";
    String GROUPDESC_DEVICE_MANAGER = "导入导出配置";

    String GROUP_PUBLISH = "publish";
    String GROUPDESC_PUBLISH  = "上报配置";

    String GROUP_NGINX = "nginx";
    String GROUPDESC_NGINX= "nginx配置";

    String GROUP_NETTY = "netty";
    String GROUPDESC_NETTY= "netty配置";

    String GROUP_TRAFFICTEST = "traffictest";
    String GROUPDESC_TRAFFICTEST= "预审测试配置";

    String GROUP_ILLEGALRELA = "illegalRela";
    String GROUPDESC_ILLEGALRELA= "违法行为关系";

    String GROUP_SYSTEM = "system";
    String GROUPDESC_SYSTEM= "基础配置";

    String GROUP_CORE_ILLEGAL = "DEFAULT";
    String GROUPDESC_CORE_ILLEGAL = "标准";

    String GROUP_TEST = "Test";
    String GROUPDESC_TEST = "测试配置";

    String GROUP_WEB_BUSINESS = "web_business";
    String GROUPDESC_WEB_BUSINESS= "业务配置";
}
