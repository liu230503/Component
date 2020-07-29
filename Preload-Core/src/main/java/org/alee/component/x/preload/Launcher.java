package org.alee.component.x.preload;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.alee.component.x.preload.template.IInitializer;
import org.alee.component.x.preload.template.IInitializerGroup;
import org.alee.component.x.preload.template.IInitializerRoot;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
class Launcher {

    volatile Map<String, Class<? extends IInitializerGroup>> mRootMap = new ConcurrentHashMap<>();

    volatile Map<String, Class<? extends IInitializer>> mGroupMap = new ConcurrentHashMap<>();
    /**
     * 标识是否已经初始化
     */
    private volatile boolean mIsInit;
    /**
     * 运行在Debug模式
     */
    private boolean mRunInDebug;

    private Launcher() {

    }

    /**
     * 获取单例对象
     *
     * @return {@link Launcher}
     */
    static Launcher getInstance() {
        return LauncherHolder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context {@link Context}
     */
    void init(Context context) {
        if (isInit()) {
            return;
        }
        long start = System.currentTimeMillis();
        PreferenceUtil.getInstance().init(context);
        Set<String> routerSet = null;
        try {
            routerSet = loadRootMap(context);
            Log.i(Constant.TAG, "Find root and group map finished, map size = " + routerSet.size() + ", cost " + (System.currentTimeMillis() - start) + " ms.");
            start = System.currentTimeMillis();
            loadInto(routerSet);
            Log.i(Constant.TAG, "Load root and group element finished, cost " + (System.currentTimeMillis() - start) + " ms.");
            mIsInit = true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    boolean isInit() {
        return mIsInit;
    }

    private Set<String> loadRootMap(Context context) throws Throwable {
        Set<String> routerMap = new HashSet<>();
        if (mRunInDebug || AppUtil.isNewVersion(context)) {
            routerMap.addAll(ClassLoader.getFileNameByPackageName(context, Constant.OUT_PUT_PACKAGE_NAME));
            if (!routerMap.isEmpty()) {
                PreferenceUtil.getInstance().putStringSet(Constant.SP_KEY_ROOT_MAP, routerMap);
            }
            AppUtil.updateVersion(context);
        } else {
            routerMap.addAll(PreferenceUtil.getInstance().getStringSet(Constant.SP_KEY_ROOT_MAP));
            if (routerMap.isEmpty()) {
                routerMap.addAll(ClassLoader.getFileNameByPackageName(context, Constant.OUT_PUT_PACKAGE_NAME));
            }
        }
        return routerMap;
    }

    private void loadInto(Set<String> set) throws Throwable {
        for (String name : set) {
            if (TextUtils.isEmpty(name)) {
                continue;
            }
            if (name.startsWith(Constant.AUTOMATIC_GENERATION_PREFIX + Constant.SUFFIX_ROOT)) {
                ClassLoader.<IInitializerRoot>newInstance(name).loadInto(mRootMap);
            } else if (name.startsWith(Constant.AUTOMATIC_GENERATION_PREFIX + Constant.SUFFIX_GROUP)) {
                ClassLoader.<IInitializerGroup>newInstance(name).loadInto(mGroupMap);
            }
        }
    }

    boolean isRunInDebug() {
        return mRunInDebug;
    }

    void setRunInDebug(boolean runInDebug) {
        mRunInDebug = runInDebug;
    }

    Class<? extends IInitializer> searchByPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        for (String key : mGroupMap.keySet()) {
            if (TextUtils.equals(path, key)) {
               return mGroupMap.get(key);
            }
        }
        return null;
    }

    /**
     * 静态内部类持有外部对象实现单利方式
     */
    private static class LauncherHolder {
        private static Launcher INSTANCE = new Launcher();
    }
}
