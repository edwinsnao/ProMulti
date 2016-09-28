package com.example.king.fragement.main;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

//import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Kings on 2016/1/28.
 * 用以全局传递数据
 *
 */
public class BaseApplication extends Application {
    private boolean isNightMode = false;

    public boolean isNightMode(){
        return isNightMode;
    }
    public void setIsNightMode(boolean isNightMode){
        this.isNightMode = isNightMode;
    }

    public void onCreate(){
        super.onCreate();
//        LeakCanary.install(this);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setDebugMode(false);
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
        /*
        * 因为需要api最小14，所以我注销了它了
        * */
//        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });
    }

}
