package com.example.king.fragement.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/1/21.
 */
public class NoNetWork extends AppCompatActivity {
    String which_activity = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonetwork);
        which_activity = getIntent().getStringExtra("activity");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent it = new Intent();
        if(which_activity!=null && which_activity=="News")
            it.setClass(NoNetWork.this,MideaFragment.class);
        else if(which_activity!=null && which_activity=="News1")
            it.setClass(NoNetWork.this,MideaFragment1.class);
        /*
        * 这里必须是true，表示已经处理了触摸事件
        * */
            return true;
    }
}
