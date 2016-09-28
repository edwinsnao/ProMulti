package com.example.king.fragement.main.mywork.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.king.fragement.main.mywork.model.UserEntity;


/**
 * Created by Administrator on 2016/3/24.
 */
public class GlobalConfig {
    //使用单例
    public volatile static UserEntity user;

    public static UserEntity getUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (user == null) {
            synchronized (UserEntity.class) {
                if (user == null) {
                    user = new UserEntity();
                    user.setNickName(sp.getString("nickName",""));
                    user.setUserId(sp.getString("userId", ""));
                    user.setToken(sp.getString("token", ""));
                    user.setHeadPortrait(sp.getString("headPortrait",""));
                }
            }
        }
        return user;
    }

}
