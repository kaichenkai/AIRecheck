package com.seemmo.airecheck.annotation;

import com.seemmo.airecheck.commons.model.SystemEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel注解定义
 * 主要用于Excel 表中字段标题与实际字段的对应
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 导出字段名（默认调用当前字段的“get”方法，如指定导出字段为对象，请填写“对象名.对象属性”，例：“area.name”、“office.name”）
     */
    String value() default "";
    
    /**
     * 导出字段标题（需要添加批注请用“**”分隔，标题**批注，仅对导出模板有效）
     */
    String title();
    
    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    int type() default 0;

    /**
     * 校验，true - 是  false - 不是
     * @return
     */
    boolean check() default true;

    /**
     * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
     */
    int align() default 0;
    
    /**
     * 导出字段字段排序（升序）
     */
    int sort() default 0;

    /**
     * 是否为图片
     */
    int image() default 0;

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
     * 反射类型
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
     * @return
     */
    String fieldValue() default "";
    
    /**
     * 字段归属组（根据分组导出导入）
     */
    int[] groups() default {};

    /**
     * 是否是超链接
     * @return
     */
    boolean isHyperlink() default false;
}
