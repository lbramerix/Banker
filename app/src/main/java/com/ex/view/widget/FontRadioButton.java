package com.ex.view.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.ex.chat.compat.FontConfig;

public class FontRadioButton extends AppCompatRadioButton {

    public FontRadioButton(Context context) {
        super(context);
        initFont();
    }

    public FontRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFont();
    }

    public FontRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont();
    }

    public void initFont() {
        if (null != getTypeface() && getTypeface().isBold()) {
            this.setTypeface(FontConfig.boldTypeFace);
        } else {
            this.setTypeface(FontConfig.typeFace);
        }
        this.postInvalidate();
    }
}
