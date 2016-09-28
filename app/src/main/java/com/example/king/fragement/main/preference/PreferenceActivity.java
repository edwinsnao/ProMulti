package com.example.king.fragement.main.preference;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.king.fragement.R;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.BaseActivity;

/**
 * Created by Kings on 2016/3/27.
 */
public class PreferenceActivity extends BaseActivity{
    private  int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            theme = Utils.getAppTheme(this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        setContentView(R.layout.preference);
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
        setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
        bar.setTitle("Preference");
        /*
        * 左上方返回到主界面
        * */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
