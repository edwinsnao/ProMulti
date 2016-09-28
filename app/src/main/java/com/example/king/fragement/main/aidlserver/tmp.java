package com.example.king.fragement.main.aidlserver;


        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.os.SystemClock;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.ActivityOptionsCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.util.Pair;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.widget.Toolbar;
        import android.transition.ChangeBounds;
        import android.transition.Explode;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.ViewConfiguration;
        import android.view.ViewGroup;
        import android.widget.AbsListView;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.edwinsnao.midea.CommonException;
        import com.edwinsnao.midea.Constaint;
        import com.example.king.fragement.R;
        import com.example.king.fragement.dummy.DummyContent;
        import com.example.king.fragement.dummy.DummyContent1;
        import com.example.king.fragement.dummy.DummyContent2;
        import com.example.king.fragement.main.LogWrap;
        import com.example.king.fragement.main.MainActivity1;
        import com.example.king.fragement.main.customView.ScrollLayout;
        import com.example.king.fragement.main.utils.StickyListView;
        import com.example.king.fragement.main.utils.TransitionHelper;
        import com.example.king.fragement.midea.NetUtil;
        import com.example.king.fragement.midea.NewsContentActivity;
        import com.example.king.fragement.midea.NewsItem;
        import com.example.king.fragement.midea.NewsItemAdapter;
        import com.example.king.fragement.midea.NewsItemBiz;
        import com.example.king.fragement.midea.NewsItemDao;
        import com.melnykov.fab.FloatingActionButton;
        import com.melnykov.fab.ScrollDirectionListener;
        import com.nostra13.universalimageloader.core.assist.FailReason;
        import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

        import java.io.UnsupportedEncodingException;
        import java.lang.ref.WeakReference;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Queue;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
        import java.util.concurrent.LinkedBlockingQueue;
        import java.util.concurrent.ScheduledExecutorService;
        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.TimeoutException;

/**
 * Created by Kings on 2016/1/25.
 */
@SuppressLint("ValidFragment")
public class tmp extends Fragment implements AbsListView.OnScrollListener
        ,ImageLoadingListener
//        ,GestureDetector.OnGestureListener
{
    private Explode explode;
    private ChangeBounds bounds;
//    private String mQuery = null;

    long exitTime = 0;
//    private static final int LOAD_MORE = 0x110;
//    private static final int LOAD_REFREASH = 0x111;

//    private static final int TIP_ERROR_NO_NETWORK = 0X112;
//    private static final int TIP_ERROR_SERVER = 0X113;

    /**
     * 是否是第一次进入
     */
    private boolean isFirstIn = true;

    /**
     * 是否连接网络
     */
    boolean isConnNet = false;

    /**
     * 当前数据是否是从网络中获取的
     */
    boolean isLoadingDataFromNetWork;

    public  NewsItemDao mNewsItemDao;
    //        ,PullToRefreshBase.OnRefreshListener {
    //    Context context = getActivity();
    /*
    * 控制到达底部之前的4个item就开始handler的运行
    * */
    final int AUTOLOAD_THREADSHOLD = 1;
    /*
    * 最多200个后就不加载了
    * */
    private SwipeRefreshLayout swipe;
    private int newsType = 0;
    //    private int newsType = Constaint.NEWS_TYPE_YUNJISUAN;
    private NewsItemBiz mNewsItemBiz;
    private  NewsItemAdapter mAdapter;
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();
    /**
     * 当前页面
     */
    private int currentPage = 1;
    //    PullToRefreshListView refresh;
//    static String action;
//    int tmp = 0;
//    String newsType1 = null;
    private boolean scrollFlag = false;
    private int lastVisibleItemPosition;// 标记上次滑动位置
    private final int MAXIMUM_ITEMS = 1000;
    //    private  DummyContent adapter;
//    private  DummyContent1 adapter1;
//    private  DummyContent2 adapter2;
    //    private SimpleAdapter adapter;
    private View mFooterView;
    private List<Bitmap> mBitmaps = new ArrayList<>();


//    final Handler handler;
    /*
    * 这种写法导致内存泄漏
    *
    * */

    //    private static class MyHandler extends Handler{
//        WeakReference<MainActivity1> mActivity;
//
//        MyHandler(MainActivity1 activity){
//            mActivity = new WeakReference<MainActivity1>(activity);
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
////                        Log.d("handlemessage", "handle messae");
//                    mAdapter.notifyDataSetChanged();
////                    mIsLoading = false;
//                    break;
////                case 1:
////                    Toast.makeText(getActivity(), "网络连接信号不好", Toast.LENGTH_SHORT).show();
////                    mFooterView1 = LayoutInflater.from(getActivity()).inflate(R.layout.timeout_view, null);
////                    list.removeFooterView(mFooterView);
////                        /*
////                        * 防止没执行一次就加一个textview（footerview的内容）
////                        * */
////                    if (list.getFooterViewsCount() == 0)
////                        list.addFooterView(mFooterView1);
////                    timeout = (TextView) mFooterView1.findViewById(R.id.timeout);
////                    timeout.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            pool.execute(mAddNewsItem);
////                        }
////                    });
////                    break;
//                case 2:
////                        Log.e("mesage2", "message2");
////                    mIsLoading = false;
////                    scrollTag = false;
//                    list.removeFooterView(mFooterView);
//                    list.addFooterView(mFooterView2);
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//    MyHandler handler = new MyHandler((MainActivity1) getActivity());
//    Handler handler = new Handler();
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                        Log.d("handlemessage", "handle messae");
                    mAdapter.notifyDataSetChanged();
                    mIsLoading = false;
                    break;
//                case 1:
//                    Toast.makeText(getActivity(), "网络连接信号不好", Toast.LENGTH_SHORT).show();
//                    mFooterView1 = LayoutInflater.from(getActivity()).inflate(R.layout.timeout_view, null);
//                    list.removeFooterView(mFooterView);
//                        /*
//                        * 防止没执行一次就加一个textview（footerview的内容）
//                        * */
//                    if (list.getFooterViewsCount() == 0)
//                        list.addFooterView(mFooterView1);
//                    timeout = (TextView) mFooterView1.findViewById(R.id.timeout);
//                    timeout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            pool.execute(mAddNewsItem);
//                        }
//                    });
//                    break;
                case 2:
//                        Log.e("mesage2", "message2");
                    mIsLoading = false;
                    scrollTag = false;
                    list.removeFooterView(mFooterView);
                    list.addFooterView(mFooterView2);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //    Toolbar toolbar;
    boolean mIsLoading = false;
    boolean mMoreDataAvailable = true;
    boolean mWasLoading = false;

    //    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition = -1;
    //    private Callbacks mCallbacks = this.sDummyCallbacks;
    private   ListView list;
    //    private static ListView list;
    //    static ListView list = MainActivity.list;
    private ExecutorService pool;
    final int proc = Runtime.getRuntime().availableProcessors();
    //    final Queue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
//    final Queue<Runnable> tasks1 = new LinkedBlockingQueue<Runnable>();
    TextView timeout;
    boolean scrollTag = true;
    View mFooterView1;
    View mFooterView2;
    //    private FloatingActionButton fab;
//    FloatingActionButton fab = MainActivity1.fab;
//    FirstInNoDataLoadDatasTask taskGetData = new FirstInNoDataLoadDatasTask();
//    LoadDatasHasDataTask taskLoadData = new LoadDatasHasDataTask();
    private NewsItem newsItem;
    private Thread getDetailThread;
    private View rootView;
//    MainActivity1.MyOntouchListener listener;
//    GestureDetector mGestureDetector;
//    private final MyHandler handler = new MyHandler();


    public tmp() {
//        mNewsItemBiz = new NewsItemBiz();
    }

    public tmp(String query) {
        LogWrap.d("query"+ String.valueOf(query));
        mBitmaps = new ArrayList<>();
        if (query.equalsIgnoreCase("ProductNews")) {
            this.newsType = Constaint.NEWS_TYPE_YIDONG;
        } else if (query.equalsIgnoreCase("GroupNews")) {
            this.newsType = Constaint.NEWS_TYPE_YANFA;
        }
//        mNewsItemBiz = new NewsItemBiz();
    }


//    private static class MyHandler extends  Handler{
//        private WeakReference<NewsFragment> instance;
//
//        public MyHandler(NewsFragment instance){
//            this.instance = new WeakReference<NewsFragment>(instance);
//        }
//        public MyHandler(){
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
////                        Log.d("handlemessage", "handle messae");
//                    mAdapter.notifyDataSetChanged();
//                    mIsLoading = false;
//                    break;
////                case 1:
////                    Toast.makeText(getActivity(), "网络连接信号不好", Toast.LENGTH_SHORT).show();
////                    mFooterView1 = LayoutInflater.from(getActivity()).inflate(R.layout.timeout_view, null);
////                    list.removeFooterView(mFooterView);
////                        /*
////                        * 防止没执行一次就加一个textview（footerview的内容）
////                        * */
////                    if (list.getFooterViewsCount() == 0)
////                        list.addFooterView(mFooterView1);
////                    timeout = (TextView) mFooterView1.findViewById(R.id.timeout);
////                    timeout.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            pool.execute(mAddNewsItem);
////                        }
////                    });
////                    break;
//                case 2:
////                        Log.e("mesage2", "message2");
//                    mIsLoading = false;
//                    scrollTag = false;
//                    list.removeFooterView(mFooterView);
//                    list.addFooterView(mFooterView2);
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        new LoadDatasTask().execute();
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        if(rootView == null) {
            v = inflater.inflate(R.layout.news_fragment, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
            initView(v);
        }else{
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent!=null){
                parent.removeView(rootView);
            }
        }
//        pool = Executors.newFixedThreadPool(4);
        return v;
    }

    private void initView(View v) {
//        fab = new FloatingActionButton(getActivity());
//        fab = (FloatingActionButton) v.findViewById(R.id.fab1);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe);

        //设置卷内的颜色
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected()) {
                    Toast.makeText(getContext().getApplicationContext(), "已经是最新的了", Toast.LENGTH_SHORT).show();
//                    if(currentPage == 1&& mIsLoading == true){
//                    if (currentPage == 1 || currentPage == 2) {
//                        Log.d("currentpage in news1:", String.valueOf(currentPage));
//                        LoadDatasTask task1 = new LoadDatasTask();
//                        task1.execute();
                        /*
                        * 这个每次都新建就可以解决不能重复更新（执行同一个asynctask）问题
                        * */
//                        new MideaFragment.LoadDatasTask().execute();
//                        MideaFragment.task1.execute();
//                    }

                } else {
                    Toast.makeText(getContext().getApplicationContext(), "没有网络，请稍后刷新", Toast.LENGTH_SHORT).show();
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
//                list.setSelection(0);
            }
        });
        ScrollLayout scrollLayout = (ScrollLayout) v.findViewById(R.id.scroll_layout);
        scrollLayout.setIsScroll(false);
        list = (ListView) v.findViewById(android.R.id.list);
