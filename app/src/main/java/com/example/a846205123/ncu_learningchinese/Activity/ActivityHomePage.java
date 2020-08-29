package com.example.a846205123.ncu_learningchinese.Activity;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.example.a846205123.ncu_learningchinese.FirebaseModel.FirebaseDownloadData;
import com.example.a846205123.ncu_learningchinese.FirebaseModel.FirebaseProfileData;

import com.example.a846205123.ncu_learningchinese.Method.Music;
import com.example.a846205123.ncu_learningchinese.R;
import com.example.a846205123.ncu_learningchinese.Method.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityHomePage extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private Student student =new Student(user.getEmail());
    private Music music = null;
    private Vibrator mVibrator;//宣告震動
    private FirebaseDownloadData firebaseDownloadData = new FirebaseDownloadData();//從資料庫下載資料，直接實體化就會執行
    private FirebaseProfileData firebaseProfileData = new FirebaseProfileData();//將學生單字列表權重高的，篩選出來，直接實體化就會執行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //設定螢幕不隨著手機選轉
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //設螢幕直向顯示
        FindView();
        InitializeMethod();
        Log.d("onCreate",student.getEmail());
        Toast.makeText(this, student.getEmail(), Toast.LENGTH_LONG).show();
    }

    private  void FindView(){
        findViewById(R.id.BtnProfile).setOnClickListener(this);
        findViewById(R.id.BtnPractice).setOnClickListener(this);
        findViewById(R.id.BtnTest).setOnClickListener(this);
        findViewById(R.id.BtnCreatWord).setOnClickListener(this);
        findViewById(R.id.BtnLoginout).setOnClickListener(this);
    }

    private void InitializeMethod(){
        music = new Music(this);
        mVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BtnProfile:
                mVibrator.vibrate(50);
                startActivity(new Intent(this,ActivityProfileView.class));
                break;
            case R.id.BtnPractice:
                mVibrator.vibrate(50);
                startActivity(new Intent(this,ActivityWordType.class));
                break;
            case R.id.BtnTest:
                //TODO
                //VocabularyData vocabularyData = new VocabularyData();
                //vocabularyData.RefreshStudentsData(VocabularyData.List_Animal,"animal");
                //vocabularyData.RefreshStudentsData(VocabularyData.List_Fruit,"fruit");
                //vocabularyData.RefreshStudentsData(VocabularyData.List_People,"people");
                //vocabularyData.RefreshStudentsData(VocabularyData.List_Transport,"transport");
                //vocabularyData.RefreshStudentsData(VocabularyData.List_Greeting,"greeting");
                mVibrator.vibrate(50);
                startActivity(new Intent(this,ActivityTest.class));
                break;
            case R.id.BtnCreatWord:
                mVibrator.vibrate(50);
                startActivity(new Intent(this,ActivityCreateWord.class));
                break;
            case R.id.BtnLoginout:
                mVibrator.vibrate(50);
                auth.signOut();
                startActivity(new Intent(this,ActivityLogin.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Notify")
                    .setMessage("Do you want to exit?")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        return  true;
    }
}
