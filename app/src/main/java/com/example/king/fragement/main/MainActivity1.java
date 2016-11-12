package com.example.king.fragement.main;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceFragment;
//import android.support.design.widget.FloatingActionButton;
import com.example.king.fragement.NetWorkConnectChangedReceiver;
import com.example.king.fragement.main.preference.PreferenceActivity;
import com.example.king.fragement.main.utils.TransitionHelper;
import com.example.king.fragement.midea.NewsItemDao;
import com.melnykov.fab.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.preference.AdvancePreferenceExample;
import com.example.king.fragement.main.preference.SettingFragment;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.ItemListActivity;
import com.example.king.fragement.midea.ItemListActivity1;
import com.melnykov.fab.ScrollDirectionListener;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class MainActivity1 extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        ImageLoadingListener,TransferListener
//        ,GestureDetector.OnGestureListener
{
//        ,TabHost.OnTabChangeListener {

//    public final static int num = 3;
//    BaseApplication mBaseApplication= null;
    HomeFragment1 homeFragment;
    Fragment sort ;
    /*
    * 用以记录点击日夜间模式的次数来区别日还是夜
    * */
    int i=1;
    Fragment personFragment;
    SearchMideaFragment search;
//    Fragment cheese = new CheeseListFragment();
//    Fragment midea = new MideaFragment();
//    Fragment midea1 = new MideaFragment1();
    Fragment sorttypeFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
//    static boolean isNight = false;
    private DrawerLayout mDrawerLayout;
    long exitTime = 0;
    ViewPager viewPager;
    ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
//    public static FloatingActionButton fab;
    private FloatingActionButton fab;
    int lastVisibleItemPosition = 0;
//    static ListView list;
//    static View mFooterView;
//    public static TextView network;
//    static boolean scrollFlag = false;
    //    public static List<Class> activities = new ArrayList<>();
//    Adapter adapter;
//    public static List<Map<String,Class>> activities_list = new ArrayList<>(30);
    /*
    * 不能SparseArray，因为不能用String，用了int则不能表达class的名称（在listview中显示）
    * */
//    public static List<SparseArray<Class>> activities_list = new ArrayList<>(30);
//    public static List<String> time = new ArrayList<>();
    private int theme = 0;
//    private IntentFilter mIntentFilter;
//    private NetWorkConnectChangedReceiver mLocalReceiver;
//    private LocalBroadcastManager lbm;
    private SettingFragment mSettingsFragment;
//    private ListView list = NewsFragment.list;
//    RecyclerView mRecyclerView;
    FragmentTabHost mTabHost;
//     Handler handler = new Handler();
//    private final MyHandler handler = new MyHandler(this);
//    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();
    LayoutInflater mLayoutInflater;
//    Boolean state = false;
    String state = null;
    BroadcastReceiver networkListener;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo networkInfo;
    private Thread thread;
    /*
    * wrong nullpointer
    * */
//    private PackageManager pm = getPackageManager();
    private PackageManager pm;
    /*
    * wrong context要在oncreate等方法里
    * */
//    private ComponentName cmpName = new ComponentName(getApplicationContext(),NetWorkConnectChangedReceiver.class);
    private ComponentName cmpName;
    private List<Bitmap> mBitmaps = new ArrayList<>();
    private Fragment frag;
    private NewsFragment newsFrag;
    private CheeseListFragment rvFragment;
    private TextView radio0,radio1,radio2;
    private ImageButton img0,img1,img2;
    private LinearLayout ll0,ll1,ll2;
    SystemBarTintManager barTintManager;
//    private TransferListener trans ;
//    private final int[] icons = {R.drawable.ic_tab_my_normal,R.drawable.ic_tab_nodes_normal,
//    R.drawable.ic_tab_discovery_normal};
//    GestureDetector mGestureDetector;
//    private TextView network;

    @Override
    protected void onStart() {
        super.onStart();
        LogWrap.e("onStart:MainActivity1");
    }

    public FloatingActionButton getFab(){
        return fab;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            barTintManager = new SystemBarTintManager(this);
            barTintManager.setStatusBarTintEnabled(true);
//                mTintManager.setNavigationBarTintEnabled(true);
                // mTintManager.setTintColor(0xF00099CC);
//            barTintManager.setTintDrawable(UIElementsHelper
//                        .getGeneralActionBarBackground(this));

            }

         }

             @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
        win.setAttributes(winParams);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogWrap.e("onCreate:MainActivity1");
