package com.example.king.fragement;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edwinsnao.midea.CommonException;
import com.edwinsnao.midea.Constaint;
import com.example.king.fragement.main.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.*;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by test on 15-11-4.
 */
public class SplashActivity extends AppCompatActivity {
//	private Fade fade;
	private int[] newstype = new int[1];
	private ImageView img;
	private NewsItemDao mNewsItemDao = BaseApplication.getNewsItemDao();
	private NewsItemBiz mNewsItemBiz = BaseApplication.getNewsItemBiz();
	private List<NewsItem> mDatas = null;
	private final MyHandler handler = new MyHandler(this);
//	private FirstInNoDataLoadDatasTask loader1;
//	private FirstInNoDataLoadDatasTask loader2;

	private class MyHandler extends Handler {
		private final WeakReference<SplashActivity> mActivity;

		MyHandler(SplashActivity instance) {
			mActivity = new WeakReference<SplashActivity>(instance);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
//		fade = new Fade();
//		fade.setDuration(500);
		img = (ImageView) findViewById(R.id.splash_img);
		/**
		 * 放到runnable里面才可以显示
		 * 因为我加了其他两个线程
		 * */
//		getWindow().setBackgroundDrawable(null);
		/**
		 *不显示图片的,要postdelayed才可以
		 * */
		handler.postDelayed(splash, 500);

	}

	Runnable splash = new Runnable() {
		@Override
		public void run() {
			/**
			 * 放到runnable里面才可以显示
			 * */
			Animation ani = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splash_translate);
			img.setAnimation(ani);
//			getWindow().setEnterTransition(fade);
			if (NetUtil.checkNet(getApplicationContext())) {
				List<NewsItem> newsItems = mNewsItemDao.list(Constaint.NEWS_TYPE_YANFA, 1);
				if (newsItems == null || newsItems.size() == 0) {
					/**
					 * 第一次进入时没有数据,则先下载数据
					 * */
					Log.e("0Null", "null");
					newstype[0] = Constaint.NEWS_TYPE_YANFA;
					new FirstInNoDataLoadDatasTask().execute(newstype[0]);
//					loader1 = new FirstInNoDataLoadDatasTask();
//					loader1.execute(newstype[0]);
				} else {
					/**
					 * 不是第一次进入(有数据),则看看有没有新的
					 * */
					/**
					 * 这里是预防中途断了网，这时运行这个代码会崩溃
					 * */
					if (NetUtil.checkNet(getApplicationContext()))
					/**
					 * 必须使用线程,否则报很奇怪的错误,说list集合get方法不能在null pointer
					 * 但是我觉得应该是因为网络以及数据库操作不可以在主线程进行才说的过去呀
					 * 所以就是这个原因导致了不能进行网络爬取,所以是nullpointer?
					 * */
						new Thread(new Runnable() {
							@Override
							public void run() {
								getLatestNews(Constaint.NEWS_TYPE_YANFA, 0);
							}
						}).start();
				}
				List<NewsItem> newsItems1 = mNewsItemDao.list(Constaint.NEWS_TYPE_YIDONG, 1);
				if (newsItems1 == null || newsItems1.size() == 0) {
					/**
					 * 第一次进入时没有数据,则先下载数据
					 * */
					Log.e("1Null", "null");
					newstype[0] = Constaint.NEWS_TYPE_YIDONG;
					new FirstInNoDataLoadDatasTask().execute(newstype[0]);
//					loader2 = new FirstInNoDataLoadDatasTask();
//					loader2.execute(newstype[0]);
				} else {
					/**
					 * 不是第一次进入(有数据),则看看有没有新的
					 * */
					if (NetUtil.checkNet(getApplicationContext()))
						new Thread(new Runnable() {
							@Override
							public void run() {
								getLatestNews(Constaint.NEWS_TYPE_YIDONG, 0);
							}
						}).start();
				}
			}
			Intent main = new Intent(SplashActivity.this, com.example.king.fragement.main.MainActivity1.class);
			startActivity(main);
//			getWindow().setExitTransition(fade);
			/**
			 * 不要结束动画（fade的动画）
			 * */
			finishAfterTransition();
		}
	};


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
			mNewsItemDao.add(newsItems);
			getLatestNews(newstype, ++page);
		}
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
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
//		if (loader1 != null && loader1.getStatus() == AsyncTask.Status.RUNNING)
//			loader1.cancel(true); // 如果Task还在运行，则先取消它
//		if (loader2 != null && loader2.getStatus() == AsyncTask.Status.RUNNING)
//			loader2.cancel(true); // 如果Task还在运行，则先取消它
	}

	class FirstInNoDataLoadDatasTask extends AsyncTask<Integer, Void, Void> {
		//
		@Override
		protected Void doInBackground(Integer... newsType) {
			try {
				List<NewsItem> newsItems = mNewsItemBiz.getNewsItems(newsType[0], 0);
				mDatas = newsItems;
				/**已经不满一页数据了，说明已经最后一页*/
//            TODO 假如最后一页刚好8个item呢？怎么办？
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
			mNewsItemDao.add(mDatas);
		}
	}
}
