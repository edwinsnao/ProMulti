package com.example.king.fragement.main.model;

import java.io.Serializable;

/**
 * Created by Kings on 2016/5/6.
 */
public class ImageEntity implements Serializable {
    private String id;
    private String userId;
    private String activityId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String image;

    @Override
    public String toString() {
        return image;
//        return "ImagesEntity{" +
//                "id='" + id + '\'' +
//                ", activityId='" + activityId + '\'' +
//                ", userId='" + userId + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
//                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