//        registerLocalBroadcastReceiver();
//        Wrong
//        state = getIntent().getBooleanExtra("State",false);
//        Log.e("State1",state);
//        ToDO
//        if(state!=null&&state==true){
//            HomeFragment1.network.setVisibility(View.GONE);
//        }else{
//            HomeFragment1.network.setVisibility(View.VISIBLE);
//            HomeFragment1.network.setText("当前网络链接不可用，请稍后尝试");
//        }
//        mGestureDetector = new GestureDetector(this);
//        mBaseApplication = (BaseApplication) getApplication();
        if (savedInstanceState == null) {
            theme = Utils.getAppTheme(this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
//        if(mBaseApplication.isNightMode()){
//            ChangeToNight();
////            mBaseApplication.setIsNightMode(true);
////            setTheme(R.style.AppBaseTheme_Night);
////            recreateOnResume();
//        }
//        else{
//            ChangeToDay();
////            mBaseApplication.setIsNightMode(false);
////            setTheme(R.style.AppTheme);
////            recreateOnResume();
//        }
//        recreateOnResume();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment);
        applyKitKatTranslucency();
//        barTintManager = new SystemBarTintManager(this);
//        barTintManager.setStatusBarTintEnabled(true);
        // inside your activity (if you did not enable transitions in your theme)
//        Explode explode = new Explode();
//        explode.setDuration(700);
//        getWindow().setEnterTransition(explode);
//        getWindow().setExitTransition(explode);
        // set an exit transition
//        getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setAllowReturnTransitionOverlap(false);
//        MainActivity1Binding binding = DataBindingUtil.setContentView(this, R.layout.home_fragment);
//        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
//        binding.setTransition1Sample(sample);
//        ActivityTransition1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition1);
//        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
//        binding.setTransition1Sample(sample);
//        network = (TextView) findViewById(R.id.network);
//        list = (ListView) findViewById(android.R.id.list);
         /*
        * Release版本要注释掉，只用于调试（内存泄漏）
        * */
//        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskReads();
        /*
        * 这段代码应该放在HomeFragment中就不用使用静态的network
        * */
//        networkListener = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
////                state = intent.getBooleanExtra("State",false);
////                Toast.makeText(MainActivity1.this,state,Toast.LENGTH_SHORT).show();
////                if(intent!=null) {
//////                    state = intent.getExtras().getString("State");
////                    state = intent.getStringExtra("State");
//////                    wrong
//////                    if (state != null && state == "true") {
////                    if (state!=null&&state.equals("true")) {
////                        HomeFragment1.network.setVisibility(View.GONE);
////                    } else if(state!=null&&state.equals("false")){
////                        HomeFragment1.network.setVisibility(View.VISIBLE);
////                        HomeFragment1.network.setText("当前网络链接不可用，请稍后尝试");
////                    }
////                }
//                if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
//                    mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//                    networkInfo = mConnectivityManager.getActiveNetworkInfo();
//                    if(networkInfo!=null&&networkInfo.isAvailable()){
//                        network.setVisibility(View.GONE);
//                    } else{
//                        network.setVisibility(View.VISIBLE);
//                        network.setText("当前网络链接不可用，请稍后尝试");
//                    }
//                }
//            }
//        };
//        IntentFilter filter = new IntentFilter("com.example.king.netstate");
////        IntentFilter filter = new IntentFilter();
////        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(networkListener,filter);
//        Intent intent = getIntent();
//        if(intent!=null) {
//            state = intent.getExtras().getString("State");
//            if (state != null && state == "true") {
//                network.setVisibility(View.GONE);
//            } else {
//                network.setVisibility(View.VISIBLE);
//                network.setText("当前网络链接不可用，请稍后尝试");
//            }
//        }
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
////        StrictMode.setThreadPolicy(oldPolicy);
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
////                .detectAll()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
//        initDataTask task = new initDataTask();
//        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);

//        }
//        network = (TextView) findViewById(R.id.network);
        homeFragment = new HomeFragment1();
//        frag = getSupportFragmentManager().findFragmentById(R.id.main_container);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        list = (ListView) findViewById(android.R.id.list);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
//        list.setEmptyView(findViewById(R.id.empty));
//        initDataTask1 task = new initDataTask1(MainActivity1.this);
//        getSupportLoaderManager().initLoader(0,null,task);
        mLayoutInflater = LayoutInflater.from(MainActivity1.this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(this);
         /*
        * DrawerLayout must be measured with MeasureSpec.EXACTLY.
        * */
//        mDrawerLayout.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
//        int w = mDrawerLayout.getMeasuredWidth();
//        int h = mDrawerLayout.getMeasuredHeight();
//        Toast.makeText(this, "Width:" + w + " Height:" + h, Toast.LENGTH_LONG).show();
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
//        if(nav != null){
//            setupDrawerContent(nav);
//        }
        fab = (FloatingActionButton) findViewById(R.id.fab1);
//        fab.attachToListView(list, new ScrollDirectionListener() {
//            @Override
//            public void onScrollDown() {
//                fab.hide();
//            }
//
//            @Override
//            public void onScrollUp() {
//                if(fab.getVisibility()!=View.VISIBLE)
//                    fab.setVisibility(View.VISIBLE);
//                fab.show();
//            }
//        });
//        fab.attachToListView(mRecyclerView, new ScrollDirectionListener() {
//            @Override
//            public void onScrollDown() {
//                fab.hide();
//            }
////
//            @Override
//            public void onScrollUp() {
//                if (fab.getVisibility() != View.VISIBLE)
//                    fab.setVisibility(View.VISIBLE);
//                fab.show();
//            }
//        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit1();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag =  homeFragment.getAdapter().currentFragment;
                if(frag instanceof NewsFragment) {
                    newsFrag = (NewsFragment) homeFragment.getAdapter().currentFragment;
                    newsFrag.transferMsg();
                }else if(frag instanceof CheeseListFragment){
                    rvFragment = (CheeseListFragment) homeFragment.getAdapter().currentFragment;
                    rvFragment.transferMsg();
                }
//                        layoutinflater不行
//                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//                        View v1 = inflater.inflate(R.layout.news_fragment,null);
//                        ListView list = (ListView) v1.findViewById(android.R.id.list);
//                        list.setSelection(0);
//                        接口不行
//                trans.transferMsg();
//             * 和list.setselection(0)一样
//             *
//                        if (!CheeseListFragment.rv.isStackFromBottom()) {
//                            rv.setStackFromBottom(true);
//                        }
//                        LinearLayoutManager lm= (LinearLayoutManager) CheeseListFragment.rv.getLayoutManager();
//                        if(lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop()!=0 || lm.findFirstVisibleItemPosition()!=0)
//                        {
//                            CheeseListFragment.rv.smoothScrollToPosition(0);
//                        }
//                        list.smoothScrollToPosition(0);
//                        fab.hide();
//                        list.setStackFromBottom(false);
//                        Snackbar.make(v,"Demo",Snackbar.LENGTH_SHORT)
//                                .setAction("Action",null).show();
            }
        });
