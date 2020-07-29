package org.alee.component.x.demo.reflex;

import android.util.Log;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/24
 * @description: 用于被反射调用
 *
 *********************************************************/
public class ReflexDemo {

    private int mId = 0xff;

    private static final String TAG = "Reflex";

    private void doSomething(){
        printLog(TAG,"I will do something!");
    }

    private Boolean doSomething(String something){
        printLog(TAG,something);
        return true;
    }

    private static void printLog(String tag,String message){
        Log.i(tag,message);
    }
}
