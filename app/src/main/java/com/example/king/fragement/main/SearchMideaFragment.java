package com.example.king.fragement.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.edwinsnao.midea.Constaint;
import com.example.king.fragement.R;
import com.example.king.fragement.main.contacts.model.SortModel;
import com.example.king.fragement.midea.NewsContentActivity;
import com.example.king.fragement.midea.NewsItem;
import com.example.king.fragement.midea.NewsItemDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/1/27.
 */
public class SearchMideaFragment extends Fragment {
    ListView list;
    ListView list1;
    EditText et;
    Handler handler;
    TextInputLayout textinput;
    Button btn;
    String query=null;
    View mFooterView;
    View mFooterView1;
    TextView empty;
    int start = 0;
    int end = 10;
    List<NewsItem> mDatas = new ArrayList<NewsItem>();
    SearchAdapter_Product mAdapterProduct;
    SearchAdapter_Group mAdapterGroup;
//    NewsItemAdapter mAdapter;
//    NewsItemDao mNewsItemDao = NewsFragment.mNewsItemDao;
    NewsItemDao mNewsItemDao ;
    boolean mIsLoading = false;
    boolean mMoreDataAvailable = true;
    boolean mWasLoading = false;
    boolean scrollTag = false;
    boolean scrollFlag = false;
    final static int MAXIMUM_ITEMS = 200;
    final static int AUTOLOAD_THREADSHOLD = 0;
    TextView product;
    TextView group;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.searchnews,container,false);
        initData();
        initView(v);
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mSearchNews);
        handler = null;
    }

    private void initData() {
        handler = new Handler();
        mNewsItemDao = BaseApplication.getNewsItemDao();
//        mAdapter = new SearchAdapter(getActivity(),mDatas,query);
//        mAdapter = new NewsItemAdapter(getActivity(),mDatas);
    }

    private void initView(View v) {
        product = (TextView) v.findViewById(R.id.txt_product);
        group = (TextView) v.findViewById(R.id.txt_group);
        mFooterView1  = LayoutInflater.from(getActivity()).inflate(R.layout.nodata_view,null);
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_view, null);
        empty = (TextView) v.findViewById(R.id.empty);
        list = (ListView) v.findViewById(R.id.list_product);
        list.setEmptyView(v.findViewById(R.id.empty));
        list.addFooterView(mFooterView,null,false);
//        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem =  (NewsItem)parent.getAdapter().getItem(position);
                Intent it = new Intent(getActivity(), NewsContentActivity.class);
                String urlStr = null;
//                if (newsItem.getNewsType() == Constaint.NEWS_TYPE_YANFA) {
//                    urlStr = "http://www.midea.com/cn/news_center/Group_News";
//                } else if (newsItem.getNewsType() == Constaint.NEWS_TYPE_YIDONG) {
                    urlStr = "http://www.midea.com/cn/news_center/Product_News";
//                }
                int size = newsItem.getLink().length();
                it.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                startActivity(it);
            }
        });
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
                /*
                * 匿名内部类，所以一定不为null,不用判断
                * */
