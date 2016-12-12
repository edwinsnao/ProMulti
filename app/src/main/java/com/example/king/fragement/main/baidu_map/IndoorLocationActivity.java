package com.example.king.fragement.main.baidu_map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.king.fragement.R;
import com.example.king.fragement.main.BaseApplication;
import com.example.king.fragement.main.crypto.Crypto;
import com.example.king.fragement.main.crypto.KeyManager;
import com.example.king.fragement.main.maps.HistoryAdapter;
import com.example.king.fragement.main.maps.HistoryMaps;
import com.example.king.fragement.main.maps.SwipeDeleteListView;
import com.example.king.fragement.main.maps.TraceDao;
import com.example.king.fragement.main.maps.TraceItem;
import com.example.king.fragement.main.utils.BaiduUtils;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * 此demo用来展示如何结合定位SDK实现室内定位，并使用MyLocationOverlay绘制定位位置
 */
public class IndoorLocationActivity extends Activity {

    // 定位相关
    LocationClient mLocClient;
    //    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    /**
     * 获取位置距离常量
     */
    private int constant=0;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private StripListView stripListView;
    private BaseStripAdapter mFloorListAdapter;
    private MapBaseIndoorMapInfo mMapBaseIndoorMapInfo = null;
    // UI相关

    private Button requestLocButton,compute,save,load;
    boolean isFirstLoc = true; // 是否首次定位

    protected MapStatusUpdate msUpdate = null;
    /**
     *  覆盖物
     */
    protected OverlayOptions overlay,StartOverlay,EndOverlay;
    /**
     *  路线覆盖物
     */
    private PolylineOptions polyline = null;
    /**
     * 手机加速度感应器服务注册
     */
    private Acc acc=new Acc();
    private LatLng latLng,ll;
    private List<BDLocation> history = new ArrayList<>();
    private List<LatLng> pointList = new ArrayList<LatLng>();
    /**
     * 最小距离单位(米)
     */
    private final Double MinDistance=2.0;
    /**
     * 因距离太大丢失的点数
     */
    private int LostLoc=0;
    /**
     * 最大距离单位(米)
     */
    private final Double MaxDistance=90.00;
    /**
     * 第一次定位丢失的点数
     */
    private int FLostLoc=0;
    private int tmp;
    //    private BitmapDescriptor mBitmap;
    private int locTime = 0;
    private TraceDao1 mTraceDao;
    private TraceItem mTraceItem;
    private  int tag;
    private AlertDialog historyDialog;
    private int mStep = 0;
    private Bundle bundle = new Bundle();
    private HistoryAdapter mAdapter;
    private View mFooterView;
    private Crypto crypto;
    private KeyManager km;
    private List<TraceItem> mDatas,mDatas1;
    private SensorEventListener mSensorEventListener;
    private Sensor mStepSensor;
    private SensorManager mSensorManager;
    private TextView step,info;
    private LatLng latLng1;
    List<LatLng> historyFromLoad = new ArrayList<LatLng>();
    private List<TraceItem> traceItems;


    private BDLocationListener mListener = new BDLocationListener(){

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("locClient", String.valueOf(isFirstLoc));
            Log.e("locTime", String.valueOf(locTime++));
            // TODO Auto-generated method stub
            // 将GPS设备采集的原始GPS坐标转换成百度坐标
            ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            CoordinateConverter converter = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.GPS);
            // latLng 待转换坐标
            converter.coord(ll);
            LatLng desLatLng = converter.convert();
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            if(isFirstLoc){
                isFirstLoc=false;
//                    if(constant<pointList.size()){
//                        if(DistanceUtil.getDistance(pointList.get(constant),ll)>DistanceUtil.getDistance(pointList.get(constant+1),ll)){
//                            save("距离: "+DistanceUtil.getDistance(pointList.get(constant+1),ll)+" 时间: "+getStringDate()+" 点数: "+constant);
//                            if(DistanceUtil.getDistance(pointList.get(constant+1),ll)>100&&isGetNewRoute){
//                                IsGetNewRoute();
//                            }
//                            constant++;
//                        }else{
//                            save("距离: "+DistanceUtil.getDistance(pointList.get(constant),ll)+" 时间: "+getStringDate()+" 点数: "+constant);
//                            if(DistanceUtil.getDistance(pointList.get(constant),ll)>100&&isGetNewRoute){
//                                IsGetNewRoute();
//                            }
//                        }
//                    }

//                drawRealtimePoint(ll);
                drawRealtimePoint(desLatLng);
            }else{
                showRealtimeTrack(location);
            }
            history.add(location);
            Log.e("address",String.valueOf(location.getAddress().address));
            Log.e("time",String.valueOf(location.getTime()));
            Log.e("latitude",String.valueOf(location.getLatitude()));
            Log.e("lontitude",String.valueOf(location.getLongitude()));
//                Log.e("getSatelliteNumber",String.valueOf(location.getSatelliteNumber()));

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RelativeLayout layout = new RelativeLayout(this);

