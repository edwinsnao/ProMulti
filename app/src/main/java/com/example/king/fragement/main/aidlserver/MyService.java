package com.example.king.fragement.main.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kings on 2016/4/5.
 */
public class MyService extends Service {
    private MyServiceImpl service;
    public class MyServiceImpl extends IMyService.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int val1,int val2) throws RemoteException {
            return val1+val2;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("bind","bind");
        return service;
//        return new MyServiceImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        service = new MyServiceImpl();
        try {
//            service.add(1,1);
            Toast.makeText(getApplicationContext(), "1+1=:"+service.add(1,1), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Log.e("unbind","unbind");
        super.onDestroy();
    }
}