//                if (null != mOnScrollListener) {
//                onScroll(view, firstVisibleItem,
//                        visibleItemCount, totalItemCount);
//                }
//                if (firstVisibleItem > lastVisibleItemPosition) {
//                    MainActivity1.toolbar.setTitle("双击返回顶部");
//                }
//                if (firstVisibleItem < lastVisibleItemPosition) {
//                    MainActivity1.toolbar.setTitle("Fragement");
//                }
                if (!mIsLoading && mMoreDataAvailable) {
                    if (totalItemCount >= MAXIMUM_ITEMS) {
                        mMoreDataAvailable = false;
                        list.removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
                    } else if (scrollTag&&totalItemCount>=8&&totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
                    {
//                        if(totalItemCount<10){
//                            mIsLoading = false;
//                        }
//                        else {
                            mIsLoading = true;
//                        currentPage++;
                            //                 如果新闻已经加载了则从数据库加载的
//                        List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                  /*
               * 一开始的加载出现了null-pointer
               * 所以说明是用newsItems.get(0)来判空才正确
               * if(newsItems==null)  ----------wrong
               * */
//                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
//                        Log.d("newsItems in News1:", String.valueOf(newsItems.size()));
                /*
                * 捕捉到第一次加载newsItems.size()==0
                * */
//                        if (newsItems.size() == 0) {
//                            handler.postDelayed(mAddNewsItem, 1000);
//                        } else {
////                    MideaFragment.currentPage += 1;
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
//                            mIsLoading = false;
                            handler.postDelayed(mSearchNews, 1000);
//                        }
//                        }

                    }
//            new LoadDatasTask().execute();
                }
            }
        });
        list1 = (ListView) v.findViewById(R.id.list_group);
        list1.setEmptyView(v.findViewById(R.id.empty));
        list1.addFooterView(mFooterView,null,false);
//        list.setAdapter(mAdapter);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem =  (NewsItem)parent.getAdapter().getItem(position);
                Intent it = new Intent(getActivity(), NewsContentActivity.class);
                String urlStr = null;
//                if (newsItem.getNewsType() == Constaint.NEWS_TYPE_YANFA) {
                    urlStr = "http://www.midea.com/cn/news_center/Group_News";
//                } else if (newsItem.getNewsType() == Constaint.NEWS_TYPE_YIDONG) {
//                    urlStr = "http://www.midea.com/cn/news_center/Product_News";
//                }
                int size = newsItem.getLink().length();
                it.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
                startActivity(it);
            }
        });
        list1.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                /*
                * 匿名内部类，所以一定不为null,不用判断
                * */
