package org.alee.component.x.preload.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: 用于标记  org.alee.component.x.preload.template.IInitializer
 *
 *********************************************************/
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface InitialMark {
    /**
     * 唯一标识 用于作为每个 org.alee.component.x.preload.template.IInitializer的标记
     *
     * @return
     */
    String Path();

    /**
     * 唯一标识 用于标记每个 org.alee.component.x.preload.template.IInitializer 所属组别
     *
     * @return
     */
    String Group() default "";
}
