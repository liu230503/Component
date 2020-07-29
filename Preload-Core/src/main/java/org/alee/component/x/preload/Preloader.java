package org.alee.component.x.preload;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.alee.component.x.preload.annotation.Preloading;
import org.alee.component.x.preload.exception.ContextException;
import org.alee.component.x.preload.exception.PreloadException;
import org.alee.component.x.preload.template.IInitializer;

import java.lang.reflect.Method;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
public class Preloader {
    private _Preloader mHonestMan;


    private Preloader() {
        mHonestMan = new _Preloader();
    }

    /**
     * 获取单例对象
     *
     * @return {@link Preloader}
     */
    public static Preloader getInstance() {
        return PreloaderHolder.INSTANCE;
    }

    public static Config createConfig() {
        return new Config();
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void prelpading(Application application, String methodName) {
        Preloading preloading = getAnnotation(application.getClass(), methodName);
        if (null == preloading) {
            throw new PreloadException("Unable to find the [ preloading ] annotation successfully!");
        }
        this.prelpading(application, preloading.value(), preloading.ProcessType(), preloading.ProcessName());
    }

    private Preloading getAnnotation(Class<?> cla, String methodName) {
        Preloading target = matchMethod(cla.getDeclaredMethods(), methodName);
        target = null == target ? matchMethod(cla.getMethods(), methodName) : target;
        return target;
    }

    public void prelpading(Context context, Class<? extends IInitializer> cla, @Process.Type int processType, String[] processName) {
        if (null == context) {
            throw new ContextException();
        }
        context = context.getApplicationContext();
        if (null == cla) {
            throw new PreloadException("The original [ Initializer ] cannot be NULL!");
        }
        Launcher.getInstance().init(context);
        long start = System.currentTimeMillis();
        if (Process.SPECIFY_PROCESS == processType) {
            mHonestMan.startUpOnSpecifyProcess(context, cla, processName);
        } else if (Process.MAIN_PROCESS == processType) {
            mHonestMan.startUpOnMainProcess(context, cla);
        } else {
            mHonestMan.startUpOnCurrentProcess(context, cla, -1, null);
        }
        if (Launcher.getInstance().isRunInDebug()) {
            Log.i(Constant.TAG, "初始化共耗时 " + (System.currentTimeMillis() - start) + " 毫秒");
            mHonestMan.printActivated();
        }
    }

    private @Nullable
    Preloading matchMethod(Method[] methods, String methodName) {
        if (null == methods || 0 >= methods.length) {
            return null;
        }
        for (Method method : methods) {
            if (null == method) {
                continue;
            }
            Preloading preloading = method.getAnnotation(Preloading.class);
            if (null == preloading) {
                continue;
            }
            preloading = TextUtils.isEmpty(methodName) ? preloading : TextUtils.equals(methodName, method.getName()) ? preloading : null;
            if (null == preloading) {
                continue;
            }
            return preloading;
        }
        return null;
    }


    public void prelpading(Context context, Class<? extends IInitializer> cla) {
        this.prelpading(context, cla, Process.ALL_PROCESS, null);
    }


    public static class Config {

        public Config setRunMode(boolean isDebug) {
            Launcher.getInstance().setRunInDebug(isDebug);
            return this;
        }

    }


    /**
     * 静态内部类持有外部对象实现单利方式
     */
    private static class PreloaderHolder {
        private static Preloader INSTANCE = new Preloader();
    }


}
