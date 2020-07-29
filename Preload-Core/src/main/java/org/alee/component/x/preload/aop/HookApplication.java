package org.alee.component.x.preload.aop;

import android.app.Application;

import org.alee.component.x.preload.Preloader;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/20
 * @description: Application 钩子定义类
 *
 *********************************************************/
@Aspect
public class HookApplication {

    /**
     * 类名- Application
     */
    private static final String CLASS_NAME_APPLICATION = "android.app.Application";

    /**
     * 类名- Application
     */
    private static final String CLASS_NAME_PRELOADING = "org.alee.component.x.preload.annotation.Preloading";


    /**
     * 方法名
     */
    private static final String FUNCTION_NAME_ON_CREATE = "hookOnCreate()";


    @Pointcut(value = "target(" + CLASS_NAME_APPLICATION + ") && @annotation(" + CLASS_NAME_PRELOADING + ")")
    public void hookOnCreate() {

    }

    @Before(FUNCTION_NAME_ON_CREATE)
    public void onCreateProcessor(JoinPoint joinPoint) throws Throwable {
        Object object = joinPoint.getTarget();
        if (!(object instanceof Application)) {
            return;
        }
        Application application = (Application) object;
        Preloader.getInstance().prelpading(application, joinPoint.getSignature().getName());
    }


}
