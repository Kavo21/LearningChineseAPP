package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.Context;
import android.icu.text.MessagePattern;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.example.a846205123.ncu_learningchinese.Method.TextToSpeech_Method;
import com.example.a846205123.ncu_learningchinese.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.a846205123.ncu_learningchinese.Data.VocabularyData.List_ProfileWord;

public class Profile_learn extends PageView implements View.OnClickListener{

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public  ArrayList<String> Profile_Words = new ArrayList<>();

    private ArrayList <VocabularyData> CopyVocabularyList = new ArrayList<>(); //暫存從資料庫抓下來的List

    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private View.OnClickListener clickListener;
    private TextToSpeech_Method textToSpeechMethod;

    public Profile_learn(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_learn, null);
        addView(view);

        textToSpeechMethod = new TextToSpeech_Method(context);

        CopyVocabularyList = List_ProfileWord;
        for (int i = 0; i < List_ProfileWord.size(); i++) {
            String Word = List_ProfileWord.get(i).ChineseWord +"    "+ List_ProfileWord.get(i).EnglishWord;
            Profile_Words.add(Word);

        }
        listView = findViewById(R.id.listView);
        listAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,Profile_Words);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textToSpeechMethod.Speech(List_ProfileWord.get(position).ChineseWord);
                Log.d("TAG0", List_ProfileWord.get(position).ChineseWord);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (clickListener != null) {
            clickListener.onClick(this);
        }
    }

    @Override
    public void refresh() {

    }
}
