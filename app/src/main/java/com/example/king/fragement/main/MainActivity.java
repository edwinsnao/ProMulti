package com.example.king.fragement.main;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.midea.*;
import com.example.king.fragement.midea.ItemListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kings on 2016/1/14.
 */
public class MainActivity extends AppCompatActivity implements MideaFragment.Callbacks,MideaFragment1.Callbacks,
        NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
     Adapter adapter;
    long exitTime = 0;
    ViewPager viewPager;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
//    static View mFooterView;
    public  TextView network;
    //    public static List<Class> activities = new ArrayList<>();
    private PackageManager pm = getPackageManager();
    ComponentName compName = new ComponentName(getApplicationContext()
    ,NetWorkConnectChangedReceiver.class);
    //    public boolean onCreateOptionsMenu(Menu menu) {

    //    public static Class[] activities1 = {DialogPra.class};

    @Override
    protected void onStart() {
        super.onStart();
        pm.setComponentEnabledSetting(compName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        ,PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pm.setComponentEnabledSetting(compName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*
        * Release版本要注释掉，只用于调试（内存泄漏）
        * */
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
//        list = (ListView) findViewById(android.R.id.list);
//        list.addFooterView(mFooterView, null, false);
        network = (TextView) findViewById(R.id.network);
//        final View v = findViewById(R.id.network);
//        v.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this, "width:"+v.getWidth()+"height:"+v.getHeight(), Toast.LENGTH_SHORT).show();
//            }
//        });
        if (isConnected()) {
            network.setVisibility(View.GONE);
        } else {
            network.setText("当前网络链接不可用，请稍后尝试");
            network.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    startActivity(it);
                }
            });
            Toast.makeText(MainActivity.this, "当前网络链接不可用，请稍后尝试", Toast.LENGTH_SHORT).show();
        }
        /*
        * 舍弃，一旦接收后不会更新改变（网络好了不会消失）
        * 用isConnected()
        * */
//         state = getIntent().getStringExtra("State");
//        if (Boolean.valueOf(state)) {
//            network.setVisibility(View.GONE);
//        } else {
//            network.setText("当前网络链接不可用，请稍后尝试");
//            network.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                    startActivity(it);
//                }
//            });
//            Toast.makeText(MainActivity.this, "当前网络链接不可用，请稍后尝试", Toast.LENGTH_SHORT).show();
//        }
//        ITEMS.add(new DummyItem(5L, "OsLogin.class", OsLogin.class, "A demo", "10-5"));
//        ITEMS.add(new DummyItem(6L, "FileTest.class", FileTest.class, "A demo", "10-8"));
//        ITEMS.add(new DummyItem(7L, "Broadcasr.class", BroadcastTest.class, "A demo", "10-9"));
//        ITEMS.add(new DummyItem(11L, "Process.class", , "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "MainActivity.class", , "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "AboutUs.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "NewsFragement.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "News2.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "MapActivity.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "MapActivity1.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "SettingsActivity.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "NewsActivity.class",, "A demo", "10-10"));
//        ITEMS.add(new DummyItem(11L, "NewsActivity1.class",, "A demo", "10-10"));
//        initData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                backToTop();
//                /*
//                * 调用backtoTop（）会报content view not yet create错
//                * 所以使用这个技巧来获得listview后setselection返回地一个
//                * */
//                toolbar.setTitle("双击返回顶部");
//                exit1();
//
//            }
//        });
        setSupportActionBar(toolbar);
//        list = (ListView) findViewById(android.R.id.list);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//        NavigationView nav = (NavigationView) findViewById(R.id.nav_view);
        NavigationView nav = (NavigationView) findViewById(R.id.nav);
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
        if(nav != null){
            setupDrawerContent(nav);
        }
         viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null){
            setupViewPager(viewPager);
        }
         fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                         /*
//             * 和list.setselection(0)一样
//             * */
//                        if (!list.isStackFromBottom()) {
//                            list.setStackFromBottom(true);
//                        }
//                        list.setStackFromBottom(false);
//                        Snackbar.make(v,"Demo",Snackbar.LENGTH_SHORT)
//                                .setAction("Action",null).show();
//                    }
//                });
//        mFooterView = LayoutInflater.from(MainActivity.this).inflate(R.layout.loading_view, null);
//        getListView().addFooterView(mFooterView, null, false);
        TabLayout tabLayout  = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // TODO Auto-generated method stub
