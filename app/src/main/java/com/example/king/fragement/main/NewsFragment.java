package com.example.king.fragement.main;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class NewsFragment extends Fragment implements ImageLoadingListener,TransferListener
    ,NewsItemAdapter.OnImgLongClickListener
//        ,GestureDetector.OnGestureListener
{
    private Toolbar bar;
    private ImageView preview;
    private Dialog dialog;
    private Explode explode;
    private boolean canLoadMoreData = true;
    private ChangeBounds bounds;
//    private String mQuery = null;

    long exitTime = 0;

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
    private NewsItemBiz mNewsItemBiz;
    private  NewsItemAdapter mAdapter;
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();
    /**
     * 当前页面
     */
    private int currentPage = 1;
    private boolean scrollFlag = false;
    private int lastVisibleItemPosition;// 标记上次滑动位置
    private final int MAXIMUM_ITEMS = 1000;
    private View mFooterView;
    private List<Bitmap> mBitmaps = new ArrayList<>();


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

    private   ListView list;
    boolean scrollTag = true;
    View mFooterView2;
    //    private FloatingActionButton fab;
//    FloatingActionButton fab = MainActivity1.fab;
//    FirstInNoDataLoadDatasTask taskGetData = new FirstInNoDataLoadDatasTask();
//    LoadDatasHasDataTask taskLoadData = new LoadDatasHasDataTask();
    private NewsItem newsItem;
    private View rootView;


    public NewsFragment() {
    }

    public NewsFragment(String query) {
        LogWrap.d("query"+ String.valueOf(query));
        mBitmaps = new ArrayList<>();
        if (query.equalsIgnoreCase("ProductNews")) {
            this.newsType = Constaint.NEWS_TYPE_YIDONG;
        } else if (query.equalsIgnoreCase("GroupNews")) {
            this.newsType = Constaint.NEWS_TYPE_YANFA;
        }
//        mNewsItemBiz = new NewsItemBiz();
    }



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
                /*
                * 不set false的话会一直不返回，卡在那里
                * */
                new FetchLatestNewsTask().execute();
                swipe.setRefreshing(false);
//                list.setSelection(0);
            }
        });
        ScrollLayout scrollLayout = (ScrollLayout) v.findViewById(R.id.scroll_layout);
        scrollLayout.setIsScroll(false);
        list = (ListView) v.findViewById(android.R.id.list);
//        list.setFriction(ViewConfiguration.getScrollFriction() * 2);
        list.setEmptyView(v.findViewById(R.id.empty));
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
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
        list.setAdapter(mAdapter);
//        final NewsItemAdapter adpter = new NewsItemAdapter(getActivity(),mDatas);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newsItem = (NewsItem) parent.getAdapter().getItem(position);
                 /*
        * 这里paramint（position）不要减去1
        * */
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
                ImageView img = (ImageView) mAdapter.getView1(context,position,view,parent);
                TextView txt = (TextView) mAdapter.getView2(context,position,view,parent);
//                ImageView img = (ImageView) adpter.getView1(context,position,view,parent);
//                TextView txt = (TextView) adpter.getView2(context,position,view,parent);
                Pair<View,String>[] pairs = TransitionHelper.createSafeTransitionParticipants(
                        context,false,new Pair<>(img,getString(R.string.explode)),
                        new Pair<>(txt,getString(R.string.share_title)));
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                startActivity(intent,transitionActivityOptions.toBundle());
//                    }
//                });
            }
        });

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
                        Log.e("currentPage",String.valueOf(currentPage));
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
                        if (newsItems == null || newsItems.size() < 8 ) {
                            if(canLoadMoreData == true)
//                                new FirstInNoDataLoadDatasTask().execute();
                                new LoadDatasTask().execute();
                            else{
                                handler.sendEmptyMessage(2);
                                /**什么都不做*/
                                return ;
                            }
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
        mFooterView2 = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.nodata_view, null);
        v.findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new LoadDatasTask().execute();
                /**
                * 不能用mAddItem这个runnable，一直说网络失败
                 * 因为网络操作需要在子线程中，handler不算！？
                * */
//                new FetchLatestNewsTask();
                new FirstInNoDataLoadDatasTask().execute();
//                FirstInNoDataLoadDatasTask task = new FirstInNoDataLoadDatasTask();
//                handler.postDelayed(mAddNewsItem, 1000);
                /*
                * 费内存
                * */
