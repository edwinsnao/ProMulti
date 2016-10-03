package com.example.king.fragement.main;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

//import com.squareup.leakcanary.LeakCanary;
import com.example.king.fragement.midea.DBHelper;
import com.example.king.fragement.midea.NewsItemBiz;
import com.example.king.fragement.midea.NewsItemDao;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Kings on 2016/1/28.
 * 用以全局传递数据
 *
 */
public class BaseApplication extends Application {
    private boolean isNightMode = false;
    private final static NewsItemBiz mNewsItemBiz = new NewsItemBiz();
//    private static  DBHelper mDbHelper;
    /**
    * wrong
    * */
//    private static  DBHelper mDbHelper = new DBHelper(BaseApplication.this);
    private   DBHelper mDbHelper = new DBHelper(BaseApplication.this);
//    private static final NewsItemDao mNewsItemDao = new NewsItemDao(BaseApplication.this);
    private static  NewsItemDao mNewsItemDao;

    public boolean isNightMode(){
        return isNightMode;
    }
    public void setIsNightMode(boolean isNightMode){
        this.isNightMode = isNightMode;
    }

    public static NewsItemBiz getNewsItemBiz(){
        return mNewsItemBiz;
    }


    public static NewsItemDao getNewsItemDao(){
        return mNewsItemDao;
    }

    public  DBHelper getDBHelper(){
        return mDbHelper;
    }

    public void onCreate(){
        super.onCreate();
//        LeakCanary.install(this);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setDebugMode(false);
        mNewsItemDao = new NewsItemDao(BaseApplication.this);
//        mDbHelper = new DBHelper(BaseApplication.this);
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
