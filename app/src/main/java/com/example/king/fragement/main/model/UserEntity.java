package com.example.king.fragement.main.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/19.
 */
public class UserEntity implements Serializable {
    private String userId;
    private String nickName;
    private String headPortrait;
    private String residence;//居住地
    private String sex;
    private String aboutMe;
    private String stature;//身高
    private String weight;//体重
    private String race;//种族
    private String constellation;//星座
    private String job;//工作
    private String age;//年龄
    private String sexuality;//性取向
    private String token;
    private int isUpdateInfo;//第一次是否已经更新本人信息

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", token='" + token + '\'' +
                ", constellation='" + constellation + '\'' +
                ", stature='" + stature + '\'' +
                ", weight='" + weight + '\'' +
                ", age='" + age + '\'' +
                ", job='" + job + '\'' +
                ", sexuality='" + sexuality + '\'' +
                ", race='" + race + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getStature() {
        return stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSexuality() {
        return sexuality;
    }

    public void setSexuality(String sexuality) {
        this.sexuality = sexuality;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getIsUpdateInfo() {
        return isUpdateInfo;
    }

    public void setIsUpdateInfo(int isUpdateInfo) {
        this.isUpdateInfo = isUpdateInfo;
    }
}
