package com.example.king.fragement.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


import com.edwinsnao.midea.CommonException;
import com.edwinsnao.midea.Constaint;
import com.example.king.fragement.R;
import com.example.king.fragement.dummy.DummyContent;
import com.example.king.fragement.dummy.DummyContent1;
import com.example.king.fragement.dummy.DummyContent2;
import com.example.king.fragement.midea.NetUtil;
import com.example.king.fragement.midea.NewsContentActivity;
import com.example.king.fragement.midea.NewsItem;
import com.example.king.fragement.midea.NewsItemAdapter;
import com.example.king.fragement.midea.NewsItemBiz;
import com.example.king.fragement.midea.NewsItemDao;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MideaFragment1
        extends ListFragment implements OnScrollListener {
    public MideaFragment1() {
        super();
        mNewsItemBiz = new NewsItemBiz();
    }
    long exitTime = 0;
    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFREASH = 0x111;

    private static final int TIP_ERROR_NO_NETWORK = 0X112;
    private static final int TIP_ERROR_SERVER = 0X113;

    /**
     * 是否是第一次进入
     */
    private boolean isFirstIn = true;

    /**
     * 是否连接网络
     */
    static boolean isConnNet = false;

    /**
     * 当前数据是否是从网络中获取的
     */
    static boolean isLoadingDataFromNetWork;

    static NewsItemDao mNewsItemDao;
    //        ,PullToRefreshBase.OnRefreshListener {
    //    Context context = getActivity();
    /*
    * 控制到达底部之前的4个item就开始handler的运行
    * */
    final static int AUTOLOAD_THREADSHOLD = 4;
    /*
    * 最多200个后就不加载了
    * */
    SwipeRefreshLayout swipe;
    static int newsType = Constaint.NEWS_TYPE_YANFA;
    //    private int newsType = Constaint.NEWS_TYPE_YUNJISUAN;
    static NewsItemBiz mNewsItemBiz;
    static NewsItemAdapter mAdapter;
    static List<NewsItem> mDatas = new ArrayList<NewsItem>();
    /**
     * 当前页面
     */
    static int currentPage = 1;
    //    PullToRefreshListView refresh;
    static String action;
    int tmp = 0;
    static boolean scrollFlag = false;
    static int lastVisibleItemPosition;// 标记上次滑动位置
    final static int MAXIMUM_ITEMS = 200;
    private static DummyContent adapter;
    private static DummyContent1 adapter1;
    private static DummyContent2 adapter2;
    //    private SimpleAdapter adapter;
    static View mFooterView;
    static Handler handler;
//    Toolbar toolbar;
    static boolean mIsLoading = false;
    static boolean mMoreDataAvailable = true;
    static boolean mWasLoading = false;

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition = -1;
    private Callbacks mCallbacks = this.sDummyCallbacks;
//    static ListView list = MainActivity1.list;
//    static ListView list = MainActivity.list;

    public MideaFragment1(int newsType) {
        this.newsType = newsType;
        mNewsItemBiz = new NewsItemBiz();

    }

    private Callbacks sDummyCallbacks = new Callbacks() {
        public void onItemSelected(String paramAnonymousLong) {
        }

//        @Override
//        public void backToTop() {
//
//        }
    };

    private void setActivatedPosition(int paramInt) {
        if (paramInt == -1) {
            getListView().setItemChecked(this.mActivatedPosition, false);
        }
        for (; ; ) {
            this.mActivatedPosition = paramInt;
//      return;
            getListView().setItemChecked(paramInt, true);
        }
    }
    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }
    public void onActivityCreated(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        if (isConnected())  {
//            Intent it = new Intent();
//            it.putExtra("activity","News");
//            it.setClass(getActivity(),NoNetWork.class);
//            startActivity(it);
//        }
        mNewsItemBiz = new NewsItemBiz();
          /*
        * 初始化newsItemdao
        * */
        mNewsItemDao = BaseApplication.getNewsItemDao();
        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
        LogWrap.d("mAdapter in Activity"+ String.valueOf(mAdapter));
        final LoadDatasTask task = new LoadDatasTask();
        task.execute();
        /*
        * 不会执行
        * */
//        if(isConnected()){
//            if(currentPage == 1&& mIsLoading == true){
//                task.execute();
//            }
//        }
//        try {
//            Toast.makeText(getActivity(), "" + mNewsItemBiz.getNewsItems(newsType, currentPage), Toast.LENGTH_SHORT).show();
//        } catch (CommonException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        if(isFirstIn){
            isFirstIn = false;
            if (NetUtil.checkNet(getActivity())) {
                isConnNet = true;
                // 获取最新数据
                isLoadingDataFromNetWork = true;
//                有网的时候就检查数据库有没有，如果有则直接拿数据，否则更新
                try {
                    List<NewsItem> newsItems = MideaFragment1.mNewsItemDao.list(MideaFragment1.newsType, MideaFragment1.currentPage);
                    if (newsItems.size() == 0) {
                        List<NewsItem> newsItems1 = mNewsItemBiz.getNewsItems(newsType, currentPage);
                        mAdapter.addAll(newsItems1);
//                        这里出错了，少了1，导致数据库得不到第一次的数据
//                        mNewsItemDao.add(newsItems);
                        mNewsItemDao.add(newsItems1);
                    } else {
                        MideaFragment1.mAdapter.addAll(newsItems);
                        MideaFragment1.mAdapter.notifyDataSetChanged();
                    }
                    // 设置刷新时间
//                    AppUtil.setRefreashTime(getActivity(), newsType);
                    // 清除数据库数据
//                    mNewsItemDao.deleteAll(newsType);
                    // 调试
//                    Cursor c = mNewsItemDao.query();
//                    while(c.moveToNext()){
//                        Log.d("Cursor in new col0",c.getString(0));
//                        Log.d("Cursor in new col1",c.getString(1));
//                        Log.d("Cursor in new col2",c.getString(2));
//                        Log.d("Cursor in new col3",c.getString(3));
//                        Log.d("Cursor in new col4",c.getString(4));
//                        Log.d("Cursor in new col5",c.getString(5));
//                    }
//                    c.close();

                } catch (CommonException e) {
                    e.printStackTrace();
                    isLoadingDataFromNetWork = false;
//                    return TIP_ERROR_SERVER;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
//            如果没有网络则直接在数据库拿数据
            else {
                isConnNet = false;
                // 获取最新数据
                isLoadingDataFromNetWork = false;
                List<NewsItem> newsItems = MideaFragment1.mNewsItemDao.list(MideaFragment1.newsType, MideaFragment1.currentPage);
                MideaFragment1.mAdapter.addAll(newsItems);
                MideaFragment1.mAdapter.notifyDataSetChanged();
            }
        }

//        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
//        Log.d("mAdapter in Activity", String.valueOf(mAdapter));
//    paramBundle = DummyContent_1.getItems();
        handler = new Handler();
        swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe);
//        ViewTreeObserver vto = swipe.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//
//                final DisplayMetrics metrics = getResources().getDisplayMetrics();
//                Float mDistanceToTriggerSync = Math.min(((View) swipe.getParent()).getHeight() * 0.6f, 500 * metrics.density);
//
//                try {
//                    Field field = SwipeRefreshLayout.class.getDeclaredField("mDistanceToTriggerSync");
//                    field.setAccessible(true);
//                    field.setFloat(swipe, mDistanceToTriggerSync);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                ViewTreeObserver obs = swipe.getViewTreeObserver();
//                obs.removeOnGlobalLayoutListener(this);
//            }
//        });
//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isConnected()){
//                    if(currentPage == 1&& mIsLoading == true){
                    if(currentPage == 1|| currentPage == 2)
                    {
                        LogWrap.d("currentpage in news1:"+String.valueOf(currentPage));
                        LoadDatasTask task1 = new LoadDatasTask();
                        task1.execute();
                        /*
                        * 这个每次都新建就可以解决不能重复更新（执行同一个asynctask）问题
                        * */
                        new MideaFragment.LoadDatasTask().execute();
//                        MideaFragment.task1.execute();
                    }

                }
                else{
                    Toast.makeText(getActivity(),"没有网络，请稍后刷新",Toast.LENGTH_SHORT).show();
                }
//                swipe.setRefreshing(false);

////                new GetDataTask().execute();
////                currentPage++;
////                new LoadDatasTask().execute();
//
//                if (NetUtil.checkNet(getActivity())) {
//                    isConnNet = true;
//                    // 获取最新数据
//                    try {
//                        List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
////                        mAdapter.setDatas(newsItems);
//                        mAdapter.addAll(newsItems);
//
//                        isLoadingDataFromNetWork = true;
//                        // 设置刷新时间
////                        AppUtil.setRefreashTime(getActivity(), newsType);
//                        // 清除数据库数据
////                        null-pointer
//                        mNewsItemDao.deleteAll(newsType);
//                        // 存入数据库
//                        mNewsItemDao.add(newsItems);
//
//                    } catch (CommonException e) {
//                        e.printStackTrace();
//                        isLoadingDataFromNetWork = false;
////                        return TIP_ERROR_SERVER;
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    isConnNet = false;
//                    isLoadingDataFromNetWork = false;
//                    // TODO从数据库中加载
//                    List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//                    mDatas = newsItems;
//                    //mAdapter.setDatas(newsItems);
////                    return TIP_ERROR_NO_NETWORK;
//                }
//
////                return -1;
                /*
                * 不set false的话会一直不返回，卡在那里
                * */
                swipe.setRefreshing(false);
//                getListView().setSelection(0);
            }
        });
