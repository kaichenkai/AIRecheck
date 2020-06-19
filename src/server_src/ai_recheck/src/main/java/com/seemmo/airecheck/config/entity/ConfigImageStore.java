package com.seemmo.airecheck.config.entity;

import com.seemmo.airecheck.config.annotation.ConfigModel;
import com.seemmo.airecheck.config.annotation.Model;
import com.seemmo.airecheck.config.constant.ConfigResEnum;
import com.seemmo.airecheck.config.constant.Level;
import com.seemmo.airecheck.utils.SysPropertyUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 系统配置项目
 */
@ConfigModel(group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE, groupLevel = Level.RELOAD)
public class ConfigImageStore {
    @NotBlank(message = "不能为空")
    @Model(desc = "图片存放地址", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static String imgRootDir=SysPropertyUtil.getINSTALLDIR()+"/data/image/";

    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "hostCode", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int hostCode=0;

    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "coreThreadNum", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int coreThreadNum=10;
    /**
     * 最大线程数
     */
    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "maxThreadNum", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int maxThreadNum=10;
    /**
     * 最大等待任务数
     */
    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "maxWaitTaskNum", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int maxWaitTaskNum=1000;
    /**
     * 缓存中存放的数据
     */
    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "maxCacheNum", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int maxCacheNum=1000;
    /**
     * 缓存空闲时间
     */
    @Pattern(regexp = "[0-9]+", message = "必须是数字")
    @Model(desc = "cacheIdelTime", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static int cacheIdelTime=6000000;
    /**
     * 临时图片存放地址
     */
    @NotBlank(message = "不能为空")
    @Model(desc = "临时图片存放地址", level = Level.RESTART, group = ConfigResEnum.GROUP_IMAGESTORE, groupDesc = ConfigResEnum.GROUPDESC_IMAGESTORE)
    private static String tempDir=SysPropertyUtil.getINSTALLDIR()+"/data/image/temp";


    public static String getImgRootDir() {
        return imgRootDir;
    }

    public static void setImgRootDir(String imgRootDir) {
        ConfigImageStore.imgRootDir = imgRootDir;
    }

    public static int getHostCode() {
        return hostCode;
    }

    public static void setHostCode(int hostCode) {
        ConfigImageStore.hostCode = hostCode;
    }

    public static int getCoreThreadNum() {
        return coreThreadNum;
    }

    public static void setCoreThreadNum(int coreThreadNum) {
        ConfigImageStore.coreThreadNum = coreThreadNum;
    }

    public static int getMaxThreadNum() {
        return maxThreadNum;
    }

    public static void setMaxThreadNum(int maxThreadNum) {
        ConfigImageStore.maxThreadNum = maxThreadNum;
    }

    public static int getMaxWaitTaskNum() {
        return maxWaitTaskNum;
    }

    public static void setMaxWaitTaskNum(int maxWaitTaskNum) {
        ConfigImageStore.maxWaitTaskNum = maxWaitTaskNum;
    }

    public static int getMaxCacheNum() {
        return maxCacheNum;
    }

    public static void setMaxCacheNum(int maxCacheNum) {
        ConfigImageStore.maxCacheNum = maxCacheNum;
    }

    public static int getCacheIdelTime() {
        return cacheIdelTime;
    }

    public static void setCacheIdelTime(int cacheIdelTime) {
        ConfigImageStore.cacheIdelTime = cacheIdelTime;
    }

    public static String getTempDir() {
        return tempDir;
    }

    public static void setTempDir(String tempDir) {
        ConfigImageStore.tempDir = tempDir;
    }
}
