package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.a846205123.ncu_learningchinese.Method.Music;
import com.example.a846205123.ncu_learningchinese.R;
import com.example.a846205123.ncu_learningchinese.Method.SelectItem;

public class ActivityWordType extends AppCompatActivity implements View.OnClickListener{
    private Music music = null;
    private SelectItem selectItem = new SelectItem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordtype);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //設定螢幕不隨著手機選轉
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //設螢幕直向顯示
        FindView();
        InitializeMethod();
    }

    private void InitializeMethod(){
        music = new Music(this);
    }

    private void InitializeValue(){
        selectItem.setBoolAnimal(false);
        selectItem.setBoolFruit(false);
        selectItem.setBoolGretting(false);
        selectItem.setBoolPeople(false);
        selectItem.setBoolTransport(false);
    }

    private void FindView(){
        findViewById(R.id.BtnAnimal).setOnClickListener(this);
        findViewById(R.id.BtnFruit).setOnClickListener(this);
        findViewById(R.id.BtnGretting).setOnClickListener(this);
        findViewById(R.id.BtnPeople).setOnClickListener(this);
        findViewById(R.id.BtnTransport).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BtnAnimal:
                selectItem.setBoolAnimal(true);
                startActivity(new Intent(this,ActivityVocabulary.class));
                break;
            case R.id.BtnFruit:
                selectItem.setBoolFruit(true);
                startActivity(new Intent(this,ActivityVocabulary.class));
                break;
            case R.id.BtnGretting:
                selectItem.setBoolGretting(true);
                startActivity(new Intent(this,ActivityVocabulary.class));
                break;
            case R.id.BtnPeople:
                selectItem.setBoolPeople(true);
                startActivity(new Intent(this,ActivityVocabulary.class));
                break;
            case R.id.BtnTransport:
                selectItem.setBoolTransport(true);
                startActivity(new Intent(this,ActivityVocabulary.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        InitializeValue();
        music.MusicPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        music.MusicStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        music.MusicDestory();
    }
}