        initData();
//        mBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_d);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainview = inflater.inflate(R.layout.activity_location_baidu, null);
        layout.addView(mainview);

        mFooterView = LayoutInflater.from(IndoorLocationActivity.this).inflate(R.layout.maps_list_footer, null);
        requestLocButton = (Button) mainview.findViewById(R.id.button1);
        mCurrentMode = LocationMode.COMPASS;
        requestLocButton.setText("普通");
        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        // 地图初始化
        mMapView = (MapView) mainview.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 开启室内图
        mBaiduMap.setIndoorEnable(true);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
//        mLocClient.registerLocationListener(myListener);
        mLocClient.registerLocationListener(mListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setProdName("LocationManager");
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);
        option.setLocationNotify(true);
        option.disableCache(false);
        option.setNeedDeviceDirect(true);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocClient.setLocOption(option);
        mLocClient.start();

        stripListView = new StripListView(this);
        layout.addView(stripListView);
        setContentView(layout);
        step = (TextView) findViewById(R.id.steps);
        info = (TextView) findViewById(R.id.et_streetView);
        try {
            info.setText("上次定位距离:"+String.valueOf(DistanceUtil.getDistance(historyFromLoad.get(0),historyFromLoad.get(historyFromLoad.size()-1)))+",时长：" + BaiduUtils.dateDiff(this,crypto.armorDecrypt(traceItems.get(0).getDate()), crypto.armorDecrypt(traceItems.get(traceItems.size() - 1).getDate()), "yyyy-MM-dd-HH:mm:ss", "m")
                    + "分钟"+",步数:"+mTraceDao.getLastStep().getStep());
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
        mTraceDao = BaseApplication.getTraceDao1();
        save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IndoorLocationActivity.this, "saving.....", Toast.LENGTH_SHORT).show();
                save.setEnabled(false);
                Thread saveThread = new Thread(saveHistory);
                saveThread.start();
                save.setEnabled(true);
                Toast.makeText(IndoorLocationActivity.this, "saved!", Toast.LENGTH_SHORT).show();
            }
        });
        load = (Button) findViewById(R.id.btn_load);
        load.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 查询有多少次
                 * */
                HandlerThread thread = new HandlerThread("MyThread");
                thread.start();
                final Handler handler = new Handler(thread.getLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 0:
                                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                final View v1 = inflater.inflate(R.layout.loading_dialog, null);
                                final SwipeDeleteListView lv = (SwipeDeleteListView) v1.findViewById(R.id.list_history);
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        /**
                                         * listview是从0开始，但是我的tag是从1开始，所以position+1
                                         * */
                                        bundle.putInt("choice", position + 1);
                                        Intent it = new Intent();
                                        it.setClass(IndoorLocationActivity.this, HistoryMaps1.class);
                                        it.putExtras(bundle);
                                        startActivity(it);
                                        finish();
                                    }
                                });
                                mAdapter = new HistoryAdapter(IndoorLocationActivity.this, mDatas, mDatas1, lv);
                                lv.setDivider(getResources().getDrawable(R.drawable.divider));
                                lv.setAdapter(mAdapter);
                                lv.setFooterDividersEnabled(true);
                                lv.setHeaderDividersEnabled(true);
                                lv.addFooterView(mFooterView);
                                historyDialog = new AlertDialog.Builder(IndoorLocationActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                                        .setTitle("历史记录有" + tag + "数据").setView(v1)//在这里把写好的这个listview的布局加载dialog中
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                int choice = 0;
                                                EditText et_choice = (EditText) v1.findViewById(R.id.et_searchData);
                                                if (TextUtils.isEmpty(et_choice.getText())) {
//                                            加入不输入任何信息，则默认取最新的数据
                                                    choice = tag;
                                                } else
                                                    choice = Integer.valueOf(et_choice.getText().toString());
                                                bundle.putInt("choice", choice);
                                                Intent it = new Intent();
                                                it.setClass(IndoorLocationActivity.this, HistoryMaps1.class);
                                                it.putExtras(bundle);
                                                startActivity(it);
                                                dialog.cancel();
                                                IndoorLocationActivity.this.finish();
                                            }
                                        }).create();
                                historyDialog.setButton3("清空历史记录", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mTraceDao.deleteAll();
                                        mAdapter.notifyDataSetChanged();
                                        dialog.cancel();
                                    }
                                });
                                historyDialog.setButton2("清除指定记录", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                        final View v = inflater.inflate(R.layout.delete_history_map, null);
                                        new AlertDialog.Builder(IndoorLocationActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                                                .setMessage("删除指定记录")
                                                .setView(v)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        /**
                                                         * 默认删除第一次记录(最远的记录)
                                                         * */
                                                        int choice1 = 1;
                                                        EditText et_choice = (EditText) v.findViewById(R.id.et_DelteData);
                                                        if (TextUtils.isEmpty(et_choice.getText())) {
                                                            choice1 = tag;
                                                        } else
                                                            choice1 = Integer.valueOf(et_choice.getText().toString());
                                                        final int finalChoice = choice1;
                                                        new AlertDialog.Builder(IndoorLocationActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                                                                .setMessage("删除记录后不能恢复记录，是否继续？")
                                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        mTraceDao.deleteAll(finalChoice);
                                                                        /**
                                                                         * mDatas是从mTraceDao.searchData得到的
                                                                         * 因为上面一句 已经删除了
                                                                         * 所以拿到的已经没有finalchoice了所以报错
                                                                         * */
                                                                        mAdapter.notifyDataSetChanged();
                                                                        dialog.cancel();
                                                                    }
                                                                })
                                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.cancel();
                                                                    }
                                                                })
                                                                .create().show();
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                .create().show();
                                    }
                                });
                                historyDialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
                                /**
                                 * 显示历史的dialog泄露了内存，因为我显示历史后会点击listview的item
                                 * 然后就会finish这个activity而进入historyActivity
                                 * 但是这个dialog在activity退出之前没有进行dismiss所以泄漏了
                                 * */
                                historyDialog.show();
                                break;
                        }
                    }
                };
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        tag = mTraceDao.maxTag();
                        mDatas = mTraceDao.searchDistinctDataStart();
                        mDatas1 = mTraceDao.searchDistinctDataDestination();
                        handler.sendEmptyMessage(0);
                    }
                };
                handler.post(runnable);
