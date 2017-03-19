package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Parikshit on 3/12/17.
 */

public class TextFonts extends TextView {
    public TextFonts(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf"));
    }
}
