package com.example.king.fragement.main.parcel_serial;

import java.io.Serializable;

/**
 * Created by Kings on 2016/2/10.
 */
public class Learn implements Serializable {
    private static  final long serialVersionID = -7060210544600464481L;
    private String name;
    private String date;

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNeedToLearn() {
        return needToLearn;
    }

    public void setNeedToLearn(String needToLearn) {
        this.needToLearn = needToLearn;
    }

    private String last;
    private String needToLearn;

}
