package org.alee.component.x.preload;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import org.alee.component.x.preload.exception.PreloadException;
import org.alee.component.x.preload.template.IInitializer;

import java.util.ArrayList;
import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
final class _Preloader {

    private volatile List<IInitializer> mWaitStartUp;

    private volatile List<Class<? extends IInitializer>> mActivated;

    private Context mContext;

    _Preloader() {
        mWaitStartUp = new ArrayList<>();
        mActivated = new ArrayList<>();
    }

    void printActivated() {
        StringBuilder builder = new StringBuilder("初始化顺序为：\r\n");
        for (Class cla : mActivated) {
            builder.append("------[" + cla.getName() + "]\r\n");
        }
        Log.i(Constant.TAG, builder.toString());
    }


    synchronized void startUpOnMainProcess(Context context, Class<? extends IInitializer> cla) {
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(context);
        if (null == info) {
            throw new PreloadException("No current process found!");
        }
        if (TextUtils.equals(info.processName, getAppPackageName(context))) {
            startUpOnCurrentProcess(context, cla, getCurrentProcessId(), info.processName);
        }
    }

    private ActivityManager.RunningAppProcessInfo getCurrentProcessInfo(Context context) {
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processesList = am.getRunningAppProcesses();
        if (null == processesList || 0 >= processesList.size()) {
            return null;
        }
        int pid = getCurrentProcessId();
        for (ActivityManager.RunningAppProcessInfo info : processesList) {
            if (null == info) {
                continue;
            }
            if (pid != info.pid) {
                continue;
            }
            return info;
        }
        return null;
    }

    private String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    synchronized void startUpOnCurrentProcess(Context context, Class<? extends IInitializer> cla, int processId, String processName) {
        mContext = context;
        if (mActivated.contains(cla)) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            if (TextUtils.isEmpty(processName)) {
                processName = getCurrentProcessInfo(context).processName;
            }
            IInitializer initializer = cla.getDeclaredConstructor().newInstance();
            mWaitStartUp.add(initializer);
            startUpDependencies(initializer, processId, processName);
            try {
                if (TextUtils.equals(getAppPackageName(context), processName)) {
                    initializer.initOnMainProcess(context);
                } else {
                    initializer.initOnOtherProcess(context, processId, processName);
                }
                mActivated.add(initializer.getClass());
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                mWaitStartUp.remove(initializer);
            }
        } catch (Throwable ignore) {
            throw new PreloadException(ignore);
        } finally {
            if (Launcher.getInstance().isRunInDebug()) {
                Log.i(Constant.TAG, "[" + cla.getName() + "] 初始化耗时 " + (System.currentTimeMillis() - start) + " 毫秒");
            }
        }
    }

    private int getCurrentProcessId() {
        return Process.myPid();
    }

    /**
     * 启动依赖关系 此处目前才用的深度优先递归 后期计划修改为广度优先递归
     *
     * @param initializer
     * @param processId
     * @param processName
     */
    private void startUpDependencies(@Nullable IInitializer initializer, int processId, String processName) {
        List<Class<? extends IInitializer>> classList = initializer.getDependencies(processId, processName);
        if (null != classList && 0 < classList.size()) {
            for (Class<? extends IInitializer> cla : classList) {
                startUpOnCurrentProcess(mContext, cla, processId, processName);
            }
            return;
        }

        List<String> stringList = initializer.getDependencies(processName, processId);
        if (null == stringList || 0 >= stringList.size()) {
            return;
        }
        classList = new ArrayList<>();
        for (String path : stringList) {
            Class<? extends IInitializer> cla = Launcher.getInstance().searchByPath(path);
            if (null == cla) {
                continue;
            }
            classList.add(cla);
        }
        for (Class<? extends IInitializer> cla : classList) {
            startUpOnCurrentProcess(mContext, cla, processId, processName);
        }
    }

    synchronized void startUpOnSpecifyProcess(Context context, Class<? extends IInitializer> cla, String... processNames) {
        if (null == processNames || 0 >= processNames.length) {
            throw new PreloadException("When Process.Type is SPECIFY_PROCESS, the process name must be specified");
        }
        ActivityManager.RunningAppProcessInfo info = getCurrentProcessInfo(context);
        if (null == info) {
            throw new PreloadException("No current process found!");
        }
        for (String name : processNames) {
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            if (TextUtils.equals(info.processName, name)) {
                startUpOnCurrentProcess(context, cla, getCurrentProcessId(), info.processName);
                return;
            }
        }
    }

}
