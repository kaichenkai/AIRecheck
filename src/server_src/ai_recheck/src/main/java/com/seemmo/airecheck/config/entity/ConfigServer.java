package com.seemmo.airecheck.config.entity;

import com.seemmo.airecheck.config.annotation.ConfigModel;
import com.seemmo.airecheck.config.annotation.Model;
import com.seemmo.airecheck.config.constant.ConfigResEnum;
import com.seemmo.airecheck.config.constant.DOM;
import com.seemmo.airecheck.config.constant.Level;
import com.seemmo.airecheck.utils.SysPropertyUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 系统配置项目
 */
@ConfigModel(group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER, groupLevel = Level.RELOAD)
public class ConfigServer {
    /**
     * spring项目启动时的 服务端口
     */
    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "ai_traffic 服务端口", level = Level.RESTART, group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER)
    private static int port = 8081;

    /**
     * 数据保存根目录
     */
    @NotBlank(message = "不能为空")
    @Model(desc = " dataRootDir", level = Level.RESTART, group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER)
    private static String dataRootDir = SysPropertyUtil.getINSTALLDIR()+"/data/";
    /**
     * 统计时间间隔，单位分钟，默认60分钟
     */
//    @Pattern(regexp = "[0-9]+", message = "必须是数字")
//    @Model(desc = "statisticsSpan", level = Level.RESTART, group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER)
//    private static int statisticsSpan = 60;

    /**
     * session 时间超时(单位为s)
     */
//    @Pattern(regexp = "[0-9]+", message = "必须是数字")
//    @Model(desc = "session超时时间(s)", level = Level.RESTART, group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER)
//    private static int sessionTimeout = 1800;

//    @Model(desc = "开启统计", level = Level.RESTART, group = ConfigResEnum.GROUP_SERVER, groupDesc = ConfigResEnum.GROUPDESC_SERVER,dom = DOM.CHECKBOX)
//    private static boolean isStartStatistics = true;

    public static int getPort() {
        return port < 80 ? 8081 : port;
    }

    public static void setPort(int port) {
        ConfigServer.port = port;
    }

    public static String getDataRootDir() {
        return dataRootDir;
    }

    public static void setDataRootDir(String dataRootDir) {
        ConfigServer.dataRootDir = dataRootDir;
    }

//    public static int getStatisticsSpan() {
//        return statisticsSpan;
//    }
//
//    public static void setStatisticsSpan(int statisticsSpan) {
//        ConfigServer.statisticsSpan = statisticsSpan;
//    }
//
//    public static int getSessionTimeout() {
//        return sessionTimeout;
//    }
//
//    public static void setSessionTimeout(int sessionTimeout) {
//        ConfigServer.sessionTimeout = sessionTimeout;
//    }
//
//    public static boolean isIsStartStatistics() {
//        return isStartStatistics;
//    }
//
//    public static void setIsStartStatistics(boolean isStartStatistics) {
//        ConfigServer.isStartStatistics = isStartStatistics;
//    }
}
