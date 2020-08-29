package com.example.a846205123.ncu_learningchinese.Method;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.a846205123.ncu_learningchinese.R;

/**
 * Created by a846205123 on 2018/4/13.
 */

public class Music {
    public MediaPlayer musicPlayer;

    public Music(Context context){
        musicPlayer = MediaPlayer.create(context, R.raw.mainmusic);//撥放音樂，第二參數是音樂來源
    }

    public void MusicPlay(){
        musicPlayer.start();
    }

    public void MusicStop(){
        musicPlayer.stop();
    }

    public void MusicDestory(){
        musicPlayer.release();
    }
}
