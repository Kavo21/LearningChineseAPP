package com.example.a846205123.ncu_learningchinese.Method;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by a846205123 on 2018/4/5.
 */

public class TextToSpeech_Method implements TextToSpeech.OnInitListener{

    public TextToSpeech textToSpeech;

    public TextToSpeech_Method(Context context) {
        textToSpeech = new TextToSpeech(context,this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            textToSpeech.setLanguage(new Locale("Zh"));//設定語言
        }
    }

    public  void Speech(String text){
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    public void destroy(){
        textToSpeech.shutdown();
    }
}





