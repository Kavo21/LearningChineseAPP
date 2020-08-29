package com.example.a846205123.ncu_learningchinese.FirebaseModel;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUploadData {
    private String Type;//單字類別
    private static Long ChildCount;//節點數量
    private FirebaseDatabase firebasedata = FirebaseDatabase.getInstance();
    private DatabaseReference mainReference;

    //建構值，傳入參數後，自動呼叫計算節點方法，並把結果存在共用的 ChildCount;//節點數量
   public FirebaseUploadData(String type){
       Type = type;
       GetChildCount(type);
   }

   //計算節點，以利後續新增值
   private void GetChildCount(String type){
       mainReference  = firebasedata.getReference("wordlist").child(type);
       mainReference.addValueEventListener(new ValueEventListener() {
           public void onDataChange(DataSnapshot dataSnapshot) {
               //把節點數量存到 ChildCount
               ChildCount = dataSnapshot.getChildrenCount();
               Log.d("Count",String.valueOf(ChildCount));
           }
           public void onCancelled(DatabaseError databaseError) { }
       });
   }

   public void UploadData(String English,String Chinese){
       Log.d("COUNT",String.valueOf(ChildCount));
       DatabaseReference uploadDataRef  = firebasedata.getReference("wordlist").child(Type).child(String.valueOf(ChildCount+1));
       uploadDataRef.child("EnglishWord").setValue(English);
       uploadDataRef.child("ChineseWord").setValue(Chinese);

   }
}

