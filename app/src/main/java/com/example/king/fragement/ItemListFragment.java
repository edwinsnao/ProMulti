package com.example.king.fragement;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.king.fragement.dummy.DummyContent;
import com.example.king.fragement.dummy.DummyContent.DummyItem;
import com.example.king.fragement.dummy.DummyContent1;
import com.example.king.fragement.dummy.DummyContent2;
import com.example.king.fragement.main.LogWrap;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class ItemListFragment
        extends ListFragment implements OnScrollListener {
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
    Toolbar toolbar;
    static boolean mIsLoading = false;
    static boolean mMoreDataAvailable = true;
    static boolean mWasLoading = false;

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition = -1;
    private Callbacks mCallbacks = this.sDummyCallbacks;
    static ListView list = ItemListActivity.list;
    private Callbacks sDummyCallbacks = new Callbacks() {
        public void onItemSelected(long paramAnonymousLong) {
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

    public void onActivityCreated(Bundle paramBundle) {
        super.onCreate(paramBundle);
//    paramBundle = DummyContent_1.getItems();
        handler = new Handler();
        swipe = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe);
        ViewTreeObserver vto = swipe.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onGlobalLayout() {

                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                Float mDistanceToTriggerSync = Math.min(((View) swipe.getParent()).getHeight() * 0.6f, 500 * metrics.density);

                try {
                    Field field = SwipeRefreshLayout.class.getDeclaredField("mDistanceToTriggerSync");
                    field.setAccessible(true);
                    field.setFloat(swipe, mDistanceToTriggerSync);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ViewTreeObserver obs = swipe.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
            }
        });
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetDataTask().execute();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        //建议把它写一个公共的变量，这里方便阅读就不写了。
        intentFilter.addAction("com.example.king.about");
        intentFilter.addAction("com.example.king.sorting");
        intentFilter.addAction("com.example.king.main");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                action = intent.getAction();
                LogWrap.d( action);
                if (action == null) {
                    LinkedList<DummyContent.DummyItem> items2 = DummyContent.getItems();
                    adapter = new DummyContent(getActivity(), items2);
                } else {
                    switch (action) {
                        case "com.example.king.sorting":
                            LinkedList<DummyContent1.DummyItem> items = DummyContent1.getItems();
                            adapter1 = new DummyContent1(getActivity(), items);
                            LogWrap.d( "sorting");
                            break;
                        case "com.example.king.about":
                            LinkedList<DummyContent2.DummyItem> items1 = DummyContent2.getItems();
                            adapter2 = new DummyContent2(getActivity(), items1);
                            LogWrap.d("about");
                            break;
                        default:
                            LinkedList<DummyContent.DummyItem> items2 = DummyContent.getItems();
                            adapter = new DummyContent(getActivity(), items2);
                            break;

                    }
                }
//                if (action == null) {
//                    refresh.setAdapter(adapter);
//                } else {
//                    switch (action) {
//                        case "com.example.king.sorting":
//                            refresh.setAdapter(adapter1);
//                            break;
//                        case "com.example.king.about":
//                            refresh.setAdapter(adapter2);
//                            break;
//                        default:
//                            refresh.setAdapter(adapter);
//                            break;
//
//                    }
//                }
                if (action == null) {
                    setListAdapter(adapter);
                } else {
                    switch (action) {
                        case "com.example.king.sorting":
                            setListAdapter(adapter1);
                            break;
                        case "com.example.king.about":
                            setListAdapter(adapter2);
                            break;
                        default:
                            setListAdapter(adapter);
                            break;

                    }
                }
//                refresh.setOnItemClickListener(new OnItemClickListener() {
//                    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
////          paramAnonymousAdapterView = (DummyItem)ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
////          paramAnonymousAdapterView = new Intent(ItemListFragment.this.getActivity(), paramAnonymousAdapterView.getActivity());
//// wrong
//// DummyItem item3 = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
////                        Intent it2 = new Intent(ItemListFragment.this.getActivity(), item3.getActivity());
////                        ItemListFragment.this.startActivity(it2);
//                        if (action == null) {
//                            DummyItem item = (DummyItem) ItemListFragment.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                            Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
//                            ItemListFragment.this.startActivity(it);
//                        } else {
//                            switch (action) {
//                                case "com.example.king.sorting":
//                                    DummyContent1.DummyItem item = (DummyContent1.DummyItem) ItemListFragment.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                                    Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
//                                    ItemListFragment.this.startActivity(it);
//                                    break;
//                                case "com.example.king.about":
//                                    DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) ItemListFragment.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                                    Intent it1 = new Intent(ItemListFragment.this.getActivity(), item2.getActivity());
//                                    ItemListFragment.this.startActivity(it1);
//                                    break;
//                                default:
//                                    DummyItem item4 = (DummyItem) ItemListFragment.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                                    Intent it3 = new Intent(ItemListFragment.this.getActivity(), item4.getActivity());
//                                    ItemListFragment.this.startActivity(it3);
//                                    break;
//
//                            }
//                        }
//                    }
//                });
                getListView().setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
//          paramAnonymousAdapterView = (DummyItem)ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//          paramAnonymousAdapterView = new Intent(ItemListFragment.this.getActivity(), paramAnonymousAdapterView.getActivity());
// wrong
// DummyItem item3 = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                        Intent it2 = new Intent(ItemListFragment.this.getActivity(), item3.getActivity());
//                        ItemListFragment.this.startActivity(it2);
                        if (action == null) {
                            DummyItem item = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
                            Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
                            ItemListFragment.this.startActivity(it);
                        } else {
                            switch (action) {
                                case "com.example.king.sorting":
                                    DummyContent1.DummyItem item = (DummyContent1.DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
                                    Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
                                    ItemListFragment.this.startActivity(it);
                                    break;
                                case "com.example.king.about":
                                    DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
                                    Intent it1 = new Intent(ItemListFragment.this.getActivity(), item2.getActivity());
                                    ItemListFragment.this.startActivity(it1);
                                    break;
                                default:
                                    DummyItem item4 = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
                                    Intent it3 = new Intent(ItemListFragment.this.getActivity(), item4.getActivity());
                                    ItemListFragment.this.startActivity(it3);
                                    break;

                            }
                        }
                    }
                });
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
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
        toolbar = ItemListActivity.toolbar;
