package com.example.brianchan.ripple_android;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Brian Chan on 2/28/2017.
 */

public class RippleFont extends TextView {
    public RippleFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/lobster.ttf"));
    }
}
