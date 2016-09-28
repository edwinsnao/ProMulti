package com.example.king.fragement.main.mywork.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.adapter.DocAdapter;
import com.example.king.fragement.main.mywork.config.Config;
import com.example.king.fragement.main.mywork.config.GlobalConfig;
import com.example.king.fragement.main.mywork.model.UserEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.HttpUtil;

/**
 * Created by Kings on 2016/5/5.
 */
public class DocFragment extends Fragment {
    private List<String> res = new ArrayList<>();
    UserEntity user;
    List<UserEntity> users = new ArrayList<>();
    private String pageTime = new String();
    TextView basic,job,resident,tag,aboutMe;
    DocAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cell_unit_doc, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, String> map = new HashMap<>();
        map.put("userId", GlobalConfig.getUser(getActivity()).getUserId());
        map.put("token", GlobalConfig.getUser(getActivity()).getToken());
        if (!pageTime.isEmpty()) {
            map.put("pageTime", pageTime);
        }
        GetDocTask task = new GetDocTask(getActivity());
        task.execute(map);
    }

    private void initView(View v) {

       basic = (TextView) v.findViewById(R.id.basic);
       job = (TextView) v.findViewById(R.id.job);
       resident = (TextView) v.findViewById(R.id.resident);
       tag = (TextView) v.findViewById(R.id.tag);
       aboutMe = (TextView) v.findViewById(R.id.aboutme);
    }

    class GetDocTask extends AsyncTask<Map<String, String>, String, String> {
        private Context context;

        public GetDocTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            /*
            * selectMyImage返回个人的
            * {"code":200,"msg":"success","item":[{"imageId":2,
            * "imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426201057694264.jpg"
            * ,"userId":"u20160318123459","createTime":"2016-4-27 19:20:20"},{"imageId":28,"
            * imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504102348308283.jpg","userId":"u20160318123459","createTime":"2016-05-04 10:23:53"},
            * {"imageId":27,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504101821820344.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-04 10:18:25"},{"imageId":26,
            * "imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504101057926084.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-04 10:10:58"},{"imageId":25,
            * "imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154419977827.jpg"
            * ,"userId":"u20160318123459","createTime":"2016-05-03 15:44:23"},{"imageId":24,
            * "imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154304978849.jpg","userId":"u20160318123459","createTime":"2016-05-03 15:43:08"},
            * {"imageId":23,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154246355466.jpg"
            * ,"userId":"u20160318123459","createTime":"2016-05-03 15:42:50"},{"imageId":7,
            * "imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160502194450241091.jpg","userId":"u20160318123459",
            * "createTime":"2016-05-02 19:44:52"},{"imageId":6,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160502193953965334.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-02 19:39:56"},{"imageId":5,"imageUrl":
            * "http://image-social.oss-cn-qingdao.aliyuncs.com/20160502173115994102.jpg","userId":"u20160318123459","createTime":"2016-05-02 17:31:15"}]
            * */
            String result = HttpUtil.sendPostMessage(
//                    "http://121.42.202.164:8080/bph_social/activityController/myPublishActivity",
                    /*
                    * {"code":200,"msg":"success","item":{"nickName":"方面"
                    * ,"headPortrait":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426221707431934.jpg","res
                    * idence":"中国","sex":"男","aboutMe":"--","stature":"194","weight":"94","race":"--","constellation":"--","job":"--","
                    * age":"49","sexuality":null,"popularity":0,"attentionNum":1,"fansNum":1,"
                    * inactive":0}}
                    * */
                    "http://121.42.202.164:8080/bph_social/userController/selectMyInfo",
                    map, "utf-8");
//            String result = HttpUtil.sendPostMessage(
//                    "http://121.42.202.164:8080/bph_social/imageController/selectAllImage",
//                    map, "utf-8");
//            Toast.makeText(getActivity(),"result"+result,Toast.LENGTH_SHORT).show();
            /*
            * {"code":200,"msg":"success","item":{"images":[
            * {"imageId":2,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160426201057694264.jpg",
            * "userId":"u20160318123459","createTime":"2016-4-27 19:20:20"},
            * {"imageId":1,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160427102926723161.jpg",
            * "userId":"u20160420130118576920","createTime":"2016-4-27 18:20:20"},
            * {"imageId":28,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504102348308283.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-04 10:23:53"},
            * {"imageId":27,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504101821820344.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-04 10:18:25"},
            * {"imageId":26,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160504101057926084.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-04 10:10:58"},
            * {"imageId":25,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154419977827.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-03 15:44:23"},
            * {"imageId":24,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154304978849.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-03 15:43:08"},
            * {"imageId":23,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154246355466.jpg",
            * "userId":"u20160318123459","createTime":"2016-05-03 15:42:50"},
            * {"imageId":22,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503154009726924.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:40:09"},
            * {"imageId":21,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153431668443.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:34:31"},
            * {"imageId":20,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153345924938.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:33:45"},
            * {"imageId":19,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153333369911.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:33:33"},
            * {"imageId":18,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153315740521.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:33:15"},
            * {"imageId":17,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153300171500.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:33:00"},
            * {"imageId":16,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153220939324.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:32:20"},
            * {"imageId":15,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153203998962.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:32:03"}
            * ,{"imageId":14,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153201651693.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:32:01"},
            * {"imageId":13,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153123950158.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:31:23"},
            * {"imageId":12,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153029902820.jpg",
            * "userId":"u20160421152653438451","createTime":"2016-05-03 15:30:29"},
            * {"imageId":11,"imageUrl":"http://image-social.oss-cn-qingdao.aliyuncs.com/20160503153017538045.jpg"
            * ,"userId":"u20160421152653438451","createTime":"2016-05-03 15:30:17"}],"currentPage":0,"pageTime":null
            * */
            Log.e("result2",result);
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
//                    List<ImagesEntity>images = new ArrayList<>();
                    /*
                    * 用来使用all所有图片的接口，因为这个接口多一个images字段
                    * */
                    JSONObject item = json.getJSONObject("item");
                     /*
                    * 用来使用my我的图片的接口，没有images字段
                    * */
//                    JSONArray item = json.getJSONArray("item");
                    Gson gson = new Gson();
//                    UserEntity user = gson.fromJson(item.toString(), UserEntity.class);
//                    JSONObject object1 = object.getJSONObject("item");
//                    Log.i("code_in_collection", object1.toString());
//                    JSONArray array = item.getJSONArray("images");
                    /*
                    * 用来使用all所有图片的接口，因为这个接口多一个images字段
                    * */
//                    for (int i = 0; i < array.length(); i++) {
                    /*
                    * 用来使用my我的图片的接口，没有images字段
                    * */
                    for (int i = 0; i < item.length(); i++) {
                        String json1 = item.toString();
                        UserEntity user = gson.fromJson(json1, UserEntity.class);
                       basic.setText(user.getAge()+"-"+user.getStature()+"cm-"+
                        user.getWeight()+"-"+user.getSex()+"-"+user.getConstellation());
                       job.setText(user.getSexuality()+"-"+user.getJob());
                       resident.setText(user.getResidence());
                       aboutMe.setText(user.getAboutMe());

//                        String json1 = item.get(0).toString();
//                        Activity1 activity = gson.fromJson(json1, Activity1.class);
//                        user = activity.getUser();
//                       basic.setText(user.getAge()+"-"+user.getStature()+"cm-"+
//                        user.getWeight()+"-"+user.getSex()+"-"+user.getConstellation());
//                       job.setText(user.getSexuality()+"-"+user.getJob());
//                       resident.setText(user.getResidence());
//                       aboutMe.setText(user.getAboutMe());

//                        Log.e("imageUrl", image.toString());
                    }
//                    simpleAdapter = new PicAdapter(getContext());
//                    simpleAdapter.addAll(images);
//                    SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("userId", user.getUserId());
//                    editor.putString("token", user.getToken());
//                    editor.putString("nickName", user.getNickName());
//                    editor.putString("headPortrait", user.getHeadPortrait());
//                    editor.putBoolean("isLogin", true);
//                    editor.commit();
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
