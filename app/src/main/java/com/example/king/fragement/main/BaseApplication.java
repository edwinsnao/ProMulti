package com.example.king.fragement.main;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

//import com.squareup.leakcanary.LeakCanary;
import com.example.king.fragement.AboutUs;
import com.example.king.fragement.BroadcastTest;
import com.example.king.fragement.FileTest;
import com.example.king.fragement.JniTest;
import com.example.king.fragement.MapsActivity;
import com.example.king.fragement.MapsActivity1;
import com.example.king.fragement.NewsFragement;
import com.example.king.fragement.OsLogin;
import com.example.king.fragement.QueryProcess;
import com.example.king.fragement.SettingsActivity;
import com.example.king.fragement.main.aidlserver.Client;
import com.example.king.fragement.main.crypto.Crypto;
import com.example.king.fragement.main.crypto.KeyManager;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.maps.TraceDao;
import com.example.king.fragement.main.music_player.HomeActivity;
import com.example.king.fragement.main.mywork.activity.PreviewActivity;
import com.example.king.fragement.main.nfc.NFCard;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.picTest.ChoosePic;
import com.example.king.fragement.main.sensor.IBMEyes;
import com.example.king.fragement.main.webview.TestConvertHtml;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.DBHelper;
import com.example.king.fragement.midea.NewsItemBiz;
import com.example.king.fragement.midea.NewsItemDao;
//import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kings on 2016/1/28.
 * 用以全局传递数据
 *
 */
public class BaseApplication extends Application {
    private boolean isNightMode = false;
    private static List<Map<String,Class>> activities_list = new ArrayList<>(28);
    private static List<String> time = new ArrayList<>();
    private final static NewsItemBiz mNewsItemBiz = new NewsItemBiz();
    private static Crypto mCrypto;
    private static KeyManager km;

    public static KeyManager getKm() {
        return km;
    }

    public static Crypto getmCrypto() {
        return mCrypto;
    }
    //    private static  DBHelper mDbHelper;

    public static TraceDao getTraceDao() {
        return traceDao;
    }

    public static void setTraceDao(TraceDao traceDao) {
        BaseApplication.traceDao = traceDao;
    }

    /**
    * wrong

    * */
//    private static  DBHelper mDbHelper = new DBHelper(BaseApplication.this);
    private   DBHelper mDbHelper = new DBHelper(BaseApplication.this);
    private   static com.example.king.fragement.main.maps.DBHelper mTraceDbHelper;
    private static TraceDao traceDao;
//    private static final NewsItemDao mNewsItemDao = new NewsItemDao(BaseApplication.this);
    private static  NewsItemDao mNewsItemDao;

    public static com.example.king.fragement.main.maps.DBHelper getTraceDbHelper() {
        return mTraceDbHelper;
    }

    public boolean isNightMode(){
        return isNightMode;
    }
    public void setIsNightMode(boolean isNightMode){
        this.isNightMode = isNightMode;
    }

    public static NewsItemBiz getNewsItemBiz(){
        return mNewsItemBiz;
    }

    public static List<Map<String, Class>> getActivities_list() {
        return activities_list;
    }

    public static List<String> getTime() {
        return time;
    }

    public static NewsItemDao getNewsItemDao(){
        return mNewsItemDao;
    }

    public  DBHelper getDBHelper(){
        return mDbHelper;
    }