//        list = (StickyListView) v.findViewById(R.id.list);
//        list.setFriction(ViewConfiguration.getScrollFriction() * 2);
        list.setEmptyView(v.findViewById(R.id.empty));
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
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
        /*
        * 没数据时的提示
        * */
//        View v = inflater.inflate(R.layout.emtyview, null);
//        ImageView empty = (ImageView) v.findViewById(R.id.empty);
//        empty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new LoadDatasTask().execute();
//            }
//        });
//        empty.setVisibility(View.GONE);
//        list.removeAllViews();
//        ((ViewGroup)list.getParent()).addView(empty);
//        list.setEmptyView(empty);
        list.addFooterView(mFooterView, null, false);
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
//        setListAdapter(mAdapter);
        list.setAdapter(mAdapter);
//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
//        refresh.getRefreshableView().addFooterView(mFooterView, null, false);
//        refresh.setOnRefreshListener(this);
        final NewsItemAdapter adpter = new NewsItemAdapter(getActivity(),mDatas);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newsItem = (NewsItem) parent.getAdapter().getItem(position);
//                if(getDetailThread == null) {
//                    getDetailThread = new Thread(getDetail);
//                getDetail.run();
//                    getDetailThread.start();
//                }
//                pool.execute(getDetailThread);
//                DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                MideaFragment1.this.startActivity(it);
                 /*
        * 这里paramint（position）不要减去1
        * */
//                TraceItem newsItem = mDatas.get(paramAnonymousInt);
//                TraceItem newsItem = (TraceItem) paramAnonymousAdapterView.getAdapter().getItem(paramAnonymousInt);
//                getDetailThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                Intent intent = new Intent(getContext().getApplicationContext(), NewsContentActivity.class);
        /*
        * 只能对一个专题有效
        * */

                /*
                * 点击后不能看到文章的原因
                * */
                String urlStr = null;
                if (newsType == Constaint.NEWS_TYPE_YANFA) {
                    urlStr = "http://www.midea.com/cn/news_center/Group_News";
                } else if (newsType == Constaint.NEWS_TYPE_YIDONG) {
                    urlStr = "http://www.midea.com/cn/news_center/Product_News";
                }
                int size = newsItem.getLink().length();
                /*
                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
                * */
//                try {
//                    urlStr = URLUtil.generateUrl(newsType, currentPage);
//                    urlStr = URLDecoder.decode(urlStr);
                intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
                intent.putExtra("img_url",newsItem.getImgLink());
                intent.putExtra("title",newsItem.getTitle());
                /*
                * 用了webview就不需要下面两句了
                * */
//                        intent.putExtra("time", newsItem.getDate());
//                        intent.putExtra("title", newsItem.getTitle());
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                /*这里报错commonexceptio
                总结一下commonexceptio的原因：
                1.可能是没有加internet权限
                2.肯呢个是没有对链接格式化
                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
                所以一直报错，所以要先对得到的链接先格式化
                */
//                TODO 防止内存泄漏
//                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
              /*
              * 这部分动画放到activity的接口中，防止内存泄漏
              * */
                Activity context =  getActivity();
//                final Pair<View, String>[] pairs = NewsItemAdapter.getPair(context);
                if(explode == null) {
                    explode = new Explode();
                }
                explode.setDuration(500);
