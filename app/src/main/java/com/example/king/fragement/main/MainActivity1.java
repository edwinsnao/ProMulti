package com.example.king.fragement.main;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceFragment;
//import android.support.design.widget.FloatingActionButton;
import com.example.king.fragement.NetWorkConnectChangedReceiver;
import com.example.king.fragement.main.preference.PreferenceActivity;
import com.example.king.fragement.main.utils.TransitionHelper;
import com.example.king.fragement.midea.NewsItemDao;
import com.melnykov.fab.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.AboutUs;
import com.example.king.fragement.BroadcastTest;
import com.example.king.fragement.FileTest;
import com.example.king.fragement.JniTest;
import com.example.king.fragement.MapsActivity;
import com.example.king.fragement.MapsActivity1;
import com.example.king.fragement.NewsFragement;
import com.example.king.fragement.OsLogin;
import com.example.king.fragement.QueryProcess;
import com.example.king.fragement.R;
import com.example.king.fragement.SettingsActivity;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.preference.AdvancePreferenceExample;
import com.example.king.fragement.main.preference.SettingFragment;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.ItemListActivity;
import com.example.king.fragement.midea.ItemListActivity1;
import com.melnykov.fab.ScrollDirectionListener;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class MainActivity1 extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
		ImageLoadingListener, TransferListener {
	HomeFragment1 homeFragment;
	Fragment sort;
	/**
	 * 用以记录点击日夜间模式的次数来区别日还是夜
	 */
	int i = 1;
	Fragment personFragment;
	SearchMideaFragment search;
	Fragment sorttypeFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	private DrawerLayout mDrawerLayout;
	long exitTime = 0;
	ViewPager viewPager;
	ActionBarDrawerToggle toggle;
	private Toolbar toolbar;
	private FloatingActionButton fab;
	int lastVisibleItemPosition = 0;
	/**
	 * 不能SparseArray，因为不能用String，用了int则不能表达class的名称（在listview中显示）
	 */
	private int theme = 0;
	private SettingFragment mSettingsFragment;
	FragmentTabHost mTabHost;
	LayoutInflater mLayoutInflater;
	String state = null;
	BroadcastReceiver networkListener;
	private ConnectivityManager mConnectivityManager;
	private NetworkInfo networkInfo;
	private Thread thread;
	private PackageManager pm;
	/**
	 * wrong context要在oncreate等方法里
	 */
//    private ComponentName cmpName = new ComponentName(getApplicationContext(),NetWorkConnectChangedReceiver.class);
	private ComponentName cmpName;
	private List<Bitmap> mBitmaps = new ArrayList<>();
	private Fragment frag;
	private NewsFragment newsFrag;
	private CheeseListFragment rvFragment;
	private TextView radio0, radio1, radio2;
	private ImageButton img0, img1, img2;
	private LinearLayout ll0, ll1, ll2;
	SystemBarTintManager barTintManager;

	@Override
	protected void onStart() {
		super.onStart();
		LogWrap.e("onStart:MainActivity1");
	}

	public FloatingActionButton getFab() {
		return fab;
	}

	public Toolbar getToolbar() {
		return toolbar;
	}

	private void applyKitKatTranslucency() {

		// KitKat translucent navigation/status bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			barTintManager = new SystemBarTintManager(this);
			barTintManager.setStatusBarTintEnabled(true);
		}

	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogWrap.e("onCreate:MainActivity1");
		if (savedInstanceState == null) {
			theme = Utils.getAppTheme(this);
		} else {
			theme = savedInstanceState.getInt("theme");
		}
		setTheme(theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_fragment);
		applyKitKatTranslucency();
		homeFragment = new HomeFragment1();
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		final ActionBar ab = getSupportActionBar();
		ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);
		mLayoutInflater = LayoutInflater.from(MainActivity1.this);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		NavigationView nav = (NavigationView) findViewById(R.id.nav);
		nav.setNavigationItemSelectedListener(this);
		/**
		 * DrawerLayout must be measured with MeasureSpec.EXACTLY.
		 * */
