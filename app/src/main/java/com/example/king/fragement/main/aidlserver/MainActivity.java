package com.example.king.fragement.main.aidlserver;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.king.fragement.R;

public class MainActivity extends AppCompatActivity {
    IMyService service;
    Button button;
    Button button1;
    int result ;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = IMyService.Stub.asInterface(iBinder);
            try {
                result = service.add(2,1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_server);
        button = (Button) findViewById(R.id.start);
        button1 = (Button) findViewById(R.id.end);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
                Intent intent = new Intent(MainActivity.this,MyService.class);
//                intent.setAction("com.android.service");
                //Android5.0后service不能采用隐式启动，故此处加上包名
//                intent.setPackage("com.example.aidl");
                //        Intent intent = new Intent("com.android.MYSERVICE");
                /*
                * start方法
                * */
//                startService(intent);
                //  Log.e("MainActivity", "server start");

                /*
                * 使用aidl
                * */
                bindService(intent,con, Service.BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this, "result="+result, Toast.LENGTH_SHORT).show();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.android.service");
                //Android5.0后service不能采用隐式启动，故此处加上包名
//                intent.setPackage("com.example.aidl");
                //        Intent intent = new Intent("com.android.MYSERVICE");
//                stopService(intent);
                if(con!=null) {
                    unbindService(con);
                    con = null;
                }
                //  Log.e("MainActivity", "server start");
            }
        });


        // }}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(con!=null) {
            unbindService(con);
            con = null;
        }
    }
}
