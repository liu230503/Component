package org.alee.component.demo.remover;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.alee.compinent.remover.annotation.RepeatedClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Repeat-Click-Remover";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_click);
        button.setOnClickListener(new View.OnClickListener() {
            @RepeatedClick
            @Override
            public void onClick(View v) {
                Log.i(TAG, v.getId()+"is On Click");
            }
        });
    }
}
