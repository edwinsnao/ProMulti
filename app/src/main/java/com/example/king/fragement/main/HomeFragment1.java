package com.example.king.fragement.main;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;
import com.example.king.fragement.main.multi_items.GuanZhuFragment;
import com.example.king.fragement.main.multi_items.WoFragment;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


/**
 * Created by Kings on 2016/1/25.
 */
public class HomeFragment1 extends Fragment {
	private TextView network;
	private RadioButton search;
	private RadioButton home;
	private BroadcastReceiver networkListener;
	private ConnectivityManager mConnectivityManager;
	private NetworkInfo networkInfo;
	private IntentFilter filter;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private Toolbar toolbar;
	private SystemBarTintManager barTintManager;
	private final int[] icons = {R.drawable.ic_tab_discovery_normal, R.drawable.ic_tab_my_normal
			, R.drawable.ic_tab_nodes_normal};
	private final String[] titles = {"GroupNews", "ProductNews", "Learned", "关注的人", "我"};
	private final List<String> titles1 = new ArrayList<>();
	private final List<Fragment> fragments = new ArrayList<>();
	private Adapter adapter;

	private void setupTabIcons() {
		tabLayout.getTabAt(0).setCustomView(getTabView(0));
		tabLayout.getTabAt(1).setCustomView(getTabView(1));
		tabLayout.getTabAt(2).setCustomView(getTabView(2));
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		MainActivity1 m = (MainActivity1) activity;
		toolbar = m.getToolbar();
		barTintManager = m.barTintManager;
	}

	/**
	 * 改变viewpager切换的颜色
	 * 在之前没有图标时默认有变化，加了图标要自己处理
	 * 这里其实需要两组图片，一组变化前，一组变化后
	 */
	private void changeTabSelect(TabLayout.Tab tab) {
		LogWrap.e("changetabselect");
		View view = tab.getCustomView();
		ImageView img_title = (ImageView) view.findViewById(R.id.img);
		TextView txt_title = (TextView) view.findViewById(R.id.title);
		txt_title.setTextColor(getResources().getColor(R.color.white));
		if (txt_title.getText().toString().equals("GroupNews")) {
			img_title.setImageResource(R.drawable.ic_tab_discovery_normal);
			img_title.setColorFilter(R.color.white);
			viewPager.setCurrentItem(0);
		} else if (txt_title.getText().toString().equals("ProductNews")) {
			img_title.setImageResource(R.drawable.ic_tab_my_normal);
			img_title.setColorFilter(R.color.white);
			viewPager.setCurrentItem(1);
		} else if (txt_title.getText().toString().equals("Learned")) {
			img_title.setImageResource(R.drawable.ic_tab_nodes_normal);
			img_title.setColorFilter(R.color.white);
			viewPager.setCurrentItem(2);
		}
	}

	private void changeTabNormal(TabLayout.Tab tab) {
		LogWrap.e("changetabunselect");
		View view = tab.getCustomView();
		ImageView img_title = (ImageView) view.findViewById(R.id.img);
		TextView txt_title = (TextView) view.findViewById(R.id.title);
		txt_title.setTextColor(getResources().getColor(R.color.graywhite));
		if (txt_title.getText().toString().equals("GroupNews")) {
			img_title.setImageResource(R.drawable.ic_tab_discovery_normal);
			img_title.setColorFilter(R.color.gray);
			viewPager.setCurrentItem(0);
		} else if (txt_title.getText().toString().equals("ProductNews")) {
			img_title.setImageResource(R.drawable.ic_tab_my_normal);
			img_title.setColorFilter(R.color.gray);
			viewPager.setCurrentItem(1);
		} else if (txt_title.getText().toString().equals("Learned")) {
			img_title.setImageResource(R.drawable.ic_tab_nodes_normal);
			img_title.setColorFilter(R.color.gray);
			viewPager.setCurrentItem(2);
		}
	}