//        getWindow().setEnterTransition(explode);
                context.getWindow().setExitTransition(explode);
//                context.getWindow().setReenterTransition(explode);
                // set an exit transition
                context.getWindow().setAllowEnterTransitionOverlap(false);
                context.getWindow().setAllowReturnTransitionOverlap(false);
                if(bounds == null)
                    bounds = new ChangeBounds();
                bounds.setDuration(500);
                context.getWindow().setSharedElementEnterTransition(bounds);;
                context.getWindow().setSharedElementExitTransition(bounds);
//                View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.news_item_yidong, null);
//                ImageView img = (ImageView) list.findViewById(R.id.id_newsImg);
//                ImageView img = (ImageView) v.findViewById(R.id.id_newsImg);
                ImageView img = (ImageView) adpter.getView1(context,position,view,parent);
                TextView txt = (TextView) adpter.getView2(context,position,view,parent);
                Pair<View,String>[] pairs = TransitionHelper.createSafeTransitionParticipants(
                        context,false,new Pair<>(img,getString(R.string.explode)),
                        new Pair<>(txt,getString(R.string.share_title)));
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img,getString(R.string.explode));
                startActivity(intent,transitionActivityOptions.toBundle());
//                        startActivity(intent);
//                    }
//                });
//                getDetailThread.start();
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

//        list;
//        下拉不更新的原因？
//        SwpipeListViewOnScrollListener listener = new SwpipeListViewOnScrollListener(swipe);
//        SwpipeListViewOnScrollListener2 listener = new SwpipeListViewOnScrollListener2(swipe);
//        list.setOnScrollListener(listener);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
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
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    swipe.setEnabled(true);
//            ItemListActivity1.toolbar.setTitle("Fragement");
                } else {
                    swipe.setEnabled(false);
                }
                /*
                * 匿名内部类，所以一定不为null,不用判断
                * */
//                if (null != mOnScrollListener) {
//                onScroll(view, firstVisibleItem,
//                        visibleItemCount, totalItemCount);
//                }
//                去掉，防止mainactivity.toolbar泄漏内存，该功能在MideaFragment单独实现
               /* if (firstVisibleItem > lastVisibleItemPosition) {

//            Log.d("dc", "上滑");
//            toolbar = ItemListActivity.toolbar;
                    MainActivity1.toolbar.setTitle("双击返回顶部");

                }
                if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
                    MainActivity1.toolbar.setTitle("MideaNews");
//            toolbar = ItemListActivity.toolbar;
                }*/
                if (!mIsLoading && mMoreDataAvailable) {
                    if (totalItemCount >= MAXIMUM_ITEMS) {
                        mMoreDataAvailable = false;
                        list.removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
                    } else if (scrollTag && totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
                    {
                        mIsLoading = true;
                        currentPage++;
                        //                 如果新闻已经加载了则从数据库加载的
                        List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                  /*
               * 一开始的加载出现了null-pointer
               * 所以说明是用newsItems.get(0)来判空才正确
               * if(newsItems==null)  ----------wrong
               * */
//                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
                /*
                * 捕捉到第一次加载newsItems.size()==0
                * */
                        if (newsItems.size() < 8) {
//                            NewsFragment.handler.postDelayed(mAddNewsItem, 1000);
//                            executeNormal(mAddNewsItem);
                            handler.postDelayed(mAddNewsItem,1000);
//                            pool.execute(mAddNewsItem);
//                            pool.schedule(mAddNewsItem,1000, TimeUnit.MILLISECONDS);
                        } else {
//                    MideaFragment.currentPage += 1;
//                    List<TraceItem> newsItems = MideaFragment.mNewsItemDao.list(MideaFragment.newsType, MideaFragment.currentPage);
                       /*
                    * 因为前面已经list通过，证明数据库已经有，所以不需要加进来
                    * 我的数据库出现重复的原因
                    * */
//                            mAdapter.addAll(newsItems);
                      /*
                    * 不要忘记了这句，否则不更新
                    * */
//                            mAdapter.notifyDataSetChanged();
//                            NewsFragment.mIsLoading = false;
                            /*
                            * 因为每个asynctask只能执行一次，所以没执行一次后都要new
                            * 这样的开销好大，所以LoadData还是用handler
                            * */
//                            new LoadDatasHasDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                            handler.postDelayed(mAddNewsItemFromDatabase, 1000);
//                            executeDatabase(mAddNewsItemFromDatabase);
//                            pool.execute(mAddNewsItemFromDatabase);
//                            pool.schedule(mAddNewsItem,1000,TimeUnit.MILLISECONDS);
                        }

                    }
//            new NewsFragment.LoadDatasTask().execute();
                }
            }
        });
//        fab.attachToListView(list, new ScrollDirectionListener() {
//            @Override
//            public void onScrollDown() {
//                fab.hide();
//                fab.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onScrollUp() {
//                if (fab.getVisibility() != View.VISIBLE)
//                    fab.setVisibility(View.VISIBLE);
//                fab.show();
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.smoothScrollToPosition(0);
//                fab.hide();
//                fab.setVisibility(View.GONE);
//            }
//        });
        mFooterView2 = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.nodata_view, null);
        v.findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new LoadDatasTask().execute();
//                new FirstInNoDataLoadDatasTask().execute();
//                FirstInNoDataLoadDatasTask task = new FirstInNoDataLoadDatasTask();
                handler.postDelayed(mAddNewsItem, 1000);
                /*
                * 费内存
                * */
//                taskGetData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            }
        });



    }

//    private Callbacks sDummyCallbacks = new Callbacks() {
//        public void onItemSelected(String paramAnonymousLong) {
//        }
//
////        @Override
////        public void backToTop() {
////
////        }
//    };

//    private void setActivatedPosition(int paramInt) {
//        if (paramInt == -1) {
//            list.setItemChecked(this.mActivatedPosition, false);
//        }
//        for (; ; ) {
//            this.mActivatedPosition = paramInt;
////      return;
//            list.setItemChecked(paramInt, true);
//        }
//    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }


    //    public void onActivityCreated(Bundle paramBundle) {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        pool = Executors.newFixedThreadPool(4);
//        mGestureDetector = new GestureDetector(this);
//        listener = new MainActivity1.MyOntouchListener()
//        {
//
//            @Override
//            public void onTouchEvent(MotionEvent event)
//            {
//                mGestureDetector.onTouchEvent( event );
//            }
//        };
//        ((MainActivity1) getActivity()).registerListener( listener );
//        if (isConnected())  {
//            Intent it = new Intent();
//            it.putExtra("activity","News");
//            it.setClass(getActivity(),NoNetWork.class);
//            startActivity(it);
//        }
//        Log.d("newsType:", String.valueOf(newsType));
//        mNewsItemBiz = new NewsItemBiz();
          /*
        * 初始化newsItemdao
        * */
        /*
        * 放到activity里面初始化通过 接口来获取
        * */
//        mNewsItemDao = new NewsItemDao(getActivity());
        mNewsItemBiz = new NewsItemBiz();
        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
