package com.example.king.fragement.main.mywork.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.king.fragement.R;


public class ForgetKeyWordActivity extends FragmentActivity {

    private String phone;
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_key_word);
    }
}
