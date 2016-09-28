package com.example.king.fragement.main.mywork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.king.fragement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/5/5.
 */
public class DocAdapter  extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<DocEntity> doc = new ArrayList<>();
    @Override
    public int getCount() {
        return doc.size();
    }

    @Override
    public Object getItem(int i) {
        return doc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.cell_unit_doc, parent,false);
            holder = new ViewHolder();

            holder.basic = (TextView) convertView.findViewById(R.id.basic);
            holder.job = (TextView) convertView.findViewById(R.id.job);
            holder.resident = (TextView) convertView.findViewById(R.id.resident);
            holder.tag = (TextView) convertView.findViewById(R.id.tag);
            holder.aboutMe = (TextView) convertView.findViewById(R.id.aboutme);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        DocEntity image = doc.get(position);
        holder.basic.setText(image.getAge()+"-"+image.getStature()+"cm-"+
        image.getWeight()+"-"+image.getSex()+"-"+image.getConstellation());
        holder.job.setText(image.getSexuality()+"-"+image.getJob());
        holder.resident.setText(image.getResidence());
        holder.aboutMe.setText(image.getAboutMe());
//        }
        return convertView;
    }
    private final class ViewHolder
    {
        TextView basic;
        TextView job;
        TextView resident;
        TextView tag;
        TextView aboutMe;
    }
}