	public View getTabView(int position) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
		TextView txt_title = (TextView) view.findViewById(R.id.title);
		txt_title.setText(titles[position]);
		ImageView img_title = (ImageView) view.findViewById(R.id.img);
		img_title.setImageResource(icons[position]);
		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		filter = new IntentFilter("com.example.king.netstate");
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		networkListener = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					mConnectivityManager = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
					networkInfo = mConnectivityManager.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isAvailable()) {
						network.setVisibility(View.GONE);
					} else {
						network.setVisibility(View.VISIBLE);
						network.setText("当前网络链接不可用，请稍后尝试");
					}
				}
			}
		};
		getContext().getApplicationContext().registerReceiver(networkListener, filter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getContext().getApplicationContext().unregisterReceiver(networkListener);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.home_fragment1, container, false);
		initView(v);
		return v;

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (isConnected()) {
			network.setVisibility(View.GONE);
		} else {
			network.setText("当前网络链接不可用，请稍后尝试");
			network.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
					getActivity().startActivity(it);
				}
			});
		}

	}

	public Adapter getAdapter() {
		return adapter;
	}

	private void initView(View v) {
		viewPager = (ViewPager) v.findViewById(R.id.viewpager);
		/**
		 * 设置为5,因为有5个fragment,是设置为4?还是5?
		 * 流畅度好预加载
		 * */
		viewPager.setOffscreenPageLimit(5);
		network = (TextView) v.findViewById(R.id.network);
		if (viewPager != null) {
			setupViewPager(viewPager);
		}
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				ArgbEvaluator evaluator = new ArgbEvaluator();
				if (position == 0) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); //先设置第0页时还没有滑动时tablayout的颜色
					toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));   //先设置第0页时还没有滑动时toolbar的颜色
					barTintManager.setTintColor(getResources().getColor(R.color.colorPrimary));   //先设置第0页时还没有滑动时toolbar的颜色
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.green));
					tabLayout.setBackgroundColor(evaluate);//设置背景颜色为算出的两种颜色之间的过渡色
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}
				if (0 < position && position < 1) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
					toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
					barTintManager.setTintColor(getResources().getColor(R.color.colorPrimary));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.green));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

				if (position == 1) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.green));
					toolbar.setBackgroundColor(getResources().getColor(R.color.green));
					barTintManager.setTintColor(getResources().getColor(R.color.green));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.green), getResources().getColor(R.color.orange));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

				if (1 < position && position < 2) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.green));
					toolbar.setBackgroundColor(getResources().getColor(R.color.green));
					barTintManager.setTintColor(getResources().getColor(R.color.green));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.green), getResources().getColor(R.color.orange));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}


				if (position == 2) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.orange));
					toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
					barTintManager.setTintColor(getResources().getColor(R.color.orange));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.orange), getResources().getColor(R.color.red));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

				if (2 < position && position < 3) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.orange));
					toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
					barTintManager.setTintColor(getResources().getColor(R.color.orange));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.orange), getResources().getColor(R.color.red));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

				if (position == 3) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.red));
					toolbar.setBackgroundColor(getResources().getColor(R.color.red));
					barTintManager.setTintColor(getResources().getColor(R.color.red));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.red), getResources().getColor(R.color.purple));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

				if (3 < position && position < 4) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.red));
					toolbar.setBackgroundColor(getResources().getColor(R.color.red));
					barTintManager.setTintColor(getResources().getColor(R.color.red));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.red), getResources().getColor(R.color.purple));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}
				if (position == 4) {
					tabLayout.setBackgroundColor(getResources().getColor(R.color.purple));
					toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
					barTintManager.setTintColor(getResources().getColor(R.color.purple));
					int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.purple), getResources().getColor(R.color.red));
					tabLayout.setBackgroundColor(evaluate);
					toolbar.setBackgroundColor(evaluate);
					barTintManager.setTintColor(evaluate);
				}

			}


			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		tabLayout = (TabLayout) v.findViewById(R.id.tabs);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

		if (viewPager != null) {
			tabLayout.setupWithViewPager(viewPager);
		}
		viewPager.setCurrentItem(0);
	}


	private void setupViewPager(ViewPager viewPager) {
		fragments.add(new NewsFragment("GroupNews"));
		fragments.add(new NewsFragment("ProductNews"));
		fragments.add(new CheeseListFragment());
		fragments.add(new GuanZhuFragment());
		fragments.add(new WoFragment());
		/**
		 * 只显示图片
		 * */
		titles1.add("GroupNews");
		titles1.add("ProductNews");
		titles1.add("Learned");
		titles1.add("关注的人");
		titles1.add("我");
		adapter = new Adapter(getActivity().getSupportFragmentManager(), fragments, titles1);
		viewPager.setAdapter(adapter);
	}

	public boolean isConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	public void switchTheme() {
		viewPager.invalidate();
	}

	class Adapter extends FragmentPagerAdapter {
		Fragment currentFragment;
		private List<Fragment> mFragments = new ArrayList<>();
		private List<String> mFragmentsTitle = new ArrayList<>();
		private final int[] icons = {R.drawable.ic_tab_discovery_normal, R.drawable.ic_tab_my_normal
				, R.drawable.ic_tab_nodes_normal};

		public Adapter(FragmentManager fm) {
			super(fm);
		}

		public Adapter(FragmentManager fm, List<Fragment> fragments, @Nullable List<String> titles) {
			super(fm);
			mFragments = fragments;
			mFragmentsTitle = titles;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
			currentFragment = (Fragment) object;
		}

		public void addFragment(Fragment fragment, String title) {
			mFragments.add(fragment);
			mFragmentsTitle.add(title);
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
			if (mFragmentsTitle.get(position) == null)
				return "";
			return mFragmentsTitle.get(position);
		}

	}
}
