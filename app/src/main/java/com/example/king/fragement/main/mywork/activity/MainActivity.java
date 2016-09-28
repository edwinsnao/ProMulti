package com.example.king.fragement.main.mywork.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fm;
    private HomeFragment homeFragment;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_mywork);
        homeFragment = new HomeFragment();
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.main_container,homeFragment,"Personal");
        transaction.commit();
        transaction.show(homeFragment);

    }
}
