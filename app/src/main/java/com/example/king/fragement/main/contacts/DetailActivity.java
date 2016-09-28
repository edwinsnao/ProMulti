package com.example.king.fragement.main.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/2/1.
 */
public class DetailActivity extends AppCompatActivity {
    TextView name;
    TextView phoneNumber;
    TextView homeNumber;
    TextView hintHomeNumber;
    Button call;
    Button send;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
        setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
        bar.setTitle("ContactsDetail");
        /*
        * 左上方返回到主界面
        * */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initView();
        initData();
    }

    private void initData() {
        String contact_name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        phoneNumber.setText(number);
        name.setText(contact_name);
    }

    private void initView() {
        call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("tel:"+phoneNumber.getText().toString());
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(uri);
                startActivity(it);
            }
        });
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动编辑短信的界面
                Uri smsToUri = Uri.parse("smsto:"+phoneNumber.getText().toString());

                Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setType("vnd.android-dir/mms-sms");
                // intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码
                startActivity(intent);
            }
        });
        homeNumber = (TextView) findViewById(R.id.home_number);
        hintHomeNumber = (TextView) findViewById(R.id.hint_homenumber);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        name = (TextView) findViewById(R.id.contact_name);
    }
}
