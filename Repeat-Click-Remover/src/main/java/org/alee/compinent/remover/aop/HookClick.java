package org.alee.compinent.remover.aop;

import android.util.Log;
import android.view.View;

import org.alee.compinent.remover.ClickDeduplicator;
import org.alee.compinent.remover.annotation.RepeatedClick;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/8/5
 * @description: xxxx
 *
 *********************************************************/
@Aspect
public class HookClick {

    private static final String CLASS_NAME_ANNOTATION = "org.alee.compinent.remover.annotation.RepeatedClick";

    @Pointcut("execution(@" + CLASS_NAME_ANNOTATION + " * *(..))")
    public void hookOnClickByAnnotation() {

    }

    @Around("hookOnClickByAnnotation()")
    public void onClickByAnnotationProcessor(ProceedingJoinPoint joinPoint) throws Throwable {
        if (null == joinPoint.getArgs() || 0 >= joinPoint.getArgs().length) {
            joinPoint.proceed();
            return;
        }
        View view = null;
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof View) {
                view = (View) o;
                break;
            }
        }
        if (null == view) {
            joinPoint.proceed();
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (null == methodSignature) {
            joinPoint.proceed();
            return;
        }
        Method method = methodSignature.getMethod();
        if (null == method) {
            joinPoint.proceed();
            return;
        }
        RepeatedClick repeatedClick = method.getAnnotation(RepeatedClick.class);
        if (null == repeatedClick) {
            joinPoint.proceed();
            return;
        }
        if (ClickDeduplicator.isFastDoubleClick(view,repeatedClick.interval())){
            return;
        }
        joinPoint.proceed();
    }


}
