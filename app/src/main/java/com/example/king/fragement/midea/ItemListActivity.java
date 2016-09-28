package com.example.king.fragement.midea;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.example.king.fragement.R;
import com.example.king.fragement.ReceiveMsgService;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.LogWrap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link com.example.king.fragement.midea.ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link com.example.king.fragement.midea.ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks{
//        ,View.OnTouchListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
//    PullToRefreshListView refresh;
    public static FloatingActionButton fab;
    protected String TAG = "mylog";
    ReceiveMsgService receiveMsgService;
    private boolean conncetState = true; // 记录当前连接状态，因为广播会接收所有的网络状态改变wifi/3g等等，所以需要一个标志记录当前状态
    float from_x,from_y,to_x,to_y;
    int lastVisibleItemPosition = 0;
    static boolean scrollFlag = false;
    int scrollX;
    long exitTime = 0;
    private boolean mTwoPane;
//    ActionBarDrawerToggle toggle;
    public static Toolbar toolbar;
    public static ListView list;
    public static TextView network;
    ImageView image;
    private int theme = 0;

//    private int newsType = Constaint.NEWS_TYPE_YIDONG;
//    private NewsItemBiz mNewsItemBiz;
//    private NewsItemAdapter mAdapter;
//    private List<TraceItem> mDatas = new ArrayList<TraceItem>();
    /**
     * 当前页面
     */
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            theme= Utils.getAppTheme(this);
        }else{
            theme=savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_app_bar1);
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
//        mNewsItemBiz = new NewsItemBiz();
//        new LoadDatasTask().execute();
//        mAdapter = new NewsItemAdapter(ItemListActivity.this, mDatas);
//        Log.d("mAdapter in Activity", String.valueOf(mAdapter));
        network = (TextView) findViewById(R.id.network);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
//        final View v = findViewById(R.id.network);
        /*
        * x=0 y=63
        * */
//        v.post(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(ItemListActivity.this, "width:"+v.getWidth()+"height:"+v.getHeight(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ItemListActivity.this, "当前网络链接不可用，请稍后尝试", Toast.LENGTH_SHORT).show();
        }
//        refresh = (PullToRefreshListView) findViewById(R.id.pullToRefresh);
//        refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });
//        bar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        bar.setDisplayHomeAsUpEnabled(true);
//        image = (ImageView) findViewById(R.id.imageView);
        toolbar.setTitle(getTitle());
        list = (ListView) findViewById(android.R.id.list);
//        Intent it2 = new Intent();
//        it2.setAction("com.example.king.main");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(it2);
//        String state = getIntent().getStringExtra("State");
//        bind();
        /*
        * wrong
        *  Can't create handler inside thread
        *  that has not called Looper.prepare()
        * */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(isConnected()){
//                    network.setVisibility(View.GONE);
//                }else{
//                    network.setText("当前网络链接不可用，请稍后尝试");
//                    network.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                            startActivity(it);
//                        }
//                    });
//                    Toast.makeText(ItemListActivity.this,"当前网络链接不可用，请稍后尝试",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).start();
//        ImageView image = (ImageView)findViewById(R.id.login);
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(ItemListActivity.this,OsLogin.class);
//                startActivity(it);
//            }
//        });

        /*
        * 会导致textview一直不消失
        * */
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
//            Toast.makeText(ItemListActivity.this, "当前网络链接不可用，请稍后尝试", Toast.LENGTH_SHORT).show();
//        }
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                backToTop();
                /*
                * 调用backtoTop（）会报content view not yet create错
                * 所以使用这个技巧来获得listview后setselection返回地一个
                * */
                toolbar.setTitle("双击返回顶部");
                exit1();

            }
        });
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);


//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
////                View v1 = inflater.iR(0);
////                ((ListView)v1.findViewById(R.id.item_list)).setSelection(0);
//                backToTop();
//            }
//        });
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        /*
        * DrawerLayout must be measured with MeasureSpec.EXACTLY.
        * */
//        drawer.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
//        int w = drawer.getMeasuredWidth();
//        int h = drawer.getMeasuredHeight();
        /*
        * 0 0
        * */
