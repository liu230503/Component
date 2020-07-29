package org.alee.demo.preload;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.alee.component.x.preload.annotation.InitialMark;
import org.alee.component.x.preload.template.IInitializer;

import java.util.ArrayList;
import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/23
 * @description: xxxx
 *
 *********************************************************/
@InitialMark(Path = "/demo/f")
public class FInitializer implements IInitializer {
    @Override
    public void initOnMainProcess(@NonNull Context context) throws Throwable {
        Log.i("Preload::", "[" + this.getClass().getName() + "] is init On Main Process!");
    }

    @Override
    public void initOnOtherProcess(@NonNull Context context, @NonNull int i, @NonNull String s) throws Throwable {
        Log.i("Preload::", this.getClass().getName() + " is init On Other Process!");
    }

    @Nullable
    @Override
    public List<Class<? extends IInitializer>> getDependencies(@NonNull int i, @NonNull String s) {
        List<Class<? extends IInitializer>> list = new ArrayList<>();
        list.add(GInitializer.class);
        return list;
    }

    @Nullable
    @Override
    public List<String> getDependencies(@NonNull String s, @NonNull int i) {
        return null;
    }
}