//        Log.d("mAdapter in Activity", String.valueOf(mAdapter));
//        final LoadDatasTask task = new LoadDatasTask();
//        task.execute();
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
        if (isFirstIn) {
            isFirstIn = false;
            if (NetUtil.checkNet(getContext().getApplicationContext())) {
                isConnNet = true;
                // 获取最新数据
                isLoadingDataFromNetWork = true;
//                有网的时候就检查数据库有没有，如果有则直接拿数据，否则更新
                try {
                    List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                    if (newsItems.size() == 0) {
//                        TODO 这里并发
                        List<NewsItem> newsItems1 = mNewsItemBiz.getNewsItems(newsType, currentPage);
                        mAdapter.addAll(newsItems1);
//                        这里少了一句notify
                        mAdapter.notifyDataSetChanged();
//                        这里出错了，少了1，导致数据库得不到第一次的数据
//                        mNewsItemDao.add(newsItems);
                        mNewsItemDao.add(newsItems1);
                    } else {
                        mAdapter.addAll(newsItems);
                        mAdapter.notifyDataSetChanged();
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
                List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                mAdapter.addAll(newsItems);
                mAdapter.notifyDataSetChanged();
            }
        }
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.news_fragment,null);
////        return super.onCreateView(inflater, container, savedInstanceState);
//        initView(v);
//        pool = Executors.newFixedThreadPool(4);

//        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
//        Log.d("mAdapter in Activity", String.valueOf(mAdapter));
//    paramBundle = DummyContent_1.getItems();
//        handler = new Handler();
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
//                    case 0:
////                        Log.d("handlemessage", "handle messae");
//                        mAdapter.notifyDataSetChanged();
//                        mIsLoading = false;
//                        break;
//                    case 1:
//                        Toast.makeText(getActivity(), "网络连接信号不好", Toast.LENGTH_SHORT).show();
//                        mFooterView1 = LayoutInflater.from(getActivity()).inflate(R.layout.timeout_view, null);
//                        list.removeFooterView(mFooterView);
//                        /*
//                        * 防止没执行一次就加一个textview（footerview的内容）
//                        * */
//                        if (list.getFooterViewsCount() == 0)
//                            list.addFooterView(mFooterView1);
//                        timeout = (TextView) mFooterView1.findViewById(R.id.timeout);
//                        timeout.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pool.execute(mAddNewsItem);
//                            }
//                        });
//                        break;
//                    case 2:
////                        Log.e("mesage2", "message2");
//                        mIsLoading = false;
//                        scrollTag = false;
//                        list.removeFooterView(mFooterView);
//                        list.addFooterView(mFooterView2);
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };
//        swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe);
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
//        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if(isConnected()){
////                    if(currentPage == 1&& mIsLoading == true){
//                    if(currentPage == 1|| currentPage == 2)
//                    {
//                        Log.d("currentpage in news1:",String.valueOf(currentPage));
//                        LoadDatasTask task1 = new LoadDatasTask();
//                        task1.execute();
//                        /*
//                        * 这个每次都新建就可以解决不能重复更新（执行同一个asynctask）问题
//                        * */
//                        new MideaFragment.LoadDatasTask().execute();
////                        MideaFragment.task1.execute();
//                    }
//
//                }
//                else{
//                    Toast.makeText(getActivity(),"没有网络，请稍后刷新",Toast.LENGTH_SHORT).show();
//                }
////                swipe.setRefreshing(false);
//
//////                new GetDataTask().execute();
//////                currentPage++;
//////                new LoadDatasTask().execute();
////
////                if (NetUtil.checkNet(getActivity())) {
////                    isConnNet = true;
////                    // 获取最新数据
////                    try {
////                        List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//////                        mAdapter.setDatas(newsItems);
////                        mAdapter.addAll(newsItems);
////
////                        isLoadingDataFromNetWork = true;
////                        // 设置刷新时间
//////                        AppUtil.setRefreashTime(getActivity(), newsType);
////                        // 清除数据库数据
//////                        null-pointer
////                        mNewsItemDao.deleteAll(newsType);
////                        // 存入数据库
////                        mNewsItemDao.add(newsItems);
////
////                    } catch (CommonException e) {
////                        e.printStackTrace();
////                        isLoadingDataFromNetWork = false;
//////                        return TIP_ERROR_SERVER;
////                    } catch (UnsupportedEncodingException e) {
////                        e.printStackTrace();
////                    }
////                } else {
////                    isConnNet = false;
////                    isLoadingDataFromNetWork = false;
////                    // TODO从数据库中加载
////                    List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
////                    mDatas = newsItems;
////                    //mAdapter.setDatas(newsItems);
//////                    return TIP_ERROR_NO_NETWORK;
////                }
////
//////                return -1;
//                /*
//                * 不set false的话会一直不返回，卡在那里
//                * */
//                swipe.setRefreshing(false);
////                list.setSelection(0);
//            }
//        });
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
//                list.setOnItemClickListener(new OnItemClickListener() {
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
       /* mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
//        toolbar = MainActivity1.toolbar;
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            //            @Override
//            public void onClick(View v) {
////                backToTop();
//                *//*
//                * 调用backtoTop（）会报content view not yet create错
//                * 所以使用这个技巧来获得listview后setselection返回地一个
//                * *//*
//                toolbar.setTitle("双击返回顶部");
//                exit1();
//
//            }
//        });
//    setListAdapter(new DummyContent_1(getActivity(), paramBundle));
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        *//*
        * 没数据时的提示
        * *//*
//        View v = inflater.inflate(R.layout.emtyview, null);
//        ImageView empty = (ImageView) v.findViewById(R.id.empty);
//        empty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new LoadDatasTask().execute();
//            }
//        });
//        empty.setVisibility(View.GONE);
//        list.removeAllViews();
//        ((ViewGroup)list.getParent()).addView(empty);
//        list.setEmptyView(empty);
        list.addFooterView(mFooterView, null, false);
//        setListAdapter(new DummyContent(getActivity(), items));
        *//*
        * listview中调用顺序是
        * 1.addHeaderView
        * 2.addFooterView
        * 3.setListAdapter
        * 否则报空指针错误
        * *//*
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
        *//*
        * refresh中和上面listview有一点区别
        * 先setAdapter的到了refresh
        * 然后再addfooterView，否则会报错nullpointer
        * 因为那时refresh还没有初始化
        * *//*
//        LinkedList<DummyItem> items2 = DummyContent.getItems();
//        adapter = new DummyContent(getActivity(), items2);
//        setListAdapter(adapter);
//        setListAdapter(mAdapter);
        list.setAdapter(mAdapter);
//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
//        refresh.getRefreshableView().addFooterView(mFooterView, null, false);
//        refresh.setOnRefreshListener(this);
        final NewsItemAdapter adpter = new NewsItemAdapter(getActivity(),mDatas);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newsItem = (NewsItem) parent.getAdapter().getItem(position);
//                if(getDetailThread == null) {
//                    getDetailThread = new Thread(getDetail);
//                getDetail.run();
//                    getDetailThread.start();
//                }
//                pool.execute(getDetailThread);
//                DummyItem item = (DummyItem) MideaFragment1.this.getListAdapter().getItem(paramAnonymousInt);
//                Intent it = new Intent(MideaFragment1.this.getActivity(), item.getActivity());
//                MideaFragment1.this.startActivity(it);
                 *//*
        * 这里paramint（position）不要减去1
        * *//*
//                TraceItem newsItem = mDatas.get(paramAnonymousInt);
//                TraceItem newsItem = (TraceItem) paramAnonymousAdapterView.getAdapter().getItem(paramAnonymousInt);
//                getDetailThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        Intent intent = new Intent(getContext().getApplicationContext(), NewsContentActivity.class);
        *//*
        * 只能对一个专题有效
        * *//*

                *//*
                * 点击后不能看到文章的原因
                * *//*
                        String urlStr = null;
                        if (newsType == Constaint.NEWS_TYPE_YANFA) {
                            urlStr = "http://www.midea.com/cn/news_center/Group_News";
                        } else if (newsType == Constaint.NEWS_TYPE_YIDONG) {
                            urlStr = "http://www.midea.com/cn/news_center/Product_News";
                        }
                        int size = newsItem.getLink().length();
                *//*
                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
                * *//*
//                try {
//                    urlStr = URLUtil.generateUrl(newsType, currentPage);
//                    urlStr = URLDecoder.decode(urlStr);
                        intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
                *//*
                * 用了webview就不需要下面两句了
                * *//*
//                        intent.putExtra("time", newsItem.getDate());
//                        intent.putExtra("title", newsItem.getTitle());
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                *//*这里报错commonexceptio
                总结一下commonexceptio的原因：
                1.可能是没有加internet权限
                2.肯呢个是没有对链接格式化
                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
                所以一直报错，所以要先对得到的链接先格式化
                *//*
//                TODO
//                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
                Activity context =  getActivity();
//                final Pair<View, String>[] pairs = NewsItemAdapter.getPair(context);
                if(explode == null) {
                    explode = new Explode();
                }
                explode.setDuration(500);
//        getWindow().setEnterTransition(explode);
//                context.getWindow().setExitTransition(explode);
//                context.getWindow().setReenterTransition(explode);
                // set an exit transition
                context.getWindow().setAllowEnterTransitionOverlap(false);
                context.getWindow().setAllowReturnTransitionOverlap(false);
                if(bounds == null)
                bounds = new ChangeBounds();
//                context.getWindow().setSharedElementEnterTransition(bounds);;
                context.getWindow().setSharedElementExitTransition(explode);
//                View v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.news_item_yidong, null);
//                ImageView img = (ImageView) list.findViewById(R.id.id_newsImg);
//                ImageView img = (ImageView) v.findViewById(R.id.id_newsImg);
                ImageView img = (ImageView) adpter.getView1(context,position,view,parent);
//                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img,getString(R.string.explode));
                startActivity(intent,transitionActivityOptions.toBundle());
//                        startActivity(intent);
//                    }
//                });
//                getDetailThread.start();
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

//        list;
//        下拉不更新的原因？
//        SwpipeListViewOnScrollListener listener = new SwpipeListViewOnScrollListener(swipe);
//        SwpipeListViewOnScrollListener2 listener = new SwpipeListViewOnScrollListener2(swipe);
//        list.setOnScrollListener(listener);
        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;
                        // 判断滚动到底部
            *//*
            * 这里不要直接使用ItemListFragement.list，会报错nullpointer
            * *//*
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
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);
                // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    swipe.setEnabled(true);
//            ItemListActivity1.toolbar.setTitle("Fragement");
                } else {
                    swipe.setEnabled(false);
                }
                *//*
                * 匿名内部类，所以一定不为null,不用判断
                * *//*
//                if (null != mOnScrollListener) {
//                onScroll(view, firstVisibleItem,
//                        visibleItemCount, totalItemCount);
//                }
//                去掉，防止mainactivity.toolbar泄漏内存，该功能在MideaFragment单独实现
               *//* if (firstVisibleItem > lastVisibleItemPosition) {

//            Log.d("dc", "上滑");
//            toolbar = ItemListActivity.toolbar;
                    MainActivity1.toolbar.setTitle("双击返回顶部");

                }
                if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
                    MainActivity1.toolbar.setTitle("MideaNews");
//            toolbar = ItemListActivity.toolbar;
                }*//*
                if (!mIsLoading && mMoreDataAvailable) {
                    if (totalItemCount >= MAXIMUM_ITEMS) {
                        mMoreDataAvailable = false;
                        list.removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
                    } else if (scrollTag && totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
                    {
                        mIsLoading = true;
                        currentPage++;
                        //                 如果新闻已经加载了则从数据库加载的
                        List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                  *//*
               * 一开始的加载出现了null-pointer
               * 所以说明是用newsItems.get(0)来判空才正确
               * if(newsItems==null)  ----------wrong
               * *//*
//                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
                        LogWrap.d("newsItems in News1:"+ String.valueOf(newsItems.size()));
                *//*
                * 捕捉到第一次加载newsItems.size()==0
                * *//*
                        if (newsItems.size() < 8) {
//                            NewsFragment.handler.postDelayed(mAddNewsItem, 1000);
//                            executeNormal(mAddNewsItem);
                            pool.execute(mAddNewsItem);
//                            pool.schedule(mAddNewsItem,1000, TimeUnit.MILLISECONDS);
                        } else {
//                    MideaFragment.currentPage += 1;
//                    List<TraceItem> newsItems = MideaFragment.mNewsItemDao.list(MideaFragment.newsType, MideaFragment.currentPage);
                       *//*
                    * 因为前面已经list通过，证明数据库已经有，所以不需要加进来
                    * 我的数据库出现重复的原因
                    * *//*
//                            mAdapter.addAll(newsItems);
                      *//*
                    * 不要忘记了这句，否则不更新
                    * *//*
//                            mAdapter.notifyDataSetChanged();
//                            NewsFragment.mIsLoading = false;
                            *//*
                            * 因为每个asynctask只能执行一次，所以没执行一次后都要new
                            * 这样的开销好大，所以LoadData还是用handler
                            * *//*
//                            new LoadDatasHasDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                            handler.postDelayed(mAddNewsItemFromDatabase, 1000);
//                            executeDatabase(mAddNewsItemFromDatabase);
//                            pool.execute(mAddNewsItemFromDatabase);
//                            pool.schedule(mAddNewsItem,1000,TimeUnit.MILLISECONDS);
                        }

                    }
//            new NewsFragment.LoadDatasTask().execute();
                }
            }
        });*/

