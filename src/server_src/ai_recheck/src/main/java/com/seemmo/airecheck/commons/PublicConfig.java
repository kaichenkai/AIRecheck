package com.seemmo.airecheck.commons;

import com.seemmo.airecheck.config.entity.ConfigImageStore;
import com.seemmo.airecheck.config.entity.ConfigServer;
import org.springframework.stereotype.Component;

/**
 * 全局配置
 */
@Component
public class PublicConfig {

    private String imgRootDir = ConfigImageStore.getImgRootDir();
    private String recordRootDir = ConfigServer.getDataRootDir();


    public String getRecordRootDir() {
        return recordRootDir;
    }

    public void setRecordRootDir(String recordRootDir) {
        this.recordRootDir = recordRootDir;
    }

    public String getImgRootDir() {
        return imgRootDir;
    }

    public void setImgRootDir(String imgRootDir) {
        this.imgRootDir = imgRootDir;
    }
}