    private void initData() {
//        Map<String,Class> name_activities = new HashMap<>();
        Map<String,Class> name_activities = new ConcurrentHashMap<>();
//        SparseArray<Class> name_activities = new SparseArray<>();
        name_activities.put("DialogPra", com.example.king.fragement.main.DialogPra.class);
//        name_activities.put(0,com.example.king.fragement.main.DialogPra.class);
        activities_list.add(name_activities);
//        activities.add(DialogPra.class);
        time.add("2016-1-15");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("QueryProcess",QueryProcess.class);
//        name_activities.put(1,QueryProcess.class);
        activities_list.add(name_activities);
//        activities.add(QueryProcess.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Download",com.example.king.fragement.MainActivity.class);
//        name_activities.put(2,com.example.king.fragement.MainActivity.class);
        activities_list.add(name_activities);
//        activities.add(MainActivity.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("AboutUs",AboutUs.class);
//        name_activities.put(3,AboutUs.class);
        activities_list.add(name_activities);
//        activities.add(AboutUs.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("CSDN_News",NewsFragement.class);
//        name_activities.put(4,NewsFragement.class);
        activities_list.add(name_activities);
//        activities.add(NewsFragement.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("News",Main.class);
//        name_activities.put(5,Main.class);
        activities_list.add(name_activities);
//        activities.add(Main.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
//        name_activities.put(6,MapsActivity.class);
        name_activities.put("Maps",MapsActivity.class);
        activities_list.add(name_activities);
//        activities.add(MapsActivity.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Maps1",MapsActivity1.class);
//        name_activities.put(7,MapsActivity1.class);
        activities_list.add(name_activities);
//        activities.add(MapsActivity1.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Setting",SettingsActivity.class);
//        name_activities.put(8,SettingsActivity.class);
        activities_list.add(name_activities);
//        activities.add(SettingsActivity.class);
        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Midea_News",ItemListActivity.class);
////        name_activities.put(9,ItemListActivity.class);
//        activities_list.add(name_activities);
//        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Jni_Test",JniTest.class);
//        name_activities.put(10,JniTest.class);
        activities_list.add(name_activities);
//        activities.add(ItemListActivity.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("OSLogin",OsLogin.class);
//        name_activities.put(11,OsLogin.class);
        activities_list.add(name_activities);
//        activities.add(OsLogin.class);
        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Midea_News1",ItemListActivity1.class);
////        name_activities.put(12,ItemListActivity1.class);
//        activities_list.add(name_activities);
////        activities.add(ItemListActivity1.class);
//        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("FileTest",FileTest.class);
//        name_activities.put(13,FileTest.class);
        activities_list.add(name_activities);
//        activities.add(FileTest.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Broadcast", BroadcastTest.class);
//        name_activities.put(14,BroadcastTest.class);
        activities_list.add(name_activities);
//        activities.add(BroadcastTest.class);
        time.add("2015-10-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("File_Search", SearchFile.class);
//        name_activities.put(15,SearchFile.class);
        activities_list.add(name_activities);
        time.add("2016-1-21");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("HightLight", HightLight.class);
//        name_activities.put(16,HightLight.class);
        activities_list.add(name_activities);
        time.add("2016-1-22");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Contacts", com.example.king.fragement.main.contacts.MainActivity.class);
//        name_activities.put(17, com.example.king.fragement.main.contacts.MainActivity.class);
        activities_list.add(name_activities);
        time.add("2016-2-1");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("ShowLocation", TencentMaps.class);
//        name_activities.put(18, TencentMaps.class);
        activities_list.add(name_activities);
        time.add("2016-2-3");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("Parcelable & Serializable", PagerActivity.class);
//        name_activities.put(19, PagerActivity.class);
        activities_list.add(name_activities);
        time.add("2016-2-10");
        name_activities = new ConcurrentHashMap<>();
//        name_activities = new SparseArray<>();
        name_activities.put("WifiDirect", WiFiDirectActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-2-29");
//        name_activities = new ConcurrentHashMap<>();
//        name_activities.put("BaiduLocation", LocationDemo.class);
////        name_activities.put(20, WiFiDirectActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-3-24");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("Music", HomeActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-3-27");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("NFC", NFCard.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-3-27");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("AidlServer", com.example.king.fragement.main.aidlserver.MainActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-4-5");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("AidlClient", Client.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-4-5");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("FastJson & Volley", com.example.king.fragement.main.fastJson.MainActivity3.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-4-9");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("ChoosePic", ChoosePic.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-4-10");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("Sensor", IBMEyes.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-4-26");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("MyWork", PreviewActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-5-6");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("OpenGl", com.example.king.fragement.main.opengl.MainActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-5-10");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("Camera", com.example.king.fragement.main.camera.CameraActivity.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-6-4");
        name_activities = new ConcurrentHashMap<>();
        name_activities.put("Html2Bitmap", TestConvertHtml.class);
//        name_activities.put(20, WiFiDirectActivity.class);
        activities_list.add(name_activities);
        time.add("2016-6-7");
//        name_activities.put("Preference", AdvancePreferenceExample.class);
////        name_activities.put(20, WiFiDirectActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-3-4");
    }

    public void onCreate(){
        super.onCreate();
//        LeakCanary.install(this);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setDebugMode(false);
        mCrypto = new Crypto(this);
        km = new KeyManager(getApplicationContext());
        mNewsItemDao = new NewsItemDao(BaseApplication.this);
        mTraceDbHelper = new com.example.king.fragement.main.maps.DBHelper(BaseApplication.this);
        traceDao = new TraceDao();
        initData();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
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
