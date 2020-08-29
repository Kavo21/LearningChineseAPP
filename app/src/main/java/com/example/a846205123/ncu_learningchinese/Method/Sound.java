package com.example.a846205123.ncu_learningchinese.Method;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.a846205123.ncu_learningchinese.R;

/**
 * Created by a846205123 on 2018/4/8.
 */

public class Sound {
    private SoundPool soundPool;
    private int CorrectSound;
    private int FailedSound;
    private int DeWay;
    private int Explosion;

    public  Sound(Context context){
        //SoundPool 建構值
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,10);
        //載入音效 在raw資料夾裡面
        CorrectSound = soundPool.load(context, R.raw.correct_sound,1);
        FailedSound = soundPool.load(context,R.raw.fault_sound,1);
        DeWay = soundPool.load(context,R.raw.deway,1);
        Explosion=soundPool.load(context,R.raw.hard_attack,1);
    }

    public void SoundPlayCorrect() {
        soundPool.play(CorrectSound,1,1,1,1,1);
    }
    public void SoundPlayFailed(){
        soundPool.play(FailedSound,1,1,1,1,1);
    }

    public void SoundDeWay(){
        soundPool.play(DeWay,1,1,1,1,1);
    }
    public void SoundExplosion(){
        soundPool.play(Explosion,1,1,1,1,1);
    }

}
