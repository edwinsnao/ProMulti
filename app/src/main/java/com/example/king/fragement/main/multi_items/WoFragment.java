package com.example.king.fragement.main.multi_items;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import com.bumptech.glide.Glide;
import com.example.king.fragement.AboutUs;
import com.example.king.fragement.BroadcastTest;
import com.example.king.fragement.FileTest;
import com.example.king.fragement.JniTest;
import com.example.king.fragement.MapsActivity;
import com.example.king.fragement.MapsActivity1;
import com.example.king.fragement.NewsFragement;
import com.example.king.fragement.OsLogin;
import com.example.king.fragement.QueryProcess;
import com.example.king.fragement.R;
import com.example.king.fragement.SettingsActivity;
import com.example.king.fragement.SplashActivity;
import com.example.king.fragement.main.Cheeses;
import com.example.king.fragement.main.ItemDecoration;
import com.example.king.fragement.main.Main;
import com.example.king.fragement.main.SearchFile;
import com.example.king.fragement.main.TransferListener;
import com.example.king.fragement.main.aidlserver.*;
import com.example.king.fragement.main.aidlserver.MainActivity;
import com.example.king.fragement.main.fastJson.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.multi_items.Message;
import com.example.king.fragement.main.multi_items.WoAdapter;
import com.example.king.fragement.main.music_player.HomeActivity;
import com.example.king.fragement.main.nfc.NFCard;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.picTest.ChoosePic;
import com.example.king.fragement.main.sensor.IBMEyes;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.ItemListActivity;
import com.example.king.fragement.midea.ItemListActivity1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kings on 2016/1/14.
 */
public class WoFragment extends Fragment implements TransferListener {

    int lastVisibleItem;
    private ListView lv;
    private Message msg;
    private List<Message> msg_list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.multi_items_wo,container,false);
        lv = (ListView) v.findViewById(R.id.multi_items_wo_lv);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_ZAN);
        msg_list.add(msg);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_PINLUN_PIC);
        msg_list.add(msg);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_GUANZHU_NI);
        msg_list.add(msg);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_CANYU_HUODONG);
        msg_list.add(msg);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_GUANZHU_HUODONG);
        msg_list.add(msg);
        msg = new Message();
        msg.setType(WoAdapter.VALUE_LIUYAN_HUODONG);
        msg_list.add(msg);
        lv.setAdapter(new WoAdapter(getContext().getApplicationContext(),msg_list));
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void transferMsg() {
        lv.smoothScrollToPosition(0);
    }
}
