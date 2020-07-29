package org.alee.component.x.demo.preload;

import android.app.Application;
import android.content.Context;

import org.alee.component.x.preload.Preloader;
import org.alee.component.x.preload.annotation.Preloading;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/23
 * @description: xxxx
 *
 *********************************************************/
public class DemoApplication extends Application {
    @Preloading(value = MainInitializer.class)
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Preloader.createConfig().setRunMode(false);
    }
}