//        MainActivity1.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//   /*
////             * 和list.setselection(0)一样
////             * */
//                if (!list.isStackFromBottom()) {
//                    list.setStackFromBottom(true);
//                }
//                list.setStackFromBottom(false);
//                Snackbar.make(v, "Demo", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
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
//        list.setOnItemClickListener(new OnItemClickListener() {
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
//                list.setSelection(0);
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

//    public void onAttach(Activity paramActivity) {
//        super.onAttach(paramActivity);
//        if (!(paramActivity instanceof Callbacks)) {
//            throw new IllegalStateException("Activity must implement fragment's callbacks.");
//        }
//        this.mCallbacks = ((Callbacks) paramActivity);
//    }

    public void onDetach() {
        super.onDetach();
//        this.mCallbacks = this.sDummyCallbacks;
    }


    public void backToTop() {
        list.setSelection(0);
    }

//    public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong) {
//        super.onListItemClick(paramListView, paramView, paramInt, paramLong);
////        TraceItem newsItem = mDatas.get(paramInt-1);
////        Intent intent = new Intent(getActivity(), NewsContentActivity.class);
////        intent.putExtra("url", newsItem.getLink());
////        startActivity(intent);
//        /*
//        * 这里paramint（position）不要减去1
//        * */
//        TraceItem newsItem = mDatas.get(paramInt);
//        Intent intent = new Intent(getActivity(), NewsContentActivity.class);
//        /*
//        * 只能对一个专题有效
//        * */
//        String urlStr = null;
//        if (newsType == Constaint.NEWS_TYPE_YIDONG) {
//            urlStr = "http://www.midea.com/cn/news_center/Product_News";
//        } else if (newsType == Constaint.NEWS_TYPE_YANFA) {
//            urlStr = "http://www.midea.com/cn/news_center/Group_News";
//        }
//
//        int size = newsItem.getLink().length();
//                /*
//                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
//                * */
////                try {
////                    urlStr = URLUtil.generateUrl(newsType, currentPage);
////                    urlStr = URLDecoder.decode(urlStr);
//        intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//        System.out.println(urlStr + newsItem.getLink().substring(1, size));
//        Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
//                /*这里报错commonexceptio
//                总结一下commonexceptio的原因：
//                1.可能是没有加internet权限
//                2.肯呢个是没有对链接格式化
//                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
//                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
//                所以一直报错，所以要先对得到的链接先格式化
//                */
//        startActivity(intent);
//        this.mCallbacks.onItemSelected(urlStr + newsItem.getLink().substring(1, size));
//    }

    public void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
