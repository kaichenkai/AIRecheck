package com.seemmo.airecheck.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 标记一个属性字段是被忽略的。例如我们建立的配置对象后，或多或少会有些辅助成员属性，是不需要
 * 反写入数据库配置表中的，因此使用该属性进行标记。举个例子：
 * <pre>
 *    &#064;Ignore
 *    public String fieldName {
 *        ...
 *    }
 * </pre>
 * @author ming.xin
 * @date 2016年4月29日 下午3:09:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Ignore {
}