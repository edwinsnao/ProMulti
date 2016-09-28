package com.example.king.fragement.main.mywork.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.adapter.FragmentAdapter;
import com.example.king.fragement.main.mywork.config.Config;
import com.example.king.fragement.main.mywork.config.GlobalConfig;
import com.example.king.fragement.main.mywork.model.Activity1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.HttpUtil;


/**
 * Created by Kings on 2016/5/4.
 */
public class HomeFragment extends Fragment {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private ViewPager viewPager;
    private String pageTime = new String();
    ImageView circle;
    TextView huoyuedu,popularity,fens,guanzhu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment_mywork, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Map<String, String> map = new HashMap<>();
        map.put("userId", GlobalConfig.getUser(getActivity()).getUserId());
        map.put("token", GlobalConfig.getUser(getActivity()).getToken());
        if (!pageTime.isEmpty()) {
            map.put("pageTime", pageTime);
        }
        GetDataTask task = new GetDataTask(getActivity());
        task.execute(map);
        initView(view);
    }

    private void initView(View v) {
//        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabstrip);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        circle = (ImageView) v.findViewById(R.id.circle);
        huoyuedu = (TextView) v.findViewById(R.id.huoyuedu);
        huoyuedu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        fens = (TextView) v.findViewById(R.id.fens);
        fens.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        guanzhu = (TextView) v.findViewById(R.id.guanzhu);
        guanzhu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        popularity = (TextView) v.findViewById(R.id.renqi);
        popularity.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
//        tabs.setViewPager(viewPager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setupViewPager(ViewPager upViewPager) {
        fragments.add(new ListFragment());
        fragments.add(new PicFragment());
        fragments.add(new DocFragment());
        titles.add("");
        titles.add("");
        titles.add("");
        FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(),
                fragments, titles);
        viewPager.setAdapter(adapter);
    }

    class GetDataTask extends AsyncTask<Map<String, String>, String, String> {
        private Context context;

        public GetDataTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            String result = HttpUtil.sendPostMessage(
                    "http://121.42.202.164:8080/bph_social/userController/selectMyInfo",
                    map, "utf-8");
            Log.e("result0", result);
            return result;
        }

        /**
         * 此处更改UI
         */
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                int code = json.getInt("code");
                if (code == Config.SUCCESS) {
                    JSONObject item = json.getJSONObject("item");
                    Gson gson = new Gson();
                    String json1 = item.toString();
                    Activity1 activity = gson.fromJson(json1, Activity1.class);
//                        images = activity.getImages();
//                    URL myFileURL;
//                    Bitmap bitmap=null;
//                    try{
//                        myFileURL = new URL(activity.getHeadPortrait());
//                        //获得连接
//                        HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
//                        //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
//                        conn.setConnectTimeout(6000);
//                        //连接设置获得数据流
//                        conn.setDoInput(true);
//                        //不使用缓存
//                        conn.setUseCaches(false);
//                        //这句可有可无，没有影响
//                        //conn.connect();
//                        //得到数据流
//                        InputStream is = conn.getInputStream();
//                        //解析得到图片
//                        bitmap = BitmapFactory.decodeStream(is);
//                        //关闭数据流
//                        is.close();
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                        circle.setImageBitmap(bitmap);
//                    Glide.with(getActivity()).load(activity.getHeadPortrait()).fitCenter().into(circle);
                    Glide.with(context).load(activity.getHeadPortrait()).asBitmap().centerCrop().into(new BitmapImageViewTarget(circle) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            circle.setImageDrawable(circularBitmapDrawable);
                        }
                    });
                    huoyuedu.setText("活跃度:"+String.valueOf(activity.getPopularity()));
                    popularity.setText("人气:"+String.valueOf(activity.getPopularity())+"万人");
                    fens.setText("粉丝:"+String.valueOf(activity.getFansNum()));
                    guanzhu.setText("粉丝:"+String.valueOf(activity.getAttentionNum()));
                } else {
                    Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, "数据解析错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
