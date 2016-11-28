package com.example.king.fragement.midea;

/**
 * Created by test on 15-11-14.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.edwinsnao.midea.CommonException;
import com.example.king.fragement.R;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.BaseActivity;
import com.example.king.fragement.main.BaseApplication;
import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.midea.detail.NewsDto;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.reflect.Method;
import java.util.List;


public class NewsContentActivity extends BaseActivity {


	/**
	 * 该页面的url
	 */

	/**
	 * 我发现leakcanary说changeBounds的Parent，所以觉得是changedBounds泄露了内存
	 * 因为Explode的动画没泄露
	 * 所以我就比较两个的区别
	 * 发现Explode不是全部变量
	 * 所以这里注释了ChangeBounds后就没有内存泄露了，生命周期是oncreate，而不是和Activity一起
	 */
	ChangeBounds bounds;
	private int theme = 0;
	ProgressBar mProgressBar;
	WebView mWebView;
	ImageView img;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String title;
	private String url;

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishAfterTransition();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.share_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		showSystemShareOption(this, title, url);
		return true;
	}

	/**
	 * 调用系统安装了的应用分享
	 *
	 * @param context
	 * @param title
	 * @param url
	 */
	public void showSystemShareOption(Activity context,
									  final String title, final String url) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
		intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
		context.startActivity(Intent.createChooser(intent, "选择分享"));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			theme = Utils.getAppTheme(this);
		} else {
			theme = savedInstanceState.getInt("theme");
		}
		setTheme(theme);
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/**
		* 异步显示loading的交互
		* */
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.detail1);
		imageLoader = BaseApplication.getLoader();
		/**
		 * 由于FIFO不符合需求，应该LIFO才对，因为快速滚动的时候应该快点看到下面的图片
		 * */
//		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = BaseApplication.getOptions();
		img = (ImageView) findViewById(R.id.share);
		String img_link = getIntent().getStringExtra("img_url");
		if (img_link != null) {
			img.setVisibility(View.VISIBLE);
			imageLoader.displayImage(img_link, img, options);
		} else {
			img.setVisibility(View.GONE);
		}
		bounds = new ChangeBounds();
		bounds.setDuration(500);
		getWindow().setSharedElementEnterTransition(bounds);
		getWindow().setSharedElementExitTransition(bounds);
		getWindow().setAllowReturnTransitionOverlap(false);
		getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setReenterTransition(explode);
//        finishAfterTransition();
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
		TextView txt = (TextView) findViewById(R.id.share_title);
		txt.setText(title);
		setSupportActionBar(bar);
		mProgressBar = (ProgressBar) findViewById(R.id.loading_progressbar);
		LinearLayout ll = (LinearLayout) findViewById(R.id.webview_ll);
		mWebView = new WebView(getApplicationContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mWebView.setLayoutParams(lp);
		ll.addView(mWebView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBlockNetworkImage(true);
		mWebView.setTransitionGroup(true);

//        支持缩放
		mWebView.getSettings().setBuiltInZoomControls(true);
//        隐藏缩放按钮
		mWebView.getSettings().setDisplayZoomControls(false);
//        取消滚动条
		//去掉缩放按钮
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// Use the API 11+ calls to disable the controls
			mWebView.getSettings().setBuiltInZoomControls(true);
			mWebView.getSettings().setDisplayZoomControls(false);
		} else {
			// Use the reflection magic to make it work on earlier APIs
			getControlls();
		}

		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				WebSettings settings = mWebView.getSettings();
				settings.setBuiltInZoomControls(true);
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setVisibility(View.VISIBLE);
				mProgressBar.setProgress(newProgress);
				if (newProgress == 100) {
					mProgressBar.setVisibility(View.GONE);
					mWebView.getSettings().setBlockNetworkImage(false);
				}
			}
		});
		mWebView.loadUrl(url);
		/**
		* 通过反射实现webview反色（夜间模式）
		* */
		try {
			Class clsWebSettingsClassic =
					getClassLoader().loadClass("android.webkit.WebSettingsClassic");
			Method md = clsWebSettingsClassic.getMethod(
					"setProperty", String.class, String.class);
			md.invoke(mWebView.getSettings(), "inverted", "true");
			md.invoke(mWebView.getSettings(), "inverted_contrast", "1");
		} catch (Exception e) {}

		if (!isConnected()) {
			Toast toast = new Toast(NewsContentActivity.this);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.makeText(NewsContentActivity.this, "没有网络，请连接后重新加载", Toast.LENGTH_SHORT).show();
		}


	}

	private void getControlls() {
		try {
			Class webview = Class.forName("android.webkit.WebView");
			Method method = webview.getMethod("getZoomButtonsController");
			ZoomButtonsController zoom_controll = (ZoomButtonsController) method.invoke(this, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//    点击返回不是退出程序而是返回上一页
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		/**、
		 * 这里内存泄漏了，因为它的父容器在退出前没有被销毁，所以就会持有引用，内存泄漏
		 * */

		if (mWebView != null) {
			// 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
			ViewParent parent = mWebView.getParent();
			if (parent != null) {
				((ViewGroup) parent).removeView(mWebView);
			}

			mWebView.stopLoading();
			// 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
			mWebView.getSettings().setJavaScriptEnabled(false);
			mWebView.clearHistory();
			mWebView.clearView();
			mWebView.removeAllViews();

			try {
				mWebView.destroy();
			} catch (Throwable ex) {

			}
		}
		bounds = null;
		finishAfterTransition();
	}


	private class getDetail extends AsyncTask<Void, Void, Void> {
		private NewsDto newsDto;
		String url;
		private List<NewsItem> newses;
		private NewsItemBiz biz;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				newsDto = biz.getNews(url);

				newses = newsDto.getNewses();
			} catch (CommonException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void o) {
			super.onPostExecute(o);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (theme == Utils.getAppTheme(this)) {
		} else {
			reload();
		}
	}

	public void reload() {
		Intent intent = getIntent();
		LogWrap.d("it in reload:" + String.valueOf(intent));
		overridePendingTransition(0, 0);//不设置进入退出动画
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
		finishAfterTransition();
		overridePendingTransition(0, 0);
		ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img, getString(R.string.explode));
		startActivity(intent, transitionActivityOptions.toBundle());
	}

	public boolean isConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("theme", theme);
	}

}
