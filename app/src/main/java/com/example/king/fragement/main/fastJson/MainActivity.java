package com.example.king.fragement.main.fastJson;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.king.fragement.R;

/**
 * Created by Kings on 2016/4/9.
 */
public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private String url="http://www.weather.com.cn/data/sk/101010100.html";
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        mContext = this;
        tv = (TextView) findViewById(R.id.show);
        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        FastJsonRequest<Weather> fastJsonRequest = new
                FastJsonRequest<Weather>(url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, Weather.class, new Response.Listener<Weather>() {
            @Override
            public void onResponse(Weather weather) {
                WeatherInfo weatherInfo = weather.getWeatherInfo();
//                tv.setText(weatherInfo.getCity()+"\n");
//                tv.setText(weatherInfo.getTime()+"\n");
//                tv.setText(weatherInfo.getTmp());
            }
        },null);
        mQueue.add(fastJsonRequest);



    }
}