//    setListAdapter(new DummyContent_1(getActivity(), paramBundle));
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
        LinkedList<DummyContent.DummyItem> items2 = DummyContent.getItems();
        adapter = new DummyContent(getActivity(), items2);
        setListAdapter(adapter);
//        refresh = (PullToRefreshListView) getActivity().findViewById(R.id.pullToRefresh);
//        refresh.setAdapter(adapter);
//        refresh.getRefreshableView().addFooterView(mFooterView, null, false);
//        refresh.setOnRefreshListener(this);


        getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                DummyItem item = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
                Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
                ItemListFragment.this.startActivity(it);
            }
        });
//        refresh.getRefreshableView().setSelection(0);
//        refresh.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
////                DummyItem item = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                DummyItem item = (DummyItem) ItemListFragment.this.refresh.getRefreshableView().getAdapter().getItem(paramAnonymousInt);
//                Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
//                ItemListFragment.this.startActivity(it);
//            }
//        });

//        getListView();
        SwpipeListViewOnScrollListener listener = new SwpipeListViewOnScrollListener(swipe);
        getListView().setOnScrollListener(listener);
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
////          paramAnonymousAdapterView = (DummyItem)ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
////          paramAnonymousAdapterView = new Intent(ItemListFragment.this.getActivity(), paramAnonymousAdapterView.getActivity());
//                if(action == null) {
//                    DummyItem item = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                    Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
//                    ItemListFragment.this.startActivity(it);
//                }else {
//                    switch (action) {
//                        case "com.example.king.sorting":
//                            DummyContent1.DummyItem item = (DummyContent1.DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it = new Intent(ItemListFragment.this.getActivity(), item.getActivity());
//                            ItemListFragment.this.startActivity(it);
//                            break;
//                        case "com.example.king.about":
//                            DummyContent2.DummyItem item2 = (DummyContent2.DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it1 = new Intent(ItemListFragment.this.getActivity(), item2.getActivity());
//                            ItemListFragment.this.startActivity(it1);
//                            break;
//                        default:
//                            DummyItem item3 = (DummyItem) ItemListFragment.this.getListAdapter().getItem(paramAnonymousInt);
//                            Intent it2 = new Intent(ItemListFragment.this.getActivity(), item3.getActivity());
//                            ItemListFragment.this.startActivity(it2);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mAddItemsRunnable);
        handler = null;
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
        this.mCallbacks.onItemSelected(DummyContent.ITEMS.get(paramInt).id);
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
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
            /*
            * 这里不要直接使用list，会报错nullpointer
            * */
                if (getListView().getLastVisiblePosition() == (getListView()
                        .getCount() - 1)) {
                    ItemListActivity.fab.setVisibility(View.VISIBLE);
                    toolbar.setTitle("双击回到顶部");
                }

                // 判断滚动到顶部
                if (getListView().getFirstVisiblePosition() == 0) {
                    ItemListActivity.fab.setVisibility(View.GONE);
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

    static  Runnable mAddItemsRunnable = new Runnable() {
        @Override
        public void run() {
            if (action == null) {
                adapter.addMoreItems(10);
                mIsLoading = false;
//                adapter.notifyDataSetChanged();
            } else {
                switch (action) {
                    case "com.example.king.about":
                        adapter2.addMoreItems(10);
                        mIsLoading = false;
//                        adapter2.notifyDataSetChanged();
                        break;
                    case "com.example.king.sorting":
                        adapter1.addMoreItems(10);
                        mIsLoading = false;
//                        adapter1.notifyDataSetChanged();
                        break;
                    default:
                        adapter.addMoreItems(10);
                        mIsLoading = false;
                        break;
//                        adapter.notifyDataSetChanged();

                }
            }
        }
    };

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
            toolbar.setTitle("双击返回顶部");

        }
        if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
            toolbar.setTitle("Fragement");
