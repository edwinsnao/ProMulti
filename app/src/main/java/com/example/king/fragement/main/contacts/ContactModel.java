package com.example.king.fragement.main.contacts;

import android.graphics.Bitmap;

/**
 * Created by fazhao on 2017/2/10.
 */

public class ContactModel {
    private long contactId;
    private Bitmap bitmap;
    private String phonenumber;

    public ContactModel(long contactId,Bitmap bitmap,String phonenumber){
        this.contactId = contactId;
        this.bitmap = bitmap;
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public long getContactId() {
        return contactId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
