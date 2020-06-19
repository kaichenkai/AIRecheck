package com.seemmo.airecheck.imgstore.entity;

/**
 * This is description
 *
 * @author panie
 * @since 2019/10/22 14:27
 */
public interface EnumsSplitImage {
    /**
     * 用来表示切图标注方式 0- 手动标识，1-算法切图
     */
     enum EnumSplitImageType {
        DEFAULT(0), SUANFA(1);
        private int code;

        public int getCode() {
            return code;
        }

        EnumSplitImageType(int code) {
            this.code = code;
        }
    }

    /**
     * 用来标识是否保存图片到硬盘
     */
    enum EnumImageSaveModel {
        NONE(0), SAVE_IMAGEID(1);
        private int code;

        public int getCode() {
            return code;
        }

        EnumImageSaveModel(int code) {
            this.code = code;
        }
    }
}
