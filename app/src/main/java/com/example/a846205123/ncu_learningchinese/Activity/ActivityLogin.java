package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a846205123.ncu_learningchinese.R;
import com.example.a846205123.ncu_learningchinese.Method.Sound;
import com.example.a846205123.ncu_learningchinese.Method.TextToSpeech_Method;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth auth = FirebaseAuth.getInstance(); //Get Firebase
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText Account , Password;
    private TextToSpeech_Method textToSpeech_method;
    private Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR); //設定螢幕不隨著手機選轉
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //設螢幕直向顯示
        FindView();
        InitializeMethod();
        FirebaseAuthStateListener();
    }

    //Initialize object and Set listener
    private  void FindView() {
        Account = (EditText) findViewById(R.id.Input_Account);
        Password = (EditText) findViewById(R.id.Input_Password);
        findViewById(R.id.BtnLogin).setOnClickListener(this); //Set Btn as listener
        findViewById(R.id.link_signup).setOnClickListener(this);//Set TextView as listener
    }

    private void InitializeMethod(){
        textToSpeech_method = new TextToSpeech_Method(this);//Initialize Text To Speech
        sound = new Sound(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnLogin:
                if (Account.getText().length() == 0 || Password.getText().length() == 0) {
                    Toast.makeText(ActivityLogin.this, "Account and Password can't null", Toast.LENGTH_SHORT).show();
                    textToSpeech_method.Speech("請輸入帳號密碼");
                    break;
                }else
                    LoginMethod();
                break;
            case R.id.link_signup:
                if (Account.getText().length() != 0 || Password.getText().length() != 0) {
                    register(Account.getText().toString(), Password.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    //帳號聆聽者，監聽帳號狀態
    private void FirebaseAuthStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user==null) {
                    Toast.makeText(ActivityLogin.this, "Please Sign or Login your account", Toast.LENGTH_SHORT).show();
                }else{
                    // TODO after login
                    FirebaseUser fireUser = auth.getCurrentUser();
                    if (fireUser.isEmailVerified()){
                        startActivity(new Intent(ActivityLogin.this,ActivityHomePage.class));
                        Log.d("onAuthStateChanged", "登入:"+ user.getUid());
                        finish();
                    }else {
                        sendEmailVerification();
                    }
                }
            }
        };
    }

    //寄送認證信
    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("", "sendEmailVerification", task.getException());
                            Toast.makeText(ActivityLogin.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //登入
    private  void  LoginMethod(){
            auth.signInWithEmailAndPassword(Account.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fireUser = auth.getCurrentUser();
                                //檢查帳戶是否有經過信箱認證過
                                if (fireUser.isEmailVerified()){
                                    sound.SoundPlayCorrect();
                                    Toast.makeText(ActivityLogin.this, "Authentication Success.",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ActivityLogin.this,ActivityHomePage.class));
                                    finish();
                                }else {
                                    sound.SoundPlayFailed();
                                    Toast.makeText(ActivityLogin.this, "Please check your email and verify your email address.",Toast.LENGTH_SHORT).show();
                                }
                                // Sign in success, update UI with the signed-in user's information
                            } else if (!task.isSuccessful()){
                                // If sign in fails, display a message to the user.
                                sound.SoundPlayFailed();
                                Toast.makeText(ActivityLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }


    private void register(final String email, final String password) {
        new AlertDialog.Builder(ActivityLogin.this)
                .setTitle("Sign up")
                .setMessage(" Do you want to register with this account and password??")
                .setPositiveButton("Sign up",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createUser(email, password);
                            }
                        })
                .setNeutralButton("Cancel", null)
                .show();
    }

    //註冊帳號
    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                String message = task.isSuccessful() ? "Sign Up Successful" : "Sign Up Failed";
                                new AlertDialog.Builder(ActivityLogin.this)
                                        .setMessage(message)
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }
}
