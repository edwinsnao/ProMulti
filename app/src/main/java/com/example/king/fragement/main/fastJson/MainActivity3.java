package com.example.king.fragement.main.fastJson;

/**
 * Created by Kings on 2016/4/10.
 */
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.king.fragement.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
public class MainActivity3 extends ActionBarActivity {

    private static final int MSG_SUCCESS = 0;//获取天气信息成功的标识
    private static final int MSG_FAILURE = 1;//获取天气信息失败的标识
    private static final int MSG_UPDATE_UI = 2;//更新UI

    private static TextView textView;
    private static ProgressDialog dialog;
    /*
        * 这里拿到的RequestQueue是一个请求队列对象，
        * 它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求。
        * RequestQueue内部的设计就是非常合适高并发的，
        * 因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，
        * 这是非常浪费资源的，基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了。
        * */
    private RequestQueue mQueue;
    //用于更新UI线程的handler
//    private Handler mHandler=new Handler(){
//        //此方法在UI线程中执行
//        @Override
//        public void handleMessage(Message msg) {
//            switch(msg.what){
//                case MSG_SUCCESS:
//                    WeatherInfo weather=(WeatherInfo)msg.obj; //获取消息信息
//                    textView.setText(weather.getContent());
//                    dialog.dismiss();
//                    break;
//                case MSG_FAILURE:
//                    dialog.dismiss();
//                    textView.setText("获取信息失败，请稍后重试");
//                    break;
//                case MSG_UPDATE_UI:
//                    dialog.show();
//                    textView.setText("获取信息失败，请稍后重试");
//                    break;
//            }
//        }
//    };
    private MyHandler mHandler = new MyHandler(this);

    static class MyHandler extends Handler{
        WeakReference<MainActivity3> mActivity;

        MyHandler(MainActivity3 activity){
            mActivity = new WeakReference<MainActivity3>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_SUCCESS:
                    WeatherInfo weather=(WeatherInfo)msg.obj; //获取消息信息
                    textView.setText(weather.getContent());
                    dialog.dismiss();
                    break;
                case MSG_FAILURE:
                    dialog.dismiss();
                    textView.setText("获取信息失败，请稍后重试");
                    break;
                case MSG_UPDATE_UI:
                    dialog.show();
                    textView.setText("获取信息失败，请稍后重试");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);
        textView=(TextView)findViewById(R.id.show);
        mQueue=Volley.newRequestQueue(MainActivity3.this);
//        dialog = new ProgressDialog(getApplicationContext());
        dialog = new ProgressDialog(MainActivity3.this);
        new Thread(new WeatherTest()).start();//在新线程中读取数据并绑定
    }

    /*
     * 读取天气数据使用的新线程
     * 天气接口示例：
     * 		http://api.qingyunke.com/api.php?key=free&appid=0&msg=天气北京
     * */
    class WeatherTest implements Runnable{

        @Override
        public void run() {
            dialog.setMessage("正在获取....");
            mHandler.obtainMessage(MSG_UPDATE_UI).sendToTarget();
            String strURL="http://api.qingyunke.com/api.php?key=free&appid=0&msg=";//请求数据的地址
            String strCity="";
            try {
					/*
					 * 注：volley中的参数必须进行编码，不然会出现乱码问题
					 * */
                strCity=URLEncoder.encode("天气广州","UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            strURL+=strCity;
				/*
				 * 使用Volley中封装的StringRequest对象来发送HTTP请求
				 * StringRequest需要三个参数：
				 * 		0,获取数据的方法
				 * 		1，目标服务器的URL地址
				 * 		2，服务器响应成功的回调
				 * 		3，服务器响应失败的回调
				 * */
            StringRequest stringRequest = new StringRequest(Method.GET,strURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
			                                	/*成功获取到数据之后的处理*/
                            WeatherInfo weatherInfo=null;
                            //将json串转为对象
                            weatherInfo=JSON.parseObject(response.toString(),WeatherInfo.class);
                            //绑定数据过程
                            mHandler.obtainMessage(MSG_SUCCESS,weatherInfo).sendToTarget();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //读取失败的处理
                    mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
                }
            });
            mQueue.add(stringRequest); //将请求添加到请求队列中
        }}
}
