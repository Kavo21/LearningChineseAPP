//package com.example.a846205123.ncu_learningchinese.FirebaseModel;
//
//import android.util.Log;
//
//import com.example.a846205123.ncu_learningchinese.Data.VocabularyData;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//
//public class FirebaseWeightData {
//    private String Type,index;//單字類別
//    private FirebaseDatabase firebasedata = FirebaseDatabase.getInstance();
//    private static Long ChildCount;//節點數量
//    private FirebaseAuth auth = FirebaseAuth.getInstance();
//    private FirebaseUser user = auth.getCurrentUser();
//    private DatabaseReference mainReference;
//    String userEmail = user.getEmail();
//    String[] Email = userEmail.split("@");
//
//    //建構值，傳入參數後，自動呼叫計算節點方法，並把結果存在共用的 ChildCount;//節點數量
//    public FirebaseWeightData(String type){
//
//        Type = type;
//        GetChildCount(type);
//        UploadData(index);
//    }
//
//    //計算節點，以利後續新增值
//    private void GetChildCount(String type){
//
//        mainReference  = firebasedata.getReference("UserWord").child(Email[0]).child(Type).child(String.valueOf(index)).child("Weight");
//        mainReference.addValueEventListener(new ValueEventListener() {
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //把節點數量存到 ChildCount
//                ChildCount = dataSnapshot.getChildrenCount();
//                Log.d("Count",String.valueOf(ChildCount));
//            }
//            public void onCancelled(DatabaseError databaseError) { }
//        });
//    }
//
//    public void UploadData(String index){
//        DatabaseReference uploadDataRef  = firebasedata.getReference("UserWord").child(Email[0]).child(Type);
//        uploadDataRef.child(String.valueOf(index)).child("Weight").setValue(ChildCount+1);
//    }
//}
//
