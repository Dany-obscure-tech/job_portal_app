package com.daniyalfarid.jobportal.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SegoeUIItalic extends android.support.v7.widget.AppCompatTextView {
    public SegoeUIItalic(Context context, AttributeSet attrs){
        super(context,attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/Segoe UI Italic.ttf"));

    }
}
