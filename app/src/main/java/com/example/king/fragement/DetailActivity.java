package com.example.king.fragement;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import util.NetUtil;
/*
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

import jerome.news.util.NetUtil;
import jerome.news.util.TtsUtil;*/

public class DetailActivity extends AppCompatActivity {

	TextView mTitle = null;
	TextView mWhere = null;
	static TextView mContent = null;
	ImageView mImage = null;
	Button mSpeak = null;
	static private String contentStr="";
	private String urlStr="";
	private boolean isSpeak = false;
	
	static ScrollView mScrollView = null;
	static Button reload = null;
	static TextView loadTip = null;
	static LinearLayout detailLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		AppBarLayout bar = (AppBarLayout) findViewById(R.id.app_bar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		toolbar.setTitle(getTitle());
//		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
//		actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

		init();
		
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			final String[] values = bundle.getStringArray("data");
			mTitle.setText(values[0]);
			mWhere.setText(values[1] + " " + values[2]);
			mContent.setText(values[4]);
			urlStr = values[3];
			if ("".equals(values[4].trim()) && !"".equals(values[3])) {
				reloadData();
			} else {
				contentStr = values[4];
				mScrollView.setVisibility(View.VISIBLE);
				detailLayout.setVisibility(View.GONE);
			}
		}
	}
	
	private void reloadData() {
		new Thread() {
			public void run() {
				Message msg = new Message();
				msg.what = 0;
				msg.obj = NetUtil.getNewsContentByUrl(urlStr);
				mHandler.sendMessage(msg);
			}
		}.start();
	}

	final static Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				String textString = (String) msg.obj;
				if (null != textString && !"".equals(textString)) {
					contentStr = textString;
					mContent.setText(textString);
					mScrollView.setVisibility(View.VISIBLE);
					detailLayout.setVisibility(View.GONE);
				} else {
					mScrollView.setVisibility(View.GONE);
					detailLayout.setVisibility(View.VISIBLE);
					loadTip.setText("fail get news content data");
					reload.setVisibility(View.VISIBLE);
				}
				break;
			}
		}
	};
	
	private void init(){
		mTitle = (TextView) findViewById(R.id.detail_title);
		mWhere = (TextView) findViewById(R.id.detail_where_time);
		mContent = (TextView) findViewById(R.id.detail_content);
		/*mSpeak = (Button) findViewById(R.id.detail_speak);
		mSpeak.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TtsUtil.getInstance(DetailActivity.this).stopSpeak();
				speak();
			}

		});*/

		loadTip = (TextView) findViewById(R.id.detail_loading);
		mScrollView = (ScrollView) findViewById(R.id.main_scroll);
		reload = (Button) findViewById(R.id.detail_reload);
		detailLayout = (LinearLayout) findViewById(R.id.detail_load_layout);
		reload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mScrollView.setVisibility(View.GONE);
				detailLayout.setVisibility(View.VISIBLE);
				loadTip.setText("loading...");
				reload.setVisibility(View.GONE);
				reloadData();
			}

		});
	}

	/*@Override
	protected void onDestroy() {
		if (isSpeak) {
			TtsUtil.getInstance(this).stopSpeak();
		}
		super.onDestroy();
	}*/

	/*private void speak() {
		isSpeak = true;
		int code = TtsUtil.getInstance(this).startSpeak(contentStr, mTtsListener);
		if (code != ErrorCode.SUCCESS) {
			if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
				// δ��װ����ת����ʾ��װҳ��
				Toast.makeText(DetailActivity.this, getString(R.string.tip_speak_not_installed), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(DetailActivity.this, getString(R.string.tip_speak_error), Toast.LENGTH_SHORT).show();
			}
		}
	}*/

	/**
	 * �ϳɻص�����
	 */
	/*private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			
		}

		@Override
		public void onSpeakPaused() {
			
		}

		@Override
		public void onSpeakResumed() {
			
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			
		}

		@Override
		public void onCompleted(SpeechError error) {
			
		}
	};*/
}