//        IntentFilter intentFilter = new IntentFilter();
//        //建议把它写一个公共的变量，这里方便阅读就不写了。
//        intentFilter.addAction("com.example.king.about");
//        intentFilter.addAction("com.example.king.sorting");
//        intentFilter.addAction("com.example.king.main");
//        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                action = intent.getAction();
//                Log.d("action", action);
//                if (action == null) {
//                    LinkedList<DummyItem> items2 = DummyContent.getItems();
//                    adapter = new DummyContent(getActivity(), items2);
//                } else {
//                    switch (action) {
//                        case "com.example.king.sorting":
//                            LinkedList<DummyContent1.DummyItem> items = DummyContent1.getItems();
//                            adapter1 = new DummyContent1(getActivity(), items);
//                            Log.d("receive", "sorting");
//                            break;
//                        case "com.example.king.about":
//                            LinkedList<DummyContent2.DummyItem> items1 = DummyContent2.getItems();
//                            adapter2 = new DummyContent2(getActivity(), items1);
//                            Log.d("receive", "about");
//                            break;
//                        default:
//                            LinkedList<DummyItem> items2 = DummyContent.getItems();
//                            adapter = new DummyContent(getActivity(), items2);
//                            break;
//
//                    }
//                }
////                if (action == null) {
////                    refresh.setAdapter(adapter);
////                } else {
////                    switch (action) {
////                        case "com.example.king.sorting":
////                            refresh.setAdapter(adapter1);
////                            break;
////                        case "com.example.king.about":
////                            refresh.setAdapter(adapter2);
////                            break;
////                        default:
////                            refresh.setAdapter(adapter);
////                            break;
////
////                    }
////                }
//                if (action == null) {
//                    setListAdapter(adapter);
//                } else {
//                    switch (action) {
//                        case "com.example.king.sorting":
//                            setListAdapter(adapter1);
//                            break;
//                        case "com.example.king.about":
//                            setListAdapter(adapter2);
//                            break;
//                        default:
//                            setListAdapter(adapter);
//                            break;
//
//                    }
//                }
////                refresh.setOnItemClickListener(new OnItemClickListener() {
////                    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
//////          paramAnonymousAdapterView = (DummyItem)MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//////          paramAnonymousAdapterView = new Intent(MideaFragment1.this.getActivity(), paramAnonymousAdapterView.getActivity());
////// wrong
////// DummyItem item3 = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//////                        Intent it2 = new Intent(MideaFragment1.this.getActivity(), item3.getActivity());
//////                        MideaFragment1.this.startActivity(it2);
////                        if (action == null) {
////                            DummyItem item = (DummyItem) MideaFragment1.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
////                            Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
////                            MideaFragment1.this.startActivity(it);
////                        } else {
////                            switch (action) {
////                                case "com.example.king.sorting":
////                                    DummyContent1.DummyItem item = (DummyContent1.DummyItem) MideaFragment1.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
////                                    Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
////                                    MideaFragment1.this.startActivity(it);
////                                    break;
////                                case "com.example.king.about":
////                                    DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) MideaFragment1.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
////                                    Intent it1 = new Intent(MideaFragment1.this.getActivity(), item2.getActivity());
////                                    MideaFragment1.this.startActivity(it1);
////                                    break;
////                                default:
////                                    DummyItem item4 = (DummyItem) MideaFragment1.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
////                                    Intent it3 = new Intent(MideaFragment1.this.getActivity(), item4.getActivity());
////                                    MideaFragment1.this.startActivity(it3);
////                                    break;
////
////                            }
////                        }
////                    }
////                });
//                getListView().setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
////          paramAnonymousAdapterView = (DummyItem)MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
////          paramAnonymousAdapterView = new Intent(MideaFragment1.this.getActivity(), paramAnonymousAdapterView.getActivity());
//// wrong
//// DummyItem item3 = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
////                        Intent it2 = new Intent(MideaFragment1.this.getActivity(), item3.getActivity());
////                        MideaFragment1.this.startActivity(it2);
//                        if (action == null) {
//                            DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                            MideaFragment1.this.startActivity(it);
//                        } else {
//                            switch (action) {
//                                case "com.example.king.sorting":
//                                    DummyContent1.DummyItem item = (DummyContent1.DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                                    Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                                    MideaFragment1.this.startActivity(it);
//                                    break;
//                                case "com.example.king.about":
//                                    DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                                    Intent it1 = new Intent(MideaFragment1.this.getActivity(), item2.getActivity());
//                                    MideaFragment1.this.startActivity(it1);
//                                    break;
//                                default:
//                                    DummyItem item4 = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                                    Intent it3 = new Intent(MideaFragment1.this.getActivity(), item4.getActivity());
//                                    MideaFragment1.this.startActivity(it3);
//                                    break;
//
//                            }
//                        }
//                    }
//                });
//            }
//        };
//        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
//        final String action = getActivity().getIntent().getAction();
//        Log.d("action",action);
//        if (action == null) {
//            LinkedList<DummyContent.DummyItem> items2 = DummyContent.getItems();
//            adapter = new DummyContent(getActivity(), items2);
//        } else {
//            switch (action) {
//                case "sorting":
//                    LinkedList<DummyContent1.DummyItem> items = DummyContent1.getItems();
//                    adapter1 = new DummyContent1(getActivity(), items);
//                    break;
//                case "about":
//                    LinkedList<DummyContent2.DummyItem> items1 = DummyContent2.getItems();
//                    adapter2 = new DummyContent2(getActivity(), items1);
//                    break;
//                default:
//                    LinkedList<DummyContent.DummyItem> items2 = DummyContent.getItems();
//                    adapter = new DummyContent(getActivity(), items2);
//                    break;
//
//            }
//        }
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_view, null);
//        toolbar = MainActivity1.toolbar;
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            //            @Override
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
//    setListAdapter(new DummyContent_1(getActivity(), paramBundle));
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /*
        * 没数据时的提示
        * */
        View v = inflater.inflate(R.layout.emtyview,null);
        ImageView empty = (ImageView) v.findViewById(R.id.empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadDatasTask().execute();
            }
        });