//        mFooterView = LayoutInflater.from(MainActivity1.this).inflate(R.layout.loading_view, null);
//        list.addFooterView(mFooterView, null, false);
//        TabLayout tabLayout  = (TabLayout) findViewById(R.id.tabs);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        if(viewPager != null){
//            setupViewPager(viewPager);
//        }
//        tabLayout.setupWithViewPager(viewPager);
//        initData();
//        homeFragment = new HomeFragment1();
        radio0 = (TextView) findViewById(R.id.radio0);
        radio1 = (TextView) findViewById(R.id.radio1);
        radio2 = (TextView) findViewById(R.id.radio2);
        ll0 = (LinearLayout) findViewById(R.id.ll0);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        img0 = (ImageButton) findViewById(R.id.img0);
        img1 = (ImageButton) findViewById(R.id.img1);
        img2 = (ImageButton) findViewById(R.id.img2);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        transaction.add(R.id.main_container, homeFragment, "NewsFragment");
//        transaction.replace(R.id.main_container,homeFragment);
        transaction.commit();
        transaction.show(homeFragment);
        /*
        * 默认主界面是homefragment
        * */
        radio0.setTextColor(getResources().getColor(R.color.colorPrimary));
        img0.setColorFilter(getResources().getColor(R.color.colorPrimary));

        /**
        * 把onclick修改为了onTouch就使得点击更加灵敏(区域变大了)
        * */
        ll0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent ev) {
                resetOtherTabs();
                radio0.setTextColor(getResources().getColor(R.color.colorPrimary));
                img0.setColorFilter(getResources().getColor(R.color.colorPrimary));
                transaction = fragmentManager.beginTransaction();
//                        Fragment homeFragment = new HomeFragment();
                hideAllFragment(transaction);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment1();
                    /**
                    * 一定要在replace和add以及commit之前
                    * */
                    transaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_exit);
                    transaction.replace(R.id.main_container, homeFragment);
                    transaction.commit();
                } else {
                    transaction.show(homeFragment);
                    transaction.commit();
                }
                return true;
            }
        });
        ll1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent ev) {
                resetOtherTabs();
                radio1.setTextColor(getResources().getColor(R.color.colorPrimary));
                img1.setColorFilter(getResources().getColor(R.color.colorPrimary));
                transaction = fragmentManager.beginTransaction();
                hideAllFragment(transaction);
                if (search == null) {
                    search = new SearchMideaFragment();
                    transaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_exit);
                    transaction.add(R.id.main_container, search);
                    transaction.commit();
                } else {
                    transaction.show(search);
                    transaction.commit();
                }
                return true;
            }
        });
        ll2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent ev) {
                resetOtherTabs();
                radio2.setTextColor(getResources().getColor(R.color.colorPrimary));
                img2.setColorFilter(getResources().getColor(R.color.colorPrimary));
                transaction = fragmentManager.beginTransaction();
                hideAllFragment(transaction);
                if (personFragment == null) {
                    personFragment = new PersonFragment();
                    transaction.setCustomAnimations(R.anim.fragment_in,R.anim.fragment_exit);
                    transaction.add(R.id.main_container, personFragment);
                    transaction.commit();
                } else {
                    transaction.show(personFragment);
                    transaction.commit();
                }
                return true;
            }
        });
    }


    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        mBitmaps.add(loadedImage);
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {

    }

    public void cleanBitmapList(){
        if(mBitmaps.size()>0){
            for(int i = 0 ; i<mBitmaps.size();i++){
                Bitmap b = mBitmaps.get(i);
                if(!b.isRecycled()&&b!=null){
                    b.recycle();
                }
            }
        }
    }

    @Override
    public void transferMsg() {

    }


    /*
*  LocalBroadcastReciver,原本想提高安全，还有节省资源而写的，但是不能动态监听所以不用了
*
* */
    /*private void registerLocalBroadcastReceiver() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("com.example.king.netstate");
        mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        mIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        lbm = LocalBroadcastManager.getInstance(MainActivity1.this);
        mLocalReceiver = new NetWorkConnectChangedReceiver();
        lbm.registerReceiver(mLocalReceiver,mIntentFilter);
    }*/

