package org.alee.component.demo.remover;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/8/5
 * @description: xxxx
 *
 *********************************************************/
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
class Test implements AutoCloseable  {
    @Override
    public void close() throws Exception {
        ConcurrentMap map = new ConcurrentHashMap<>();
    }
}