//        empty.setVisibility(View.GONE);
//        getListView().removeAllViews();
//        ((ViewGroup)getListView().getParent()).addView(empty);
//        getListView().setEmptyView(empty);
        getListView().addFooterView(mFooterView, null, false);
//        setListAdapter(new DummyContent(getActivity(), items));
        /*
        * listview中调用顺序是
        * 1.addHeaderView
        * 2.addFooterView
        * 3.setListAdapter
        * 否则报空指针错误
        * */
//        if (action == null) {
//            setListAdapter(adapter);
//        } else {
//            switch (action) {
//                case "com.example.king.sorting":
//                    setListAdapter(adapter1);
//                    break;
//                case "com.example.king.about":
//                    setListAdapter(adapter2);
//                    break;
//                default:
//                    setListAdapter(adapter);
//                    break;
//
//            }
//        }
        /*
        * refresh中和上面listview有一点区别
        * 先setAdapter的到了refresh
        * 然后再addfooterView，否则会报错nullpointer
        * 因为那时refresh还没有初始化
        * */
//        LinkedList<DummyItem> items2 = DummyContent.getItems();
//        adapter = new DummyContent(getActivity(), items2);
//        setListAdapter(adapter);
        setListAdapter(mAdapter);
//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
//        refresh.getRefreshableView().addFooterView(mFooterView, null, false);
//        refresh.setOnRefreshListener(this);


        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
