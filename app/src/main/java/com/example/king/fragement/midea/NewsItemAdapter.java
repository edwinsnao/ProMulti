package com.example.king.fragement.midea;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.king.fragement.R;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.BaseApplication;
import com.example.king.fragement.main.MainActivity1;
import com.example.king.fragement.main.customView.RecycleImageView;
import com.example.king.fragement.main.utils.TransitionHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class NewsItemAdapter extends BaseAdapter {
	private OnImgLongClickListener onImgLongClickListener;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<NewsItem> mDatas = new ArrayList<>();

	/**
	 * 使用了github开源的ImageLoad进行了数据加载
	 */
	private ImageLoader imageLoader = BaseApplication.getLoader();
	private DisplayImageOptions options;

	public NewsItemAdapter(Context context, List<NewsItem> datas) {
		mContext = context.getApplicationContext();
		this.mDatas = datas;
		mInflater = LayoutInflater.from(mContext);

		options = BaseApplication.getOptions();
	}

	public View getView1(Activity context, int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.news_item_yidong, parent, false);
			if (holder == null)
				holder = new ViewHolder();

			holder.mImg = (RecycleImageView) convertView.findViewById(R.id.id_newsImg);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return holder.mImg;
	}

	public View getView2(Activity context, int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.news_item_yidong, parent, false);
			if (holder == null)
				holder = new ViewHolder();

			holder.mTitle = (TextView) convertView.findViewById(R.id.id_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return holder.mTitle;
	}

	public static Pair<View, String>[] getPair(Activity context) {
		View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.news_item_yidong, null);
		ImageView img = (ImageView) view.findViewById(R.id.id_newsImg);
		Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(context, false, new Pair<>(img, "explode"));
		return pairs;
	}

	public void addAll(List<NewsItem> mDatas) {
		this.mDatas.addAll(mDatas);
	}


	public void refresh(List<NewsItem> mDatas) {
		if (this.mDatas != null)
			this.mDatas.clear();
		if (this.mDatas == null)
			this.mDatas = new ArrayList<>();
		this.mDatas.addAll(mDatas);
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
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

	public interface OnImgLongClickListener {
		public void onLongClick(String imgLink);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.news_item_yidong, parent, false);
			if (holder == null)
				holder = new ViewHolder();

			holder.mContent = (TextView) convertView.findViewById(R.id.id_content);
			holder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
			holder.mDate = (TextView) convertView.findViewById(R.id.id_date);
			holder.mImg = (RecycleImageView) convertView.findViewById(R.id.id_newsImg);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final NewsItem newsItem = mDatas.get(position);
		holder.mTitle.setText(newsItem.getTitle());
		if (Utils.getAppTheme(mContext) == R.style.AppBaseTheme_Night)
			holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.graybg));
		else
			holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.black2));
		holder.mContent.setText(newsItem.getContent());
		holder.mDate.setText(newsItem.getDate());
		if (newsItem.getImgLink() != null) {
			holder.mImg.setVisibility(View.VISIBLE);
			imageLoader.displayImage(newsItem.getImgLink(), holder.mImg, options);
		} else {
			holder.mImg.setVisibility(View.GONE);
		}

		holder.mImg.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				onImgLongClickListener.onLongClick(newsItem.getImgLink());
				return true;
			}

		});
		return convertView;
	}

	private final class ViewHolder {
		TextView mTitle;
		TextView mContent;
		RecycleImageView mImg;
		TextView mDate;
	}

	public void setOnImgLongClickListener(OnImgLongClickListener onImgLongClickListener) {
		this.onImgLongClickListener = onImgLongClickListener;
	}
}
