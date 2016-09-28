package com.example.king.fragement.main.multi_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.LogWrap;

import java.util.List;

/**
 * Created by Kings on 2016/5/2.
 */
public class GuanZhu extends BaseAdapter {


    private ImageView imageView;


    public static final int VALUE_ZAN = 0;
    public static final int VALUE_ZAN_MUTIL= 1;
    public static final int VALUE_FAQI_HUODONG = 2;
    public static final int VALUE_CANYU_HUODONG = 3;
//    public static final int VALUE_GUANZHU_HUODONG = 4;
//    public static final int VALUE_LIUYAN_HUODONG = 5;
    //    public static final int VALUE_RIGHT_AUDIO = 6;
    private LayoutInflater mInflater;


    private List<Message> myList;


    public GuanZhu(Context context, List<Message> myList) {
        this.myList = myList;


        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return myList.size();
    }


    @Override
    public Object getItem(int arg0) {
        return myList.get(arg0);
    }


    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {


        Message msg = myList.get(position);
        int type = getItemViewType(position);
        ViewHolderTime holderTime = null;

        if (convertView == null) {
            switch (type) {
                case VALUE_ZAN:
                    holderTime = new ViewHolderTime();
                    convertView = mInflater.inflate(R.layout.multi_items_guanzhu_zan,
                            null);
                    break;
                case VALUE_ZAN_MUTIL:
                    convertView = mInflater.inflate(R.layout.multi_items_guanzhu_multi_zan,
                            null);
                    break;


                case VALUE_FAQI_HUODONG:
                    convertView = mInflater.inflate(R.layout.multi_items_guanzhu_faqi_huodong,
                            null);
                    break;


                case VALUE_CANYU_HUODONG:
                    convertView = mInflater.inflate(R.layout.multi_items_multi_canyu_huodong,
                            null);
                    break;


                default:
                    break;
            }

        } else {
            LogWrap.d("baseAdapter"+"Adapter_:"+(convertView == null) );
            switch (type) {
                case VALUE_ZAN:
                    break;
                case VALUE_ZAN_MUTIL:
                    break;
                case VALUE_FAQI_HUODONG:
                    break;
                case VALUE_CANYU_HUODONG:
                    break;


                default:
                    break;
            }

        }
        return convertView;
    }


    @Override
    public int getItemViewType(int position) {


        Message msg = myList.get(position);
        int type = msg.getType();
        LogWrap.e("TYPE:"+"" + type);
        return type;
    }


    @Override
    public int getViewTypeCount() {
        return 7;
    }


    class ViewHolderTime {
        private TextView tvTimeTip;// 时间
    }




}