//        if (this.mActivatedPosition != -1) {
//            paramBundle.putInt("activated_position", this.mActivatedPosition);
//        }
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        super.onViewCreated(paramView, paramBundle);
//        if ((paramBundle != null) && (paramBundle.containsKey("activated_position"))) {
//            setActivatedPosition(paramBundle.getInt("activated_position"));
//        }
//        getListView();

//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
        /*
        * wrong:nullpointer
        * */
//        refresh.getRefreshableView();
    }

    public void setActivateOnItemClick(boolean paramBoolean) {
        ListView localListView = list;
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
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
            /*
            * 这里不要直接使用list，会报错nullpointer
            * */
//                if (list.getLastVisiblePosition() == (list
//                        .getCount() - 1)) {
////                    ItemListActivity1.fab.setVisibility(View.VISIBLE);
////                    toolbar.setTitle("双击回到顶部");
//                }
//
//                // 判断滚动到顶部
//                if (list.getFirstVisiblePosition() == 0) {
////                    ItemListActivity1.fab.setVisibility(View.GONE);
////                    toolbar.setTitle("Fragment");
//                }

                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                scrollFlag = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
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
            Toast.makeText(getContext().getApplicationContext(), "再点击一次返回顶部",
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
//            toolbar.setTitle("Midea");

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
//                list.removeFooterView(mFooterView);
////                refresh.getRefreshableView().removeFooterView(mFooterView);
//            } else if (totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
////        else
//            {
//                mIsLoading = true;
//                handler.postDelayed(mAddItemsRunnable, 1000);
//            }
//        }
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

//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        // 参数解释：
//
//        // e1：第1个ACTION_DOWN MotionEvent
//
//        // e2：最后一个ACTION_MOVE MotionEvent
//
//        // velocityX：X轴上的移动速度，像素/秒
//
//        // velocityY：Y轴上的移动速度，像素/秒
//
//        // 触发条件 ：
//
//        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
//
//        final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;
//
//        if (motionEvent.getX() - motionEvent1.getX() > FLING_MIN_DISTANCE
//                && Math.abs(v) > FLING_MIN_VELOCITY)
//
//        {
//
//            // Fling left
//
//            Log.i("MyGesture", "Fling left");
//
////            Toast.makeText(this, "Fling Left", Toast.LENGTH_SHORT).show();
//
//        }
//
//        else if (motionEvent.getX() - motionEvent1.getX() > FLING_MIN_DISTANCE
//                && Math.abs(v) > FLING_MIN_VELOCITY)
//
//        {
//
//            // Fling right
//
//            Log.i("MyGesture", "Fling right");
//
////            Toast.makeText(this, "Fling Right", Toast.LENGTH_SHORT).show();
//
//        }
//        Log.e("newsFragment","flingtest");
//
//        return onFling(motionEvent, motionEvent1, v , v1/4);
////        return false;
//    }



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
            Toast.makeText(getContext().getApplicationContext(), "OK..", Toast.LENGTH_SHORT).show();
            list.setSelection(0);
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
//        if (mWasLoading) {
//            mWasLoading = false;
////            mIsLoading = true;
////            handler.postDelayed(mAddNewsItem, 1000);
//        }
    }

    public interface getNewsItemDao{

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStop() {
        super.onStop();
//        handler.removeCallbacks(mAddNewsItem);
////        todo loaddata task end
//        mWasLoading = mIsLoading;
//        handler.removeCallbacks(mAddNewsItemFromDatabase);
////        pool.shutdown();
//        mIsLoading = false;
        handler.removeCallbacks(mAddNewsItem);
        handler.removeCallbacksAndMessages(null);
//        handler.removeCallbacks(mAddNewsItem);
//        todo loaddata task end
        mWasLoading = mIsLoading;
//        handler.removeCallbacks(mAddNewsItemFromDatabase);
//        wrong
        handler.removeCallbacks(mAddNewsItemFromDatabase);
//        handler.removeCallbacksAndMessages(mAddNewsItemFromDatabase);
        pool.shutdown();
//        handler = null;
        mIsLoading = false;
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        handler.removeCallbacks(mAddNewsItem);
////        todo loaddata task end
//        mWasLoading = mIsLoading;
//        handler.removeCallbacks(mAddNewsItemFromDatabase);
////        pool.shutdown();
//        mIsLoading = false;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        wrong?
//        handler.removeCallbacksAndMessages(mAddNewsItem);
//        true
//        handler.removeCallbacks(mAddNewsItem);
//        handler.removeCallbacksAndMessages(null);
////        handler.removeCallbacks(mAddNewsItem);
////        todo loaddata task end
//        mWasLoading = mIsLoading;
////        handler.removeCallbacks(mAddNewsItemFromDatabase);
////        wrong
//        handler.removeCallbacks(mAddNewsItemFromDatabase);
////        handler.removeCallbacksAndMessages(mAddNewsItemFromDatabase);
//        pool.shutdown();
////        handler = null;
//        mIsLoading = false;
        cleanBitmapList();
        mAdapter.imageLoader.clearMemoryCache();
//        mAdapter.imageLoader.clearDiscCache();
    }

    public void cleanBitmapList(){
        if(mBitmaps.size()>0){
            for(int i = 0 ;i<mBitmaps.size();i++){
                Bitmap b = mBitmaps.get(i);
                if(b!=null&& !b.isRecycled()){
                    b.recycle();
                }
            }
        }
    }

    //就是list.setemptyView中的empty点击的事件，无网到有网的swiperefresh
    class FirstInNoDataLoadDatasTask extends AsyncTask<Void, Void, Void> {
        //
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);

                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                mDatas = newsItems;
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
//            List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//            if (mDatas.size() == 0)
//            if (newsItems.size() == 0)
//            {
            mAdapter.addAll(mDatas);
            mNewsItemDao.add(mDatas);
//            }
//            else
//                mAdapter.addAll(newsItems);
            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
            /*
            * 防止在网络不好重新加载出现的问题
            * */
//            currentPage = 1;
//            mXListView.stopRefresh();
        }
    }

    //    database has data but no network
    class LoadDatasHasDataTask extends AsyncTask<Void, Void, Void> {
        //
        @Override
        protected Void doInBackground(Void... params) {

            mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
            mDatas = newsItems;

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);

//            if (newsItems.size() == 0)
//            {
//                mAdapter.addAll(mDatas);
//                mNewsItemDao.add(mDatas);
//            }
//            else
            /*
            * 不用判空，因为如果没网，数据库没数据，则会显示emptyview
            * 这时候就调用LoadADataFirstInTask
            * */
//            if (mDatas.size() == 0)
            mAdapter.addAll(mDatas);
            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
            /*
            * 防止在网络不好重新加载出现的问题
            * */
//            currentPage = 1;
//            mXListView.stopRefresh();
        }
    }

    //    有网络的状态（1.正常状况：已经有数据的看更新，那么其实可以什么不做）
