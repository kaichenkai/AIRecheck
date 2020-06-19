package com.seemmo.airecheck.config.constant;

/**
 * @Description 文档对象模型（Document Object Model，简称DOM）,即一个属性要在页面上的展现形式
 * @date 2016年5月24日 下午4:24:15
 */
public enum DOM {
    TAB("tab"),
    TEXTBOX("textbox"),
    SELECT("select"),
    CHECKBOX("checkbox"),
    TEXTAREA("textarea"),
    //OTHER表示其他，特殊的展现情况
    OTHER("other"),
    UPLOAD("upload"),
    NONE("");

    private String value;

    private DOM(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
