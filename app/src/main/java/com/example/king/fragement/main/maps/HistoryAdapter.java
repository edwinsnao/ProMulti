package com.example.king.fragement.main.maps;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.king.fragement.R;
import com.example.king.fragement.main.BaseApplication;
import com.example.king.fragement.main.crypto.Crypto;
import com.example.king.fragement.main.crypto.KeyManager;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Kings on 2016/2/13.
 */
public class HistoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<TraceItem> mDatas;
    private List<TraceItem> mDatas1;
    private SwipeDeleteListView listView;
    private Crypto crypto = BaseApplication.getmCrypto();
    String key = "12345678909876543212345678909876";
    String iv = "1234567890987654";
//    String key = "12345678909876543212345678909876";
//    String iv = "1234567890987654";
//
//    KeyManager km = TencentMaps.km;


    /**
     * 使用了github开源的ImageLoad进行了数据加载
     */

    public HistoryAdapter(Context context, List<TraceItem> datas)
    {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);


    }
    public HistoryAdapter(Context context, List<TraceItem> datas,SwipeDeleteListView lv)
    {
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
        listView = lv;

//        km.setIv(iv.getBytes());
//        km.setId(key.getBytes());

    }
    public HistoryAdapter(Context context, List<TraceItem> datas,List<TraceItem> datas1,SwipeDeleteListView lv)
    {
        this.mDatas = datas;
        this.mDatas1 = datas1;
        mInflater = LayoutInflater.from(context);
        listView = lv;
//        km.setIv(iv.getBytes());
//        km.setId(key.getBytes());

    }

    public void addAll(List<TraceItem> mDatas)
    {
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.history_maps_adapter, parent,false);
            holder = new ViewHolder();

            holder.time_start = (TextView) convertView.findViewById(R.id.time_history);
            holder.time_end = (TextView) convertView.findViewById(R.id.time_history_end);
            holder.address_start = (TextView) convertView.findViewById(R.id.address_history);
            holder.address_destination = (TextView) convertView.findViewById(R.id.address_history_destination);
            holder.tag = (TextView) convertView.findViewById(R.id.number_history);
            holder.delete = (TextView) convertView.findViewById(R.id.delete);


            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        if(mDatas1!=null) {
            TraceItem traceItem1 = mDatas1.get(position);
//        holder.address.setText("出发点："+traceItem.getAddress());
             try{
                holder.time_end.setText("结束时间：" + crypto.armorDecrypt(traceItem1.getDate()));
                holder.address_destination.setText("目的地：" + crypto.armorDecrypt(traceItem1.getName()));
//                holder.time_end.setText("结束时间：" + traceItem1.getDate());
//                holder.address_destination.setText("目的地：" + traceItem1.getName());
//            }
//                Log.e("目的地：" , crypto.armorDecrypt(traceItem1.getName()));
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }
        else{
            holder.time_end.setVisibility(View.GONE);
            holder.address_destination.setVisibility(View.GONE);
            holder.tag.setVisibility(View.GONE);
        }
        TraceItem traceItem = mDatas.get(position);
        holder.tag.setText("编号：" + traceItem.getTag());
        try{
            holder.time_start.setText("出发时间：" + crypto.armorDecrypt(traceItem.getDate()));
//            holder.time_start.setText("出发时间：" + traceItem.getDate());
            /*
            * 不能用TencemtMaps.crypto，因为已经销毁，onpause()
            * */
            holder.address_start.setText("出发地：" + crypto.armorDecrypt(traceItem.getName()));
//            holder.address_start.setText("出发地：" + crypto.armorDecrypt(traceItem.getName()));
//            holder.address_start.setText("出发地：" + traceItem.getName());
//            Log.e("出发地：" , crypto.armorDecrypt(traceItem.getName()));
//        }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                notifyDataSetChanged();
                listView.turnToNormal();
            }
        });


        return convertView;
    }

    private final class ViewHolder
    {
        TextView time_start;
        TextView time_end;
        TextView tag;
        TextView address_start;
        TextView address_destination;
        TextView delete;
    }
}