//        Toast.makeText(this, "Width:" + w + " Height:" + h, Toast.LENGTH_LONG).show();
//        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        fab = (FloatingActionButton) findViewById(R.id.fab);
//        AppBarLayout bar = (AppBarLayout) findViewById(R.id.app_bar);
//        NavigationView nav = (NavigationView) findViewById(R.id.nav);
//        nav.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

             /*
             * 和list.setselection(0)一样
             * */
                if (!list.isStackFromBottom()) {
                    list.setStackFromBottom(true);
                }
                list.setStackFromBottom(false);
            }
//                if (!refresh.getRefreshableView().isStackFromBottom()) {
//                    refresh.getRefreshableView().setStackFromBottom(true);
//                }
//                refresh.getRefreshableView().setStackFromBottom(false);
//            }
        });
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;
                        // 判断滚动到底部
                        if (list.getLastVisiblePosition() == (list
                                .getCount() - 1)) {
                            fab.setVisibility(View.VISIBLE);
                            toolbar.setTitle("双击回到顶部");
                        }

                        // 判断滚动到顶部
                        if (list.getFirstVisiblePosition() == 0) {
                            fab.setVisibility(View.GONE);
                            toolbar.setTitle("Fragment");
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            /**
             * firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
             * visibleItemCount：当前能看见的列表项个数（小半个也算） totalItemCount：列表项共数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
//                if (scrollFlag
//                        && ScreenUtil.getScreenViewBottomHeight(list) >= ScreenUtil
//                        .getScreenHeight(ItemListActivity.this)) {
                if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
                    fab.setVisibility(View.VISIBLE);
                    toolbar.setTitle("双击回到顶部");
                } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                    fab.setVisibility(View.GONE);
                    toolbar.setTitle("Fragement");
                } else {
                    return;
                }
                lastVisibleItemPosition = firstVisibleItem;
            }

        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     *
     * @param id
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, String.valueOf(id));
            com.example.king.fragement.midea.ItemDetailFragment fragment = new com.example.king.fragement.midea.ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        }
        else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
//            Intent detailIntent = new Intent(this, NewsContentActivity.class);
//            detailIntent.putExtra(com.example.king.fragement.midea.ItemDetailFragment.ARG_ITEM_ID, id);
//            startActivity(detailIntent);
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

//    public void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//            exitTime = System.currentTimeMillis();
//        } else {
////            ActivityManager am = (ActivityManager)getSystemService (Context.ACTIVITY_SERVICE);
////            am.restartPackage(getPackageName()); //虽为restart，但并不是重启
//            finish();
//            System.exit(0);
//        }
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

    @Override
    protected void onResume() {
        super.onResume();
        if(theme==Utils.getAppTheme(this)){
        }else{
            reload();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("theme",theme);
    }

    public void reload() {
        Intent intent = getIntent();
        LogWrap.d("it in reload:"+String.valueOf(intent));
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new CheeseListFragment(), "Category 1");
//        adapter.addFragment(new CheeseListFragment(), "Category 2");
//        adapter.addFragment(new CheeseListFragment(), "Category 3");
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        MyToast toast1 = new MyToast(ItemListActivity.this,"reflect");
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                from_x = event.getX();
//                from_y = event.getY();
//                Toast.makeText(ItemListActivity.this, "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
//                toast1.show();
//                toolbar.setTitle("Fragement");
//                Log.d("test","test");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Toast.makeText(ItemListActivity.this, "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
//                toast1.show();
//                Log.d("test", "test");
//                break;
//            case MotionEvent.ACTION_UP:
//                to_x = event.getX();
//                to_y = event.getY();
//                float xdistance = from_x-to_x;
//                float ydistance = from_y-to_y;
//
//                Log.e("","Distamce++++++++++++++" + xdistance + "__" + ydistance);
//                if(ydistance-60<0 && xdistance-100>0) {
//                    Log.e("","-------左滑+++++++++++");
//
//                } else if(ydistance-60<0 && xdistance+100<0) {
//                    Log.e("","-------右滑+++++++++++++");
//
//                }
//                Toast.makeText(ItemListActivity.this, "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
//                toast1.show();
//                scrollX = list.getScrollX();
//                Log.d("test","test");
//                toolbar.setTitle("双击返回顶部");
//                Toast.makeText(ItemListActivity.this, "双击返回顶部", Toast.LENGTH_SHORT).show();
//                detectScrollX();
//                break;
//        }
//        return false;
//    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
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

   /*
   wrong
   @Override
    public void backToTop() {
        ItemListFragment listfragment =  new ItemListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, listfragment)
                .commit();
        listfragment.getListView().setSelection(0);
    }*/

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
//        toggle.syncState();
    }

