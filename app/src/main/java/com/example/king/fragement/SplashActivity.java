package com.example.king.fragement;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.king.fragement.main.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by test on 15-11-4.
 */
public class SplashActivity extends AppCompatActivity{
    Fade fade ;
    /*
    * 不用在这里，因为这样会导致splashactivity不能释放
    * */
//    public static List<Map<String,Class>> activities_list = new ArrayList<>(30);
//    public static List<String> time = new ArrayList<>();
//    private static Handler handler = new Handler();
    private final MyHandler handler = new MyHandler(this);

    private static class MyHandler extends  Handler{
        private final WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity instance){
            mActivity = new WeakReference<SplashActivity>(instance);
        }
//        public MyHandler(){
//        }

    }
    /*wrong*/
//    private static class MyHandler extends  Handler{
//        private WeakReference<SplashActivity> mActivity;
//
//        public MyHandler(SplashActivity instance){
//            mActivity = new WeakReference<SplashActivity>(instance);
//        }
////        public MyHandler(){
////        }
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        fade = new Fade();
        fade.setDuration(700);
        ImageView img = (ImageView) findViewById(R.id.splash_img);
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.splash_translate);
        img.setAnimation(ani);
        getWindow().setEnterTransition(fade);
//        getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setAllowReturnTransitionOverlap(false);
        getWindow().setBackgroundDrawable(null);
//        initData();
//        PackageManager pm = getPackageManager();
//        try{
//            PackageInfo info = pm.getPackageInfo("com.example.king.fragement",0);
//            TextView tv = (TextView) findViewById(R.id.version);
//            tv.setText("Version:"+info);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        handler.postDelayed(splash,1000);

    }

    Runnable splash = new Runnable() {
        @Override
        public void run() {
//                Intent main = new Intent(SplashActivity.this,ItemListActivity.class);
            Intent main = new Intent(SplashActivity.this,com.example.king.fragement.main.MainActivity1.class);
            startActivity(main);
//            startActivity(main, ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
            finish();
            /*
            * 不要结束动画（fade的动画）
            * */
//            getWindow().setExitTransition(fade);
//            finishAfterTransition();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacks(splash);
        handler.removeCallbacksAndMessages(null);
//        handler = null;
    }
}
