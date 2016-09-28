package com.example.king.fragement.main.maps;

import android.os.Bundle;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentPoi;

import java.util.List;

public class TraceItem1 {
    private int id;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int level;

    private String name;
   
    public String address;
   
    private String date;
    private String provider;
    private String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setLongitude(String longitude) {

        this.longitude = longitude;
    }

    private String longitude;
    private  double accuracy;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getNation() {
        return null;
    }

    public String getProvince() {
        return null;
    }

    public String getCity() {
        return null;
    }



    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }




    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString()
    {
        return "TraceItem [id=" + id + ", name=" + name + ", address=" + address +  ", tag=" + tag + ", date=" + date + ", latitude=" + latitude
                + ", longitude=" + longitude + ", provider=" + provider +   ", accuracy=" + accuracy+"]";
    }

}