//                DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                MideaFragment1.this.startActivity(it);
                 /*
        * 这里paramint（position）不要减去1
        * */
//                TraceItem newsItem = mDatas.get(paramAnonymousInt);
                NewsItem newsItem = (NewsItem) paramAnonymousAdapterView.getAdapter().getItem(paramAnonymousInt);
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
        /*
        * 只能对一个专题有效
        * */

                /*
                * 点击后不能看到文章的原因
                * */
                String urlStr = "http://www.midea.com/cn/news_center/Group_News";
                int size = newsItem.getLink().length();
                /*
                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
                * */
//                try {
//                    urlStr = URLUtil.generateUrl(newsType, currentPage);
//                    urlStr = URLDecoder.decode(urlStr);
                intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
                LogWrap.d("url"+ String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                /*这里报错commonexceptio
                总结一下commonexceptio的原因：
                1.可能是没有加internet权限
                2.肯呢个是没有对链接格式化
                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
                所以一直报错，所以要先对得到的链接先格式化
                */
                startActivity(intent);
//                Toast.makeText(getActivity(), "haha", Toast.LENGTH_SHORT).show();
            }
        });
//        refresh.getRefreshableView().setSelection(0);
//        refresh.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
////                DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                DummyItem item = (DummyItem) MideaFragment1.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                MideaFragment1.this.startActivity(it);
//            }
//        });

//        getListView();
//        下拉不更新的原因？
//        SwpipeListViewOnScrollListener listener = new SwpipeListViewOnScrollListener(swipe);
        SwpipeListViewOnScrollListener1 listener = new SwpipeListViewOnScrollListener1(swipe);
        getListView().setOnScrollListener(listener);