//                if (null != mOnScrollListener) {
//                onScroll(view, firstVisibleItem,
//                        visibleItemCount, totalItemCount);
//                }
//                if (firstVisibleItem > lastVisibleItemPosition) {
//                    MainActivity1.toolbar.setTitle("双击返回顶部");
//                }
//                if (firstVisibleItem < lastVisibleItemPosition) {
//                    MainActivity1.toolbar.setTitle("Fragement");
//                }
                if (!mIsLoading && mMoreDataAvailable) {
                    if (totalItemCount >= MAXIMUM_ITEMS) {
                        mMoreDataAvailable = false;
                        list1.removeFooterView(mFooterView);
//                refresh.getRefreshableView().removeFooterView(mFooterView);
                    } else if (scrollTag&&totalItemCount>=8&&totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount)
//        else
                    {
//                        if(totalItemCount<10){
//                            mIsLoading = false;
//                        }
//                        else {
                            mIsLoading = true;
//                        currentPage++;
                            //                 如果新闻已经加载了则从数据库加载的
//                        List<TraceItem> newsItems = mNewsItemDao.list(newsType, currentPage);
                  /*
               * 一开始的加载出现了null-pointer
               * 所以说明是用newsItems.get(0)来判空才正确
               * if(newsItems==null)  ----------wrong
               * */
//                Log.d("newsItems in News1:",String.valueOf(newsItems.get(0)));
//                        Log.d("newsItems in News1:", String.valueOf(newsItems.size()));
                /*
                * 捕捉到第一次加载newsItems.size()==0
                * */
//                        if (newsItems.size() == 0) {
//                            handler.postDelayed(mAddNewsItem, 1000);
//                        } else {
////                    MideaFragment.currentPage += 1;
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
//                            mIsLoading = false;
                            handler.postDelayed(mSearchNews, 1000);
//                        }
//                        }

                    }
//            new LoadDatasTask().execute();
                }
            }
        });
        textinput = (TextInputLayout) v.findViewById(R.id.textinput);
        et = (EditText) v.findViewById(R.id.et_searchnews);
        EditText editText = textinput.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start1, int before, int count) {
//                       // TODO: 2016/1/27   关键字过长
                  /*
                * 修改或者清空时把标志重置，否则出现bug
                * */
                end = 10;
                start = 0;
                if (s.length()>10){
                    textinput.setErrorEnabled(true);
                    textinput.setError("关键字过长");
                }else{
                    textinput.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn = (Button) v.findViewById(R.id.btn_searchnews);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2016/1/27 可以改为
//                end = 10;
//                start = 0;
                if(TextUtils.isEmpty(et.getText())){
                    textinput.setHint("请输入您需要查找的关键字");
                }
                else{
                    query = et.getText().toString();
//                    initData();
                    /*
                    *点击查询，如果mdatas不为空，则清空数据
                    * 否则残留上一次搜索的结果
                    * */
                    if(mDatas!=null){
                        mDatas.clear();
                    }
                    mAdapterProduct = new SearchAdapter_Product(getActivity(), mDatas, query);
                    list.setAdapter(mAdapterProduct);
                    mAdapterGroup = new SearchAdapter_Group(getActivity(), mDatas, query);
                    list1.setAdapter(mAdapterGroup);
//                    handler.postDelayed(mSearchNews,1000);
                    new LoadData().execute();
                }
            }
        });
    }
    class LoadData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            return null;
        }
    @Override
    protected void onPostExecute(Void result) {
        List<NewsItem> newsItems = mNewsItemDao.searchnews_type(et.getText().toString(),Constaint.NEWS_TYPE_YANFA,start,end);
        List<NewsItem> newsItems1 = mNewsItemDao.searchnews_type(et.getText().toString(),Constaint.NEWS_TYPE_YIDONG,start,end);
        if(newsItems.size()!=0) {
                /*
                * 超过一个页面的数据才再执行runnable继续获取
                * 否则onscroll会一直执行runnable，消耗资源并不断刷新屏幕
                * */
            if(newsItems.size()>=8)
                scrollTag=true;
            else {
                    /*
                    * 修复数量少于一页（已经没数据）还显示loading
                    * */
                scrollTag = false;
                list.removeFooterView(mFooterView);
                list.addFooterView(mFooterView1);
            }
            mAdapterGroup.addAll(newsItems);
            mAdapterGroup.notifyDataSetChanged();
            mAdapterProduct.addAll(newsItems1);
            mAdapterProduct.notifyDataSetChanged();
            start += 10;
            end += 10;
            product.setVisibility(View.VISIBLE);
            group.setVisibility(View.VISIBLE);
        }else{
            list.removeFooterView(mFooterView);
            list.addFooterView(mFooterView1);
            list1.removeFooterView(mFooterView);
            list1.addFooterView(mFooterView1);
            empty.setVisibility(View.VISIBLE);
        }
        mIsLoading = false;

    }
    }

    Runnable mSearchNews = new Runnable() {
        @Override
        public void run() {
            List<NewsItem> newsItems = mNewsItemDao.searchnews_type(et.getText().toString(),Constaint.NEWS_TYPE_YANFA,start,end);
            List<NewsItem> newsItems1 = mNewsItemDao.searchnews_type(et.getText().toString(),Constaint.NEWS_TYPE_YIDONG,start,end);
            if(newsItems.size()!=0) {
                /*
                * 超过一个页面的数据才再执行runnable继续获取
                * 否则onscroll会一直执行runnable，消耗资源并不断刷新屏幕
                * */
                if(newsItems.size()>=8)
                    scrollTag=true;
                else {
                    /*
                    * 修复数量少于一页（已经没数据）还显示loading
                    * */
                    scrollTag = false;
                    list.removeFooterView(mFooterView);
                    list.addFooterView(mFooterView1);
                    list1.removeFooterView(mFooterView);
                    list1.addFooterView(mFooterView1);
                }
                mAdapterProduct.addAll(newsItems1);
                mAdapterProduct.notifyDataSetChanged();
                mAdapterGroup.addAll(newsItems);
                mAdapterGroup.notifyDataSetChanged();
                start += 10;
                end += 10;
                group.setVisibility(View.VISIBLE);
                product.setVisibility(View.VISIBLE);
            }else{
                list.removeFooterView(mFooterView);
                list.addFooterView(mFooterView1);
                list1.removeFooterView(mFooterView);
                list1.addFooterView(mFooterView1);
                empty.setVisibility(View.VISIBLE);
            }
            mIsLoading = false;

        }
    };
    /*
    * 搜索结果红色显示
    * */
    public class SearchAdapter_Group extends BaseAdapter {
        private List<NewsItem> mDatas;
        private LayoutInflater mInflater;
        String query=null;

        private ImageLoader imageLoader = ImageLoader.getInstance();
        private DisplayImageOptions options;

        public SearchAdapter_Group(Context context, List<NewsItem> datas, @Nullable String query){
            this.mDatas = datas;
            mInflater = LayoutInflater.from(context);

            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder().showStubImage(R.drawable.blank)
                    .showImageForEmptyUri(R.drawable.blank).showImageOnFail(R.drawable.blank)
                    .cacheOnDisc()
//                    .displayer(new RoundedBitmapDisplayer(20))
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
            this.query = query;
        }

        public void addAll(List<NewsItem> mDatas){
            this.mDatas.addAll(mDatas);
        }

        public void removeAll(List<NewsItem> mDatas){
            this.mDatas.removeAll(mDatas);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertview == null){
                convertview = mInflater.inflate(R.layout.news_item_yidong,parent,false);
                holder = new ViewHolder();

                holder.mContent = (TextView) convertview.findViewById(R.id.id_content);
//                holder.txt_catalog = (TextView) convertview.findViewById(R.id.txt_catalog);
                holder.mTitle = (TextView) convertview.findViewById(R.id.id_title);
                holder.mDate = (TextView) convertview.findViewById(R.id.id_date);
                holder.mImg = (ImageView) convertview.findViewById(R.id.id_newsImg);
                convertview.setTag((holder));
            }
            else
            {
                holder = (ViewHolder) convertview.getTag();
            }
            NewsItem newsItem = mDatas.get(position);
            int type = newsItem.getNewsType();
//            if(type == Constaint.NEWS_TYPE_YANFA)
//                holder.txt_catalog.setText("集团新闻");
//                else{
//                holder.txt_catalog.setText("产品新闻");
//            }
//        holder.mTitle.setText(newsItem.getTitle());
//        holder.mContent.setText(newsItem.getContent());
            if(type == Constaint.NEWS_TYPE_YANFA) {
                int chageTextColor;
                ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.red));
                NewsItem anchor = mDatas.get(position);

                SpannableStringBuilder builder = new SpannableStringBuilder(
                        anchor.getTitle());
                if (query != null) {
                    chageTextColor = String.valueOf(anchor.getTitle()).indexOf(query);
                    if (chageTextColor != -1) {
                        builder.setSpan(redSpan, chageTextColor, chageTextColor
                                        + query.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.mTitle.setText(builder);
                    } else
                        holder.mTitle.setText(anchor.getTitle());
                } else
                    holder.mTitle.setText(anchor.getTitle());
                SpannableStringBuilder builder1 = new SpannableStringBuilder(
                        String.valueOf(anchor.getContent()));
                if (query != null) {
                    chageTextColor = String.valueOf(anchor.getContent()).indexOf(
                            query);
                    if (chageTextColor != -1) {
                        builder1.setSpan(redSpan, chageTextColor, chageTextColor
                                        + query.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.mContent.setText(builder1);
                    } else
                        holder.mContent.setText(String.valueOf(anchor.getContent()));
                } else
                    holder.mContent.setText(String.valueOf(anchor.getContent()));
                holder.mDate.setText(newsItem.getDate());
                if (newsItem.getImgLink() != null) {
                    holder.mImg.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(newsItem.getImgLink(), holder.mImg, options);
                } else {
                    holder.mImg.setVisibility(View.GONE);
                }
            }
            return convertview;
        }
        private final class ViewHolder
        {
            TextView mTitle;
            TextView mContent;
            ImageView mImg;
            TextView mDate;
//            TextView txt_catalog;
        }

    }
    public class SearchAdapter_Product extends BaseAdapter {
        private List<NewsItem> mDatas;
        private LayoutInflater mInflater;
        String query=null;

        private ImageLoader imageLoader = ImageLoader.getInstance();
        private DisplayImageOptions options;

        public SearchAdapter_Product(Context context, List<NewsItem> datas, @Nullable String query){
            this.mDatas = datas;
            mInflater = LayoutInflater.from(context);

            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder().showStubImage(R.drawable.blank)
                    .showImageForEmptyUri(R.drawable.blank).showImageOnFail(R.drawable.blank)
                    .cacheOnDisc()
//                    .displayer(new RoundedBitmapDisplayer(20))
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
            this.query = query;
        }

        public void addAll(List<NewsItem> mDatas){
            this.mDatas.addAll(mDatas);
        }

        public void removeAll(List<NewsItem> mDatas){
            this.mDatas.removeAll(mDatas);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertview == null){
                convertview = mInflater.inflate(R.layout.news_item_yidong,parent,false);
                holder = new ViewHolder();

                holder.mContent = (TextView) convertview.findViewById(R.id.id_content);
//                holder.txt_catalog = (TextView) convertview.findViewById(R.id.txt_catalog);
                holder.mTitle = (TextView) convertview.findViewById(R.id.id_title);
                holder.mDate = (TextView) convertview.findViewById(R.id.id_date);
                holder.mImg = (ImageView) convertview.findViewById(R.id.id_newsImg);
                convertview.setTag((holder));
            }
            else
            {
                holder = (ViewHolder) convertview.getTag();
            }
            NewsItem newsItem = mDatas.get(position);
            int type = newsItem.getNewsType();
//            if(type == Constaint.NEWS_TYPE_YANFA)
//                holder.txt_catalog.setText("集团新闻");
//                else{
//                holder.txt_catalog.setText("产品新闻");
//            }
//        holder.mTitle.setText(newsItem.getTitle());
//        holder.mContent.setText(newsItem.getContent());
            if(type == Constaint.NEWS_TYPE_YIDONG) {
                int chageTextColor;
                ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.red));
                NewsItem anchor = mDatas.get(position);

                SpannableStringBuilder builder = new SpannableStringBuilder(
                        anchor.getTitle());
                if (query != null) {
                    chageTextColor = String.valueOf(anchor.getTitle()).indexOf(query);
                    if (chageTextColor != -1) {
                        builder.setSpan(redSpan, chageTextColor, chageTextColor
                                        + query.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.mTitle.setText(builder);
                    } else
                        holder.mTitle.setText(anchor.getTitle());
                } else
                    holder.mTitle.setText(anchor.getTitle());
                SpannableStringBuilder builder1 = new SpannableStringBuilder(
                        String.valueOf(anchor.getContent()));
                if (query != null) {
                    chageTextColor = String.valueOf(anchor.getContent()).indexOf(
                            query);
                    if (chageTextColor != -1) {
                        builder1.setSpan(redSpan, chageTextColor, chageTextColor
                                        + query.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        holder.mContent.setText(builder1);
                    } else
                        holder.mContent.setText(String.valueOf(anchor.getContent()));
                } else
                    holder.mContent.setText(String.valueOf(anchor.getContent()));
                holder.mDate.setText(newsItem.getDate());
                if (newsItem.getImgLink() != null) {
                    holder.mImg.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(newsItem.getImgLink(), holder.mImg, options);
                } else {
                    holder.mImg.setVisibility(View.GONE);
                }
            }
            return convertview;
        }
        private final class ViewHolder
        {
            TextView mTitle;
            TextView mContent;
            ImageView mImg;
            TextView mDate;
//            TextView txt_catalog;
        }

    }

}
