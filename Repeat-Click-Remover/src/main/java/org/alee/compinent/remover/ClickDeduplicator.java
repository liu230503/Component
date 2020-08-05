package org.alee.compinent.remover;

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
     * 最后一次点击时间
     */
    private static long sLastClickTime;
    /**
     * 最后一次点击的view 的id
     */
    private static int sLastClickViewId;

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
        long timeInterval = Math.abs(time - sLastClickTime);
        if (sLastClickViewId == viewId && timeInterval < interval) {
            return true;
        }
        sLastClickTime = time;
        sLastClickViewId = viewId;
        return false;
    }
}
