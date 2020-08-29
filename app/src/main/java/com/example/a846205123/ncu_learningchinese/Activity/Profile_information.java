package com.example.a846205123.ncu_learningchinese.Activity;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.example.a846205123.ncu_learningchinese.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Profile_information extends PageView{
    private TextView UserEmail,UserName,UserId,VerifyEmail;
    private SoundPool soundPool;
    private Vibrator mVibrator;//宣告震動
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private String userEmail = user.getEmail();

    public Profile_information(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_profile, null);
        addView(view);
        FindView();
        SetProfile();
    }

    private void FindView(){
        UserEmail = findViewById(R.id.txtUserEmail);
        UserName = findViewById(R.id.txtName);
        UserId = findViewById(R.id.txtUserId);
        VerifyEmail = findViewById(R.id.txtVerifyEmail);
    }

    private void SetProfile(){
        //顯示使用者信箱
        UserEmail.setText(userEmail.toString());
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
    public void refresh() {

    }
}
