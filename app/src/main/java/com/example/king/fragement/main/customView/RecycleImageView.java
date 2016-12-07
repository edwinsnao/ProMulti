package com.example.king.fragement.main.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.king.fragement.main.BaseApplication;

public class RecycleImageView extends ImageView {
	public RecycleImageView(Context context) {
		super(context);
	}

	public RecycleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RecycleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		BaseApplication.getLoader().cancelDisplayTask(this);
		setImageBitmap(null);
		setImageDrawable(null);
		setImageURI(null);
	}
}