package com.example.king.fragement.main.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/2/16.
 */
public class HistoryDetail extends Activity {
    ListView lv ;
//    Toolbar toolbar;
    int tag;
    View mFooterView;
    HistoryAdapter mAdapter;
    private List<TraceItem> mDatas = new ArrayList<TraceItem>();
    TraceDao mTraceDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_history);
        initData();
        initView();

    }

    private void initData() {
        tag = getIntent().getIntExtra("choice",1);
//        mTraceDao = new TraceDao(HistoryDetail.this);
        mTraceDao = BaseApplication.getTraceDao();
        HandlerThread thread = new HandlerThread("MyThread");
        thread.start();
//        final Handler handler = new Handler(thread.getLooper());
        final Handler handler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        mAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDatas = mTraceDao.searchData(tag);
                handler.sendEmptyMessage(0);
            }
        };
        handler.post(runnable);
        mAdapter = new HistoryAdapter(HistoryDetail.this,mDatas);
    }

    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar bar = getSupportActionBar();
//        bar.setDisplayHomeAsUpEnabled(true);
//        bar.setHomeButtonEnabled(true);
//        toolbar.setTitle("Detail");
        mFooterView = LayoutInflater.from(HistoryDetail.this).inflate(R.layout.maps_list_footer, null);
        lv = (ListView) findViewById(R.id.detail_lv);
        lv.setAdapter(mAdapter);
        lv.addFooterView(mFooterView);

    }
}
