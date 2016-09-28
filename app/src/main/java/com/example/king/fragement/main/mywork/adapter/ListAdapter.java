package com.example.king.fragement.main.mywork.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.king.fragement.R;
import com.example.king.fragement.main.mywork.model.Activity1;
import com.example.king.fragement.main.mywork.model.ImageEntity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Created by Kings on 2016/5/5.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Activity1> mDatas;
    private List<ImageEntity> images;
    public static ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;



    public ListAdapter(Context context, List<Activity1> datas,List<ImageEntity> images){
        mContext = context.getApplicationContext();
        this.mDatas = datas;
        this.images = images;
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
    public ListAdapter(Context context, List<Activity1> datas){
        mContext = context.getApplicationContext();
        this.mDatas = datas;
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
    public ListAdapter(Context context){
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
    public void addAll(List<Activity1> mDatas)
    {
        this.mDatas.addAll(mDatas);
    }
    public void addAll1(List<ImageEntity> images)
    {
        this.images.addAll(images);
    }
    @Override
    public int getCount() {
//        return 8;
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
            convertView = mInflater.inflate(R.layout.cell_unit_list, parent,false);
            holder = new ViewHolder();

            holder.img_list = (ImageView) convertView.findViewById(R.id.img_list);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.dest = (TextView) convertView.findViewById(R.id.dest);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.type = (TextView) convertView.findViewById(R.id.txt0);
            holder.popularity = (TextView) convertView.findViewById(R.id.txt1);
            holder.join = (TextView) convertView.findViewById(R.id.txt2);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Activity1 image = mDatas.get(position);
        /*
        * gridview不显示是因为这个原因
        * */
//        if (image.getImageUrl() != null)
//        {
//            imageLoader.displayImage(image.getImageUrl(), holder.mImg, options,new SimpleImageLoadingListener());
        imageLoader.displayImage(image.getImages().get(0).getImage(), holder.img_list, options);
//        Glide.with(mContext).load(images.get(position).getImageUrl()).fitCenter().into(holder.img_list);
        /*
        * 该接口设计的初衷是详情的页面的数据展示
        * 所以会出现一个activity有多个图片（一般两张），所以我们只取第一张就好了，否则报错，
        * index 2 size 2
        * */
//        Glide.with(mContext).load(image.getImages().get(0).getImage()).fitCenter().into(holder.img_list);
//        if(image.getTitle()!=null)
        holder.title.setText(image.getTitle());
//        if(image.getAddress()!=null)
        holder.dest.setText(image.getAddress());
//        if(image.getStartTime()!=null)
        holder.time.setText(image.getStartTime());
        holder.type.setText(String.valueOf(image.getType()));
        holder.popularity.setText(String.valueOf(image.getPopularity()));
        holder.join.setText(String.valueOf(image.getJoinNumber()));
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
        ImageView img_list;
        TextView title;
        TextView dest;
        TextView time;
        TextView type;
        TextView popularity;
        TextView join;


    }
}