//        MainActivity1.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//   /*
////             * 和list.setselection(0)一样
////             * */
//                if (!getListView().isStackFromBottom()) {
//                    getListView().setStackFromBottom(true);
//                }
//                getListView().setStackFromBottom(false);
//                Snackbar.make(v,"Demo",Snackbar.LENGTH_SHORT)
//                        .setAction("Action",null).show();
//            }
//        });
//        refresh.setOnScrollListener(this);


//        try
//        {
//            Field localField = AbsListView.class.getDeclaredField("mFastScroller");
//            localField.setAccessible(true);
////      paramBundle = localField.get(this);
//            Object obj = localField.get(this);
//            localField = localField.getType().getDeclaredField("mThumbDrawable");
//            localField.setAccessible(true);
//            Drawable localDrawable = (Drawable)localField.get(paramBundle);
//            localField.set(paramBundle, getResources().getDrawable(R.mipmap.ic_launcher));
//        getListView().setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
////          paramAnonymousAdapterView = (DummyItem)MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
////          paramAnonymousAdapterView = new Intent(MideaFragment1.this.getActivity(), paramAnonymousAdapterView.getActivity());
//                if(action == null) {
//                    DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                    Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                    MideaFragment1.this.startActivity(it);
//                }else {
//                    switch (action) {
//                        case "com.example.king.sorting":
//                            DummyContent1.DummyItem item = (DummyContent1.DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                            MideaFragment1.this.startActivity(it);
//                            break;
//                        case "com.example.king.about":
//                            DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it1 = new Intent(MideaFragment1.this.getActivity(), item2.getActivity());
//                            MideaFragment1.this.startActivity(it1);
//                            break;
//                        default:
//                            DummyItem item3 = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it2 = new Intent(MideaFragment1.this.getActivity(), item3.getActivity());
//                            MideaFragment1.this.startActivity(it2);
//                            break;
//
//                    }
//                }
//            }
//        });
        /*
        * error inflating class fragemnt
        *
        * */
//         LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
////        View v1 = inflater.inflate(R.layout.activity_item_app_bar,null,false);
//        View v1 = inflater.inflate(R.layout.test,null,false);
//        Toolbar bar = (Toolbar) v1.findViewById(R.id.bar);
//        bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getListView().setSelection(0);
//            }
//        });
// return;
//        }
//        catch (IllegalAccessException e)
//        {
//            for (;;)
//            {
//                e.printStackTrace();
//            }
//        }
//        catch (NoSuchFieldException e)
//        {
//            for (;;)
//            {
//                e.printStackTrace();
//            }
//        }
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        if (!(paramActivity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        this.mCallbacks = ((Callbacks) paramActivity);
    }

    public void onDetach() {
        super.onDetach();
        this.mCallbacks = this.sDummyCallbacks;
    }

    public void backToTop() {
        getListView().setSelection(0);
    }

    public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong) {
        super.onListItemClick(paramListView, paramView, paramInt, paramLong);
//        TraceItem newsItem = mDatas.get(paramInt-1);
//        Intent intent = new Intent(getActivity(), NewsContentActivity.class);
//        intent.putExtra("url", newsItem.getLink());
//        startActivity(intent);
        /*
        * 这里paramint（position）不要减去1
        * */
        NewsItem newsItem = mDatas.get(paramInt);
        Intent intent = new Intent(getActivity(), NewsContentActivity.class);
        /*
        * 只能对一个专题有效
        * */
        String urlStr = "http://www.midea.com/cn/news_center/Product_News";
        int size = newsItem.getLink().length();
                /*
                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
                * */
//                try {
//                    urlStr = URLUtil.generateUrl(newsType, currentPage);
//                    urlStr = URLDecoder.decode(urlStr);
        intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//        System.out.println(urlStr + newsItem.getLink().substring(1, size));
        LogWrap.d("url"+String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                /*这里报错commonexceptio
                总结一下commonexceptio的原因：
                1.可能是没有加internet权限
                2.肯呢个是没有对链接格式化
                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
                所以一直报错，所以要先对得到的链接先格式化
                */
        startActivity(intent);
        this.mCallbacks.onItemSelected(urlStr + newsItem.getLink().substring(1, size));
    }

    public void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
        if (this.mActivatedPosition != -1) {
            paramBundle.putInt("activated_position", this.mActivatedPosition);
        }
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        super.onViewCreated(paramView, paramBundle);
        if ((paramBundle != null) && (paramBundle.containsKey("activated_position"))) {
            setActivatedPosition(paramBundle.getInt("activated_position"));
        }
        getListView();

//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
        /*
        * wrong:nullpointer
        * */
//        refresh.getRefreshableView();
    }

    public void setActivateOnItemClick(boolean paramBoolean) {
        ListView localListView = getListView();
        if (paramBoolean) {
        }
        for (int i = 1; ; i = 0) {
            localListView.setChoiceMode(i);
            return;
        }
    }

    //    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//
