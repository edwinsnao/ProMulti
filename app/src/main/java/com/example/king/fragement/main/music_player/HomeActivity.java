package com.example.king.fragement.main.music_player;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.king.fragement.R;
import com.example.king.fragement.Utils;
import com.example.king.fragement.main.BaseActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kings on 2016/3/27.
 */
public class HomeActivity extends BaseActivity {
    private ListView mMusiclist; // 音乐列表
    private List<Mp3Info> mp3Infos = null;
    private SimpleAdapter mAdapter; // 简单适配器
    private Button previousBtn; // 上一首
    private Button repeatBtn; // 重复（单曲循环、全部循环）
    private Button playBtn; // 播放（播放、暂停）
    private Button shuffleBtn; // 随机播放
    private Button nextBtn; // 下一首
    private TextView musicTitle;//歌曲标题
    private TextView musicArtist;//歌曲标题
    private TextView musicDuration; //歌曲时间
    private Button musicPlaying;    //歌曲专辑

    private int repeatState;        //循环标识
    private final int isCurrentRepeat = 1; // 单曲循环
    private final int isAllRepeat = 2; // 全部循环
    private final int isNoneRepeat = 3; // 无重复播放
    private boolean isFirstTime = true;
    private boolean isPlaying; // 正在播放
    private boolean isPause; // 暂停
    private boolean isNoneShuffle = true; // 顺序播放
    private boolean isShuffle = false; // 随机播放


    private int listPosition = 0;   //标识列表位置
    private HomeReceiver homeReceiver;  //自定义的广播接收器
    //一系列动作
    public static final String UPDATE_ACTION = "com.wwj.action.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.wwj.action.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.wwj.action.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.wwj.action.MUSIC_DURATION";
    public static final String REPEAT_ACTION = "com.wwj.action.REPEAT_ACTION";
    public static final String SHUFFLE_ACTION = "com.wwj.action.SHUFFLE_ACTION";


