package com.example.king.fragement.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import com.bumptech.glide.Glide;
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
import com.example.king.fragement.SplashActivity;
import com.example.king.fragement.main.aidlserver.*;
import com.example.king.fragement.main.aidlserver.MainActivity;
import com.example.king.fragement.main.fastJson.*;
import com.example.king.fragement.main.hightlight.HightLight;
import com.example.king.fragement.main.maps.TencentMaps;
import com.example.king.fragement.main.music_player.HomeActivity;
import com.example.king.fragement.main.mywork.activity.PreviewActivity;
import com.example.king.fragement.main.nfc.NFCard;
import com.example.king.fragement.main.parcel_serial.PagerActivity;
import com.example.king.fragement.main.picTest.ChoosePic;
import com.example.king.fragement.main.sensor.IBMEyes;
import com.example.king.fragement.main.webview.TestConvertHtml;
import com.example.king.fragement.main.wifi.WiFiDirectActivity;
import com.example.king.fragement.midea.ItemListActivity;
import com.example.king.fragement.midea.ItemListActivity1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kings on 2016/1/14.
 */
public class CheeseListFragment extends Fragment implements TransferListener {
	Slide explodeAnimation;
	int lastVisibleItem;
	RecyclerView rv;
	private List<Map<String, Class>> activities_list = BaseApplication.getActivities_list();
	private List<String> time = BaseApplication.getTime();
	private static final String mName = "CheeseList";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rv = (RecyclerView) inflater.inflate(R.layout.fragment_cheese_list, container, false);
		setupRecyclerView(rv);
		LogWrap.e("onCreateView" + mName);
		return rv;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrap.e("onCreate" + mName);
	}

	private void setupRecyclerView(RecyclerView recyclerView) {
		final SimpleStringRecyclerViewAdapter sa = new SimpleStringRecyclerViewAdapter
				(getActivity(), activities_list, time);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new ItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));
		recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == sa.getItemCount()) {

					Toast.makeText(getContext().getApplicationContext(), "There is no more data !", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void initData() {
		Map<String, Class> name_activities = new ConcurrentHashMap<>();
		name_activities.put("DialogPra", com.example.king.fragement.main.DialogPra.class);
		activities_list.add(name_activities);
		time.add("2016-1-15");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("QueryProcess", QueryProcess.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Download", com.example.king.fragement.MainActivity.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("AboutUs", AboutUs.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("CSDN_News", NewsFragement.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("News", Main.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Maps", MapsActivity.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Maps1", MapsActivity1.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Setting", SettingsActivity.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Jni_Test", JniTest.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("OSLogin", OsLogin.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("FileTest", FileTest.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Broadcast", BroadcastTest.class);
		activities_list.add(name_activities);
		time.add("2015-10-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("File_Search", SearchFile.class);
		activities_list.add(name_activities);
		time.add("2016-1-21");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("HightLight", HightLight.class);
		activities_list.add(name_activities);
		time.add("2016-1-22");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Contacts", com.example.king.fragement.main.contacts.MainActivity.class);
		activities_list.add(name_activities);
		time.add("2016-2-1");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("ShowLocation", TencentMaps.class);
		activities_list.add(name_activities);
		time.add("2016-2-3");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Parcelable & Serializable", PagerActivity.class);
		activities_list.add(name_activities);
		time.add("2016-2-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("WifiDirect", WiFiDirectActivity.class);
		activities_list.add(name_activities);
		time.add("2016-2-29");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Music", HomeActivity.class);
		activities_list.add(name_activities);
		time.add("2016-3-27");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("NFC", NFCard.class);
		activities_list.add(name_activities);
		time.add("2016-3-27");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("AidlServer", com.example.king.fragement.main.aidlserver.MainActivity.class);
		activities_list.add(name_activities);
		time.add("2016-4-5");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("AidlClient", Client.class);
		activities_list.add(name_activities);
		time.add("2016-4-5");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("FastJson & Volley", com.example.king.fragement.main.fastJson.MainActivity3.class);
		activities_list.add(name_activities);
		time.add("2016-4-9");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("ChoosePic", ChoosePic.class);
		activities_list.add(name_activities);
		time.add("2016-4-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Sensor", IBMEyes.class);
		activities_list.add(name_activities);
		time.add("2016-4-26");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("MyWork", PreviewActivity.class);
		activities_list.add(name_activities);
		time.add("2016-5-6");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("OpenGl", com.example.king.fragement.main.opengl.MainActivity.class);
		activities_list.add(name_activities);
		time.add("2016-5-10");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Camera", com.example.king.fragement.main.camera.CameraActivity.class);
		activities_list.add(name_activities);
		time.add("2016-6-4");
		name_activities = new ConcurrentHashMap<>();
		name_activities.put("Html2Bitmap", TestConvertHtml.class);
		activities_list.add(name_activities);
		time.add("2016-6-7");
	}

	private List<String> getRandomSublist(String[] array, int amount) {
		ArrayList<String> list = new ArrayList<>(amount);
		Random random = new Random();
		while (list.size() < amount) {
			list.add(array[random.nextInt(array.length)]);
		}
		return list;
	}

	@Override
	public void transferMsg() {
		rv.smoothScrollToPosition(0);
	}

	public class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {
		private final TypedValue mTypedValue = new TypedValue();
		private int mBackground;
		private List<Map<String, Class>> mValues;
		private List<String> mTimes;

		public class ViewHolder extends RecyclerView.ViewHolder {
			public Class mBoundString;

			public final View mView;
			public final ImageView mImageView;
			public final TextView mTextView;
			public final TextView time;


			public ViewHolder(View itemView) {
				super(itemView);
				mView = itemView;
				mImageView = (ImageView) itemView.findViewById(R.id.avatar);
				mTextView = (TextView) itemView.findViewById(android.R.id.text1);
				time = (TextView) itemView.findViewById(R.id.time);
			}

			@Override
			public String toString() {
				return super.toString() + " " + mTextView.getText();
			}
		}

		public Class getValueAt(int position) {
			return mValues.get(position).getClass();
		}

		public SimpleStringRecyclerViewAdapter(Context context, List<Map<String, Class>> items, List<String> time) {
			context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
			mBackground = mTypedValue.resourceId;
			mValues = items;
			mTimes = time;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.list_item3, parent, false);
			view.setBackgroundResource(mBackground);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(final ViewHolder holder, final int position) {
			int i = 0;
			Iterator it = mValues.get(position).entrySet().iterator();
			Map.Entry entry = (Map.Entry) it.next();
			holder.mBoundString = (Class) entry.getValue();
			holder.mTextView.setText(entry.getKey().toString());
			holder.time.setText(mTimes.get(position).toString());

			holder.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					/**
					 * 这里不能写getApplicationCOntext
					 * 报错 Calling startActivity() from outside of an Activity  context requires
					 * the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
					 * */
					Context context = v.getContext();
					Intent it = new Intent(context, holder.mBoundString);
					if (explodeAnimation == null)
						explodeAnimation = new Slide();
					explodeAnimation.setDuration(500);
					explodeAnimation.setSlideEdge(Gravity.RIGHT);
					getActivity().getWindow().setExitTransition(explodeAnimation);
					context.startActivity(it, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context).toBundle());
				}
			});
			/**
			 * 不要用random，因为我看过只有cheese1的大小最小，应该分辨率最低
			 * 其他的图片太大，所以不要使用，一个mimap的launcher只有3K左右
			 * 而cheese最小一张都80，最大400，所以opengl报错，out of memory
			 * 后期再查一下图片，找一些比较小的来
			 * */
			Glide.with(holder.mImageView.getContext().getApplicationContext())
					.load(Cheeses.getRandomCheeseDrawable())
					.fitCenter()
					.into(holder.mImageView);
		}

		@Override
		public int getItemCount() {
			return mValues.size();
		}
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
	}

	public void onDetach() {
		super.onDetach();
		LogWrap.e("onDetach" + mName);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
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
}
