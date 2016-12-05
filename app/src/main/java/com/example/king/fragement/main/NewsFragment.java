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
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
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
public class NewsFragment extends Fragment implements ImageLoadingListener, TransferListener
		, NewsItemAdapter.OnImgLongClickListener {
	private int lastItem = 0;
	private ImageView preview;
	private Dialog dialog;
	private Explode explode;
	private boolean canLoadMoreData = true;
	private ChangeBounds bounds;
//	private FetchLatestNewsTask lates;
//	private FetchLatestNewsTask fetch;
//	private FirstInNoDataLoadDatasTask first1;
//	private FirstInNoDataLoadDatasTask first2;

	long exitTime = 0;
	Activity context;
	private NewsItemDao mNewsItemDao;
	private final int AUTOLOAD_THREADSHOLD = 1;
	private SwipeRefreshLayout swipe;
	private int newsType = 0;
	private NewsItemBiz mNewsItemBiz;
	public NewsItemAdapter mAdapter;
	private List<NewsItem> mDatas = new ArrayList<NewsItem>();
	/**
	 * 当前页面
	 */
	private int currentPage = 1;
	private boolean scrollFlag = false;
	private boolean isUp = false;
	private int lastVisibleItemPosition;// 标记上次滑动位置
	private final int MAXIMUM_ITEMS = 1000;
	private View mFooterView;
	private List<Bitmap> mBitmaps = new ArrayList<>();
	private FloatingActionButton fab;


	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					mAdapter.notifyDataSetChanged();
					mIsLoading = false;
					break;
				case 2:
					mIsLoading = false;
					scrollTag = false;
					list.removeFooterView(mFooterView);
					list.addFooterView(mFooterView2);
					break;
			}
			super.handleMessage(msg);
		}
	};
	private boolean mIsLoading = false;
	private boolean mMoreDataAvailable = true;
	private boolean mWasLoading = false;

	private ListView list;
	private boolean scrollTag = true;
	private View mFooterView2;
	private NewsItem newsItem;
	private View rootView;
	private String mName;


	public NewsFragment() {
	}

	public NewsFragment(String query) {
		LogWrap.d("query" + String.valueOf(query));
		mBitmaps = new ArrayList<>();
		mName = query;
		if (query.equalsIgnoreCase("ProductNews")) {
			this.newsType = Constaint.NEWS_TYPE_YIDONG;
		} else if (query.equalsIgnoreCase("GroupNews")) {
			this.newsType = Constaint.NEWS_TYPE_YANFA;
		}
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context = activity;
		fab = ((MainActivity1) activity).getFab();
		LogWrap.e("onAttach" + mName);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogWrap.e("onActivityCreated" + mName);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogWrap.e("onViewCreated" + mName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogWrap.e("onCreateView" + mName);
		View v = null;
		if (rootView == null) {
			v = inflater.inflate(R.layout.news_fragment, container, false);
			initView(v);
		} else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		return v;
	}

	private void initView(View v) {
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
					/**
					 * 这个每次都新建就可以解决不能重复更新（执行同一个asynctask）问题
					 * */
					new LoadDatasTask().execute();
//					MideaFragment.task1.execute();
//                    }

				} else {
					Toast.makeText(getContext().getApplicationContext(), "没有网络，请稍后刷新", Toast.LENGTH_SHORT).show();
				}
				new FetchLatestNewsTask().execute();
				/**
				* 为了在退出时取消这个asyncTask防止内存泄漏
				* */
//				lates = new FetchLatestNewsTask();
//				lates.execute();
				/**
				 * 不set false的话会一直不返回，卡在那里
				 * */
				swipe.setRefreshing(false);
//                list.setSelection(0);
			}
		});
		ScrollLayout scrollLayout = (ScrollLayout) v.findViewById(R.id.scroll_layout);
		scrollLayout.setIsScroll(false);
		list = (ListView) v.findViewById(android.R.id.list);
		list.setEmptyView(v.findViewById(R.id.empty));
		mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.loading_view, null);
		list.addFooterView(mFooterView, null, false);
		/**
		 * listview中调用顺序是
		 * 1.addHeaderView
		 * 2.addFooterView
		 * 3.setListAdapter
		 * 否则报空指针错误
		 * */
		/**
		 * refresh中和上面listview有一点区别
		 * 先setAdapter的到了refresh
		 * 然后再addfooterView，否则会报错nullpointer
		 * 因为那时refresh还没有初始化
		 * */
		list.setAdapter(mAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				newsItem = (NewsItem) parent.getAdapter().getItem(position);
				Intent intent = new Intent(getContext().getApplicationContext(), NewsContentActivity.class);
				String urlStr = null;
				if (newsType == Constaint.NEWS_TYPE_YANFA) {
					urlStr = "http://www.midea.com/cn/news_center/Group_News";
				} else if (newsType == Constaint.NEWS_TYPE_YIDONG) {
					urlStr = "http://www.midea.com/cn/news_center/Product_News";
				}
				int size = newsItem.getLink().length();
				/**
				 * 这里的写法是错误的，因为generateUrl生成后多了index.shtml所以404
				 * */
				intent.putExtra("url", urlStr + newsItem.getLink().substring(1, size));
				intent.putExtra("img_url", newsItem.getImgLink());
				intent.putExtra("title", newsItem.getTitle());
				/**
				 * 用了webview就不需要下面两句了
				 * */
//                        intent.putExtra("time", newsItem.getDate());
//                        intent.putExtra("title", newsItem.getTitle());
//                System.out.println(urlStr + newsItem.getLink().substring(1, size));
//                Log.d("url", String.valueOf(urlStr + newsItem.getLink().substring(1, size)));
				/**这里报错commonexceptio
				 总结一下commonexceptio的原因：
				 1.可能是没有加internet权限
				 2.可能是没有对链接格式化
				 3.可能是引用了midea2 jar包中的DataUtil（因为该版本的jar包没有注释setdooutput）这个查询了很久
				 因为getLink得到的链接是 ./201505/t20150523_180260.shtml
				 所以一直报错，所以要先对得到的链接先格式化
				 */
				/**
				 * 这部分动画放到activity的接口中，防止内存泄漏
				 * */
//                final Pair<View, String>[] pairs = NewsItemAdapter.getPair(context);
				if (explode == null) {
					explode = new Explode();
				}
				explode.setDuration(500);
				context.getWindow().setExitTransition(explode);
				context.getWindow().setAllowEnterTransitionOverlap(false);
				context.getWindow().setAllowReturnTransitionOverlap(false);
				if (bounds == null)
					bounds = new ChangeBounds();
				bounds.setDuration(500);
				context.getWindow().setSharedElementEnterTransition(bounds);
				;
				context.getWindow().setSharedElementExitTransition(bounds);
				ImageView img = (ImageView) mAdapter.getView1(context, position, view, parent);
				TextView txt = (TextView) mAdapter.getView2(context, position, view, parent);
				Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(
						context, false, new Pair<>(img, getString(R.string.explode)),
						new Pair<>(txt, getString(R.string.share_title)));
				ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
				startActivity(intent, transitionActivityOptions.toBundle());
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
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
						scrollFlag = true;
						if (!isUp) {
							if (fab.getAlpha() != 0)
								dismiss();
						} else {
							if (fab.getAlpha() == 0)
								show();
						}
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
						scrollFlag = false;
						break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				/**
				 * 下滑，list往上滚动
				 * */
//                if(firstVisibleItem == 0)
//                    if(fab.getAlpha() == 1)
//                    dismiss();
				if (lastItem < firstVisibleItem) {
					lastItem = firstVisibleItem;
					/**
					 * 放到onScrollStateChanged里面判断
					 * 是否停止了滚动来判断再决定显示与否
					 * 防止多次重复绘制动画（浪费性能）
					 * */
					isUp = true;
//                    dismiss();
				}
				/**
				 * 上滑，list往下滚动
				 * */
				if (lastItem > firstVisibleItem) {
					lastItem = firstVisibleItem;
					/**
					 * 放到onScrollStateChanged里面判断
					 * 是否停止了滚动来判断再决定显示与否
					 * 防止多次重复绘制动画（浪费性能）
					 * */
					isUp = false;
//                    show();
				}
				View firstView = view.getChildAt(firstVisibleItem);
				// 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
				if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
					swipe.setEnabled(true);
				} else {
					swipe.setEnabled(false);
				}
				if (!mIsLoading && mMoreDataAvailable) {
					if (totalItemCount >= MAXIMUM_ITEMS) {
						mMoreDataAvailable = false;
						list.removeFooterView(mFooterView);
					} else if (scrollTag && totalItemCount - AUTOLOAD_THREADSHOLD <= firstVisibleItem + visibleItemCount) {
						mIsLoading = true;
						currentPage++;
						LogWrap.e("currentPage" + String.valueOf(currentPage));
						//                 如果新闻已经加载了则从数据库加载的
						List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
						/**
						 * 一开始的加载出现了null-pointer
						 * 所以说明是用newsItems.get(0)来判空才正确
						 * if(newsItems==null)  ----------wrong
						 * */
						if (newsItems == null || newsItems.size() < 8) {
							if (canLoadMoreData == true)
//                                new FirstInNoDataLoadDatasTask().execute();
								new LoadDatasTask().execute();
							else {
								handler.sendEmptyMessage(2);
								/**什么都不做*/
								return;
							}
						} else {
							/**
							 * 因为前面已经list通过，证明数据库已经有，所以不需要加进来
							 * 我的数据库出现重复的原因
							 * */
//                            mAdapter.addAll(newsItems);
							/**
							 * 不要忘记了这句，否则不更新
							 * */
//                            mAdapter.notifyDataSetChanged();
//                            NewsFragment.mIsLoading = false;
							/**
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
				}
			}
		});
		mFooterView2 = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.nodata_view, null);
		v.findViewById(R.id.empty).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/**
				 * 不能用mAddItem这个runnable，一直说网络失败
				 * 因为网络操作需要在子线程中，handler不算！？
				 * */
				new FirstInNoDataLoadDatasTask().execute();
//				first1 = new FirstInNoDataLoadDatasTask();
//				first1.execute();
				/**
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


	private void dismiss() {
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator alpha = ObjectAnimator.ofFloat(fab, "alpha", 1, 0);
		alpha.setDuration(500);
		ObjectAnimator translationY = ObjectAnimator.ofFloat(fab, "translationY", 0, 60);
		translationY.setDuration(500);
		set.playTogether(alpha, translationY);
		set.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				fab.clearAnimation();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		set.start();
	}

	private void show() {
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator alpha = ObjectAnimator.ofFloat(fab, "alpha", 0, 1);
		alpha.setDuration(500);
		ObjectAnimator translationY = ObjectAnimator.ofFloat(fab, "translationY", 0, -60);
		translationY.setDuration(500);
		set.playTogether(alpha, translationY);
		set.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				fab.clearAnimation();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		set.start();
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		LogWrap.e("onCreate" + mName);
		/**
		 * 初始化newsItemdao
		 * */
		/**
		 * 放到activity里面初始化通过 接口来获取
		 * */
		dialog = new Dialog(getActivity());
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_preview_img, null);
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
		mNewsItemDao = BaseApplication.getNewsItemDao();
		/**
		 * 因为没有初始化mDatas,所以不是最新的news,这句代替了下面的if做得事情
		 * */
		mDatas = mNewsItemDao.list(newsType, 1);
		mNewsItemBiz = BaseApplication.getNewsItemBiz();
		mAdapter = new NewsItemAdapter(getActivity(), mDatas);
		if (mDatas == null) {
			new FirstInNoDataLoadDatasTask().execute();
//			first2 = new FirstInNoDataLoadDatasTask();
//			first2.execute();
		}
		mAdapter.setOnImgLongClickListener(this);
	}


	public void onDetach() {
		super.onDetach();
		LogWrap.e("onDetach" + mName);
	}


	public void backToTop() {
		list.setSelection(0);
	}


	public void exit1() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getContext().getApplicationContext(), "再点击一次返回顶部",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			list.smoothScrollToPosition(0);
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
	public void onStart() {
		super.onStart();
		LogWrap.e("onStart" + mName);
	}

	@Override
	public void onResume() {
		super.onResume();
		LogWrap.e("onResume" + mName);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		LogWrap.e("onAttach" + mName);
	}

	@Override
	public void onPause() {
		super.onPause();
		LogWrap.e("onPause" + mName);
	}

	@Override
	public void onStop() {
		super.onStop();
		LogWrap.e("onStop" + mName);
		handler.removeCallbacksAndMessages(null);
		mWasLoading = mIsLoading;
		handler.removeCallbacks(mAddNewsItemFromDatabase);
		mIsLoading = false;
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogWrap.e("onDestroyView" + mName);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogWrap.e("onDestroy" + mName);
		cleanBitmapList();
		handler.removeCallbacksAndMessages(null);
		BaseApplication.getLoader().clearMemoryCache();
		/**
		* 取消正在执行的asynctask防止内存泄漏
		* */
//		if (first1 != null && first1.getStatus() == AsyncTask.Status.RUNNING)
//			first1.cancel(true); // 如果Task还在运行，则先取消它
//		if (first2 != null && first2.getStatus() == AsyncTask.Status.RUNNING)
//			first2.cancel(true); // 如果Task还在运行，则先取消它
//		if (fetch != null && fetch.getStatus() == AsyncTask.Status.RUNNING)
//			fetch.cancel(true); // 如果Task还在运行，则先取消它
//		if (latest != null && latest.getStatus() == AsyncTask.Status.RUNNING)
//			latest.cancel(true); // 如果Task还在运行，则先取消它
//        mAdapter.imageLoader.clearDiscCache();
	}

	public void cleanBitmapList() {
		if (mBitmaps.size() > 0) {
			for (int i = 0; i < mBitmaps.size(); i++) {
				Bitmap b = mBitmaps.get(i);
				if (b != null && !b.isRecycled()) {
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
	 */
	public int CompareDate(String DATE1, String DATE2) {


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

	public void getLatestNews(int newstype, int page) {
		List<NewsItem> newsItems = null;
		try {
			newsItems = mNewsItemBiz.getNewsItems(newstype, page);
		} catch (CommonException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		NewsItem localLatest = mNewsItemDao.listLatest(newstype);
		/**
		 * 网络请求的比本地的时间晚，所以说明是新的新闻
		 * */
		if (CompareDate(newsItems.get(0).getDate(), localLatest.getDate()) > 0) {
			mDatas = newsItems;
			mAdapter.addAll(mDatas);
			mNewsItemDao.add(mDatas);
			getLatestNews(newstype, ++page);
		}
	}

	@Override
	public void onLongClick(String url) {

		com.nostra13.universalimageloader.core.ImageLoader loader = BaseApplication.getLoader();
		loader.displayImage(url, preview);
		dialog.show();
	}


	class FetchLatestNewsTask extends AsyncTask<Void, Void, List<NewsItem>> {

		@Override
		protected List<NewsItem> doInBackground(Void... voids) {
			if(!isConnected())
				return null;
			getLatestNews(newsType, 0);
			List<NewsItem> newsItems = mNewsItemDao.list(newsType, 1);
			/**重置当前为第一页
			 * 是1而不是0
			 * */
//            currentPage = 0;
			currentPage = 1;
			return newsItems;
		}

		@Override
		protected void onPostExecute(List<NewsItem> newsItems) {
			super.onPostExecute(newsItems);
//            mAdapter.addAll(newsItems);
			if (newsItems == null) {
				new FetchLatestNewsTask().execute();
//				fetch = new FetchLatestNewsTask();
//				fetch.execute();
			}
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
			if(!isConnected())
				return null;
			try {
				mIsLoading = true;
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, 0);
				mDatas = newsItems;
				/**已经不满一页数据了，说明已经最后一页*/
				if (newsItems.size() < 8) {
					canLoadMoreData = false;
				}
			} catch (CommonException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mAdapter.refresh(mDatas);
			mNewsItemDao.add(mDatas);
			mAdapter.notifyDataSetChanged();
			mIsLoading = false;
			/**
			 * 防止在网络不好重新加载出现的问题
			 * */
//            currentPage = 1;
//            mXListView.stopRefresh();
		}
	}

	/**
	 * 请求加载（上滚加载）
	 */
	class LoadDatasTask extends AsyncTask<Void, Void, Void> {
		//
		@Override
		protected Void doInBackground(Void... params) {
			if(!isConnected())
				return null;
			try {
				mIsLoading = true;
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
				mDatas = newsItems;
				/**已经不满一页数据了，说明已经最后一页*/
				if (newsItems.size() < 8) {
					canLoadMoreData = false;
				}
			} catch (CommonException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mAdapter.addAll(mDatas);
			mNewsItemDao.add(mDatas);
			mAdapter.notifyDataSetChanged();
			mIsLoading = false;
			/**
			 * 防止在网络不好重新加载出现的问题
			 * */
//            currentPage = 1;
//            mXListView.stopRefresh();
		}
	}

	/**
	 * 从网络取数据
	 */
	private final Runnable mAddNewsItem = new Runnable() {
		@Override
		public void run() {
			try {
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType, currentPage);
				mDatas = newsItems;
				mNewsItemDao.add(newsItems);
				mAdapter.addAll(newsItems);
				mAdapter.notifyDataSetChanged();
				if (newsItems.size() >= 8)
					scrollTag = true;
				else {
					/**
					 * 修复数量少于一页（已经没数据）还显示loading
					 * */
					LogWrap.e("message2" + String.valueOf(newsItems.size()));
					mIsLoading = false;
					scrollTag = false;
					list.removeFooterView(mFooterView);
					list.addFooterView(mFooterView2);
				}
				mIsLoading = false;
			} catch (CommonException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	};
	/**
	 * 从数据库取数据
	 */
	private final Runnable mAddNewsItemFromDatabase = new Runnable() {
		@Override
		public void run() {
			List<NewsItem> newsItems = mNewsItemDao.list(newsType, currentPage);
			mAdapter.addAll(newsItems);
			mAdapter.notifyDataSetChanged();
			mIsLoading = false;
		}
	};

}