//    （2.无网到有网，和emptyview一样）---上面已经有了
// ，因为没网络的话，直接toast然后什么都不做
//    todo 以后版本再更新，因为涉及到上次更新的时间还有新闻的时间
//    class LoadDatasRefreshTask extends AsyncTask<Void, Void, Void> {
//        //
//        @Override
//        protected Void doInBackground(Void... params) {
//
//                mIsLoading = true;
//            List<TraceItem> newsItems = null;
//            try {
//                newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                mDatas = newsItems;
//            } catch (CommonException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
////                List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
////                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//
//            if (newsItems.size() == 0)
////            {
////                mAdapter.addAll(mDatas);
////                mNewsItemDao.add(mDatas);
////            }
////            else
//            /*
//            * 不用判空，因为如果没网，数据库没数据，则会显示emptyview
//            * 这时候就调用LoadADataFirstInTask
//            * */
////            if (mDatas.size() == 0)
//                mAdapter.addAll(mDatas);
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
//            /*
//            * 防止在网络不好重新加载出现的问题
//            * */
////            currentPage = 1;
////            mXListView.stopRefresh();
//        }
//    }
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                /*
                * 由于下拉刷新才增加信息，所以loaddatatask应该获取第一页的
                * 这样定死第一页可以防止swipe刷新的失败(获取了第二页)
                * */
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                mDatas = newsItems;
//                //            如果数据库没有的话才加进去,而且固定拿第一页，这样才是最新的，所以不要下面的那句
//                List<TraceItem> newsItems1 = mNewsItemDao.list(newsType, currentPage);
////            mDatas.size()==0证明没有新信息，所以从数据库拿数据
//                if (mDatas.size() == 0) {
//                    mAdapter.addAll(newsItems1);
////                MideaFragment.mNewsItemDao.add(mDatas);
//                } else {
//                    mAdapter.addAll(mDatas);
//                    mNewsItemDao.add(mDatas);
//                }
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
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
//            currentPage = 1;
////            mXListView.stopRefresh();
//        }
//    }
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                mIsLoading = true;
////                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                /*
//                * 由于下拉刷新才增加信息，所以loaddatatask应该获取第一页的
//                * 这样定死第一页可以防止swipe刷新的失败(获取了第二页)
//                * */
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 1);
//                mDatas = newsItems;
//                List<TraceItem> newsItems1 = mNewsItemDao.list(newsType, currentPage);
//                if (newsItems1.size() == 0) {
//                    mAdapter.addAll(mDatas);
//                    mNewsItemDao.add(mDatas);
//                }
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
////            如果数据库没有的话才加进去
//
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
//            currentPage = 1;
////            mXListView.stopRefresh();
//        }
//    }

    /*
    * detailCntent
    * */
    Runnable getDetail = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getContext().getApplicationContext(), NewsContentActivity.class);
        /*
        * 只能对一个专题有效
        * */

                /*
                * 点击后不能看到文章的原因
                * */
            String urlStr = null;
            if (newsType == Constaint.NEWS_TYPE_YANFA) {
                urlStr = "http://www.midea.com/cn/news_center/Group_News";
            } else if (newsType == Constaint.NEWS_TYPE_YIDONG) {
                urlStr = "http://www.midea.com/cn/news_center/Product_News";
            }
            int size = newsItem.getLink().length();
                /*
                * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
                * */
//                try {
//                    urlStr = URLUtil.generateUrl(newsType, currentPage);
//                    urlStr = URLDecoder.decode(urlStr);
            intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//            intent.putExtra("time", newsItem.getDate());
