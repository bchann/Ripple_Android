package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by Parikshit on 3/11/17.
 */

public class BtnFonts extends AppCompatButton {
    public BtnFonts(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf"));
    }
}
