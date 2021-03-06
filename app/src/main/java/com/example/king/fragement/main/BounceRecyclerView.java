package com.example.king.fragement.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by Kings on 2016/1/24.
 */
public class BounceRecyclerView extends RecyclerView {
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;

    private Context mContext;
    private int mMaxYOverscrollDistance;

    public BounceRecyclerView(Context context){
        super(context);
        mContext = context;
        initBounceRecyclerView();
    }

    public BounceRecyclerView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        initBounceRecyclerView();
    }

    public BounceRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs,defStyle);
        mContext = context;
        initBounceRecyclerView();
    }

    private void initBounceRecyclerView(){
        //get the density of the screen and do some maths with it on the max overscroll distance
        //variable so that you get similar behaviors no matter what the screen size

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
        //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }
}
