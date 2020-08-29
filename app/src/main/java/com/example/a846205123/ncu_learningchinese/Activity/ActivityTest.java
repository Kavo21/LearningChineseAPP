package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.example.a846205123.ncu_learningchinese.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityTest extends AppCompatActivity implements OnClickListener{
    private FirebaseAuth auth = FirebaseAuth.getInstance();//取的Firebase帳戶實體
    private FirebaseUser user = auth.getCurrentUser();//取得使用者
    private String userEmail = user.getEmail();//取的信箱
    private String[] Email = userEmail.split("@");//切割字元
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference StudentsReference;

    private Button actor;
    private TextView VocabularyTest;
    private ImageView Fire,BigBomb,BlueBomb,RedBomb,Bomb;
    private ArrayList<VocabularyData> CopyVocabularyList = new ArrayList<>(); //暫存從資料庫抓下來的List
    private static int index =0;
    private Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sound = new Sound(this);
        FindView();
        CopyVocabularyList = VocabularyData.List_ProfileWord;
        CheckListIsNull();
    }

    private void FindView(){
        VocabularyTest =findViewById(R.id.txtVoacbulary);
        Bomb=findViewById(R.id.imgBomb);
        BigBomb=findViewById(R.id.imgBigBomb);
        BlueBomb =findViewById(R.id.imgBlueBomb);
        Fire = findViewById(R.id.imgFire);
        RedBomb = findViewById(R.id.imgRedBomb);
        actor =(Button)findViewById(R.id.btnNext);
        actor.setOnClickListener(this);
    }

    private void CheckListIsNull(){
        if (CopyVocabularyList ==null){
            Toast.makeText(this,"目前沒有錯誤的單字",Toast.LENGTH_LONG).show();
        }
        VocabularyTest.setText(GetChineseWord(index));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                //透過 Intent 的方式開啟內建的語音辨識 Activity...
                Intent intentSpeechRecognition = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentSpeechRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intentSpeechRecognition.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說話..."); //語音辨識 Dialog 上要顯示的提示文字
                startActivityForResult(intentSpeechRecognition, 1);
                break;
        }
    }

    //語音辨識回傳結果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //把所有辨識的可能結果印出來看一看，第一筆是最 match 的。
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                checkVocabulary(result.get(0));
                debugShow(result.get(0));
            }
        }
    }

    private void debugShow(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // 確認結果
    private void checkVocabulary(String resultVocabulary)
    {
        if(resultVocabulary.equals(VocabularyTest.getText()))
        {
            sound.SoundPlayCorrect();
            if (index >= CopyVocabularyList.size()-1){
                index = 0;
                VocabularyTest.setText(GetChineseWord(index));
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 500, 0, 500);
                translateAnimation.setDuration(2000);
                actor.setAnimation(translateAnimation);
                actor.startAnimation(translateAnimation);
                Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show();
            }else {
                ++index;
                VocabularyTest.setText(GetChineseWord(index));
            }
        }
        else
        {
            sound.SoundDeWay();
            sound.SoundExplosion();
            BlueBomb.setVisibility(View.VISIBLE);
            Bomb.setVisibility(View.VISIBLE);
            BigBomb.setVisibility(View.VISIBLE);
            RedBomb.setVisibility(View.VISIBLE);
            Fire.setVisibility(View.VISIBLE);
        }
    }
    //回傳中文單字
    private String GetChineseWord(int index){
        return CopyVocabularyList.get(index).ChineseWord;
    }



    public  boolean onTouchEvent(MotionEvent e){//當觸碰到螢幕

        return true;
    }

    private void ReFreshData(String Type , int index,int RefreshNumber){
        StudentsReference = databaseReference.child("UserWord").child(Email[0]).child(Type).child(String.valueOf(index));
        Map<String, Object> UpdatesMap = new HashMap<>();
        UpdatesMap.put("Test",RefreshNumber);
        StudentsReference.updateChildren(UpdatesMap);
    }
}