//                taskGetData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            }
        });



    }


    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }


    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
          /*
        * 初始化newsItemdao
        * */
        /*
        * 放到activity里面初始化通过 接口来获取
        * */
        dialog = new Dialog(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_preview_img,null);
        preview = (ImageView) v.findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        WindowManager m = getActivity().getWindowManager();
        Window dialogWindow = dialog.getWindow();
        Display display = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = (int) (display.getHeight() * 0.8);
        lp.width = (int) (display.getWidth() * 0.95);
        dialogWindow.setAttributes(lp);
        mNewsItemDao = new NewsItemDao(getActivity());
        mNewsItemBiz = new NewsItemBiz();
        mAdapter = new NewsItemAdapter(getActivity(), mDatas);
        mAdapter.setOnImgLongClickListener(this);
        if (isFirstIn) {
            isFirstIn = false;
            if (NetUtil.checkNet(getContext().getApplicationContext())) {
                isConnNet = true;
                // 获取最新数据
                isLoadingDataFromNetWork = true;
//                有网的时候就检查数据库有没有，如果有则直接拿数据，否则更新
//                try {
                    List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//                    if (newsItems.size() == 0 || newsItems == null) {
                    if (newsItems == null || newsItems.size() == 0) {
////                        TODO 这里并发
//                        List<NewsItem> newsItems1 = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                        mAdapter.addAll(newsItems1);
////                        这里少了一句notify
//                        mAdapter.notifyDataSetChanged();
////                        这里出错了，少了1，导致数据库得不到第一次的数据
////                        mNewsItemDao.add(newsItems);
//                        mNewsItemDao.add(newsItems1);
                        new FirstInNoDataLoadDatasTask().execute();
                    } else {
                        mAdapter.addAll(newsItems);
                        mAdapter.notifyDataSetChanged();
                    }
                    // 设置刷新时间
//                    AppUtil.setRefreashTime(getActivity(), newsType);
                    // 清除数据库数据
//                    mNewsItemDao.deleteAll(newsType);

//                }
//                catch (CommonException e) {
//                    e.printStackTrace();
//                    isLoadingDataFromNetWork = false;
////                    return TIP_ERROR_SERVER;
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
            }
//            如果没有网络则直接在数据库拿数据
            else {
                isConnNet = false;
                // 获取最新数据
                isLoadingDataFromNetWork = false;
                List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                /**本地没数据且没网络（第一次进入）*/
                if(newsItems == null || newsItems.size() == 0) {
                    /**什么都不做*/
                    return;
                }else {
                    mAdapter.addAll(newsItems);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

    }


    public void onDetach() {
        super.onDetach();
    }


    public void backToTop() {
        list.setSelection(0);
    }





  /*
       * 双击返回顶部
       * */
    public void exit1() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getContext().getApplicationContext(), "再点击一次返回顶部",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            /*
            * 和setselection(0)一样，多了一个动画渐进的效果
            * */
//            refresh.getRefreshableView().smoothScrollToPosition(0);
            list.smoothScrollToPosition(0);
//            toolbar.setTitle("Midea");

        }
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity1 main = (MainActivity1) activity;
        bar = main.toolbar;
    }

    @Override
    public void onStart() {
        super.onStart();
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
//        handler.removeCallbacks(mAddNewsItem);
        handler.removeCallbacksAndMessages(null);
//        handler.removeCallbacks(mAddNewsItem);
//        todo loaddata task end
        mWasLoading = mIsLoading;
//        handler.removeCallbacks(mAddNewsItemFromDatabase);
//        wrong
        handler.removeCallbacks(mAddNewsItemFromDatabase);
//        handler.removeCallbacksAndMessages(mAddNewsItemFromDatabase);
//        pool.shutdown();
//        handler = null;
        mIsLoading = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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

    @Override
    public void transferMsg() {
        list.smoothScrollToPosition(0);
    }

    /**
     * 比较时间早晚
     * */
    public  int CompareDate(String DATE1, String DATE2) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
//                System.out.println("dt1 在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
//                System.out.println("dt1在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void getLatestNews(int newstype,int page){
//                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
        List<NewsItem> newsItems = null;
        try {
            newsItems = mNewsItemBiz.getNewsItems(newstype, page);
        } catch (CommonException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        for(int i = 0 ; i < newsItems.size();i++){
//            Log.e("currentpage",String.valueOf(page));
//            Log.e("title",newsItems.get(i).getTitle());
//            Log.e("date",newsItems.get(i).getDate());
//        }
//                for(int i = 0; i < newsItems.size(); i++)
        NewsItem localLatest = mNewsItemDao.listLatest(newstype);
//        Log.e("currentPage",String.valueOf(page));
//        Log.e("local",String.valueOf(localLatest.getDate()));
//        Log.e("local",String.valueOf(localLatest.getTitle()));
//        Log.e("Compare",String.valueOf(CompareDate(newsItems.get(0).getDate(), localLatest.getDate())));
        /**
         * 网络请求的比本地的时间晚，所以说明是新的新闻
         * */
        if (CompareDate(newsItems.get(0).getDate(), localLatest.getDate()) > 0) {
            mDatas = newsItems;
            mAdapter.addAll(mDatas);
            mNewsItemDao.add(mDatas);
            getLatestNews(newstype,++page);
        }
    }

    @Override
    public void onLongClick(String url) {

        com.nostra13.universalimageloader.core.ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(url, preview);
        dialog.show();
    }


    class FetchLatestNewsTask extends AsyncTask<Void,Void,List<NewsItem>>{

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
//                /**currentpage*/
//                int i = 0;
////                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, i++);
////                for(int i = 0; i < newsItems.size(); i++)
//                NewsItem localLatest = mNewsItemDao.listLatest(newsType);
//                /**
//                * 网络请求的比本地的时间晚，所以说明是新的新闻
//                * */
//
//                if (CompareDate(newsItems.get(0).getDate(), localLatest.getDate()) < 0) {
//                    mDatas = newsItems;
//                    mAdapter.addAll(mDatas);
//                    mNewsItemDao.add(mDatas);
//                }
                getLatestNews(newsType,0);
            /**
            *
            * */
//            List<NewsItem> newsItems = mNewsItemDao.list(newsType, 0);
            List<NewsItem> newsItems = mNewsItemDao.list(newsType, 1);
//            for(int i = 0 ; i<newsItems.size();i++) {
//                Log.e("newsitemAfterFetch", String.valueOf(newsItems.get(i).getTitle()));
//                Log.e("newsitemAfterFetch", String.valueOf(newsItems.get(i).getDate()));
//            }
            /**重置当前为第一页
             * 是1而不是0
             * */
//            currentPage = 0;
            currentPage = 1;
//            Log.e("newstype",String.valueOf(newsType));
//            Log.e("size",String.valueOf(newsItems.size()));
//            mNewsItemDao.listAll(newsType);
            return newsItems;
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            super.onPostExecute(newsItems);
//            mAdapter.addAll(newsItems);
            if(newsItems == null)
                new FetchLatestNewsTask().execute();
            else {
                mAdapter.refresh(newsItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    class FirstInNoDataLoadDatasTask extends AsyncTask<Void, Void, Void> {
        //
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);

//                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 0);
                mDatas = newsItems;
                /**已经不满一页数据了，说明已经最后一页*/
//            TODO 假如最后一页刚好8个item呢？怎么办？
                if(newsItems.size() < 8){
                    canLoadMoreData = false;
                }
            } catch (CommonException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            mNewsItemDao.listAll(newsType);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
//            if (mDatas.size() == 0)
//            if (newsItems.size() == 0)
//            {
            mAdapter.refresh(mDatas);
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
    /**
    * 请求加载（上滚加载）
    * */
    class LoadDatasTask extends AsyncTask<Void, Void, Void> {
        //
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mIsLoading = true;
//                List<TraceItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);

                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
//                List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 0);
                mDatas = newsItems;
                /**已经不满一页数据了，说明已经最后一页*/
//            TODO 假如最后一页刚好8个item呢？怎么办？
                if(newsItems.size() < 8){
                    canLoadMoreData = false;
                }
            } catch (CommonException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            mNewsItemDao.listAll(newsType);
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
                mDatas = newsItems;
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
//            Log.e("runnable",String.valueOf(newsItems.size()));
            mAdapter.addAll(newsItems);
//            mAdapter.refresh(newsItems);
//            synchronized (handler) {
            mAdapter.notifyDataSetChanged();
            mIsLoading = false;
//            handler.sendEmptyMessage(0);
//            }
//            if (newsItems.size() >= 8)
//                scrollTag = true;
//            else {
////                    /*
////                    * 修复数量少于一页（已经没数据）还显示loading
////                    * */
//                LogWrap.e("message2"+ String.valueOf(newsItems.size()));
////                synchronized (handler) {
//                mIsLoading = false;
//                scrollTag = false;
////                list.removeFooterView(mFooterView);
////                list.addFooterView(mFooterView2);
////                handler.sendEmptyMessage(2);
//                mIsLoading = false;
//                scrollTag = false;
//                list.removeFooterView(mFooterView);
//                list.addFooterView(mFooterView2);
////                }
////                    scrollTag = false;
//////                    list.removeFooterView(mFooterView);
//////                    list.addFooterView(mFooterView1);
//            }
//            mAdapter.notifyDataSetChanged();
//            mIsLoading = false;
        }
    };

}


