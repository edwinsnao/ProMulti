package com.example.king.fragement.main.aidlserver;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/4/5.
 */
public class Client extends AppCompatActivity {
    private int result;
    private Button bind;
    private Button unbind;
    private IMyService myService;
    private TextView tv;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myService = IMyService.Stub.asInterface(iBinder);
            try {
                result = myService.add(1,100);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("connect","connect");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myService = null;
            Log.e("disconnect","disconnect");
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        tv = (TextView) findViewById(R.id.show);
        Intent it  = new Intent();
        it.setClass(Client.this,MyService.class);
        it.setAction("com.android.service");
        it.setPackage("com.example.aidlserver");
        bindService(it,con, Service.BIND_AUTO_CREATE);
//            int result = myService.add();
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(" "+result);
            }
        });
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(con!=null) {
                    unbindService(con);
                    con = null;
                }
            }
        });
//            tv.setText(" "+myService.add(1,1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(con!=null)
            unbindService(con);
    }
}
