package com.example.king.fragement.main.parcel_serial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kings on 2016/2/10.
 */
public class Book implements Parcelable {
    private String name;
    private String author;
    private String publishTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

//    protected Book(Parcel in) {
//    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            Book mBook = new Book();
            mBook.name = in.readString();
            mBook.author = in.readString();
            mBook.publishTime = in.readString();
//            return new Book(in);
            return mBook;
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(publishTime);
    }
}