//                switch (scrollState) {
//                    // 当不滚动时
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
//                        scrollFlag = false;
//                        // 判断滚动到底部
//                        if (list.getLastVisiblePosition() == (list
//                                .getCount() - 1)) {
//                            fab.setVisibility(View.VISIBLE);
//                            toolbar.setTitle("双击回到顶部");
//                        }
//
//                        // 判断滚动到顶部
//                        if (list.getFirstVisiblePosition() == 0) {
//                            fab.setVisibility(View.GONE);
//                            toolbar.setTitle("Fragment");
//                        }
//
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
//                        scrollFlag = true;
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
//                        scrollFlag = false;
//                        break;
//                }
//            }
//
//            /**
//             * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
//             * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
//             */
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
////                if (scrollFlag
////                        && ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
////                        .getScreenHeight(ItemListActivity.this)) {
//                if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
//                    fab.setVisibility(View.VISIBLE);
//                    toolbar.setTitle("双击回到顶部");
//                } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
//                    fab.setVisibility(View.GONE);
//                    toolbar.setTitle("Fragement");
//                } else {
//                    return;
//                }
//                lastVisibleItemPosition = firstVisibleItem;
//            }
//
//        });

    }

//    private void initData() {
//        Map<String,Class> name_activities = new HashMap<>();
//        name_activities.put("DialogPra",DialogPra.class);
//        activities_list.add(name_activities);
////        activities.add(DialogPra.class);
//        time.add("2016-1-15");
//        name_activities = new HashMap<>();
//        name_activities.put("QueryProcess",QueryProcess.class);
//        activities_list.add(name_activities);
////        activities.add(QueryProcess.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Download",com.example.king.fragement.MainActivity.class);
//        activities_list.add(name_activities);
////        activities.add(MainActivity.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("AboutUs",AboutUs.class);
//        activities_list.add(name_activities);
////        activities.add(AboutUs.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("CSDN_News",NewsFragement.class);
//        activities_list.add(name_activities);
////        activities.add(NewsFragement.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("News",Main.class);
//        activities_list.add(name_activities);
////        activities.add(Main.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Maps",MapsActivity.class);
//        activities_list.add(name_activities);
////        activities.add(MapsActivity.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Maps1",MapsActivity1.class);
//        activities_list.add(name_activities);
////        activities.add(MapsActivity1.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Setting",SettingsActivity.class);
//        activities_list.add(name_activities);
////        activities.add(SettingsActivity.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Midea_News",ItemListActivity.class);
//        activities_list.add(name_activities);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Jni_Test",JniTest.class);
//        activities_list.add(name_activities);
////        activities.add(ItemListActivity.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("OSLogin",OsLogin.class);
//        activities_list.add(name_activities);
////        activities.add(OsLogin.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Midea_News1",ItemListActivity1.class);
//        activities_list.add(name_activities);
////        activities.add(ItemListActivity1.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("FileTest",FileTest.class);
//        activities_list.add(name_activities);
////        activities.add(FileTest.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("Broadcast",BroadcastTest.class);
//        activities_list.add(name_activities);
////        activities.add(BroadcastTest.class);
//        time.add("2015-10-10");
//        name_activities = new HashMap<>();
//        name_activities.put("File_Search",SearchFile.class);
//        activities_list.add(name_activities);
//        time.add("2016-1-21");
//        name_activities = new HashMap<>();
//        name_activities.put("HightLight",HightLight.class);
//        activities_list.add(name_activities);
//        time.add("2016-1-22");
//        name_activities = new HashMap<>();
//        name_activities.put("Contacts", com.example.king.fragement.main.contacts.MainActivity.class);
//        activities_list.add(name_activities);
//        time.add("2016-2-1");
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        /*
        * 否则点击之后不会退出
        * */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
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
                it.setAction("com.example.king.about");
