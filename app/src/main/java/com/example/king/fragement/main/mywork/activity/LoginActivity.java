package com.example.king.fragement.main.mywork.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.config.Config;
import com.example.king.fragement.main.mywork.config.Constance;
import com.example.king.fragement.main.mywork.model.UserEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import util.HttpUtil;

/**
 * Created by Administrator on 2016/3/24.
 */
public class LoginActivity extends Activity {

    private Button btn_cancel;
    private Button btn_login;
    private EditText et_phone;
    private EditText et_pwd;
    private TextView tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mywork);
        setView();
        setViewListener();
    }

    private void setView() {
        btn_cancel = (Button) findViewById(R.id.btn_login_cancel);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_forget = (TextView) findViewById(R.id.tv_forget);
    }

    private void setViewListener() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", et_phone.getText().toString());
                params.put("pwd", et_pwd.getText().toString());
                LoginTask task = new LoginTask(LoginActivity.this);
                task.execute(params);
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, ForgetKeyWordActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.CODE_REGISTER_REQUEST
                && resultCode == Config.CODE_REGISTER_RETURN) {
            //注册成功，直接返回主界面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    class LoginTask extends AsyncTask<Map<String, String>, String, String> {
        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Map<String, String>... params) {
            Map<String, String> map = params[0];
            String result = HttpUtil.sendPostMessage(Constance.LOGIN_URL, map, "utf-8");
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
                    UserEntity user = gson.fromJson(item.toString(), UserEntity.class);
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userId", user.getUserId());
                    editor.putString("token", user.getToken());
                    editor.putString("nickName", user.getNickName());
                    editor.putString("headPortrait", user.getHeadPortrait());
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