//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        /*
//        * 否则点击之后不会退出
//        * */
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
//        drawer.closeDrawers();
////        image.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent it = new Intent(ItemListActivity.this,OsLogin.class);
////                startActivity(it);
////            }
////        });
//        switch (item.getItemId()) {
//            case R.id.about:
//                Intent it = new Intent();
//                it.setAction("com.example.king.about");
////                it.putExtra("category","about");
////                it.setClass(ItemListActivity.this, AboutUs.class);
////                startActivity(it);
////                it.setClass(ItemListActivity.this, ItemListFragment.class);
////                startActivity(it);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it);
//                Log.d("broadcase", "about");
//                break;
//            case R.id.settings:
//                Intent it3 = new Intent(ItemListActivity.this,SettingsActivity.class);
//                startActivity(it3);
////                setTheme(R.style.AppBaseTheme_Night);
//                break;
//            case R.id.sorting:
//                Intent it1 = new Intent();
////                it1.putExtra("category","sorting");
////                it1.setClass(ItemListActivity.this,ItemListFragment.class);
//                it1.setAction("com.example.king.sorting");
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it1);
////                startActivity(it1);
//                Log.d("broadcase","sorting");
//                break;
//            case R.id.main:
//                Intent it2 = new Intent();
//                it2.setAction("com.example.king.main");
//                LocalBroadcastManager.getInstance(this).sendBroadcast(it2);
//                break;
//
//        }
//
//        return true;
//    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

//        public void detectScrollX(){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int tempScrollX = list.getScrollX();
//                if(tempScrollX != scrollX ){
//                    scrollX = tempScrollX;
//
//                }else {
//                    return;
//                }
//            }
//        },100);
//    }
//    private void bind() {
//        Intent intent = new Intent(ItemListActivity.this, ReceiveMsgService.class);
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            receiveMsgService = ((ReceiveMsgService.MyBinder) service)
//                    .getService();
//            receiveMsgService.setOnGetConnectState(new ReceiveMsgService.GetConnectState() { // 添加接口实例获取连接状态
//                @Override
//                public void GetState(boolean isConnected) {
//                    if (conncetState != isConnected) { // 如果当前连接状态与广播服务返回的状态不同才进行通知显示
//                        conncetState = isConnected;
//                        if (conncetState) {// 已连接
////                            handler.sendEmptyMessage(1);
//                            network.setVisibility(View.GONE);
//                        } else {// 未连接
////                            handler.sendEmptyMessage(2);
//                            network.setVisibility(View.VISIBLE);
//                            network.setText("当前网络链接不可用，请稍后尝试");
//                            network.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
//                                    startActivity(it);
//                                }
//                            });
//                            Toast.makeText(ItemListActivity.this, "当前网络链接不可用，请稍后尝试", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                }
//            });
//        }
//    };
//
//    private void unbind() {
//        if (receiveMsgService != null) {
//            unbindService(serviceConnection);
//            Log.i("mylog", "执行unbind()");
//        }
//    }

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//
//            switch (msg.what) {
//                case 1:// 已连接
//                    Toast.makeText(ItemListActivity.this, "网络已经连接", Toast.LENGTH_LONG).show();
//                    break;
//                case 2:// 未连接
//                    Toast.makeText(ItemListActivity.this, "网络未连接", Toast.LENGTH_LONG).show();
//                    break;
//                default:
//                    break;
//            }
//
//        }
//
//
//
//    };

