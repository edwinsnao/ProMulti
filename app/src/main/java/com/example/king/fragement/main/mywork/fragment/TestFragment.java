package com.example.king.fragement.main.mywork.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.king.fragement.R;


/**
 * Created by Kings on 2016/5/4.
 */
@SuppressLint("ValidFragment")
public class TestFragment extends Fragment {
    String msg;
    public TestFragment(){

    }
    public TestFragment(String txt){
        msg = txt;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test,container,false);
        initView(v);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    private void initView(View v) {
//        TextView tv = (TextView) v.findViewById(R.id.txt);
//        tv.setText(msg);
    }
}
