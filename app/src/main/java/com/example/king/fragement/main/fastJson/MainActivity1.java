package com.example.king.fragement.main.fastJson;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/4/9.
 */
public class MainActivity1 extends AppCompatActivity {
    private Context context;
//    private String url="http://www.weather.com.cn/data/sk/101280101.html";
    private String url="http://api.qingyunke.com/api.php?key=free&appid=0&msg=天气广州";
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        context=this;
        text=(TextView)this.findViewById(R.id.show);
        RequestQueue mQueue = Volley.newRequestQueue(context);
        FastJsonRequest1 fastJson=new FastJsonRequest1(url, WeatherInfo.class,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String weather) {
                        // TODO Auto-generated method stub
//                        WeatherInfo weatherInfo = weather.getWeatherInfo();
                        WeatherInfo weatherInfo = JSON.parseObject(weather.toString(),WeatherInfo.class);
                        text.setText(weatherInfo.getContent());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        });
        mQueue.add(fastJson);




    }
}