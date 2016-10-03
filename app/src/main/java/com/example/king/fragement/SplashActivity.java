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
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edwinsnao.midea.CommonException;
import com.edwinsnao.midea.Constaint;
import com.example.king.fragement.main.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.*;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by test on 15-11-4.
 */
public class SplashActivity extends AppCompatActivity{
    private Fade fade ;
    private ImageView img;
    private NewsItemDao mNewsItemDao = BaseApplication.getNewsItemDao();
    private NewsItemBiz mNewsItemBiz = BaseApplication.getNewsItemBiz();
    /*
    * 不用在这里，因为这样会导致splashactivity不能释放
    * */
//    public static List<Map<String,Class>> activities_list = new ArrayList<>(30);
//    public static List<String> time = new ArrayList<>();
//    private static Handler handler = new Handler();
    private final MyHandler handler = new MyHandler(this);

    private class MyHandler extends  Handler{
        private final WeakReference<SplashActivity> mActivity;

        MyHandler(SplashActivity instance){
            mActivity = new WeakReference<SplashActivity>(instance);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (NetUtil.checkNet(getApplicationContext())) {
////                try {
//                List<NewsItem> newsItems = mNewsItemDao.list(Constaint.NEWS_TYPE_YANFA, 1);
////                    if (newsItems.size() == 0 || newsItems == null) {
//                if (newsItems == null || newsItems.size() == 0) {
//                    /**
//                     * 第一次进入时没有数据,则先下载数据
//                     * */
//                    try {
//                        List<NewsItem> newsItem = mNewsItemBiz.getNewsItems(Constaint.NEWS_TYPE_YANFA, 0);
//                        mNewsItemDao.add(newsItem);
//                    } catch (CommonException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    /**
//                     * 不是第一次进入(有数据),则看看有没有新的
//                     * */
//                    getLatestNews(Constaint.NEWS_TYPE_YANFA,0);
//                }
//                List<NewsItem> newsItems1 = mNewsItemDao.list(Constaint.NEWS_TYPE_YIDONG, 1);
//                if (newsItems1 == null || newsItems1.size() == 0) {
//                    /**
//                     * 第一次进入时没有数据,则先下载数据
//                     * */
//                    try {
//                        List<NewsItem> newsItem = mNewsItemBiz.getNewsItems(Constaint.NEWS_TYPE_YIDONG, 0);
//                        mNewsItemDao.add(newsItem);
//                    } catch (CommonException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    /**
//                     * 不是第一次进入(有数据),则看看有没有新的
//                     * */
//                    getLatestNews(Constaint.NEWS_TYPE_YIDONG,0);
//                }
//            }
//            如果没有网络则直接在数据库拿数据
//                else {
//                    List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//                    /**本地没数据且没网络（第一次进入）*/
//                    if(newsItems == null || newsItems.size() == 0) {
//                        /**什么都不做*/
//                        return;
//                    }else {
//                        mAdapter.addAll(newsItems);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
        }

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
        img = (ImageView) findViewById(R.id.splash_img);
        /**
         * 放到runnable里面才可以显示
         * 因为我加了其他两个线程
         * */
//        Animation ani = AnimationUtils.loadAnimation(this,R.anim.splash_translate);
//        img.setAnimation(ani);
//        getWindow().setEnterTransition(fade);
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
        /**
        *不显示图片的,要postdelayed才可以
        * */
//        handler.post(splash);
        handler.postDelayed(splash,500);

    }

    Runnable splash = new Runnable() {
        @Override
        public void run() {
            /**
            * 放到runnable里面才可以显示
            * */
            Animation ani = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_translate);
            img.setAnimation(ani);
            getWindow().setEnterTransition(fade);
//                Intent main = new Intent(SplashActivity.this,ItemListActivity.class);
            if (NetUtil.checkNet(getApplicationContext())) {
//                try {
                List<NewsItem> newsItems = mNewsItemDao.list(Constaint.NEWS_TYPE_YANFA, 1);
//                    if (newsItems.size() == 0 || newsItems == null) {
                if (newsItems == null || newsItems.size() == 0) {
                    /**
                     * 第一次进入时没有数据,则先下载数据
                     * */
                    try {
                        List<NewsItem> newsItem = mNewsItemBiz.getNewsItems(Constaint.NEWS_TYPE_YANFA, 0);
                        mNewsItemDao.add(newsItem);
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    /**
                     * 不是第一次进入(有数据),则看看有没有新的
                     * */
                /**
                * 必须使用线程,否则报很奇怪的错误,说list集合get方法不能在null pointer
                 * 但是我觉得应该是因为网络以及数据库操作不可以在主线程进行才说的过去呀
                 * 所以就是这个原因导致了不能进行网络爬取,所以是nullpointer?
                * */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getLatestNews(Constaint.NEWS_TYPE_YANFA,0);
                        }
                    }).start();
                }
                List<NewsItem> newsItems1 = mNewsItemDao.list(Constaint.NEWS_TYPE_YIDONG, 1);
                if (newsItems1 == null || newsItems1.size() == 0) {
                    /**
                     * 第一次进入时没有数据,则先下载数据
                     * */
                    try {
                        List<NewsItem> newsItem = mNewsItemBiz.getNewsItems(Constaint.NEWS_TYPE_YIDONG, 0);
                        mNewsItemDao.add(newsItem);
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    /**
                     * 不是第一次进入(有数据),则看看有没有新的
                     * */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            getLatestNews(Constaint.NEWS_TYPE_YIDONG,0);
                        }
                    });
                }
            }
//            如果没有网络则直接在数据库拿数据
//                else {
//                    List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//                    /**本地没数据且没网络（第一次进入）*/
//                    if(newsItems == null || newsItems.size() == 0) {
//                        /**什么都不做*/
//                        return;
//                    }else {
//                        mAdapter.addAll(newsItems);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
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


    public void getLatestNews(int newstype,int page){
//                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
        List<NewsItem> newsItems = null;
        try {
//            if(mNewsItemBiz == null)
//                mNewsItemBiz = new NewsItemBiz();
            newsItems = mNewsItemBiz.getNewsItems(newstype, page);
        } catch (CommonException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        if(newsItems == null)
//            return;
//        for(int i = 0 ; i < newsItems.size();i++){
//            Log.e("currentpage",String.valueOf(page));
//            Log.e("title",newsItems.get(i).getTitle());
//            Log.e("date",newsItems.get(i).getDate());
//        }
//                for(int i = 0; i < newsItems.size(); i++)
        NewsItem localLatest = mNewsItemDao.listLatest(newstype);
//        Log.e("currentPage",String.valueOf(page));
//        Log.e("local",String.valueOf(localLatest.getDate()));
//        Log.e("local",String.valueOf(localLatest.getTitle()));
//        Log.e("Compare",String.valueOf(CompareDate(newsItems.get(0).getDate(), localLatest.getDate())));
        /**
         * 网络请求的比本地的时间晚，所以说明是新的新闻
         * */
        if (CompareDate(newsItems.get(0).getDate(), localLatest.getDate()) > 0) {
            mNewsItemDao.add(newsItems);
            getLatestNews(newstype,++page);
        }
    }

    /**
     * 比较时间早晚
     * */
    public  int CompareDate(String DATE1, String DATE2) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1 在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacks(splash);
        handler.removeCallbacksAndMessages(null);
//        handler = null;
    }
}