//        mDrawerLayout.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY);
//        int w = mDrawerLayout.getMeasuredWidth();
//        int h = mDrawerLayout.getMeasuredHeight();
//        Toast.makeText(this, "Width:" + w + " Height:" + h, Toast.LENGTH_LONG).show();
		toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mDrawerLayout.setDrawerListener(toggle);
		toggle.syncState();
		fab = (FloatingActionButton) findViewById(R.id.fab1);
		toolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				exit1();
			}
		});
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				frag = homeFragment.getAdapter().currentFragment;
				if (frag instanceof NewsFragment) {
					newsFrag = (NewsFragment) homeFragment.getAdapter().currentFragment;
					newsFrag.transferMsg();
				} else if (frag instanceof CheeseListFragment) {
					rvFragment = (CheeseListFragment) homeFragment.getAdapter().currentFragment;
					rvFragment.transferMsg();
				}
			}
		});
		radio0 = (TextView) findViewById(R.id.radio0);
		radio1 = (TextView) findViewById(R.id.radio1);
		radio2 = (TextView) findViewById(R.id.radio2);
		ll0 = (LinearLayout) findViewById(R.id.ll0);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		img0 = (ImageButton) findViewById(R.id.img0);
		img1 = (ImageButton) findViewById(R.id.img1);
		img2 = (ImageButton) findViewById(R.id.img2);

		fragmentManager = getSupportFragmentManager();
		transaction = fragmentManager.beginTransaction();
		hideAllFragment(transaction);
		transaction.add(R.id.main_container, homeFragment, "NewsFragment");
		transaction.commit();
		transaction.show(homeFragment);
		/*
        * 默认主界面是homefragment
        * */
		radio0.setTextColor(getResources().getColor(R.color.colorPrimary));
		img0.setColorFilter(getResources().getColor(R.color.colorPrimary));

		/**
		 * 把onclick修改为了onTouch就使得点击更加灵敏(区域变大了)
		 * */
		ll0.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				resetOtherTabs();
				radio0.setTextColor(getResources().getColor(R.color.colorPrimary));
				img0.setColorFilter(getResources().getColor(R.color.colorPrimary));
				transaction = fragmentManager.beginTransaction();
				hideAllFragment(transaction);
				if (homeFragment == null) {
					homeFragment = new HomeFragment1();
					/**
					 * 一定要在replace和add以及commit之前
					 * */
					transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_exit);
					transaction.replace(R.id.main_container, homeFragment);
					transaction.commit();
				} else {
					transaction.show(homeFragment);
					transaction.commit();
				}
				return true;
			}
		});
		ll1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				resetOtherTabs();
				radio1.setTextColor(getResources().getColor(R.color.colorPrimary));
				img1.setColorFilter(getResources().getColor(R.color.colorPrimary));
				transaction = fragmentManager.beginTransaction();
				hideAllFragment(transaction);
				if (search == null) {
					search = new SearchMideaFragment();
					transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_exit);
					transaction.add(R.id.main_container, search);
					transaction.commit();
				} else {
					transaction.show(search);
					transaction.commit();
				}
				return true;
			}
		});
		ll2.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				resetOtherTabs();
				radio2.setTextColor(getResources().getColor(R.color.colorPrimary));
				img2.setColorFilter(getResources().getColor(R.color.colorPrimary));
				transaction = fragmentManager.beginTransaction();
				hideAllFragment(transaction);
				if (personFragment == null) {
					personFragment = new PersonFragment();
					transaction.setCustomAnimations(R.anim.fragment_in, R.anim.fragment_exit);
					transaction.add(R.id.main_container, personFragment);
					transaction.commit();
				} else {
					transaction.show(personFragment);
					transaction.commit();
				}
				return true;
			}
		});
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

	public void cleanBitmapList() {
		if (mBitmaps.size() > 0) {
			for (int i = 0; i < mBitmaps.size(); i++) {
				Bitmap b = mBitmaps.get(i);
				if (!b.isRecycled() && b != null) {
					b.recycle();
				}
			}
		}
	}

	@Override
	public void transferMsg() {

	}


	/**
	 * LocalBroadcastReciver,原本想提高安全，还有节省资源而写的，但是不能动态监听所以不用了
	 */
    /*private void registerLocalBroadcastReceiver() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("com.example.king.netstate");
        mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        mIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        lbm = LocalBroadcastManager.getInstance(MainActivity1.this);
        mLocalReceiver = new NetWorkConnectChangedReceiver();
        lbm.registerReceiver(mLocalReceiver,mIntentFilter);
    }*/

