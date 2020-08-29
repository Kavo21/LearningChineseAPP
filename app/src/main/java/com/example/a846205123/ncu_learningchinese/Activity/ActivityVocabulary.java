package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.a846205123.ncu_learningchinese.FirebaseModel.FirebaseWeightData;
import com.example.a846205123.ncu_learningchinese.Method.Cn2Py;
import com.example.a846205123.ncu_learningchinese.R;
import com.example.a846205123.ncu_learningchinese.Method.SelectItem;
import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.example.a846205123.ncu_learningchinese.Method.TextToSpeech_Method;
import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityVocabulary extends AppCompatActivity implements View.OnClickListener{
    private int index = 0;
    private String VocabularyTypeRef;
    private Sound sound;
    private TextView ChineseWord,EnglishWord,PinYin;
    private ImageView UploadImage;
    private TextToSpeech_Method textToSpeechMethod;
    private SelectItem selectItem = new SelectItem();
    private ArrayList <VocabularyData> CopyVocabularyList = new ArrayList<>(); //暫存從資料庫抓下來的List
    private StorageReference storageReference;
    private String ChinesePinYin_word;

    private FirebaseAuth auth = FirebaseAuth.getInstance();//取的Firebase帳戶實體
    private FirebaseUser user = auth.getCurrentUser();//取得使用者
    private String userEmail = user.getEmail();//取的信箱
    private String[] Email = userEmail.split("@");//切割字元
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference StudentsReference;//取得學生資料庫參考

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //設定螢幕不隨著手機選轉
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //設螢幕直向顯示
        FindView();
        SelectItemMethod();
        InitializeMethod();
        SetVocabulary(GetChineseWord(index),GetEnglishWord(index));
        ChangeImage(GetEnglishWord(index));
        ChinesePinYin_word = (Cn2Py.getFullPinYin(GetChineseWord(index).toString()));
    }

    //更新權重方法
    private void ReFreshData(int index,int RefreshNumber){
        StudentsReference = databaseReference.child("UserWord").child(Email[0]).child(VocabularyTypeRef).child(String.valueOf(index));
        Map<String, Object> UpdatesMap = new HashMap<>();
        UpdatesMap.put("Weight",RefreshNumber);
        StudentsReference.updateChildren(UpdatesMap);//updateChildren只更新節點，不會覆蓋整個資料
    }

    private void FindView() {
        ChineseWord =  findViewById(R.id.txtChineseWord);
        EnglishWord = findViewById(R.id.txtEnglishWord);
        UploadImage = findViewById(R.id.uploadImage);
        PinYin = findViewById(R.id.txtPin);
        findViewById(R.id.btnNext).setOnClickListener(this); //Set Btn as listener
        findViewById(R.id.btnPronounce).setOnClickListener(this);
        findViewById(R.id.btnSpeechRecognition).setOnClickListener(this);
    }

    private void InitializeMethod(){
        textToSpeechMethod = new TextToSpeech_Method(this);
        sound = new Sound(this);
    }

    private void SelectItemMethod(){
        if(selectItem.BoolAnimal() == true)
        {
            CopyVocabularyList = VocabularyData.List_Animal;
            VocabularyTypeRef="animal";
        }
        else if(selectItem.BoolFruit() ==true)
        {
            CopyVocabularyList = VocabularyData.List_Fruit;
            VocabularyTypeRef="fruit";
        }
        else if(selectItem.BoolGretting() ==true)
        {
            CopyVocabularyList = VocabularyData.List_Greeting;
            VocabularyTypeRef="greeting";
        }
        else if(selectItem.BoolPeople()==true){
            CopyVocabularyList = VocabularyData.List_People;
            VocabularyTypeRef="people";
        }
        else if(selectItem.BoolTransport() ==true)
        {
            CopyVocabularyList = VocabularyData.List_Transport;
            VocabularyTypeRef="transport";
        }
        else {
            return;
        }
    }

    private void SetVocabulary(String Chinese , String Enlish){
        ChineseWord.setText(Chinese);
        EnglishWord.setText(Enlish);
        PinYin.setText( ChinesePinYin_word = (Cn2Py.getFullPinYin(GetChineseWord(index).toString())));
    }

    //回傳中文單字
    private String GetChineseWord(int index){
        return CopyVocabularyList.get(index).ChineseWord;
    }

    private String GetEnglishWord(int index){
        return CopyVocabularyList.get(index).EnglishWord;
    }

    private void ChangeImage(String ImageName){
        String ImagePath = VocabularyTypeRef+"/"+ImageName+".jpg";
        Log.d("Imagepath",ImagePath);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ncu-learning-chinese.appspot.com/").child(ImagePath);//"animal/bird.jpg"
        Glide.with(this).us
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                if (index >= CopyVocabularyList.size()-1){
                    index = 0;
                    SetVocabulary(GetChineseWord(index),GetEnglishWord(index));
                    ChangeImage(GetEnglishWord(index));

                }else {
                    ++index;
                    SetVocabulary(GetChineseWord(index),GetEnglishWord(index));
                    ChangeImage(GetEnglishWord(index));
                }
                break;
            case R.id.btnPronounce:
                textToSpeechMethod.Speech(ChineseWord.getText().toString());
                break;
            case R.id.btnSpeechRecognition:
                //透過 Intent 的方式開啟內建的語音辨識 Activity...
                Intent intentSpeechRecognition = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intentSpeechRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intentSpeechRecognition.putExtra(RecognizerIntent.EXTRA_PROMPT, "請說話..."); //語音辨識 Dialog 上要顯示的提示文字
                startActivityForResult(intentSpeechRecognition, 1);
                break;
        }
    }

    @Override
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

    // 確認結果
    private void checkVocabulary(String resultVocabulary)
    {
        if(resultVocabulary.equals(ChineseWord.getText()))
        {
            sound.SoundPlayCorrect();
            //更新權重 設為預設值
            ReFreshData(index+1,10);
        }
        else
        {
            sound.SoundPlayFailed();
            //更新權重 設為超過
            ReFreshData(index+1,20);
        }
    }

    //秀出使用者所念的單字
    private void debugShow(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
