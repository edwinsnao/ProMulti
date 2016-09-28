package com.example.king.fragement.main.model;

import java.io.Serializable;

public class ImagesEntity implements Serializable{
    private String id;
    private String userId;
    private String activityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String imageUrl;

    @Override
    public String toString() {
        return imageUrl;
//        return "ImagesEntity{" +
//                "id='" + id + '\'' +
//                ", activityId='" + activityId + '\'' +
//                ", userId='" + userId + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
//                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}