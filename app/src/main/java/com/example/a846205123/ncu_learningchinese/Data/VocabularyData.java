package com.example.a846205123.ncu_learningchinese.Data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VocabularyData {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference UserWord,RefreshStudentsDataRef;
    public String ChineseWord;
    public String EnglishWord;
    public static ArrayList<VocabularyData> List_Animal = new ArrayList<>();
    public static ArrayList<VocabularyData> List_Fruit = new ArrayList<>();
    public static ArrayList<VocabularyData> List_Greeting = new ArrayList<>();
    public static ArrayList<VocabularyData> List_People = new ArrayList<>();
    public static ArrayList<VocabularyData> List_Transport = new ArrayList<>();
    public static ArrayList<VocabularyData> List_ProfileWord = new ArrayList<>();

    public VocabularyData() {
    }

    VocabularyData(String Chinese, String English) {
        ChineseWord = Chinese;
        EnglishWord = English;
    }

    //使用泛型，將類別資料丟到List 裡面，PS:從資料庫撈下來存在List，需將List 設為static，否則會被初始化，無法記錄值
    public void UploadData(String Chinese, String English, String Type) {
        switch (Type.toLowerCase()) {
            case "animal":
                List_Animal.add(new VocabularyData(Chinese, English));
                CreateWord(List_Animal,Type);
                break;
            case "greeting":
                List_Greeting.add(new VocabularyData(Chinese, English));
                CreateWord(List_Greeting,Type);
                break;
            case "fruit":
                List_Fruit.add(new VocabularyData(Chinese, English));
                CreateWord(List_Fruit,Type);
                break;
            case "people":
                List_People.add(new VocabularyData(Chinese, English));
                CreateWord(List_People,Type);
                break;
            case "transport":
                List_Transport.add(new VocabularyData(Chinese, English));
                CreateWord(List_Transport,Type);
                break;
            case "profileword":
                List_ProfileWord.add(new VocabularyData(Chinese, English));
                break;
        }
    }

    public void PrintData() {
        for (int i = 0; i < List_Animal.size(); i++) {
            Log.d("ChineseData", List_Animal.get(i).ChineseWord.toString());
            Log.d("EnglishData", List_Animal.get(i).EnglishWord.toString());
        }
        Log.d("List Count", String.valueOf(List_Animal.size()));

        for (int i = 0; i < List_Greeting.size(); i++) {
            Log.d("ChineseDatass", List_Greeting.get(i).ChineseWord.toString());
            Log.d("EnglishDatass", List_Greeting.get(i).EnglishWord.toString());
        }
        Log.d("List Countss", String.valueOf(List_Greeting.size()));

    }
    public void PrintData2() {

        for (int i = 0; i < List_ProfileWord.size(); i++) {
            Log.d("ChineseDatass", List_ProfileWord.get(i).ChineseWord.toString());
            Log.d("EnglishDatass", List_ProfileWord.get(i).EnglishWord.toString());
        }
        Log.d("List Countss", String.valueOf(List_ProfileWord.size()));
    }

    public void RefreshStudentsData(final ArrayList<VocabularyData> list, final String Type){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final String userEmail = user.getEmail();
        final  String[] Email = userEmail.split("@");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        RefreshStudentsDataRef = databaseReference.child("UserWord");
        RefreshStudentsDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseReference mRef = database.getReference().child("UserWord").child(Email[0]).child(Type).child(String.valueOf(i + 1));
                        Map<String, Object> UpdatesMap = new HashMap<>();
                        UpdatesMap.put("ChineseWord",list.get(i).ChineseWord.toString());
                        UpdatesMap.put("EnglishWord",list.get(i).EnglishWord.toString());
                        mRef.updateChildren(UpdatesMap);
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //一登入就把所有資料都create上去,若使用者已登入就不重新create Data
    public void CreateWord(final ArrayList<VocabularyData> list, final String Type) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final String userEmail = user.getEmail();
        final  String[] Email = userEmail.split("@");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        UserWord = databaseReference.child("UserWord");
        UserWord.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.child(Email[0]).exists()) {
                    for (int i = 0; i < list.size(); i++) {
                        DatabaseReference mRef = database.getReference().child("UserWord").child(Email[0]).child(Type).child(String.valueOf(i + 1));
                        mRef.child("ChineseWord").setValue(list.get(i).ChineseWord.toString());
                        mRef.child("EnglishWord").setValue(list.get(i).EnglishWord.toString());
                        mRef.child("Weight").setValue(10);
                    }
                } else if (dataSnapshot.child(Email[0]).exists()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
