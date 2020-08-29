package com.example.a846205123.ncu_learningchinese.FirebaseModel;

import android.util.Log;

import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDownloadData {
    private DatabaseReference databaseReference;
    private DatabaseReference greeting,animal,transport,poeple,fruit;
    private VocabularyData vocabularyData = new VocabularyData();

    //從資料庫下載資料
    public FirebaseDownloadData() {
        VocabularyData.List_Animal.clear();//清除List ，否則會重複下載到List
        VocabularyData.List_Fruit.clear();
        VocabularyData.List_People.clear();
        VocabularyData.List_Greeting.clear();
        VocabularyData.List_Transport.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        animal = databaseReference.child("wordlist").child("animal");
        animal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                    String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                    vocabularyData.UploadData(ChineseWord, EnglishWord ,"animal");
                }
                //vocabularyData.PrintData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        greeting = databaseReference.child("wordlist").child("greeting");
        greeting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                    String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                    vocabularyData.UploadData(ChineseWord, EnglishWord ,"greeting");
                }
                //vocabularyData.PrintData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        transport = databaseReference.child("wordlist").child("transport");
        transport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                    String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                    vocabularyData.UploadData(ChineseWord, EnglishWord ,"transport");
                }
                //vocabularyData.PrintData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        poeple = databaseReference.child("wordlist").child("people");
        poeple.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                    String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                    vocabularyData.UploadData(ChineseWord, EnglishWord ,"people");
                }
                //vocabularyData.PrintData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        fruit = databaseReference.child("wordlist").child("fruit");
        fruit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String ChineseWord = ds.child("ChineseWord").getValue(String.class);
                    String EnglishWord = ds.child("EnglishWord").getValue(String.class);
                    vocabularyData.UploadData(ChineseWord, EnglishWord ,"fruit");
                }
                //vocabularyData.PrintData();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }
}
