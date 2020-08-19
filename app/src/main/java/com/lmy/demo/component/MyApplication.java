package com.lmy.demo.component;

import android.app.Application;
import android.content.Context;



/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/20
 * @description: xxxx
 *
 *********************************************************/
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
