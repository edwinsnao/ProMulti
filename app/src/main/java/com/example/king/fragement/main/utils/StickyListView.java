package com.example.king.fragement.main.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Kings on 2016/4/26.
 */
public class StickyListView extends ListView{
    private int delY;
    private int preY;
    private boolean action_up;
    public StickyListView(Context context) {
        super(context);
    }

    public StickyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                preY = getScrollX();
                int  y = (int) ev.getY();
                delY = (int) (preY-y);
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                action_up = true;
                break;
        }
//        return false;
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        this.scrollBy(0,delY/2);
        if(action_up)
            this.scrollTo(0,0);
    }
    @Override
       public void onScrollChanged(int l, int t, int oldl, int oldt) {
              // TODO Auto-generated method stub
//              super.scrollBy(l, t);
//              super.scrollTo(((oldl - l) / 10 + l), ((oldt - t) / 10 + t));
              super.onScrollChanged(l, t, ((oldl - l) / 10 + l), ((oldt - t) / 10 + t));
          }

}
