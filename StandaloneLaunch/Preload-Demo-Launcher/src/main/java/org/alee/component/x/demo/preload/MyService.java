package org.alee.component.x.demo.preload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import org.alee.component.x.preload.Preloader;

import java.lang.reflect.Method;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/23
 * @description: xxxx
 *
 *********************************************************/
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