//            intent.putExtra("title", newsItem.getTitle());
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                /*这里报错commonexceptio
                总结一下commonexceptio的原因：
                1.可能是没有加internet权限
                2.肯呢个是没有对链接格式化
                3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
                因为getLink得到的链接是 ./201505/t20150523_180260.shtml
                所以一直报错，所以要先对得到的链接先格式化
                */
            startActivity(intent);
        }
    };
    /*
    * 从网络取数据
    * */
    private  final Runnable mAddNewsItem = new Runnable() {
        @Override
        public void run() {
            try {
//                Log.e("test","testnormal");
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                if (mNewsItemBiz.getResponseCode() != 200) {
////                    Toast.makeText(getActivity(),"网络连接超时",Toast.LENGTH_SHORT).show();
//                    handler.sendEmptyMessage(1);
//                }
//                else{
                mNewsItemDao.add(newsItems);
                mAdapter.addAll(newsItems);
//                synchronized (handler) {
                mAdapter.notifyDataSetChanged();
//                mIsLoading = false;
//                handler.sendEmptyMessage(0);
//                }
//                }
                if (newsItems.size() >= 8)
                    scrollTag = true;
                else {
//                    /*
//                    * 修复数量少于一页（已经没数据）还显示loading
//                    * */
                    LogWrap.e("message2"+ String.valueOf(newsItems.size()));
//                    synchronized (handler) {
                    scrollTag = false;
//                    list.removeFooterView(mFooterView);
//                    list.addFooterView(mFooterView2);
//                    handler.sendEmptyMessage(2);
                    mIsLoading = false;
                    scrollTag = false;
                    list.removeFooterView(mFooterView);
                    list.addFooterView(mFooterView2);
//                    }
//                    scrollTag = false;
////                    list.removeFooterView(mFooterView);
////                    list.addFooterView(mFooterView1);
                }
                mIsLoading = false;
//                mAdapter.notifyDataSetChanged();

            }
            catch (CommonException e) {
                e.printStackTrace();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            mIsLoading = false;
        }
    };
    /*
    * 从数据库取数据
    * */
    private final  Runnable mAddNewsItemFromDatabase = new Runnable() {
        @Override
        public void run() {
//            Log.e("test","testdatabase");
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
            mAdapter.addAll(newsItems);
//            synchronized (handler) {
            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
//            handler.sendEmptyMessage(0);
//            }
            if (newsItems.size() >= 8)
                scrollTag = true;
            else {
//                    /*
//                    * 修复数量少于一页（已经没数据）还显示loading
//                    * */
                LogWrap.e("message2"+ String.valueOf(newsItems.size()));
//                synchronized (handler) {
                mIsLoading = false;
                scrollTag = false;
//                list.removeFooterView(mFooterView);
//                list.addFooterView(mFooterView2);
//                handler.sendEmptyMessage(2);
                mIsLoading = false;
                scrollTag = false;
                list.removeFooterView(mFooterView);
                list.addFooterView(mFooterView2);
//                }
//                    scrollTag = false;
////                    list.removeFooterView(mFooterView);
////                    list.addFooterView(mFooterView1);
            }
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
        }
    };

//    public synchronized void executeDatabase(final Runnable r) {
//        tasks.offer(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    pool.execute(mAddNewsItemFromDatabase);
//                    r.run();
//                } finally {
//                    scheduleNextDatabase();
//                }
//            }
//        });
//        if (mAddNewsItemFromDatabase == null) {
//            scheduleNextDatabase();
//        }
//    }
//
//    public synchronized void scheduleNextDatabase() {
//        if ((mAddNewsItemFromDatabase = tasks.poll()) != null) {
//            pool.execute(mAddNewsItemFromDatabase);
//        }
//    }

//    public synchronized void executeNormal(final Runnable r) {
//        tasks1.offer(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    r.run();
////                    pool.execute(mAddNewsItem);
//                } finally {
//                    scheduleNext();
//                }
//            }
//        });
//        if (mAddNewsItem == null) {
//            scheduleNext();
//        }
//    }
//
//    public synchronized void scheduleNext() {
//        if ((mAddNewsItem = tasks1.poll()) != null) {
//            pool.execute(mAddNewsItem);
//        }
//    }
}
//class SwpipeListViewOnScrollListener2 implements AbsListView.OnScrollListener {
//
//    private SwipeRefreshLayout mSwipeView;
//    private AbsListView.OnScrollListener mOnScrollListener;
//    static boolean scrollFlag = NewsFragment.scrollFlag;
//
//
//    public SwpipeListViewOnScrollListener2(SwipeRefreshLayout swipeView) {
//        mSwipeView = swipeView;
//    }
//
//    public SwpipeListViewOnScrollListener2(SwipeRefreshLayout swipeView,
//                                           AbsListView.OnScrollListener onScrollListener) {
//        mSwipeView = swipeView;
//        mOnScrollListener = onScrollListener;
//    }
//
//    @Override
//    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//        switch (scrollState) {
//            // 当不滚动时
//            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
//                scrollFlag = false;
//                // 判断滚动到底部
//            /*
//            * 这里不要直接使用ItemListFragement.list，会报错nullpointer
//            * */
////                if (MainActivity.list.getLastVisiblePosition() == (MainActivity.list
////                        .getCount() - 1)) {
//////                    MainActivity.fab.setVisibility(View.VISIBLE);
//////                    ItemListActivity1.toolbar.setTitle("双击回到顶部");
////                }
////
////                // 判断滚动到顶部
////                if (MainActivity.list.getFirstVisiblePosition() == 0) {
//////                    MainActivity.fab.setVisibility(View.GONE);
//////                    ItemListActivity1
//////                            .toolbar.setTitle("Fragment");
////                }
//
//                break;
//            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
//                scrollFlag = true;
//                break;
//            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
//                scrollFlag = false;
//                break;
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView absListView, int firstVisibleItem,
//                         int visibleItemCount, int totalItemCount) {
//        View firstView = absListView.getChildAt(firstVisibleItem);
//        int lastVisibleItemPosition = NewsFragment.lastVisibleItemPosition;
//        // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
//        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
//            mSwipeView.setEnabled(true);
////            ItemListActivity1.toolbar.setTitle("Fragement");
//        } else {
//            mSwipeView.setEnabled(false);
//        }
//        if (null != mOnScrollListener) {
//            mOnScrollListener.onScroll(absListView, firstVisibleItem,
//                    visibleItemCount, totalItemCount);
//        }
//        if (firstVisibleItem > lastVisibleItemPosition) {
//
////            Log.d("dc", "上滑");
////            toolbar = ItemListActivity.toolbar;
//            MainActivity1.toolbar.setTitle("双击返回顶部");
//
//        }
//        if (firstVisibleItem < lastVisibleItemPosition) {
//
////            Log.d("dc", "下滑");
//            MainActivity1.toolbar.setTitle("Fragement");
////            toolbar = ItemListActivity.toolbar;
//        }
//        if (!NewsFragment.mIsLoading && NewsFragment.mMoreDataAvailable) {
//            if (totalItemCount >= NewsFragment.MAXIMUM_ITEMS) {
//                NewsFragment.mMoreDataAvailable = false;
////                NewsFragment.list.removeFooterView(NewsFragment.mFooterView);
////                refresh.getRefreshableView().removeFooterView(mFooterView);
//            } else if (totalItemCount - NewsFragment.AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
////        else
//            {
//                NewsFragment.mIsLoading = true;
//                NewsFragment.currentPage++;
//                //                 如果新闻已经加载了则从数据库加载的
//                List<TraceItem> newsItems = NewsFragment.mNewsItemDao.list(NewsFragment.newsType, NewsFragment.currentPage);
//                  /*
//               * 一开始的加载出现了null-pointer
//               * 所以说明是用newsItems.get(0)来判空才正确
//               * if(newsItems==null)  ----------wrong
//               * */
////                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
//                Log.d("newsItems in News1:",String.valueOf(newsItems.size()));
//                /*
//                * 捕捉到第一次加载newsItems.size()==0
//                * */
//                if(newsItems.size()==0) {
//                    NewsFragment.handler.postDelayed(NewsFragment.mAddNewsItem,1000);
//                }
//                else
//                {
////                    MideaFragment.currentPage += 1;
////                    List<TraceItem> newsItems = MideaFragment.mNewsItemDao.list(MideaFragment.newsType, MideaFragment.currentPage);
//                       /*
//                    * 因为前面已经list通过，证明数据库已经有，所以不需要加进来
//                    * 我的数据库出现重复的原因
//                    * */
//                    NewsFragment.mAdapter.addAll(newsItems);
//                      /*
//                    * 不要忘记了这句，否则不更新
//                    * */
//                    NewsFragment.mAdapter.notifyDataSetChanged();
//                    NewsFragment.mIsLoading = false;
//                }
//
//            }
////            new NewsFragment.LoadDatasTask().execute();
//        }
//    }


