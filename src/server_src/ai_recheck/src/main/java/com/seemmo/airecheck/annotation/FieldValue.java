package com.seemmo.airecheck.annotation;

import com.seemmo.airecheck.commons.model.SystemEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编码 转换 为名称
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldValue {

    /**
     * 字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     */
    String value() default "";

    /**
     * 如果是字典类型，请设置字典的type值
     */
    SystemEnums.CODE_TYPE dictType() default SystemEnums.CODE_TYPE.NONE;

    /**
     * 如果是字典类型，则 0-系统，1-国标
     *
     * @return
     */
    String dictSource() default "0";

    /**
     * 反射类型,用于指定反射类
     */
    Class<?> fieldType() default Class.class;

    /**
     * 有反射类型时使用，用于指定反射方法
     *
     * @return
     */
    String methodName() default "";

    /**
     * 字段取值
     *
     * @return
     */
    String fieldValue() default "";
}
