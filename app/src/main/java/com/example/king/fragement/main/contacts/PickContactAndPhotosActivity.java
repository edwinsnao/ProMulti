package com.example.king.fragement.main.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.king.fragement.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fazhao on 2017/2/10.
 */

public class PickContactAndPhotosActivity extends Activity {
    private String tellNum;
    private long contactID;
    private ImageView mAvatar;
    private Bitmap mBitmap;
    private ListView mListView;
    private List<ContactModel> mModels = new ArrayList<ContactModel>();
    private ContactModel model;
    private PickContactAndPhotosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);
        mListView = (ListView) findViewById(R.id.list);
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while(cursor.moveToNext()){
            //获取联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            String contact = cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+ContactId,null,null);

            while(phone.moveToNext()){//获取联系人所有电话信息  手机、单位电话 ..
                String PhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                tellNum += (contact + ":" + PhoneNumber +"\n");
                Uri uriNumber2Contacts = Uri.parse("content://com.android.contacts/"
                        + "data/phones/filter/" + PhoneNumber);
                // 查询Uri，返回数据集
                Cursor cursorCantacts = getApplicationContext().getContentResolver().query(
                        uriNumber2Contacts,
                        null,
                        null,
                        null,
                        null);
                // 如果该联系人存在
                if (cursorCantacts.getCount() > 0) {
                    // 移动到第一条数据
                    cursorCantacts.moveToFirst();
                    // 获得该联系人的contact_id
                    contactID = cursorCantacts.getLong(cursorCantacts.getColumnIndex("contact_id"));
                    Log.e("id", String.valueOf(contactID));
                    // 获得contact_id的Uri
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
                    // 打开头像图片的InputStream
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(getApplicationContext().getContentResolver(), uri);
                    // 从InputStream获得bitmap
                    mBitmap = BitmapFactory.decodeStream(input);
                }
                model = new ContactModel(contactID,mBitmap,PhoneNumber);
                mModels.add(model);
            }
        }
        mAdapter = new PickContactAndPhotosAdapter(PickContactAndPhotosActivity.this,mModels);
        mListView.setAdapter(mAdapter);
        cursor.close();
    }
}