//            scrollFlag = true;
//
//        } else {
//
//            scrollFlag = false;
//
//        }
//    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        switch (scrollState) {
            // 当不滚动时
            case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
            /*
            * 这里不要直接使用list，会报错nullpointer
            * */
//                if (getListView().getLastVisiblePosition() == (getListView()
//                        .getCount() - 1)) {
////                    ItemListActivity1.fab.setVisibility(View.VISIBLE);
////                    toolbar.setTitle("双击回到顶部");
//                }
//
//                // 判断滚动到顶部
//                if (getListView().getFirstVisiblePosition() == 0) {
////                    ItemListActivity1.fab.setVisibility(View.GONE);
////                    toolbar.setTitle("Fragment");
//                }

                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                scrollFlag = true;
                break;
            case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                scrollFlag = false;
                break;
        }
    }

//    static  Runnable mAddItemsRunnable = new Runnable() {
//        @Override
//        public void run() {
//            if (action == null) {
//                adapter.addMoreItems(10);
//                mIsLoading = false;
////                adapter.notifyDataSetChanged();
//            } else {
//                switch (action) {
//                    case "com.example.king.about":
//                        adapter2.addMoreItems(10);
//                        mIsLoading = false;
////                        adapter2.notifyDataSetChanged();
//                        break;
//                    case "com.example.king.sorting":
//                        adapter1.addMoreItems(10);
//                        mIsLoading = false;
////                        adapter1.notifyDataSetChanged();
//                        break;
//                    default:
//                        adapter.addMoreItems(10);
//                        mIsLoading = false;
//                        break;
////                        adapter.notifyDataSetChanged();
//
//                }
//            }
//        }
//    };
  /*
       * 双击返回顶部
       * */
public void exit1() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
        Toast.makeText(getActivity(), "再点击一次返回顶部",
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
        getListView().smoothScrollToPosition(0);
//        toolbar.setTitle("Midea");

    }
}
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
// 监听上滑动
//   if(tmp > firstVisibleItem)
//        {
//
//        }
        if (firstVisibleItem > lastVisibleItemPosition) {

//            Log.d("dc", "上滑");
//            toolbar = ItemListActivity.toolbar;
//            toolbar.setTitle("双击返回顶部");

        }
        if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
//            toolbar.setTitle("Fragement");
//            toolbar = ItemListActivity.toolbar;
        }
//        if (!mIsLoading && mMoreDataAvailable) {
//            if (totalItemCount >= MAXIMUM_ITEMS) {
//                mMoreDataAvailable = false;
//                getListView().removeFooterView(mFooterView);
////                refresh.getRefreshableView().removeFooterView(mFooterView);
//            } else if (totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
////        else
//            {
//                mIsLoading = true;
//                handler.postDelayed(mAddItemsRunnable, 1000);
//            }
//        }
    }

//    @Override
//    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//        // Do work to refresh the list here.
//        String str = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//        //设置刷新标签
//        refresh.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
//        //设置下拉标签
//        refresh.getLoadingLayoutProxy().setPullLabel("下拉刷新");
//        //设置释放标签
//        refresh.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
//        //设置上一次刷新的提示标签
//        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
//        new GetDataTask().execute();
//    }

//    @Override
//    public void onRefresh(PullToRefreshBase refreshView) {
//        String str = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//        //设置刷新标签
//        refresh.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
//        //设置下拉标签
//        refresh.getLoadingLayoutProxy().setPullLabel("下拉刷新");
//        //设置释放标签
//        refresh.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
//        //设置上一次刷新的提示标签
//        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后更新时间:" + str);
//        new GetDataTask().execute();
//    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            swipe.setRefreshing(true);
//            adapter.addMoreItems(10);
            /*
            * wrong is not an enclosing class
            * 原因是出现了两个class
            * 应该写成如下
            * */
            LogWrap.d( "onPost");