//    private static  class MyHandler  extends Handler{
//        private  final WeakReference<MainActivity1> mActivity;
//
//        private MyHandler(MainActivity1 activity) {
//            mActivity = new WeakReference<MainActivity1>(activity);
//        }
//
//    }

    @Override
    protected void  onStop() {
        super.onStop();
        LogWrap.e("onStop:MainActivity1");
        if(cmpName == null)
        cmpName= new ComponentName(getApplicationContext(),NetWorkConnectChangedReceiver.class);
        pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(cmpName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                ,PackageManager.DONT_KILL_APP);
//        unregisterReceiver(networkListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogWrap.e("onDestroy:MainActivity1");
//        wrong,因为没有handlemessage所以报错nullpointer
//        handler.removeCallbacks(recreate);
//        handler = null;
//        activities_list.clear();
//        time.clear();
//        handler.removeCallbacksAndMessages(null);
//        unregisterLocalBroadcastReciever();
//        unregisterReceiver(networkListener);
        homeFragment = null;
        cleanBitmapList();
        LogWrap.e("receiver disabled");
        setContentView(R.layout.empty);
        BaseApplication.getLoader().clearMemoryCache();
        System.gc();
//        unregisterReceiver(networkListener);
//        System.exit(0);
    }

    /*private void unregisterLocalBroadcastReciever() {
        if(lbm!=null){
            if(mLocalReceiver!=null){
                lbm.unregisterReceiver(mLocalReceiver);
            }
        }
    }*/

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        // TODO Auto-generated method stub
//        for ( MyOntouchListener listener : touchListeners )
//        {
//            listener.onTouchEvent( event );
//        }
//        return mGestureDetector.onTouchEvent( event );
//    }

