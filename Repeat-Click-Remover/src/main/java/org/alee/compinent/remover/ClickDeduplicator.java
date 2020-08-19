package org.alee.compinent.remover;

import android.util.LruCache;
import android.view.View;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/8/5
 * @description: 去重器
 *
 *********************************************************/
public class ClickDeduplicator {
    /**
     * 点击事件缓存
     */
    private static LruCache<Integer, Long> sClickCache = new LruCache<>(30);

    /**
     * 判断是否快速点击
     *
     * @param v        被点击的控件
     * @param interval 两次点击事件的最小间隔时间
     * @return 结果
     */
    public static boolean isFastDoubleClick(View v, long interval) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();
        if (null == sClickCache.get(viewId)) {
            saveLastClick(viewId, time);
            return false;
        }
        long timeInterval = Math.abs(time - sClickCache.get(viewId));
        if (timeInterval < interval) {
            return true;
        }
        saveLastClick(viewId, time);
        return false;
    }

    private static void saveLastClick(int viewId, long clickTime) {
        sClickCache.put(viewId, clickTime);
    }
}
