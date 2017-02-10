package com.example.king.fragement.main.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.fragement.R;

import java.util.List;

/**
 * Created by fazhao on 2017/2/10.
 */

public class PickContactAndPhotosAdapter extends BaseAdapter {
    private List<ContactModel> mModels;
    private LayoutInflater mInflater;
    private ViewHolder viewHolder = null;
    public PickContactAndPhotosAdapter(Context mContext,List<ContactModel> models){
        mModels = models;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int i) {
        return mModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        if(viewHolder  == null)
            viewHolder = new ViewHolder();
        if(view ==null) {
            view = mInflater.inflate(R.layout.contact_item, group, false);
            viewHolder.mAvatar = (ImageView) view.findViewById(R.id.mAvatar);
            viewHolder.mPhoneNumber = (TextView) view.findViewById(R.id.phone_number);
            viewHolder.mContactId = (TextView) view.findViewById(R.id.contact_id);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        ContactModel model = mModels.get(i);
        viewHolder.mAvatar.setImageBitmap(model.getBitmap());
        viewHolder.mPhoneNumber.setText(model.getPhonenumber());
        viewHolder.mContactId.setText(String.valueOf(model.getContactId()));
        return view;
    }

    class ViewHolder{
        private ImageView mAvatar;
        private TextView mPhoneNumber;
        private TextView mContactId;
    }
}