//    private static  class MyHandler  extends Handler{
//        private  final WeakReference<MainActivity1> mActivity;
//
//        private MyHandler(MainActivity1 activity) {
//            mActivity = new WeakReference<MainActivity1>(activity);
//        }
//
//    }
	@Override
	protected void onStop() {
		super.onStop();
		LogWrap.e("onStop:MainActivity1");
		if (cmpName == null)
			cmpName = new ComponentName(getApplicationContext(), NetWorkConnectChangedReceiver.class);
		pm = getApplicationContext().getPackageManager();
		pm.setComponentEnabledSetting(cmpName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED
				, PackageManager.DONT_KILL_APP);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogWrap.e("onDestroy:MainActivity1");
		homeFragment = null;
		cleanBitmapList();
		LogWrap.e("receiver disabled");
		setContentView(R.layout.empty);
		BaseApplication.getLoader().clearMemoryCache();
		System.gc();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void hideAllFragment(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (search != null) {
			transaction.hide(search);
		}
		if (personFragment != null) {
			transaction.hide(personFragment);
		}
	}


	@Override
	protected void onPause() {
		super.onPause();
		LogWrap.e("onPause:MainActivity1");
	}


	@Override
	protected void onResume() {
		LogWrap.e("onResume:MainActivity1");
		super.onResume();
		if (theme == Utils.getAppTheme(this)) {
		} else {
			reload();
		}
		if (cmpName == null)
			cmpName = new ComponentName(getApplicationContext(), NetWorkConnectChangedReceiver.class);
		pm = getApplicationContext().getPackageManager();
		pm.setComponentEnabledSetting(cmpName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED
				, PackageManager.DONT_KILL_APP);
		/**
		 * 下面菜单栏的文字根据日夜间模式进行颜色的变化
		 * */
		resetOtherTabs();
		radio0.setTextColor(getResources().getColor(R.color.colorPrimary));
		img0.setColorFilter(getResources().getColor(R.color.colorPrimary));
	}


	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
        /*
        * 否则点击之后不会退出
        * */
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawers();
		switch (item.getItemId()) {
			case R.id.about:
				Intent it = new Intent();
				it.setClass(MainActivity1.this, AboutUs.class);
				startActivity(it, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
				break;
			case R.id.settings:
				Utils.switchAppTheme(this);
				LogWrap.d("mainActivity1");
				setTheme(Utils.getAppTheme(this));
				onResume();
				break;
		}

		return true;
	}

	public void replaceFragment(int viewId, android.app.Fragment fragment) {
		android.app.FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(viewId, fragment).commit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("theme", theme);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	  * 双击返回顶部
	  * */
	public void exit1() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(this, "再点击一次返回顶部",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			frag = homeFragment.getAdapter().currentFragment;
			if (frag instanceof NewsFragment) {
				newsFrag = (NewsFragment) homeFragment.getAdapter().currentFragment;
				newsFrag.transferMsg();
			} else if (frag instanceof CheeseListFragment) {
				rvFragment = (CheeseListFragment) homeFragment.getAdapter().currentFragment;
				rvFragment.transferMsg();
			}
			toolbar.setTitle("MideaNews");

		}
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			/**
			 * 加上这一行就不可以注销静态的receiver了
			 * */
//            System.exit(0);
		}
	}

	public void reload() {
		Intent intent = getIntent();
		LogWrap.d("it in reload:" + String.valueOf(intent));
		overridePendingTransition(0, 0);//不设置进入退出动画
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs() {
		if (theme == R.style.AppBaseTheme_Night) {

			radio0.setTextColor(getResources().getColor(R.color.night_textColor));
			radio1.setTextColor(getResources().getColor(R.color.night_textColor));
			radio2.setTextColor(getResources().getColor(R.color.night_textColor));
		} else {
			radio0.setTextColor(getResources().getColor(R.color.black));
			radio1.setTextColor(getResources().getColor(R.color.black));
			radio2.setTextColor(getResources().getColor(R.color.black));
		}
		img0.setColorFilter(getResources().getColor(R.color.gray));
		img1.setColorFilter(getResources().getColor(R.color.gray));
		img2.setColorFilter(getResources().getColor(R.color.gray));
//        }
	}

}