//
            }
        });
        compute = (Button) findViewById(R.id.btn_compute);
        compute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = (int) DistanceUtil.getDistance(pointList.get(constant),ll);
                Toast.makeText(IndoorLocationActivity.this,"距离:"+tmp,Toast.LENGTH_SHORT).show();
            }
        });
        mFloorListAdapter = new BaseStripAdapter(IndoorLocationActivity.this);

        mBaiduMap.setOnBaseIndoorMapListener(new BaiduMap.OnBaseIndoorMapListener() {
            @Override
            public void onBaseIndoorMapMode(boolean b, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
                if (b == false || mapBaseIndoorMapInfo == null) {
                    stripListView.setVisibility(View.INVISIBLE);

                    return;
                }

                mFloorListAdapter.setmFloorList(mapBaseIndoorMapInfo.getFloors());
                stripListView.setVisibility(View.VISIBLE);
                stripListView.setStripAdapter(mFloorListAdapter);
                mMapBaseIndoorMapInfo = mapBaseIndoorMapInfo;
            }
        });
    }

    private void initData() {
        crypto = Crypto.getsInstance();
        km = KeyManager.getsInstace();
        mSensorManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        /**
         * step
         * */
        mSensorEventListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[0] == 1.0f) {
                    mStep++;
                }
                StringBuilder builder = new StringBuilder("步数:");
                builder.append(Integer.toString(mStep));
                step.setText(builder);
            }
        };
        /**
         * 如果设置SENSOR_DELAY_FASTEST会浪费电的
         * */
        mSensorManager.registerListener(mSensorEventListener, mStepSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        traceItems = mTraceDao.searchData(mTraceDao.maxTag());
        latLng1 = new LatLng(traceItems.get(0).getLatitude(), traceItems.get(0).getLongitude());
        for (int i = 0; i < traceItems.size(); i++) {
            LatLng latLng = new LatLng(traceItems.get(i).getLatitude(), traceItems.get(i).getLongitude());
            historyFromLoad.add(latLng);
        }
    }

    final Runnable saveHistory = new Runnable() {
        @Override
        public void run() {
            if (mTraceDao.searchAllData() != null)
                tag = mTraceDao.maxTag() + 1;
            for (int i = 0; i < history.size(); i++) {
                mTraceItem = new TraceItem();
                try {
//                    mTraceItem.setName(crypto.armorEncrypt(history.get(i).getName().getBytes()));
                    mTraceItem.setAddress(crypto.armorEncrypt(history.get(i).getAddress().address.getBytes()));
                    mTraceItem.setLatitude(history.get(i).getLatitude());
                    mTraceItem.setLongitude(history.get(i).getLongitude());
                    mTraceItem.setTag(tag);
                    /**
                     * 这里是导致一个tag的所有记录的时间都是相同的，所以时间差为0或很接近
                     * 应该把这里放到onlocationchanged里（history里setDate）
                     * 而这里就拿history的date
                     * */
                    mTraceItem.setDate(crypto.armorEncrypt(history.get(i).getTime().getBytes()));
                    /**
                     * 在最后一个插入步数
                     * 如果是0也插入，证明不是走路（是交通工具）
                     * */
//                        if(i == history.size() - 1){
                    mTraceItem.setStep(mStep);
//                        }
                    mTraceDao.add(mTraceItem);
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
        }
    };

    /*
	 * 显示实时轨迹
	 *
	 * @param realtimeTrack
	 */
    protected void showRealtimeTrack(BDLocation location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001) {
            Toast.makeText(this, "当前无轨迹点", Toast.LENGTH_SHORT).show();
        } else {
//            latLng = new LatLng(latitude, longitude);
//            CoordinateConverter converter = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            // latLng 待转换坐标
//            converter.coord(latLng);
//            LatLng desLatLng = converter.convert();
            if (IsMove(latLng,location)) {
//            if (IsMove(desLatLng,location)) {
                // 绘制实时点
                drawRealtimePoint(latLng);
//                drawRealtimePoint(desLatLng);
            }
        }

    }

    /*
     * 绘制实时点
     *
     * @param points
     */
    private void drawRealtimePoint(LatLng point) {

        mBaiduMap.clear();
        polyline=null;
        MapStatus mMapStatus = new MapStatus.Builder().target(point)
                .zoom(20)
                .build();

        msUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);


//        overlay = new MarkerOptions().position(point)
//                .icon(mBitmap).zIndex(9).draggable(true);

        if (pointList.size() >=2 && pointList.size() <= 100000) {
            // 添加路线（轨迹）
            polyline = new PolylineOptions().width(10)
                    .color(Color.RED).points(pointList);
        }
        mBaiduMap.animateMapStatus(msUpdate);
        addMarker();

    }

    /*
	 * 判断手机是否在运动
	 */
    private boolean IsMove(LatLng latLng,BDLocation location){
//        Log.e("locTime", String.valueOf(locTime++));
        if(pointList.size()>=1){
            Double dis= DistanceUtil.getDistance(pointList.get(pointList.size()-1),latLng);
            //判断手机是否静止,如果静止,判定采集点无效,直接抛弃
            if(!acc.is_Acc&&acc.IsRun){
                acc.IsRun=false;
                return false;
            }
            //判断是否是第一次定位置,如果是第一次定位并且因为第一次抛弃的位置数量小于10个则判断两点间距离大小
            if(FLostLoc<10){
                FLostLoc=FLostLoc+1;
                if(dis>10&&FLostLoc<6){//距离大于十米,而且被抛弃数量少于5个则说明有可能是获取位置失败
                    pointList.clear();
                    pointList.add(latLng);//更新位置
                    return false;
                }
                if(dis>0&&dis<10&&FLostLoc>=6)//如果距离在10米内,则表示客户正在运动,直接跳出
                    FLostLoc=11;
            }
            //根据两点间距离判断是否发生定位漂移,如果漂移距离小于MinDistance则抛弃,如果漂移距离大于MaxDistance则取两点的中间点.
            if(dis<=MinDistance){
                if((dis<=MinDistance||dis>=MaxDistance)){
                    return false;
                }

                if(LostLoc>=4){
                    Double newlatitude=(latLng.latitude+pointList.get(pointList.size()-1).latitude)/2;
                    Double newlongitude=(latLng.longitude+pointList.get(pointList.size()-1).longitude)/2;
                    latLng = new LatLng(newlatitude, newlongitude);
                }else{
                    LostLoc=LostLoc+1;
                    return false;
                }

            }
            LostLoc=0;//重置丢失点的个数
            //			pointList.add(latLng);
            acc.is_Acc=false;
        }
        pointList.add(latLng);
        return true;
    }

    /*
	 * 添加地图覆盖物
	 */
    protected  void addMarker() {

        if (null != msUpdate) {
            mBaiduMap.setMapStatus(msUpdate);
        }

        // 路线覆盖物
        if (null != polyline) {
            mBaiduMap.addOverlay(polyline);
        }

        // 实时点覆盖物
        if (null != overlay) {
            mBaiduMap.addOverlay(overlay);
        }

        //起点覆盖物
        if (null != StartOverlay) {
            mBaiduMap.addOverlay(StartOverlay);
        }
        // 终点覆盖物
        if (null != EndOverlay) {
            mBaiduMap.addOverlay(EndOverlay);
        }
    }

    /**
     * 定位SDK监听函数
     */