//    private ArrayList<MyOntouchListener> touchListeners = new ArrayList<MainActivity1.MyOntouchListener>();
//
//
//    public void registerListener(MyOntouchListener listener)
//    {
//        touchListeners.add( listener );
//    }
//
//
//    public void unRegisterListener(MyOntouchListener listener)
//    {
//        touchListeners.remove( listener );
//    }
//
//    public interface MyOntouchListener
//    {
//        public void onTouchEvent(MotionEvent event);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//    private void initData() {
////        Map<String,Class> name_activities = new HashMap<>();
//        Map<String,Class> name_activities = new ConcurrentHashMap<>();
////        SparseArray<Class> name_activities = new SparseArray<>();
//        name_activities.put("DialogPra", com.example.king.fragement.main.DialogPra.class);
////        name_activities.put(0,com.example.king.fragement.main.DialogPra.class);
//        activities_list.add(name_activities);
////        activities.add(DialogPra.class);
//        time.add("2016-1-15");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("QueryProcess",QueryProcess.class);
////        name_activities.put(1,QueryProcess.class);
//        activities_list.add(name_activities);
////        activities.add(QueryProcess.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Download",com.example.king.fragement.MainActivity.class);
////        name_activities.put(2,com.example.king.fragement.MainActivity.class);
//        activities_list.add(name_activities);
////        activities.add(MainActivity.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("AboutUs",AboutUs.class);
////        name_activities.put(3,AboutUs.class);
//        activities_list.add(name_activities);
////        activities.add(AboutUs.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("CSDN_News",NewsFragement.class);
////        name_activities.put(4,NewsFragement.class);
//        activities_list.add(name_activities);
////        activities.add(NewsFragement.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("News",Main.class);
////        name_activities.put(5,Main.class);
//        activities_list.add(name_activities);
////        activities.add(Main.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
////        name_activities.put(6,MapsActivity.class);
//        name_activities.put("Maps",MapsActivity.class);
//        activities_list.add(name_activities);
////        activities.add(MapsActivity.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Maps1",MapsActivity1.class);
////        name_activities.put(7,MapsActivity1.class);
//        activities_list.add(name_activities);
////        activities.add(MapsActivity1.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Setting",SettingsActivity.class);
////        name_activities.put(8,SettingsActivity.class);
//        activities_list.add(name_activities);
////        activities.add(SettingsActivity.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Midea_News",ItemListActivity.class);
////        name_activities.put(9,ItemListActivity.class);
//        activities_list.add(name_activities);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Jni_Test",JniTest.class);
////        name_activities.put(10,JniTest.class);
//        activities_list.add(name_activities);
////        activities.add(ItemListActivity.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("OSLogin",OsLogin.class);
////        name_activities.put(11,OsLogin.class);
//        activities_list.add(name_activities);
////        activities.add(OsLogin.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Midea_News1",ItemListActivity1.class);
////        name_activities.put(12,ItemListActivity1.class);
//        activities_list.add(name_activities);
////        activities.add(ItemListActivity1.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("FileTest",FileTest.class);
////        name_activities.put(13,FileTest.class);
//        activities_list.add(name_activities);
////        activities.add(FileTest.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Broadcast",BroadcastTest.class);
////        name_activities.put(14,BroadcastTest.class);
//        activities_list.add(name_activities);
////        activities.add(BroadcastTest.class);
//        time.add("2015-10-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("File_Search",SearchFile.class);
////        name_activities.put(15,SearchFile.class);
//        activities_list.add(name_activities);
//        time.add("2016-1-21");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("HightLight",HightLight.class);
////        name_activities.put(16,HightLight.class);
//        activities_list.add(name_activities);
//        time.add("2016-1-22");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Contacts", com.example.king.fragement.main.contacts.MainActivity.class);
////        name_activities.put(17, com.example.king.fragement.main.contacts.MainActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-2-1");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("ShowLocation", TencentMaps.class);
////        name_activities.put(18, TencentMaps.class);
//        activities_list.add(name_activities);
//        time.add("2016-2-3");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("Parcelable & Serializable", PagerActivity.class);
////        name_activities.put(19, PagerActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-2-10");
//        name_activities = new ConcurrentHashMap<>();
////        name_activities = new SparseArray<>();
//        name_activities.put("WifiDirect", WiFiDirectActivity.class);
////        name_activities.put(20, WiFiDirectActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-2-29");
////        name_activities.put("Preference", AdvancePreferenceExample.class);
//////        name_activities.put(20, WiFiDirectActivity.class);
////        activities_list.add(name_activities);
////        time.add("2016-3-4");
//    }

    public void hideAllFragment(FragmentTransaction transaction){
        if(homeFragment!=null)
        {
            transaction.hide(homeFragment);
        }
        if(search!=null){
            transaction.hide(search);
        }
        if(personFragment!=null){
            transaction.hide(personFragment);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        LogWrap.e("onPause:MainActivity1");
    }


    @Override
    protected void onResume() {
        LogWrap.e("onResume:MainActivity1");
        super.onResume();
        if(theme==Utils.getAppTheme(this)){
        }else{
            reload();
        }
        if(cmpName == null)
        cmpName =  new ComponentName(getApplicationContext(),NetWorkConnectChangedReceiver.class);
        pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(cmpName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                ,PackageManager.DONT_KILL_APP);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*
        * 否则点击之后不会退出
        * */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawers();
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(ItemListActivity.this,OsLogin.class);
//                startActivity(it);
//            }
//        });
        switch (item.getItemId()) {
            case R.id.about:
                Intent it = new Intent();
                it.setClass(MainActivity1.this,AboutUs.class);
//                it.setAction("com.example.king.about");
//                it.putExtra("category","about");
//                it.setClass(ItemListActivity.this, AboutUs.class);
//                startActivity(it);
//                it.setClass(ItemListActivity.this, ItemListFragment.class);
//                startActivity(it);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it);
//                Log.d("broadcase", "about");
//                startActivity(it);
//                BaseActivity.trasitionToActivity(this,AboutUs.class);
                startActivity(it,ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.settings:
//                Intent it3 = new Intent(MainActivity1.this,SettingsActivity.class);
//                startActivity(it3);
//                wrong!require to change baseapplication.isnightmode
//                isNight = true;
                /*
                * 1夜间   0 日间
                * */
//                if(i==1) {
//                    mBaseApplication.setIsNightMode(true);
//                        ChangeToNight();
////            mBaseApplication.setIsNightMode(true);
//            setTheme(R.style.AppBaseTheme_Night);
//                Intent it3 = new Intent(MainActivity1.this,SettingsActivity.class);
//                startActivity(it3);
                    Utils.switchAppTheme(this);
//                homeFragment.switchTheme();
                LogWrap.d("mainActivity1");
                setTheme(Utils.getAppTheme(this));
                onResume();
//                thread = new Thread()
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                recreate();
//                finish();
//                handler.ex(recreatePostDelay,1000);
//                    reload();
//            recreateOnResume();
//            mBaseApplication.setIsNightMode(false);
//            recreateOnResume();
//                    recreate();
//                    i = 0;
//                }
//                else{
//                    mBaseApplication.setIsNightMode(false);
//                    ChangeToDay();
//                    setTheme(R.style.AppTheme);
//                    recreate();
//                    i = 1;
//                }
//                setTheme(R.style.AppBaseTheme_Night);
                break;
//            case R.id.sorting:
////                Intent it1 = new Intent();
////                it.setClass(MainActivity1.this,SortFragment.class);
////                startActivity(it);
////                it1.putExtra("category","sorting");
////                it1.setClass(ItemListActivity.this,ItemListFragment.class);
////                it1.setAction("com.example.king.sorting");
////                LocalBroadcastManager.getInstance(this).sendBroadcast(it1);
////                startActivity(it1);
//                LogWrap.d("sorting");
//                break;
//            case R.id.main:
//                Intent it1 = new Intent();
//                it1.setClass(this, PreferenceActivity.class);
////                startActivity(it1);
//                startActivity(it1,ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
////                BaseActivity.trasitionToActivity(this,PreferenceActivity.class);
////                mSettingsFragment = new SettingFragment();
////                replaceFragment(R.id.main_container, mSettingsFragment);
////                Intent it2 = new Intent();
////                it2.setAction("com.example.king.main");
////                LocalBroadcastManager.getInstance(this).sendBroadcast(it2);
//                break;

        }

        return true;
    }

    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*
      * 双击返回顶部
      * */
    public void exit1() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再点击一次返回顶部",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
//            ListView list = (ListView) findViewById(android.R.id.list);
//            list = (ListView) findViewById(android.R.id.list);
//            list.setSelection(0);
            /*
            * 和setselection(0)一样，多了一个动画渐进的效果
            * */
//            refresh.getRefreshableView().smoothScrollToPosition(0);
//            list.smoothScrollToPosition(0);
            frag =  homeFragment.getAdapter().currentFragment;
            if(frag instanceof NewsFragment) {
                newsFrag = (NewsFragment) homeFragment.getAdapter().currentFragment;
                newsFrag.transferMsg();
            }else if(frag instanceof CheeseListFragment){
                rvFragment = (CheeseListFragment) homeFragment.getAdapter().currentFragment;
                rvFragment.transferMsg();
            }
            toolbar.setTitle("MideaNews");

        }
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
//            ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
//            am.restartPackage(getPackageName()); //虽为restart，但并不是重启
//            finish();
//            Fade fade = new Fade();
//            fade.setDuration(300);
//            getWindow().setExitTransition(fade);
//            finishAfterTransition();
            finish();
            /**
            * 加上这一行就不可以注销静态的receiver了
            * */
//            System.exit(0);
        }
    }

    /**
     * wait a time until the onresume finish
     */