//            toolbar = ItemListActivity.toolbar;
        }
        if (!mIsLoading && mMoreDataAvailable) {
            if (totalItemCount >= MAXIMUM_ITEMS) {
                mMoreDataAvailable = false;
                getListView().removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
            } else if (totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
            {
                mIsLoading = true;
                handler.postDelayed(mAddItemsRunnable, 1000);
            }
        }
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
            LogWrap.d("onPost");
            if (action == null) {
                DummyItem items = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//            LinkedList<DummyItem> items = adapter.ITEMS;
//            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
                adapter.ITEMS.addFirst(items);
                adapter.notifyDataSetChanged();
            } else {
                switch (action) {
                    case "com.example.king.sorting":
//                    wrong
//                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
                        DummyContent1.DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//            LinkedList<DummyItem> items = adapter.ITEMS;
//            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
                        adapter1.ITEMS.addFirst(items1);
                        adapter1.notifyDataSetChanged();
                        break;
                    case "com.example.king.main":
//                    wrong
//                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
                        DummyItem items2 = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//            LinkedList<DummyItem> items = adapter.ITEMS;
//            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
                        adapter.ITEMS.addFirst(items2);
                        adapter.notifyDataSetChanged();
                        break;
                    case "com.example.king.about":
//                    wrong
//                        DummyItem items1 = adapter1.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
                        DummyContent2.DummyItem items3 = adapter2.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//            LinkedList<DummyItem> items = adapter.ITEMS;
//            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
                        adapter2.ITEMS.addFirst(items3);
                        adapter2.notifyDataSetChanged();
                        break;
                    default:
                        DummyItem items = adapter.new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10");
//            LinkedList<DummyItem> items = adapter.ITEMS;
//            items.addFirst(new DummyItem(30L, "Random.class", FileTest.class, "A demo", "10-10"));
                        adapter.ITEMS.addFirst(items);
                        adapter.notifyDataSetChanged();
                }
            }
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
        public abstract void onItemSelected(long paramLong);
//        public abstract  void backToTop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mWasLoading) {
            mWasLoading = false;
            mIsLoading = true;
            handler.postDelayed(mAddItemsRunnable, 1000);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(mAddItemsRunnable);
        mWasLoading = mIsLoading;
        mIsLoading = false;
    }
}

class SwpipeListViewOnScrollListener implements AbsListView.OnScrollListener {

    private SwipeRefreshLayout mSwipeView;
    private AbsListView.OnScrollListener mOnScrollListener;
    static boolean scrollFlag = ItemListFragment.scrollFlag;


    public SwpipeListViewOnScrollListener(SwipeRefreshLayout swipeView) {
        mSwipeView = swipeView;
    }

    public SwpipeListViewOnScrollListener(SwipeRefreshLayout swipeView,
                                          OnScrollListener onScrollListener) {
        mSwipeView = swipeView;
        mOnScrollListener = onScrollListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                scrollFlag = false;
                // 判断滚动到底部
            /*
            * 这里不要直接使用ItemListFragement.list，会报错nullpointer
            * */
                if (ItemListActivity.list.getLastVisiblePosition() == (ItemListActivity.list
                        .getCount() - 1)) {
                    ItemListActivity.fab.setVisibility(View.VISIBLE);
                    ItemListActivity.toolbar.setTitle("双击回到顶部");
                }

                // 判断滚动到顶部
                if (ItemListActivity.list.getFirstVisiblePosition() == 0) {
                    ItemListActivity.fab.setVisibility(View.GONE);
                    ItemListActivity.toolbar.setTitle("Fragment");
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

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        View firstView = absListView.getChildAt(firstVisibleItem);
        int lastVisibleItemPosition = ItemListFragment.lastVisibleItemPosition;
        // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
        if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
            mSwipeView.setEnabled(true);
            ItemListActivity.toolbar.setTitle("Fragement");
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
            ItemListActivity.toolbar.setTitle("双击返回顶部");

        }
        if (firstVisibleItem < lastVisibleItemPosition) {

//            Log.d("dc", "下滑");
            ItemListActivity.toolbar.setTitle("Fragement");
//            toolbar = ItemListActivity.toolbar;
        }
        if (!ItemListFragment.mIsLoading && ItemListFragment.mMoreDataAvailable) {
            if (totalItemCount >= ItemListFragment.MAXIMUM_ITEMS) {
                ItemListFragment.mMoreDataAvailable = false;
                ItemListFragment.list.removeFooterView(ItemListFragment.mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
            } else if (totalItemCount - ItemListFragment.AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
            {
                ItemListFragment.mIsLoading = true;
                ItemListFragment.handler.postDelayed(ItemListFragment.mAddItemsRunnable, 1000);
            }
        }
    }
}


/* Location:              D:\迅雷下载\dex2jar-2.0\classes-dex2jar.jar!\com\example\king\fragement\ItemListFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */