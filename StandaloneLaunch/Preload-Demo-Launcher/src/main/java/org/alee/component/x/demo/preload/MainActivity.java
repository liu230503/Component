package org.alee.component.x.demo.preload;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/23
 * @description: xxxx
 *
 *********************************************************/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(() -> {
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            MainActivity.this.startService(new Intent(MainActivity.this, MyService.class));
        }).start();
    }
}
