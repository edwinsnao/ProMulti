package com.example.king.fragement.main.model;

import java.io.Serializable;
import java.util.List;

public class Activity1 implements Serializable {
    private String activityId;//活动id
    private String address;//活动地址
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private String title;//活动标题
    private int state;//活动的状态
    private int type;//活动的消费类型
    private String createTime;//活动创建时间
    private String reason;//发布活动的原因
    private List<LabelsEntity> labels;//活动标签
    private List<ImageEntity> images;//活动图片
    private UserEntity userId;//活动发布者
    private int involvement;//活动参与状态
    private int isCollection;//是否收藏
    private int limitNumber;//限制人数
    private int joinNumber;//具体人数
    private int popularity;//人气
    private int which_one;

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    private String image;

    public int getWhich_one() {
        return which_one;
    }

    public void setWhich_one(int which_one) {
        this.which_one = which_one;
    }

    @Override
    public String toString() {
//        return "Activity1{" +
//                "activityId='" + activityId + '\'' +
//                ", address='" + address + '\'' +
//                ", startTime='" + startTime + '\'' +
//                ", endTime='" + endTime + '\'' +
//                ", title='" + title + '\'' +
//                ", state=" + state +
//                ", type=" + type +
//                ", createTime='" + createTime + '\'' +
//                ", reason='" + reason + '\'' +
//                ", labels=" + labels +
//                ", images=" + images +
//                ", userId=" + userId +
//                ", involvement=" + involvement +
//                ", isCollection=" + isCollection +
//                ", limitNumber=" + limitNumber +
//                ", joinNumber=" + joinNumber +
//                ", popularity=" + popularity +
//                '}';
        return images+startTime+address+title;
    }

    public List<LabelsEntity> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsEntity> labels) {
        this.labels = labels;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public UserEntity getUser() {
        return userId;
    }

    public void setUser(UserEntity user) {
        this.userId = user;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getInvolvement() {
        return involvement;
    }

    public void setInvolvement(int involvement) {
        this.involvement = involvement;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public int getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(int joinNumber) {
        this.joinNumber = joinNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
