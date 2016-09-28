package com.example.king.fragement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.king.fragement.main.HomeFragment1;
import com.example.king.fragement.main.LogWrap;

/**
 * Created by test on 15-10-24.
 */
public class NetWorkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "Tag";
//    ConnectivityManager mConnectivityManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent();
        it.setAction("com.example.king.netstate");
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            LogWrap.e( "wifiState" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
//                    ItemListActivity.network.setVisibility(View.VISIBLE);
//                    ItemListActivity.network.setText("当前网络链接不可用，请稍后尝试");
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
//                    ItemListActivity.network.setVisibility(View.VISIBLE);
//                    ItemListActivity.network.setText("当前网络链接不可用，请稍后尝试");
                    break;
                //
            }
        }


        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                LogWrap.e( "isConnected" + isConnected);
                it.putExtra("State",String.valueOf(isConnected));
               /* if (isConnected) {
                    *//*
                    * 静态广播，可以一直监听网络状态的变化
                    * 而导致textview可以实时变化
                    * *//*
//                    MainActivity1.network.setVisibility(View.GONE);
//                    HomeFragment1.network.setVisibility(View.GONE);
//                    com.example.king.fragement.midea.ItemListActivity.network.setVisibility(View.GONE);
//                    ItemListActivity1.network.setVisibility(View.GONE);
                    it.putExtra("State",String.valueOf(isConnected));

                } else {
//                    MainActivity1.network.setVisibility(View.VISIBLE);
//                    HomeFragment1.network.setVisibility(View.VISIBLE);
//                    HomeFragment1.network.setText("当前网络链接不可用，请稍后尝试");

//                    com.example.king.fragement.midea.ItemListActivity.network.setVisibility(View.VISIBLE);
//                    ItemListActivity1.network.setVisibility(View.VISIBLE);
//                    MainActivity.network.setText("当前网络链接不可用，请稍后尝试");
                    it.putExtra("State", String.valueOf(isConnected));

                }*/
            }
        }
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo gprs = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            LogWrap.i("网络状态改变:" + wifi.isConnected() + " 3g:" + gprs.isConnected());
            NetworkInfo info = intent
                    .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                LogWrap.e( "info.getTypeName()" + info.getTypeName());
                LogWrap.e( "getSubtypeName()" + info.getSubtypeName());
                LogWrap.e( "getState()" + info.getState());
                LogWrap.e( "getDetailedState()"
                        + info.getDetailedState().name());
                LogWrap.e( "getDetailedState()" + info.getExtraInfo());
                LogWrap.e( "getType()" + info.getType());

                if (NetworkInfo.State.CONNECTED == info.getState()) {
                } else if (info.getType() == 1) {
                    if (NetworkInfo.State.DISCONNECTING == info.getState()) {

                    }
                }
            }
        }
        /*
        * 一定要加上context才可以sendBroadcasr
        * 否则找不到这个方法
        * */
        context.sendBroadcast(it);
    }


}
