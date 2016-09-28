package com.example.king.fragement.main.mywork.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.main.mywork.adapter.ListAdapter;
import com.example.king.fragement.main.mywork.config.Config;
import com.example.king.fragement.main.mywork.config.GlobalConfig;
import com.example.king.fragement.main.mywork.model.Activity1;
import com.example.king.fragement.main.mywork.model.ImageEntity;
import com.example.king.fragement.midea.NewsItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.HttpUtil;
import util.StringConverter;

/**
 * Created by Kings on 2016/5/5.
 */
public class ListFragment extends Fragment{
    private ListAdapter adapter;
    private List<ImageEntity> images = new ArrayList<>();
    private List<Activity1> activities = new ArrayList<>();
    private int currentPage = 1;
    private View mFooterView;
    private String pageTime = new String();
    private boolean mIsLoading = false;
    private boolean scrollTag = true;
    private  boolean scrollFlag = false;
    private ListView list;
    private boolean mMoreDataAvailable = true;
    private final int MAXIMUM_ITEMS = 1000;
    Map<String, String> map;
    private boolean flag = false;

    Runnable mGetActivity = new Runnable() {
        @Override
        public void run() {
            Log.e("currentPage",String.valueOf(currentPage));
            String result = HttpUtil.sendPostMessage(
                    "http://121.42.202.164:8080/bph_social/activityController/myPublishActivity",
                    map, "utf-8");
            try {
                JSONObject json = new JSONObject(result);
                int code = json.getInt("code");
                if (code == Config.SUCCESS) {
                    JSONArray item = json.getJSONArray("item");
                    GsonBuilder gb = new GsonBuilder();
                    gb.registerTypeAdapter(String.class, new StringConverter());
                    Gson gson = gb.create();
                    for (int i = 0; i < item.length(); i++) {
                        String json1 = item.get(i).toString();
                        Activity1 activity = gson.fromJson(json1, Activity1.class);
                        images = activity.getImages();
                        activities.add(activity);
                    }
                    Log.e("activitiesSize", activities.size() + "");
                    adapter.notifyDataSetChanged();
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    mIsLoading = false;
                    break;
                case 2:
                    mIsLoading = false;
                    scrollTag = false;
                    list.removeFooterView(mFooterView);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);
//        initView(v);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!flag) {
            flag = true;
            map = new HashMap<>();
            map.put("userId", GlobalConfig.getUser(getActivity()).getUserId());
            map.put("token", GlobalConfig.getUser(getActivity()).getToken());
            map.put("pageNo", currentPage + "");
            if (!pageTime.isEmpty()) {
                map.put("pageTime", pageTime);
            }
            GetActivityTask task = new GetActivityTask(getActivity());
            task.execute(map);
            initView(view);

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View v) {
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
        adapter = new  ListAdapter(getActivity(),activities,images);
        list = (ListView) v.findViewById(R.id.lv_fragment);
        list.addFooterView(mFooterView,null,false);
        list.setAdapter(adapter);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        scrollFlag = false;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                if (!mIsLoading && mMoreDataAvailable) {
                    if (totalItemCount >= MAXIMUM_ITEMS) {
                        mMoreDataAvailable = false;
                        list.removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
                    } else if (scrollTag && totalItemCount-1 <= firstVisibleItem + visibleItemCount)
//        else
                    {
                        mIsLoading = true;
                        currentPage++;
                        map.put("pageNo", currentPage + "");
                        //                 如果新闻已经加载了则从数据库加载的
                        handler.postDelayed(mGetActivity, 1000);
                    }

                }
            }
        });
    }

    class GetActivityTask extends AsyncTask<Map<String, String>, String, String> {
        private Context context;

        public GetActivityTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            /*
            * {"code":200,"msg":"success","item":[{"activityId":"a20160427104956744868","address":"嗯哪里","startTime":"2016-04-27",
            * "endTime":"2016-05-12","title":"巴克莱","state":0,"type":3,"createTime":"2016-04-27
            * 10:49:56","reason":"啊旅途","labels":[],"images":[{"id":127,"activityId":"a20160427104956744868",
            * "image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160427104956953091.jpg"}],
            * "user":{"userId":"u20160318123459","nickName":"方面","headPortrait":"
            * http://image-social.oss-cn-qingdao.aliyuncs.com/20160426221707431934.jpg",
            * "aboutMe":"--","stature":"194","weight":"94","sex":"男","residence":"中国",
            * "race":"--","constellation":"--","job":"--","sexuality":null,"isUpdateInfo":1,
            * "popularity":0,"attentionNum":1,"fansNum":1,"inactive":0,"age":"49"},"involvement":1,
            * "isCollection":0,"limitNumber":6,"joinNumber":0,"popularity":1},
            * {"activityId":"a20160427102926289980",
            * "address":"本离去","startTime":"2016-04-27",
            * "endTime":"2016-05-12","title":"记分卡","state":0,"type":2,"createTime":"2016-04-27 10:29:26","reason":
            * "嗯离去","labels":[],"images":[{"id":124,"activityId":"a20160427102926289980","
            * image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160427102925438393.jpg"
            * },{"id":125,"activityId":"a20160427102926289980","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160427102926723161.jpg"
            * },{"id":126,"activityId":"a20160427102926289980","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160427102926672500.jpg
            * "}],"user":{"userId":"u20160318123459","nickName":"方面","headPortrait":"http://image-social.oss-cn-qingdao.aliyuncs.com/2016042622
            * 1707431934.jpg","aboutMe":"--","stature":"194","weight":"94","sex":"男","residence":"中国","race":"--","constellation":"--","job":"
            * --","sexuality":null,"isUpdateInfo":1,"popularity":0,"attentionNum":1,"fansNum":1,"inactive":0,"age":"49"},"involvement":1,"isColle
            * ction":0,"limitNumber":23,"joinNumber":0,"popularity":1},{"activityId":"a20160426181938984981","address":"本能","startTime":"2016-
            * -26","endTime":"2016-04-26","title":"出来了","state":0,"type":2,"createTime":"2016-04-26 18:19:38","reason":"抹哪里","labels":[],"im
            * ages":[{"id":114,"activityId":"a20160426181938984981","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181938479773"}
            * ,{"id":115,"activityId":"a20160426181938984981","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181938748227"}],"user
            * ":{"userId":"u20160318123459","nickName":"方面","headPortrait":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426221707431934.j
            * pg","aboutMe":"--","stature":"194","weight":"94","sex":"男","residence":"中国","race":"--","constellation":"--","job":"--","sexuality":
            * null,"isUpdateInfo":1,"popularity":0,"attentionNum":1,"fansNum":1,"inactive":0,"age":"49"},"involvement":1,"isCollection":0,"limitNumbe
            * r":6,"joinNumber":0,"popularity":2},{"activityId":"a20160426181527832975","address":"本快乐","startTime":"2016-05-03","endTime":"2016-0
            * 5-04","title":"北京魔力","state":0,"type":2,"createTime":"2016-04-26 18:15:27","reason":"恶魔姐姐","labels":[],"images":[{"id":110,"
            * ":"a20160426181527832975","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181526276059"},{"id":111,"activityId":"a2016
            * 0426181527832975","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181527460616"},{"id":112,"activityId":"a201604261815
            * 27832975","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181527635909"},{"id":113,"activityId":"a20160426181527832975
            * ","image":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426181527787928"}],"user":{"userId":"u20160318123459","nickName":"方面"
            * "headPortrait":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426221707431934.jpg","aboutMe":"--","stature":"194","weight":"94",
            * "sex":"男","residence":"中国","race":"--","constellation":"--","job":"--","sexuality":null,"isUpdateInfo":1,"popularity":0,"
            * ":1,"fansNum":1,"inactive":0,"age":"49"},"involvement":1,"isCollection":0,"limitNumber":7,"joinNumber":0,"popularity":1},{"activityId":
            * "a20160426175652102145
            * */
            String result = HttpUtil.sendPostMessage(
                    "http://121.42.202.164:8080/bph_social/activityController/myPublishActivity",
                    map, "utf-8");
//            Toast.makeText(getActivity(),"result"+result,Toast.LENGTH_SHORT).show();
            Log.e("result0",result);
            try {
                JSONObject json = new JSONObject(result);
                int code = json.getInt("code");
                if (code == Config.SUCCESS) {
//                    List<ImagesEntity>images = new ArrayList<>();
                    JSONArray item = json.getJSONArray("item");
                    GsonBuilder gb = new GsonBuilder();
                    gb.registerTypeAdapter(String.class, new StringConverter());
                    Gson gson = gb.create();
//                    Gson gson = new Gson();
//                    UserEntity user = gson.fromJson(item.toString(), UserEntity.class);
//                    JSONObject object1 = object.getJSONObject("item");
//                    Log.i("code_in_collection", object1.toString());
                    for (int i = 0; i < item.length(); i++) {
                        String json1 = item.get(i).toString();
                        Activity1 activity = gson.fromJson(json1, Activity1.class);
//                        images.add(image);
                        images = activity.getImages();
                        activities.add(activity);
////                        Log.e("imageUrl", image.toString());
//                        Log.e("imageUrl", images.get(i).getImageUrl());
//                        res.add(image.toString());
//                        Log.e("imageSize", images.size() + "");
                    }
                    Log.e("activitiesSize", activities.size() + "");
////                    simpleAdapter = new PicAdapter(getContext());
//                    simpleAdapter.addAll(images);
//                    adapter.addAll(activities);
//                    adapter.addAll1(images);
//                    adapter.notifyDataSetChanged();
//                    SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("userId", user.getUserId());
//                    editor.putString("token", user.getToken());
//                    editor.putString("nickName", user.getNickName());
//                    editor.putString("headPortrait", user.getHeadPortrait());
//                    editor.putBoolean("isLogin", true);
//                    editor.commit();
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * 此处更改UI
         */
        @Override
        protected void onPostExecute(String result) {
            adapter.notifyDataSetChanged();
        }
    }
}