    private int currentTime;
    private int duration;
    private int theme  = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        if (savedInstanceState == null) {
            theme = Utils.getAppTheme(this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
// wrong
        setSupportActionBar(bar);
        /*
        * 标题栏解释
        * */
        bar.setTitle("Music");
        /*
        * 左上方返回到主界面
        * */

        mMusiclist = (ListView) findViewById(R.id.music_list);
        mMusiclist.setOnItemClickListener(new MusicListItemClickListener());
        mMusiclist.setOnCreateContextMenuListener(new MusicListItemContextMenuListener());
        mp3Infos = MediaUtil.getMp3Infos(getApplicationContext());  //获取歌曲对象集合
        setListAdpter(MediaUtil.getMusicMaps(mp3Infos));    //显示歌曲列表
        findViewById();             //找到界面上的每一个控件
        setViewOnclickListener();   //为一些控件设置监听器
        repeatState = isNoneRepeat; // 初始状态为无重复播放状态


        homeReceiver = new HomeReceiver();
        // 创建IntentFilter
        IntentFilter filter = new IntentFilter();
        // 指定BroadcastReceiver监听的Action
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        filter.addAction(REPEAT_ACTION);
        filter.addAction(SHUFFLE_ACTION);
        // 注册BroadcastReceiver
        registerReceiver(homeReceiver, filter);


    }

    /**
     * 从界面上根据id获取按钮
     */
    private void findViewById() {
//        previousBtn = (Button) findViewById(R.id.previous_music);
//        repeatBtn = (Button) findViewById(R.id.repeat_music);
        playBtn = (Button) findViewById(R.id.play_music);
//        shuffleBtn = (Button) findViewById(R.id.shuffle_music);
//        nextBtn = (Button) findViewById(R.id.next_music);
        musicTitle = (TextView) findViewById(R.id.text1);
        musicArtist = (TextView) findViewById(R.id.text2);
        musicDuration = (TextView) findViewById(R.id.duration);
//        musicPlaying = (Button) findViewById(R.id.playing);
    }

    /**
     * 给每一个按钮设置监听器
     */
    private void setViewOnclickListener() {
        ViewOnClickListener viewOnClickListener = new ViewOnClickListener();
//        previousBtn.setOnClickListener(viewOnClickListener);
//        repeatBtn.setOnClickListener(viewOnClickListener);
        playBtn.setOnClickListener(viewOnClickListener);
//        shuffleBtn.setOnClickListener(viewOnClickListener);
//        nextBtn.setOnClickListener(viewOnClickListener);
//        musicPlaying.setOnClickListener(viewOnClickListener);
    }

    private class ViewOnClickListener implements View.OnClickListener {
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.previous_music: // 上一首
////                    playBtn.setBackgroundResource(R.drawable.play_selector);
//                    isFirstTime = false;
//                    isPlaying = true;
//                    isPause = false;
//                    previous();
//                    break;
//                case R.id.repeat_music: // 重复播放
//                    if (repeatState == isNoneRepeat) {
//                        repeat_one();
//                        shuffleBtn.setClickable(false);
//                        repeatState = isCurrentRepeat;
//                    } else if (repeatState == isCurrentRepeat) {
//                        repeat_all();
//                        shuffleBtn.setClickable(false);
//                        repeatState = isAllRepeat;
//                    } else if (repeatState == isAllRepeat) {
//                        repeat_none();
//                        shuffleBtn.setClickable(true);
//                        repeatState = isNoneRepeat;
//                    }
//                    switch (repeatState) {
//                        case isCurrentRepeat: // 单曲循环
////                            repeatBtn
////                                    .setBackgroundResource(R.drawable.repeat_current_selector);
//                            Toast.makeText(HomeActivity.this, R.string.repeat_current,
//                                    Toast.LENGTH_SHORT).show();
//                            break;
//                        case isAllRepeat: // 全部循环
////                            repeatBtn
////                                    .setBackgroundResource(R.drawable.repeat_all_selector);
//                            Toast.makeText(HomeActivity.this, R.string.repeat_all,
//                                    Toast.LENGTH_SHORT).show();
//                            break;
//                        case isNoneRepeat: // 无重复
////                            repeatBtn
////                                    .setBackgroundResource(R.drawable.repeat_none_selector);
//                            Toast.makeText(HomeActivity.this, R.string.repeat_none,
//                                    Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//
//                    break;
                case R.id.play_music: // 播放音乐
                    if(isFirstTime) {
                        play();
                        isFirstTime = false;
                        isPlaying = true;
                        isPause = false;
                    } else {
                        if (isPlaying) {
//                            playBtn.setBackgroundResource(R.drawable.pause_selector);
//                            intent.setAction("com.wwj.media.MUSIC_SERVICE");
                            intent.setClass(HomeActivity.this,PlayerService.class);
                            intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
                            startService(intent);
                            isPlaying = false;
                            isPause = true;
                            playBtn.setText("pause");
                        } else if (isPause) {
//                            playBtn.setBackgroundResource(R.drawable.play_selector);
//                            intent.setAction("com.wwj.media.MUSIC_SERVICE");
                            intent.setClass(HomeActivity.this,PlayerService.class);
                            intent.putExtra("MSG", AppConstant.PlayerMsg.CONTINUE_MSG);
                            startService(intent);
                            isPause = false;
                            isPlaying = true;
                            playBtn.setText("play");
                        }
                    }
                    break;
//                case R.id.shuffle_music: // 随机播放
//                    if (isNoneShuffle) {
////                        shuffleBtn
////                                .setBackgroundResource(R.drawable.shuffle_selector);
//                        Toast.makeText(HomeActivity.this, R.string.shuffle,
//                                Toast.LENGTH_SHORT).show();
//                        isNoneShuffle = false;
//                        isShuffle = true;
//                        shuffleMusic();
//                        repeatBtn.setClickable(false);
//                    } else if (isShuffle) {
////                        shuffleBtn
////                                .setBackgroundResource(R.drawable.shuffle_none_selector);
//                        Toast.makeText(HomeActivity.this, R.string.shuffle_none,
//                                Toast.LENGTH_SHORT).show();
//                        isShuffle = false;
//                        isNoneShuffle = true;
//                        repeatBtn.setClickable(true);
//                    }
//                    break;
//                case R.id.next_music: // 下一首
////                    playBtn.setBackgroundResource(R.drawable.play_selector);
//                    isFirstTime = false;
//                    isPlaying = true;
//                    isPause = false;
//                    next();
//                    break;
//                case R.id.playing:  //正在播放
//                    Mp3Info mp3Info = mp3Infos.get(listPosition);
//                    Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
//                    intent.putExtra("title", mp3Info.getTitle());
//                    intent.putExtra("url", mp3Info.getUrl());
//                    intent.putExtra("artist", mp3Info.getArtist());
//                    intent.putExtra("listPosition", listPosition);
//                    intent.putExtra("currentTime", currentTime);
//                    intent.putExtra("duration", duration);
//                    intent.putExtra("MSG", AppConstant.PlayerMsg.PLAYING_MSG);
//                    startActivity(intent);
//                    break;
            }
        }
    }

    private class MusicListItemClickListener implements AdapterView.OnItemClickListener {
        /**
         * 点击列表播放音乐
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            listPosition = position;
            playMusic(listPosition);
        }

    }

    public class MusicListItemContextMenuListener implements View.OnCreateContextMenuListener {

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(50);       //长按振动
            musicListItemDialog();      //长按后弹出的对话框
        }

    }

    /**
     * 填充列表
     *
//     * @param mp3Infos
     */
    public void setListAdpter(List<HashMap<String, String>> mp3list) {
        mAdapter = new SimpleAdapter(this, mp3list,
                R.layout.music_player_list, new String[] { "title",
                "Artist", "duration" }, new int[] { R.id.title,
                R.id.artist, R.id.duration });
        mMusiclist.setAdapter(mAdapter);
    }


    /**
     * 下一首歌曲
     */
    public void next() {
        listPosition = listPosition + 1;
        if(listPosition <= mp3Infos.size() - 1) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            musicTitle.setText(mp3Info.getTitle());
            musicArtist.setText(mp3Info.getArtist());
            Intent intent = new Intent();
//            intent.setAction("com.wwj.media.MUSIC_SERVICE");
            intent.setClass(HomeActivity.this,PlayerService.class);
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
            startService(intent);
        } else {
            Toast.makeText(HomeActivity.this, "没有下一首了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 上一首歌曲
     */
    public void previous() {
        listPosition = listPosition - 1;
        if(listPosition >= 0) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            musicTitle.setText(mp3Info.getTitle());
            musicArtist.setText(mp3Info.getArtist());
            Intent intent = new Intent();
//            intent.setAction("com.wwj.media.MUSIC_SERVICE");
            intent.setClass(HomeActivity.this,PlayerService.class);
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
            startService(intent);
        }else {
            Toast.makeText(HomeActivity.this, "没有上一首了", Toast.LENGTH_SHORT).show();
        }
    }

    public void play() {
//        playBtn.setBackgroundResource(R.drawable.play_selector);
        Mp3Info mp3Info = mp3Infos.get(listPosition);
        musicTitle.setText(mp3Info.getTitle());
        musicArtist.setText(mp3Info.getArtist());
        Intent intent = new Intent();
//        intent.setAction("com.wwj.media.MUSIC_SERVICE");
        intent.setClass(HomeActivity.this,PlayerService.class);
//        intent.putExtra("listPosition", 0);
        intent.putExtra("listPosition", listPosition);
        intent.putExtra("url", mp3Info.getUrl());
        intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
        startService(intent);
    }

    /**
     * 单曲循环
     */
    public void repeat_one() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 1);
        sendBroadcast(intent);
    }

    /**
     * 全部循环
     */
    public void repeat_all() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 2);
        sendBroadcast(intent);
    }

    /**
     * 顺序播放
     */
    public void repeat_none() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 3);
        sendBroadcast(intent);
    }

    /**
     * 随机播放
     */
    public void shuffleMusic() {
        Intent intent = new Intent(CTL_ACTION);
        intent.putExtra("control", 4);
        sendBroadcast(intent);
    }

    public void musicListItemDialog() {
        String[] menuItems = new String[]{"播放音乐","设为铃声","查看详情"};
        ListView menuList = new ListView(HomeActivity.this);
        menuList.setCacheColorHint(Color.TRANSPARENT);
        menuList.setDividerHeight(1);
//        menuList.setAdapter(new ArrayAdapter<String>(HomeActivity.this, R.layout.context_dialog_layout, R.id.dialogText, menuItems));
//        menuList.setLayoutParams(new ViewGroup.LayoutParams(ConstantUtil.getScreen(HomeActivity.this)[0] / 2, LayoutParams.WRAP_CONTENT));


//        final CustomDialog customDialog = new CustomDialog.Builder(HomeActivity.this).setTitle(R.string.operation).setView(menuList).create();
//        customDialog.show();

        menuList.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                customDialog.cancel();
//                customDialog.dismiss();
            }

        });
    }
    public void playMusic(int listPosition) {
        if (mp3Infos != null) {
            Mp3Info mp3Info = mp3Infos.get(listPosition);
            musicTitle.setText(mp3Info.getTitle());
            musicArtist.setText(mp3Info.getArtist());
            Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
//            Intent intent = new Intent(HomeActivity.this, PlayerService.class);
            intent.putExtra("title", mp3Info.getTitle());
            intent.putExtra("url", mp3Info.getUrl());
            intent.putExtra("artist", mp3Info.getArtist());
            intent.putExtra("listPosition", listPosition);
            intent.putExtra("currentTime", currentTime);
            intent.putExtra("repeatState", repeatState);
            intent.putExtra("shuffleState", isShuffle);
            intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG_DIRECT);
            startActivity(intent);
//            startService(intent);
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
//        unregisterReceiver(homeReceiver);
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        if(homeReceiver!=null)
//        unregisterReceiver(homeReceiver);
    }

    /**
     * 按返回键弹出对话框确定退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("退出")
                    .setMessage("您确定要退出？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                    Intent intent = new Intent(
                                            HomeActivity.this,
                                            PlayerService.class);
                                    unregisterReceiver(homeReceiver);
                                    stopService(intent); // 停止后台服务
                                }
                            }).show();

        }
        return super.onKeyDown(keyCode, event);
    }


    //自定义的BroadcastReceiver，负责监听从Service传回来的广播
    public class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(MUSIC_CURRENT)){
                //currentTime代表当前播放的时间
                currentTime = intent.getIntExtra("currentTime", -1);
                musicDuration.setText(MediaUtil.formatTime(currentTime));
            } else if (action.equals(MUSIC_DURATION)) {
                duration = intent.getIntExtra("duration", -1);
            }
            else if(action.equals(UPDATE_ACTION)) {
                //获取Intent中的current消息，current代表当前正在播放的歌曲
                listPosition = intent.getIntExtra("current", -1);
                if(listPosition >= 0) {
                    musicTitle.setText(mp3Infos.get(listPosition).getTitle());
                    musicArtist.setText(mp3Infos.get(listPosition).getArtist());
                }
            }else if(action.equals(REPEAT_ACTION)) {
                repeatState = intent.getIntExtra("repeatState", -1);
                switch (repeatState) {
                    case isCurrentRepeat: // 单曲循环
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_current_selector);
                        shuffleBtn.setClickable(false);
                        break;
                    case isAllRepeat: // 全部循环
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_all_selector);
                        shuffleBtn.setClickable(false);
                        break;
                    case isNoneRepeat: // 无重复
//                        repeatBtn
//                                .setBackgroundResource(R.drawable.repeat_none_selector);
                        shuffleBtn.setClickable(true);
                        break;
                }
            }
            else if(action.equals(SHUFFLE_ACTION)) {
                isShuffle = intent.getBooleanExtra("shuffleState", false);
                if(isShuffle) {
                    isNoneShuffle = false;
//                    shuffleBtn.setBackgroundResource(R.drawable.shuffle_selector);
                    repeatBtn.setClickable(false);
                } else {
                    isNoneShuffle = true;
//                    shuffleBtn.setBackgroundResource(R.drawable.shuffle_none_selector);
                    repeatBtn.setClickable(true);
                }
            }
        }

    }
}