//            if (action == null) {
//                DummyItem items = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
////            LinkedList<DummyItem> items = adapter.ITEMS;
////            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
//                adapter.ITEMS.addFirst(items);
//                adapter.notifyDataSetChanged();
//            } else {
//                switch (action) {
//                    case "com.example.king.sorting":
////                    wrong
////                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//                        DummyContent1.DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
////            LinkedList<DummyItem> items = adapter.ITEMS;
////            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
//                        adapter1.ITEMS.addFirst(items1);
//                        adapter1.notifyDataSetChanged();
//                        break;
//                    case "com.example.king.main":
////                    wrong
////                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//                        DummyItem items2 = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
////            LinkedList<DummyItem> items = adapter.ITEMS;
////            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
//                        adapter.ITEMS.addFirst(items2);
//                        adapter.notifyDataSetChanged();
//                        break;
//                    case "com.example.king.about":
////                    wrong
////                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//                        DummyContent2.DummyItem items3 = adapter2.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
////            LinkedList<DummyItem> items = adapter.ITEMS;
////            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
//                        adapter2.ITEMS.addFirst(items3);
//                        adapter2.notifyDataSetChanged();
//                        break;
//                    default:
//                        DummyItem items = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
////            LinkedList<DummyItem> items = adapter.ITEMS;
////            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
//                        adapter.ITEMS.addFirst(items);
//                        adapter.notifyDataSetChanged();
//                }
//            }
//            DummyContent.ITEMS.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));

//            adapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            Toast.makeText(getActivity(), "OK..", Toast.LENGTH_SHORT).show();
            getListView().setSelection(0);
            swipe.setRefreshing(false);

//            refresh.getRefreshableView().smoothScrollToPosition(0);
//            refresh.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        Toolbar toolbar = ItemListActivity.toolbar;
////        MyToast toast1 = new MyToast(getActivity(),"reflect");
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                Toast.makeText(getActivity(), "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
////                toast1.show();
//                toolbar.setTitle("Fragement");
//                Log.d("test", "test");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Toast.makeText(getActivity(), "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
////                toast1.show();
//                Log.d("test", "test");
//                break;
//            case MotionEvent.ACTION_UP:
//                Toast.makeText(getActivity(), "getHeight=:" + list.getScrollX(), Toast.LENGTH_SHORT).show();
////                toast1.show();
//                scrollX = list.getScrollX();
//                Log.d("test","test");
//                toolbar.setTitle("双击返回顶部");
//                Toast.makeText(getActivity(), "双击返回顶部", Toast.LENGTH_SHORT).show();
//                detectScrollX();
//                break;
//        }
//        return false;
//    }
//    public void detectScrollX(){
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
//        },1000);
//    }

    public static abstract interface Callbacks {
        public abstract void onItemSelected(String paramLong);
//        public abstract  void backToTop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mWasLoading) {
            mWasLoading = false;
//            mIsLoading = true;
            handler.postDelayed(mAddNewsItem, 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mAddNewsItem);
        handler = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(mAddNewsItem);
        mWasLoading = mIsLoading;
        mIsLoading = false;
    }

    static class LoadDatasTask extends AsyncTask<Void, Void, Void> {

//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                mIsLoading = true;
////                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 1);
//                mDatas = newsItems;
//            } catch (CommonException e) {
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
//        protected void onPostExecute(Void result) {
////            List<TraceItem> newsItems = MideaFragment.mNewsItemDao.list(MideaFragment.newsType, MideaFragment.currentPage);
//            if(mDatas.size()==0) {
//                mAdapter.addAll(mDatas);
//                MideaFragment.mNewsItemDao.add(mDatas);
//            }
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
//            /*
//            * 防止在网络不好重新加载出现的问题
//            * */
//            currentPage=1;
////            mXListView.stopRefresh();
//        }
//@Override
//protected Void doInBackground(Void... params) {
//    try {
//        mIsLoading = true;
////                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                /*
//                * 由于下拉刷新才增加信息，所以loaddatatask应该获取第一页的
//                * 这样定死第一页可以防止swipe刷新的失败(获取了第二页)
//                * */
//        List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 1);
//        mDatas = newsItems;
//        //            如果数据库没有的话才加进去,而且固定拿第一页，这样才是最新的，所以不要下面的那句
//        List<TraceItem> newsItems1 = mNewsItemDao.list(newsType,currentPage);
////            mDatas.size()==0证明没有新信息，所以从数据库拿数据
//        if(mDatas.size()==0) {
//            mAdapter.addAll(newsItems1);
////                MideaFragment.mNewsItemDao.add(mDatas);
//        }
//        else{
//            mAdapter.addAll(mDatas);
//            MideaFragment1.mNewsItemDao.add(mDatas);
//        }
//    } catch (CommonException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//    }
//
//    return null;
//}
//
//        @Override
//        protected void onPostExecute(Void result) {
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
//            currentPage=1;
////            mXListView.stopRefresh();
//        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                /*
                * 由于下拉刷新才增加信息，所以loaddatatask应该获取第一页的
                * 这样定死第一页可以防止swipe刷新的失败(获取了第二页)
                * */
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 1);
                mDatas = newsItems;
                List<NewsItem> newsItems1 = mNewsItemDao.list(newsType, currentPage);
                if(newsItems1.size()==0) {
                    mAdapter.addAll(mDatas);
                    mNewsItemDao.add(mDatas);
                }
            } catch (CommonException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            如果数据库没有的话才加进去

            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
            currentPage=1;
//            mXListView.stopRefresh();
        }
    }
    static Runnable mAddNewsItem = new Runnable() {
        @Override
        public void run() {
            try {
                List<NewsItem> newsItems = MideaFragment1.mNewsItemBiz.getNewsItems(MideaFragment1.newsType, MideaFragment1.currentPage);
                MideaFragment1.mNewsItemDao.add(newsItems);
                MideaFragment1.mAdapter.addAll(newsItems);
                MideaFragment1.mAdapter.notifyDataSetChanged();

            } catch (CommonException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mIsLoading = false;
        }
    };
}

