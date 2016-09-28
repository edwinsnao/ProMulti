package com.example.king.fragement.main.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.king.fragement.main.LogWrap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/2/29.
 */
public class WifiAdmin {
    private final static  String TAG = "WifiAdmin";
    private StringBuffer mStringBuffer = new StringBuffer();
    public List<ScanResult> listResult = new ArrayList<ScanResult>();
    private ScanResult mScanResult;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<WifiConfiguration> wifiConfigList = new ArrayList<WifiConfiguration>();
    WifiManager.WifiLock mWifiLock;
    private ConnectivityManager connManager;
    private Context mContext;
    private List<WifiConfiguration> wifiConfigedSpecifiedList = new ArrayList<WifiConfiguration>();
    private NetworkInfo.State state;
    private DhcpInfo mDhcpInfo;
    public boolean isConnected = false;

    public WifiAdmin(Context context){
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void firstGetWifiInfo(){
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public boolean isNetCardOpen(){
        return mWifiManager.isWifiEnabled();
    }

    public boolean isConnected(){
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        logConnect();
        if(NetworkInfo.State.CONNECTED == state){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isConnecting(){
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        logConnect();
        if(NetworkInfo.State.CONNECTING == state){
            return true;
        }
        else{
            return false;
        }
    }
    /*
    * 查看连接信息
    * */
    public void logConnect(){
        LogWrap.i("Connect:"+ String.valueOf(state));
    }

    public NetworkInfo.State getCurrentState(){
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        return state;
    }

    public void setWifiConfigedSpecifiedList(String ssid){
        wifiConfigedSpecifiedList.clear();
        if(wifiConfigList!=null){
            for(WifiConfiguration item : wifiConfigList){
                if(item.SSID.equalsIgnoreCase("\""+ssid+"\"")&& item.preSharedKey !=null){
                    wifiConfigedSpecifiedList.add(item);
                }
            }
        }
    }

    public List<WifiConfiguration> getWifiConfigedSpecifiedList(){
        return wifiConfigedSpecifiedList;
    }

    public void openNetCard(){
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }

    public void closeNetCard(){
        if(mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
    }

    public void checkNetCardState(){
        if(mWifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLING){
            LogWrap.i( "网卡正在关闭");
        }else if(mWifiManager.getWifiState()==WifiManager.WIFI_STATE_DISABLED){
            LogWrap.i( "网卡已经关闭");
        }else if(mWifiManager.getWifiState()== WifiManager.WIFI_STATE_ENABLING){
            LogWrap.i( "网卡正在开启");
        }else if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
            LogWrap.i( "网卡已经开启");
        }else if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_UNKNOWN){
            LogWrap.i("未能获取状态");
        }
    }

    public void scan(){
        mWifiManager.startScan();
        listResult = mWifiManager.getScanResults();
        wifiConfigList  = mWifiManager.getConfiguredNetworks();
        mDhcpInfo = mWifiManager.getDhcpInfo();
        if(listResult!=null){
            LogWrap.i("当前区域存在无线网络，请查看扫描结果");
        }
        else{
            LogWrap.i("当前区域不存在无线网络");
        }
    }

    public List<ScanResult> getListResult(){
        return listResult;
    }

    public String getScanResult(){
        if(mStringBuffer !=null){
            mStringBuffer = new StringBuffer();
        }
        scan();
        listResult = mWifiManager.getScanResults();
        if(listResult!=null){
            for(int i = 0;i<listResult.size();i++){
                mScanResult = listResult.get(i);
                mStringBuffer.append("NO.").append(i+1).append(":").append(mScanResult.SSID)
                        .append("->").append(mScanResult.BSSID).append("->").append(mScanResult.capabilities)
                        .append("->").append(mScanResult.frequency).append("->").append(mScanResult.level)
                        .append("->").append(mScanResult.describeContents()).append("\n\n");
            }
        }
        LogWrap.i(mStringBuffer.toString());
        return  mStringBuffer.toString();
    }

    public void connect(){
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public void  disconnectWifi(){
        int netId = getNetworkId();
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
        mWifiInfo = null;
    }

    public boolean checkNetWorkState(){
        if(mWifiInfo!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public int getNetworkId() {
        return (mWifiInfo==null)?0:mWifiInfo.getNetworkId();
    }

    public int getIPAddress(){
        return (mWifiInfo == null)?0:mWifiInfo.getIpAddress();
    }

    public void acquireWifiLock(){
        mWifiLock.acquire();
    }

    public void releaseWifiLock(){
        if(mWifiLock.isHeld()){
            mWifiLock.acquire();
        }
    }

    public void createWifiLock(){
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    public List<WifiConfiguration> getConfiguration(){
        return wifiConfigList;
    }

    public Boolean connectConfiguration(int index){
        if(index>= wifiConfigList.size()){
            return false;
        }
        return mWifiManager.enableNetwork(wifiConfigedSpecifiedList.get(index).networkId,true);
    }

    public String getMacAddress(){
        return (mWifiInfo == null)?"":mWifiInfo.getMacAddress();
    }

    public String getBSSID(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.getBSSID();
    }

    public String getWifiInfo(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.toString();
    }

    public int addNetwork(WifiConfiguration wcg){
        int wcgID = mWifiManager.addNetwork(wcg);
        mWifiManager.enableNetwork(wcgID,true);
        return wcgID;
    }

}
