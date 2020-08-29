package com.example.a846205123.ncu_learningchinese.Activity;

import android.content.Context;
import android.widget.RelativeLayout;

public abstract class PageView extends RelativeLayout {
    public PageView(Context context) {
        super(context);
    }
    public abstract void refresh();
}