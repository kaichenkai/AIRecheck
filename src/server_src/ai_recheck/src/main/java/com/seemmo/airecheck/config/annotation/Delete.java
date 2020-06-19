package com.seemmo.airecheck.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 标记一个属性字段是应该被反写删除的。例如我们建立配置对象后，产品总会把配置挪来挪去的. 因此在反写数据库时，发现没用的属性，我们应把它删掉.
 * @author PeiChongEn
 * @date 2017年11月16日15:58:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Delete {
}