//    public  void recreateOnResume() {
////        handler = new Handler();
////                handler .postDelayed(recreate, 100);
//        recreate();
//    }

//    private final static Runnable recreatePostDelay = new Runnable() {
//        public void run() {
////            recreate();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    };

//    @Override
    public void recreate() {
        super.recreate();
        resetOtherTabs();
    }

//    public boolean isConnected() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        if (networkInfo != null) {
//            return networkInfo.isAvailable();
//        }
//        return false;
//    }
//    private void setupViewPager(ViewPager viewPager){
//        adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new CheeseListFragment(),"Category 1");
////        adapter.addFragment(homeFragment.home1,"Category 1");
////        adapter.addFragment(new CheeseListFragment(),"Category 2");
//        adapter.addFragment(new MideaFragment(),"Midea_News1");
////        adapter.addFragment(homeFragment.home2,"Midea_News1");
////        adapter.addFragment(new CheeseListFragment(),"Category 3");
//        adapter.addFragment(new MideaFragment1(),"Midea_News2");
////        adapter.addFragment(homeFragment.home3,"Midea_News2");
//        viewPager.setAdapter(adapter);
//
//    }
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//
//        public Adapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public void addFragment(Fragment fragment,String title){
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }    @Override

    public void reload() {
        Intent intent = getIntent();
        LogWrap.d("it in reload:"+String.valueOf(intent));
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
//        finishAfterTransition();
        overridePendingTransition(0, 0);
//        startActivity(intent);
//        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
//        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
//        startActivity(intent,transitionActivityOptions.toBundle());
//        startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
        startActivity(intent);
        homeFragment.product.mAdapter.changeColor();
        homeFragment.group.mAdapter.changeColor();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public class initDataTask extends AsyncTask<Void,Void,Void>{
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            initData();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            toolbar = (Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            final ActionBar ab = getSupportActionBar();
//            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//            ab.setDisplayHomeAsUpEnabled(true);
//            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//            NavigationView nav = (NavigationView) findViewById(R.id.nav);
//            nav.setNavigationItemSelectedListener(MainActivity1.this);
//            toggle = new ActionBarDrawerToggle(MainActivity1.this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//            mDrawerLayout.setDrawerListener(toggle);
//            toggle.syncState();
//            fab = (FloatingActionButton) findViewById(R.id.fab1);
//            homeFragment = new HomeFragment1();
//            fragmentManager = getSupportFragmentManager();
//            transaction = fragmentManager.beginTransaction();
//            hideAllFragment(transaction);
//            transaction.add(R.id.main_container,homeFragment,"NewsFragment");
//            /*
//            * Can not perform this action after onSaveInstanceState
//            * onSaveInstanceState方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后
//
//再给它添加Fragment就会出错。解决办法就是把commit（）方法替换成 commitAllowingStateLoss()就行
//
//了，其效果是一样的。
//            * */
//            transaction.commitAllowingStateLoss();
//            transaction.show(homeFragment);
//            radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
//            ((RadioButton) radioGroup.findViewById(R.id.radio0)).setChecked(true);
//            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (checkedId) {
//                        case R.id.radio0:
//                            transaction = fragmentManager.beginTransaction();
////                        Fragment homeFragment = new HomeFragment();
//                            hideAllFragment(transaction);
//                            if(homeFragment==null) {
//                                homeFragment = new HomeFragment1();
//                                transaction.replace(R.id.main_container, homeFragment);
//                                transaction.commit();
//                            }else {
//                                transaction.show(homeFragment);
//                                transaction.commit();
//                            }
//                            break;
//                        case R.id.radio1:
//                            transaction = fragmentManager.beginTransaction();
//                            hideAllFragment(transaction);
//                            if(search == null) {
//                                search = new SearchMideaFragment();
//                                transaction.add(R.id.main_container, search);
//                                transaction.commit();
//                            }else {
//                                transaction.show(search);
//                                transaction.commit();
//                            }
//                            break;
//                        case R.id.radio2:
//                            transaction = fragmentManager.beginTransaction();
//                            hideAllFragment(transaction);
//                            if(personFragment == null) {
//                                personFragment = new PersonFragment();
//                                transaction.add(R.id.main_container, personFragment);
//                                transaction.commit();
//                            }else {
//                                transaction.show(personFragment);
//                                transaction.commit();
//                            }
//                            break;
//                    }
//
//                }
//            });
//        }
//    }

//    public class initDataTask1 extends AsyncTaskLoader<Void> implements LoaderManager.LoaderCallbacks<Object> {
//
//        public initDataTask1(Context context) {
//            super(context);
//        }
//
//        @Override
//        public Void loadInBackground() {
//            initData();
//            return null;
//        }
//
//        @Override
//        public Loader<Object> onCreateLoader(int id, Bundle args) {
//            return null;
//        }
//
//        @Override
//        public void onLoadFinished(Loader<Object> loader, Object data) {
//
//        }
//
//        @Override
//        public void onLoaderReset(Loader<Object> loader) {
//
//        }
//    }

//    private void setupDrawerContent(NavigationView nav){
//        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                item.setChecked(true);
//                mDrawerLayout.closeDrawers();
//                return true;
//            }
//        });
//    }
    /*
    * FragmentTabHost有bug
    * 1.加入后，frgament显示不到或者不能滑动
    * 2.切换日夜间模式会崩溃
    * 3.切换viewpager后fragment中listview的状态不保存（每次重建fragment，虽然有解决方法）
    * */
//private ChangeColorIconWithText getTabView(int layoutId) {
//    ChangeColorIconWithText tab = (ChangeColorIconWithText) mLayoutInflater.inflate(layoutId, null);
//    return tab;
//}
//
//    @Override
//    public void onTabChanged(String tabId) {
//        setTitle(tabId);
//
//        resetOtherTabs();
//        ChangeColorIconWithText tabview = (ChangeColorIconWithText) mTabHost.getCurrentTabView();
//        if (tabview != null)
//            tabview.setIconAlpha(1.0f);
//        hideAllFragment(transaction);
//        if(tabId.equals(R.string.home)){
//            if(homeFragment == null) {
//                homeFragment = new HomeFragment1();
//                transaction.replace(R.id.main_container, homeFragment);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }else {
//                transaction.show(homeFragment);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }
//
//
//
//        }else if(tabId.equals(R.string.search_news1)){
//            if(search == null) {
//                search = new SearchMideaFragment();
//                transaction.add(R.id.main_container, search);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }else {
//                transaction.show(search);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }
//
//
//        }else if(tabId.equals(R.string.personal)){
//            if(personFragment == null) {
//                personFragment = new PersonFragment();
//                transaction.add(R.id.main_container, personFragment);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }else {
//                transaction.show(personFragment);
////                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置动画效果
//                transaction.commit();
//            }
//        }
//    }
//
    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
//        for (int i = 0; i < 3; i++) {
        if(theme == R.style.AppBaseTheme_Night){
//            Drawable rightDrawable = getResources().getDrawable(R.drawable.ic_tab_discovery_normal);
//            rightDrawable.setco
//             rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//            tvVersionStatus.setCompoundDrawables(null, null, rightDrawable, null);

           radio0.setTextColor(getResources().getColor(R.color.night_textColor));
           radio1.setTextColor(getResources().getColor(R.color.night_textColor));
           radio2.setTextColor(getResources().getColor(R.color.night_textColor));
        }
        else{
            radio0.setTextColor(getResources().getColor(R.color.black));
            radio1.setTextColor(getResources().getColor(R.color.black));
            radio2.setTextColor(getResources().getColor(R.color.black));
        }
        img0.setColorFilter(getResources().getColor(R.color.gray));
        img1.setColorFilter(getResources().getColor(R.color.gray));
        img2.setColorFilter(getResources().getColor(R.color.gray));
//        }
    }

}


