package com.example.king.fragement.midea;

/**
 * Created by test on 15-11-14.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.edwinsnao.midea.CommonException;
import com.example.king.fragement.R;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.midea.detail.NewsDto;

import java.util.List;


public class NewsContentActivity1 extends AppCompatActivity {


    /**
     * 该页面的url
     */
    private String url;
    private NewsItemBiz mNewsItemBiz;
    private List<NewsItem> mDatas;
    private int theme = 0;

    //    private ProgressBar mProgressBar;
//    private NewsContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            theme= Utils.getAppTheme(this);
        }else{
            theme=savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail1);
        String url = getIntent().getStringExtra("url");
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
        setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
        bar.setTitle("News");
        /*
        * 左上方返回到主界面
        * */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LogWrap.d("url in content"+url);
        TextView tv = (TextView) findViewById(R.id.detail_content);


        NewsItemBiz biz = new NewsItemBiz();
        try {
//            NewsDto newsDto = biz.getNews("http://www.midea.com/cn/news_center/Product_News/index.shtml");
            NewsDto newsDto = biz.getNews(url);

            List<NewsItem> newses = newsDto.getNewses();
//            for(TraceItem news : newses)
//            {
//                System.out.println(news);
//
//            }
            LogWrap.d("size"+ String.valueOf(newses.size()));
//            for(int i = 0 ; i<newses.size();i++)
//            {
//                System.out.println("http://www.midea.com/cn/news_center/Product_News"+newses.get(i).link.substring(1,newses.get(i).link.length()));
            /*
            * 我犯了一个错误就是没有看到setcontentview中是activitmain但不是1
            * 所以找不到textview一直报错nullpointer
            * */
            tv.setText(newses.get(0).getTitle() + "\n");
//            Toast.makeText(NewsContentActivity.this, newses.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            LogWrap.d("tilte"+ "i" + String.valueOf(newses.get(0).getTitle()));
//            }
        } catch (CommonException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(theme== Utils.getAppTheme(this)){
        }else{
            reload();
        }
    }
    public void reload() {
        Intent intent = getIntent();
        LogWrap.d("it in reload:"+String.valueOf(intent));
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
    }
}
