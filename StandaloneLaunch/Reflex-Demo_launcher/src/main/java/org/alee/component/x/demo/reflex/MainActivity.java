package org.alee.component.x.demo.reflex;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/24
 * @description: xxxx
 *
 *********************************************************/
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Reflex";
    private TextView mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
//        ReflexDemo reflexDemo = new ReflexDemo();
//        ReflexDemoMapping.mId.set(reflexDemo, 99999);
//        ReflexDemoMapping.doSomething.call(reflexDemo);
//        ReflexDemoMapping.printLog.call(ReflexDemoMapping.TAG.get(), "Id is :" + ReflexDemoMapping.mId.get(reflexDemo));
//        Log.i(TAG, ReflexDemoMapping.doSomething2.call(reflexDemo, "Hi i will do Other") + "");
        List<ResolveInfo> resolveInfos = getPackageManager().queryBroadcastReceivers(new Intent("ACTION_START_BY_SDS"),0);
        for (ResolveInfo info:resolveInfos ){
            if (null==info){
                continue;
            }
            if (info.activityInfo.packageName.equals("com.fawvw.audi.insurance")){
                Intent intent = new Intent("ACTION_START_BY_SDS");
                intent.setComponent(new ComponentName(info.activityInfo.packageName,info.activityInfo.name));
                intent.putExtra("json_result","{\n" +
                        " \"query\": \"打电话\",\n" +
                        " \"serverName\": \"0004\",\n" +
                        " \"messageId\": \"24a24d53-8b13-4409-a2c8-32f1238b8943\",\n" +
                        " \"control\": {\n" +
                        "  \"voiceControl\": \"end\",\n" +
                        "  \"execType\": \"voice_action\"\n" +
                        " },\n" +
                        " \"languageOutput\": {\n" +
                        "  \"displayText\": \"\",\n" +
                        "  \"tts\": \"\"\n" +
                        " },\n" +
                        " \"intent\": \"confirm\",\n" +
                        " \"contextHint\": {},\n" +
                        " \"states\": {\n" +
                        "  \"request\": [],\n" +
                        "  \"semantic\": {\n" +
                        "   \"slots\": {},\n" +
                        "   \"domain\": \"public.phone\",\n" +
                        "   \"confidence\": 0.99825287,\n" +
                        "   \"intent\": \"confirm\"\n" +
                        "  },\n" +
                        "  \"inform\": {\n" +
                        "   \"intent\": \"confirm::confirm\"\n" +
                        "  },\n" +
                        "  \"negate\": {}\n" +
                        " },\n" +
                        " \"asterix\": \"null\",\n" +
                        " \"domain\": \"public.phone\",\n" +
                        " \"clientAction\": {\n" +
                        "  \"auto\": true,\n" +
                        "  \"action\": \"com.mobvoi.semantic.action.insurance.insurance_list\",\n" +
                        "  \"extras\": {\n" +
                        "   \"intent\": \"confirm::confirm\"\n" +
                        "  }\n" +
                        " },\n" +
                        " \"queryTime\": 1583824790262,\n" +
                        " \"clientData\": {},\n" +
                        " \"status\": \"success\"\n" +
                        "}");
//                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                intent.setPackage(info.activityInfo.packageName);
                sendBroadcast(intent);
                Log.i(TAG,"sendBroadcast:"+intent);
            }
        }
    }

    private void getView() {
        mTextView = findViewById(R.id.tv);
        //        mTextView.setText("mValue = " + mValue + "\r\n" + "sIsChanged = " + sIsChanged);
    }
}