//                it.putExtra("category","about");
//                it.setClass(ItemListActivity.this, AboutUs.class);
//                startActivity(it);
//                it.setClass(ItemListActivity.this, ItemListFragment.class);
//                startActivity(it);
                LocalBroadcastManager.getInstance(this).sendBroadcast(it);
                LogWrap.d( "about");
                break;
            case R.id.settings:
                Intent it3 = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(it3);
//                setTheme(R.style.AppBaseTheme_Night);
                break;
//            case R.id.sorting:
//                Intent it1 = new Intent();
////                it1.putExtra("category","sorting");
////                it1.setClass(ItemListActivity.this,ItemListFragment.class);
//                it1.setAction("com.example.king.sorting");
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it1);
////                startActivity(it1);
//                LogWrap.d("sorting");
//                break;
//            case R.id.main:
//                Intent it2 = new Intent();
//                it2.setAction("com.example.king.main");
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it2);
//                break;

        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
//    @Override
//        getMenuInflater().inflate(R.menu.sample_actions,menu);
//        return true;
//    }
  /*
    * 双击返回顶部
    * */
public void exit1() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(getApplicationContext(), "再点击一次返回顶部",
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
        list.smoothScrollToPosition(0);
        toolbar.setTitle(getTitle());

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
            finish();
            System.exit(0);
        }
    }
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }
    public void onWindowFocusChanged(boolean paramBoolean)
    {
        super.onWindowFocusChanged(paramBoolean);
        int[] arrayOfInt = new int[2];
        this.toolbar.getLocationOnScreen(arrayOfInt);
        int j = arrayOfInt[0];
        int k = arrayOfInt[1];
        /*
        * x=0 y=63
        * */
//        this.network.append("\nx : " + j);
//        this.network.append("\ny : " + k);
        Toast.makeText(MainActivity.this, "x:"+j+"y:"+k, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupViewPager(ViewPager viewPager){
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(),"Category 1");
//        adapter.addFragment(new CheeseListFragment(),"Category 2");
        adapter.addFragment(new MideaFragment(),"Midea_News1");
//        adapter.addFragment(new CheeseListFragment(),"Category 3");
        adapter.addFragment(new MideaFragment1(),"Midea_News2");
        viewPager.setAdapter(adapter);

    }
    private void setupDrawerContent(NavigationView nav){
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onItemSelected(String paramLong) {

    }

//    @Override
//    public void onItemSelected(String id) {
////        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//        int index = viewPager.getCurrentItem();
//        if(adapter.getPageTitle(index) == "Category 1") {
////        adapter getFragmentManager().findFragmentById(R.id.list);
//            Bundle arguments = new Bundle();
//            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
//            CheeseListFragment fragment = new CheeseListFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.item_detail_container, fragment)
//                    .commit();
//        }
//        else if(adapter.getPageTitle(index) == "Midea_News") {
////        adapter getFragmentManager().findFragmentById(R.id.list);
//            Bundle arguments = new Bundle();
////            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
////            ItemDetailFragment fragment = new ItemDetailFragment();
//            MideaFragment fragment = new MideaFragment();
////            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.item_detail_container, fragment)
//                    .commit();
//        }
//        else if(adapter.getPageTitle(index) == "Midea_News1"){
//            Bundle arguments = new Bundle();
////            arguments.putString(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
////            com.example.king.fragement.midea.ItemDetailFragment fragment = new com.example.king.fragement.midea.ItemDetailFragment();
////            fragment.setArguments(arguments);
//            MideaFragment1 fragment = new MideaFragment1();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.item_detail_container, fragment)
//                    .commit();
//        }
//
//        }
//        else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
//            Intent detailIntent = new Intent(this, NewsContentActivity.class);
//            detailIntent.putExtra(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
//        }
//    }

    static class Adapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
