package com.seemmo.airecheck.config.annotation;

import com.seemmo.airecheck.config.constant.Level;

import java.lang.annotation.*;

/**
 * 配置文件对象类的配置类型定义属性接口，描述配置主类功能关键值
 * 
 * @author ytf 时间:2016年11月30日 下午3:07:33
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigModel {

    /**
     * 组名称
     */
    String group();

    /**
     * 组的中文说明
     */
    String groupDesc() default "";

    /**
     * 组所对应的修改级别
     */
    Level groupLevel() default Level.NONE;
}
