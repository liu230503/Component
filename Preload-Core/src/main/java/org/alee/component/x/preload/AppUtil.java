package org.alee.component.x.preload;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
class AppUtil {
    private static String sNowVersionName;
    private static int sNowVersionCode;

     static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (null == packageInfo) {
            return true;
        }
        sNowVersionName = packageInfo.versionName;
        sNowVersionCode = packageInfo.versionCode;
        String lastVersionName = PreferenceUtil.getInstance().getString(Constant.SP_KEY_LAST_VERSION_NAME);
        int lastVersionCode = PreferenceUtil.getInstance().getInt(Constant.SP_KEY_LAST_VERSION_CODE, -1);
        if ((!TextUtils.equals(sNowVersionName, lastVersionName)) || sNowVersionCode != lastVersionCode) {
            return true;
        } else {
            sNowVersionName = lastVersionName;
            sNowVersionCode = lastVersionCode;
            return false;
        }
    }

     static void updateVersion(Context context) {
        if (TextUtils.isEmpty(sNowVersionName)){
            return;
        }
        if (0>=sNowVersionCode){
            return;
        }
        PreferenceUtil.getInstance().putString(Constant.SP_KEY_LAST_VERSION_NAME,sNowVersionName);
        PreferenceUtil.getInstance().putInt(Constant.SP_KEY_LAST_VERSION_CODE,sNowVersionCode);
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
            Log.e(Constant.TAG, "Get package info error.");
        }
        return packageInfo;
    }
}
