package com.example.king.fragement.main.mywork.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.model.ImagesEntity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kings on 2016/5/4.
 */
public class PicAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ImagesEntity> mDatas = new ArrayList<>();
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;



    public PicAdapter(Context context, List<ImagesEntity> datas){
        mContext = context.getApplicationContext();
        this.mDatas = datas;
//		mInflater = LayoutInflater.from(context);
        mInflater = LayoutInflater.from(mContext);

//		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//	             .enableLogging() // Not necessary in common
                .build();

        ImageLoader.getInstance().init(config);
		/*
		* 由于FIFO不符合需求，应该LIFO才对，因为快速滚动的时候应该快点看到下面的图片
		* */
//		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_menu)
                .showImageForEmptyUri(R.drawable.ic_menu).showImageOnFail(R.drawable.ic_done)
				/*
				* 不要cacheinMemory防止oom
				* */
//				.cacheInMemory()
                .cacheOnDisc()
				/*
				* 速度比默认的快2倍
				* */
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//				.displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }
    public PicAdapter(Context context){
        mContext = context.getApplicationContext();
//		mInflater = LayoutInflater.from(context);
        mInflater = LayoutInflater.from(mContext);

//		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//	             .enableLogging() // Not necessary in common
                .build();

        ImageLoader.getInstance().init(config);
		/*
		* 由于FIFO不符合需求，应该LIFO才对，因为快速滚动的时候应该快点看到下面的图片
		* */
//		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_menu)
                .showImageForEmptyUri(R.drawable.ic_menu).showImageOnFail(R.drawable.ic_done)
				/*
				* 不要cacheinMemory防止oom
				* */
//				.cacheInMemory()
                .cacheOnDisc()
				/*
				* 速度比默认的快2倍
				* */
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//				.displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }
    public void add(ImagesEntity data)
    {
//        this.mDatas = mDatas;
        this.mDatas.add(data);
    }
    public void addAll(List<ImagesEntity> mDatas)
    {
//        this.mDatas.clear();
//        this.mDatas = null;
//        this.mDatas = new ArrayList<>();
//        this.mDatas = mDatas;
        this.mDatas.addAll(mDatas);
    }
    public void removeAll()
    {
        mDatas.clear();
//        mDatas.removeAll(mDatas);
//        this.mDatas.addAll(mDatas);
    }
    @Override
    public int getCount() {

        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.cell_unit, parent,false);
            holder = new ViewHolder();

            holder.mImg = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        ImagesEntity image = mDatas.get(position);
        /*
        * gridview不显示是因为这个原因
        * */
//        if (image.getImageUrl() != null)
//        {
            holder.mImg.setVisibility(View.VISIBLE);
//            imageLoader.displayImage(image.getImageUrl(), holder.mImg, options,new SimpleImageLoadingListener());
            imageLoader.displayImage(image.getImageUrl(), holder.mImg, options);
//        Glide.with(mContext).load(image.getImageUrl()).fitCenter().into(holder.mImg);
//            imageLoader.displayImage(image.getImageUrl(), holder.mImg);
//            imageLoader.displayImage("drawable://"+R.drawable.girl1, holder.mImg, options);
//        }
//        else
//        {
//            holder.mImg.setVisibility(View.GONE);
//        }

        return convertView;
    }

    private final class ViewHolder
    {
        ImageView mImg;
    }
}
