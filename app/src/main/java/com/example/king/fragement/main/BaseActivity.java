package com.example.king.fragement.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;
import android.view.WindowManager;

import com.example.king.fragement.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import com.example.king.fragement.main.utils.TransitionHelper;

/**
 * Created by Kings on 2016/1/28.
 */
public class BaseActivity extends AppCompatActivity {
//        implements ImageLoadingListener{
        private BaseApplication mBaseApp = null;
        private WindowManager mWindowManager = null;
        private View mNightView = null;
        private Handler handler = new Handler();
        private WindowManager.LayoutParams mNightViewParam;
        private boolean mIsAddedView;
        private ImageLoader imageloader = ImageLoader.getInstance();
        private List<Bitmap> mBitmaps = new ArrayList<>();

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(getApplicationContext());
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getApplicationContext());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Visibility returnTransition = buildReturnTransition();
//        getWindow().setReenterTransition(returnTransition);
//        finishAfterTransition();
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            mBaseApp = (BaseApplication) getApplication();
//            if (mBaseApp.isNightMode())
//                setTheme(R.style.AppBaseTheme_Night);
//            else
//                setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
//        setTransition();
//            SDKInitializer.initialize(this.getApplicationContext());
//            mIsAddedView = false;
//            if (mBaseApp.isNightMode()) {
//                initNightView();
//                mNightView.setBackgroundResource(R.color.night_mask);
//            }

        }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setTransition() {
        Visibility enterTransition = buildEnterTransition();
        getWindow().setEnterTransition(enterTransition);
    }

    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(700);
//        enterTransition.excludeTarget()
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(700);
//        enterTransition.excludeTarget()
        return enterTransition;
    }

    //    Runnable recreate = new Runnable() {
//        public void run() {
//            recreate();
//        }
//    };
        @Override
        protected void onDestroy() {
//            if (mIsAddedView) {
//                mBaseApp = null;
//                mWindowManager.removeViewImmediate(mNightView);
//                mWindowManager = null;
//                mNightView = null;
//            }
//            handler.removeCallbacks(recreate);
//            handler = null;
            imageloader.clearMemoryCache();
//            cleanBitmapList();
            super.onDestroy();

        }
        public void ChangeToDay() {
            setTheme(R.style.AppTheme);
//            mBaseApp.setIsNightMode(false);
//            mNightView.setBackgroundResource(android.R.color.transparent);
        }
        public void ChangeToNight() {
//            mBaseApp.setIsNightMode(true);
            setTheme(R.style.AppBaseTheme_Night);
//            initNightView();
//            mNightView.setBackgroundResource(R.color.night_mask);
        }

//    @Override
//    public void onLoadingStarted(String imageUri, View view) {
//
//    }
//
//    @Override
//    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//    }
//
//    @Override
//    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//        mBitmaps.add(loadedImage);
//    }
//
//    @Override
//    public void onLoadingCancelled(String imageUri, View view) {
//
//    }


//  public  static void trasitionToActivity(Activity context,Class target){
//      final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(context, true);
//      Intent it = new Intent(context,target);
//      ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context, pairs);
//      context.startActivity(it,transitionActivityOptions.toBundle());
//
//  }

    public void cleanBitmapList(){
        if(mBitmaps.size()>0) {
            for (int i = 0; i < mBitmaps.size(); i++) {
                Bitmap bitmap = mBitmaps.get(i);
                if(bitmap!=null&& !bitmap.isRecycled()){
                    bitmap.recycle();
                }
            }

        }
    }

    /**
         * wait a time until the onresume finish
         */
//        public void recreateOnResume() {
//            handler.postDelayed(recreate, 100);
//        }
//        private void initNightView() {
//            if (mIsAddedView == true)
//                return;
//            mNightViewParam = new LayoutParams(
//                    LayoutParams.TYPE_APPLICATION,
//                    LayoutParams.FLAG_NOT_TOUCHABLE | LayoutParams.FLAG_NOT_FOCUSABLE,
//                    PixelFormat.TRANSPARENT);
//            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//            mNightView = new View(this);
//            mWindowManager.addView(mNightView, mNightViewParam);
//            mIsAddedView = true;
//        }

}
