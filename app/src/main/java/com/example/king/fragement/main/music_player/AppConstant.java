package com.example.king.fragement.main.music_player;

/**
 * Created by Kings on 2016/3/27.
 */
public class AppConstant {
    public final static int Play = 1;
    public final static int Pause = 2;
    public final static int Stop = 3;

    public interface PlayerMsg {
        int PLAYING_MSG = 4;
        int PLAY_MSG = 5;
        int PAUSE_MSG = 6;
        int CONTINUE_MSG = 7;
        int PROGRESS_CHANGE = 8;
        int PRIVIOUS_MSG = 9;
        int NEXT_MSG = 10;
        int STOP_MSG =11 ;
        int PLAY_MSG_DIRECT =12 ;
    }
}
