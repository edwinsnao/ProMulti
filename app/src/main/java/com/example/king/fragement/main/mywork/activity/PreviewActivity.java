package com.example.king.fragement.main.mywork.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.king.fragement.R;


/**
 * Created by Administrator on 2016/3/24.
 */
public class PreviewActivity extends Activity {

    private SharedPreferences sp =null;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_preview);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setData();
                jump();
                finish();
            }
        },2000);
    }

    private void setData(){
        sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
    }

    private void jump(){
        if(!isLogin){
            Intent intent = new Intent(PreviewActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(PreviewActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }


//    @Override
//    protected void onPause() {
//        super.onPause();
//        JPushInterface.onPause(PreviewActivity.this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        JPushInterface.onResume(PreviewActivity.this);
//    }



}
