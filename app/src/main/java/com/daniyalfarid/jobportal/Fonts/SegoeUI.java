package com.daniyalfarid.jobportal.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SegoeUI extends android.support.v7.widget.AppCompatTextView {
    public SegoeUI(Context context, AttributeSet attrs){
        super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Segoe UI.ttf"));

    }
}