//    public class MyLocationListenner implements BDLocationListener {
//
//        private String lastFloor = null;
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null) {
//                return;
//            }
//            String bid = location.getBuildingID();
//            if (bid != null && mMapBaseIndoorMapInfo != null) {
//                Log.i("indoor", "bid = " + bid + " mid = " + mMapBaseIndoorMapInfo.getID());
//                if (bid.equals(mMapBaseIndoorMapInfo.getID())) {// 校验是否满足室内定位模式开启条件
//                    // Log.i("indoor","bid = mMapBaseIndoorMapInfo.getID()");
//                    String floor = location.getFloor().toUpperCase();// 楼层
//                    Log.i("indoor", "floor = " + floor + " position = " + mFloorListAdapter.getPosition(floor));
//                    Log.i("indoor", "radius = " + location.getRadius() + " type = " + location.getNetworkLocationType());
//
//                    boolean needUpdateFloor = true;
//                    if (lastFloor == null) {
//                        lastFloor = floor;
//                    } else {
//                        if (lastFloor.equals(floor)) {
//                            needUpdateFloor = false;
//                        } else {
//                            lastFloor = floor;
//                        }
//                    }
//                    if (needUpdateFloor) {// 切换楼层
//                        mBaiduMap.switchBaseIndoorMapFloor(floor, mMapBaseIndoorMapInfo.getID());
//                        mFloorListAdapter.setSelectedPostion(mFloorListAdapter.getPosition(floor));
//                        mFloorListAdapter.notifyDataSetInvalidated();
//                    }
//
//                    if (!location.isIndoorLocMode()) {
//                        mLocClient.startIndoorMode();// 开启室内定位模式，只有支持室内定位功能的定位SDK版本才能调用该接口
//                        Log.i("indoor", "start indoormod");
//                    }
//                }
//            }
//
//            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
//            // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//        }
//    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        if(historyDialog!=null)
            historyDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener(mSensorEventListener);
        mLocClient.unRegisterLocationListener(mListener);
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
