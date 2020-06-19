package com.seemmo.airecheck.config.annotation;

import com.seemmo.airecheck.config.constant.ConfigStatus;
import com.seemmo.airecheck.config.constant.DOM;
import com.seemmo.airecheck.config.constant.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 标注配置字段。其中level为必填字段，该字段是一个枚举类型，see {@link Level}举个例子：
 * <pre>
 *    &#064;Model(level = Level.RESTART)
 *    public String fieldName {
 *        ...
 *    }
 * </pre>
 * status为扩展属性，方便后续版本更新后，标记一个字段已被删除，被删除的配置字段将不被反写入数据库配置表中，同时也会将数据库
 * 中该配置字段进行清除，see {@link ConfigStatus}举个例子：
 * <pre>
 *    &#064;Model(level = Level.RESTART, status = ConfigStatus.DELETEED)
 *    public String fieldName {
 *        ...
 *    }
 * </pre>
 * @date 2016年4月29日 下午2:51:53
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Model {
    /**
     * 字段的中文说明
     */
    String desc() default "";

    /**
     * 字段所对应的修改级别
     */
    Level level();

    /**
     * 字段状态
     */
    ConfigStatus status() default ConfigStatus.NORMAL;

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

    /**
     * 属性的页面dom展现形式
     */
    DOM dom() default DOM.TEXTBOX;

    /**
     * 是否有选项
     *
     * @return
     */
    boolean hasOption() default false;
}