//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        unbind();
//    }

    class MyToast {
        Context context = null;
        Object obj = null;

        public MyToast(Context paramContext, String paramString) {
            this.context = paramContext;
//      this$1 = Toast.makeText(paramContext, paramString, 0);
            Toast toast = Toast.makeText(paramContext, "Test", Toast.LENGTH_SHORT);
            try {
//        paramContext = BroadcastTest.this.getClass().getDeclaredField("mTN");
//        paramContext.setAccessible(true);
                Field field = toast.getClass().getDeclaredField("mTN");
                field.setAccessible(true);
//        this.obj = paramContext.get(BroadcastTest.this);
                obj = field.get(toast);
//        return;
            } catch (Exception this$1) {
                LogWrap.d( "MyToast Exception--->" + ItemListActivity.this.toString());
            }
        }

        public void hide() {
            try {
//        Method method = obj.getClass().getDeclaredMethod("hide",null);
//        method.invoke(obj,null);
                this.obj.getClass().getDeclaredMethod("hide", null).invoke(this.obj, null);
//        return;
            } catch (Exception localException) {
                LogWrap.d( "hide Exception--->" + localException.toString());
                localException.printStackTrace();
            }
        }

        public void show() {
            try {
                Field localField = this.obj.getClass().getDeclaredField("mNextView");
                localField.setAccessible(true);
                View localView = ((LayoutInflater) this.context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast, null);
                /*
                * 这里不能用tv2因为tv2是broadcastxml中的tv而我们现在是要用toat的tv所以使用tv1
                * 否则会报nullpointer
                * 因为我现在使用inflater
                * 布局文件为toast
                * toast里面没有tv2
                * */
//                TextView localTextView = (TextView) localView.findViewById(R.id.tv2);
                TextView localTextView = (TextView) localView.findViewById(R.id.tv1);
//                Button localTextView = (Button) localView.findViewById(R.id.tv1);
//        localField.set(obj,localTextView);
//                        StringBuilder localStringBuilder = new StringBuilder().append("还有");
//        BroadcastTest localBroadcastTest = BroadcastTest.this;
//        int i = localBroadcastTest.i;
//        localBroadcastTest.i = (i - 1);
//                        i--;
//                        localTextView.setText("还有" + i+"步成为开发者");
//                localTextView.setText("getHeight=:" + refresh.getRefreshableView().getScrollX());
                localTextView.setText("getHeight=:" + list.getScrollX());
                this.obj.getClass().getDeclaredMethod("show", null).invoke(this.obj, null);
                localField.set(this.obj, localView);
                Method method = obj.getClass().getDeclaredMethod("show", null);
                method.invoke(obj, null);
//        localField.set(this.obj, localView);
//        this.obj.getClass().getDeclaredMethod("show", null).invoke(this.obj, null);
//        return;
            } catch (Exception localException) {
                LogWrap.d("show Exception--->" + localException.toString());
                localException.printStackTrace();
            }
        }
    }
    public void onWindowFocusChanged(boolean paramBoolean)
    {
        super.onWindowFocusChanged(paramBoolean);
        int[] arrayOfInt = new int[2];
        this.toolbar.getLocationOnScreen(arrayOfInt);
        int j = arrayOfInt[0];
        int k = arrayOfInt[1];
        /*
        * x= 0 y=63
        * */
//        this.network.append("\nx : " + j);
//        this.network.append("\ny : " + k);
//        Toast.makeText(ItemListActivity.this, "x:"+j+"y:"+k, Toast.LENGTH_SHORT).show();
    }
//    class LoadDatasTask extends AsyncTask<Void, Void, Void>
//    {
//
//        @Override
//        protected Void doInBackground(Void... params)
//        {
//            try
//            {
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                mDatas = newsItems;
//            } catch (CommonException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result)
//        {
//            mAdapter.addAll(mDatas);
//            mAdapter.notifyDataSetChanged();
////            mXListView.stopRefresh();
//        }
//
//    }

}
