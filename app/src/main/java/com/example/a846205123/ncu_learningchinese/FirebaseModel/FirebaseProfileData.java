package com.example.a846205123.ncu_learningchinese.FirebaseModel;

import android.util.Log;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseProfileData {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private VocabularyData vocabularyData = new VocabularyData();

    //從資料庫下載資料
    public FirebaseProfileData() {
        VocabularyData.List_ProfileWord.clear();//清除List ，否則會重複下載到List

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String userEmail = user.getEmail();
        String[] Email = userEmail.split("@");

        Query query_animal = reference.child("UserWord").child(Email[0]).child("animal").orderByChild("Weight").startAt(11);
        Log.d("TAG0", query_animal.toString());
        query_animal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                        String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                        Log.d("TAG1", ChineseWord.toString()+"/"+EnglishWord.toString());
                        vocabularyData.UploadData(ChineseWord, EnglishWord ,"ProfileWord");
                        Log.d("TAGO", ChineseWord.toString());
                    }
                    vocabularyData.PrintData2();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Query query_fruit = reference.child("UserWord").child(Email[0]).child("fruit").orderByChild("Weight").startAt(11);
        Log.d("TAG0", query_fruit.toString());
        query_fruit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                        String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                        vocabularyData.UploadData(ChineseWord, EnglishWord ,"ProfileWord");
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Query query_greeting = reference.child("UserWord").child(Email[0]).child("greeting").orderByChild("Weight").startAt(11);
        Log.d("TAG0", query_greeting.toString());
        query_greeting.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                        String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                        vocabularyData.UploadData(ChineseWord, EnglishWord ,"ProfileWord");
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Query query_people = reference.child("UserWord").child(Email[0]).child("people").orderByChild("Weight").startAt(11);
        Log.d("TAG0", query_people.toString());
        query_people.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                        String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                        vocabularyData.UploadData(ChineseWord, EnglishWord ,"ProfileWord");
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        Query query_transport = reference.child("UserWord").child(Email[0]).child("transport").orderByChild("Weight").startAt(11);
        Log.d("TAG0", query_transport.toString());
        query_transport.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                        String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                        vocabularyData.UploadData(ChineseWord, EnglishWord ,"ProfileWord");
                    }

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

}

