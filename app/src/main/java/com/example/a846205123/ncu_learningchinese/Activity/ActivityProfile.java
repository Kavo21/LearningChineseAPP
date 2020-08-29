package com.example.a846205123.ncu_learningchinese.Activity;

import android.app.Service;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.example.a846205123.ncu_learningchinese.R;
import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener{
    TextView UserEmail,UserName,UserId,VerifyEmail,UIEmail;
    private Sound sound;
    private Vibrator mVibrator;//宣告震動
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private String userEmail = user.getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FindView();
        InitializeMethod();
        SetProfile();
    }

    private void FindView(){
        UserEmail = findViewById(R.id.txtUserEmail);
        UserName = findViewById(R.id.txtName);
        UserId = findViewById(R.id.txtUserId);
        VerifyEmail = findViewById(R.id.txtVerifyEmail);
        UIEmail = findViewById(R.id.email);
        findViewById(R.id.profile).setOnClickListener(this);
        findViewById(R.id.Refresh).setOnClickListener(this);
    }

    private void InitializeMethod(){
        sound = new Sound(this);
        mVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void SetProfile(){
        //顯示使用者信箱
        UserEmail.setText(userEmail.toString());
        UIEmail.setText(userEmail.toString());
        //把字元切割
        final  String[] userName = userEmail.split("@");
        //顯示切割後字元 ，用userName[0] 顯示@以前的字元
        UserName.setText(userName[0]);
        //顯示User Uid
        UserId.setText(user.getUid());
        //顯示帳號是否認證過
        VerifyEmail.setText(String.valueOf(user.isEmailVerified()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                mVibrator.vibrate(50);
                sound.SoundDeWay();

                break;
            case R.id.Refresh:

                break;
        }
    }
}