class SwpipeListViewOnScrollListener1 implements OnScrollListener {

    private SwipeRefreshLayout mSwipeView;
    private OnScrollListener mOnScrollListener;
    static boolean scrollFlag = MideaFragment1.scrollFlag;


    public SwpipeListViewOnScrollListener1(SwipeRefreshLayout swipeView) {
        mSwipeView = swipeView;
    }

    public SwpipeListViewOnScrollListener1(SwipeRefreshLayout swipeView,
                                           OnScrollListener onScrollListener) {
        mSwipeView = swipeView;
        mOnScrollListener = onScrollListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
            /*
            * 这里不要直接使用ItemListFragement.list，会报错nullpointer
            * */
//                if (MainActivity.list.getLastVisiblePosition() == (MainActivity.list
//                        .getCount() - 1)) {
////                    MainActivity.fab.setVisibility(View.VISIBLE);
////                    ItemListActivity1.toolbar.setTitle("双击回到顶部");
//                }
//
//                // 判断滚动到顶部
//                if (MainActivity.list.getFirstVisiblePosition() == 0) {
////                    MainActivity.fab.setVisibility(View.GONE);
////                    ItemListActivity1
////                            .toolbar.setTitle("Fragment");
//                }

                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                scrollFlag = true;
                break;
            case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                scrollFlag = false;
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        View firstView = absListView.getChildAt(firstVisibleItem);
        int lastVisibleItemPosition = MideaFragment1.lastVisibleItemPosition;
        // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
            mSwipeView.setEnabled(true);
//            ItemListActivity1.toolbar.setTitle("Fragement");
        } else {
            mSwipeView.setEnabled(false);
        }
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(absListView, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }
        if (firstVisibleItem > lastVisibleItemPosition) {

//            Log.d("dc", "上滑");
//            toolbar = ItemListActivity.toolbar;
//            MainActivity1.toolbar.setTitle("双击返回顶部");

        }
        if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
//            MainActivity1.toolbar.setTitle("Fragement");
//            toolbar = ItemListActivity.toolbar;
        }
        if (!MideaFragment1.mIsLoading && MideaFragment1.mMoreDataAvailable) {
            if (totalItemCount >= MideaFragment1.MAXIMUM_ITEMS) {
                MideaFragment1.mMoreDataAvailable = false;
//                MideaFragment1.list.removeFooterView(MideaFragment1.mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
            } else if (totalItemCount - MideaFragment1.AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
            {
                MideaFragment1.mIsLoading = true;
                MideaFragment1.currentPage++;
                //                 如果新闻已经加载了则从数据库加载的
                List<NewsItem> newsItems = MideaFragment1.mNewsItemDao.list(MideaFragment1.newsType, MideaFragment1.currentPage);
                  /*
               * 一开始的加载出现了null-pointer
               * 所以说明是用newsItems.get(0)来判空才正确
               * if(newsItems==null)  ----------wrong
               * */
//                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
                LogWrap.d("newsItems in News1:"+String.valueOf(newsItems.size()));
                /*
                * 捕捉到第一次加载newsItems.size()==0
                * */
                if(newsItems.size()==0) {
                    MideaFragment1.handler.postDelayed(MideaFragment1.mAddNewsItem,1000);
                }
                else
                {
//                    MideaFragment.currentPage += 1;
//                    List<TraceItem> newsItems = MideaFragment.mNewsItemDao.list(MideaFragment.newsType, MideaFragment.currentPage);
                       /*
                    * 因为前面已经list通过，证明数据库已经有，所以不需要加进来
                    * 我的数据库出现重复的原因
                    * */
                    MideaFragment1.mAdapter.addAll(newsItems);
                      /*
                    * 不要忘记了这句，否则不更新
                    * */
                    MideaFragment1.mAdapter.notifyDataSetChanged();
                    MideaFragment1.mIsLoading = false;
                }

            }
//            new MideaFragment1.LoadDatasTask().execute();
        }
    }
}




/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\MideaFragment1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */