package org.alee.compinent.remover.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/8/5
 * @description: xxxx
 *
 *********************************************************/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatedClick {
    /**
     * 两次点击间隔
     *
     * @return 单位毫秒
     */
    long interval() default 1000;
}
