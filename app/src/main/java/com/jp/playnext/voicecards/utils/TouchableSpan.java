package com.jp.playnext.voicecards.utils;

/**
 * Created by danielmorais on 2/24/17.
 */
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class TouchableSpan  extends ClickableSpan {
    private boolean mIsPressed;
    private int mPressedBackgroundColor;
    private int mNormalTextColor;
    private int mPressedTextColor;
    private int mBackgroundColor;

    public TouchableSpan(int textColor,int backgroundColor) {
        this(textColor,backgroundColor,textColor,backgroundColor);
    }

    public TouchableSpan(int normalTextColor,int backgroundColor, int pressedTextColor, int pressedBackgroundColor) {
        mBackgroundColor = backgroundColor;
        mNormalTextColor = normalTextColor;
        mPressedTextColor = pressedTextColor;
        mPressedBackgroundColor = pressedBackgroundColor;
    }

    public void setPressed(boolean isSelected) {
        mIsPressed = isSelected;
    }




    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
        ds.bgColor = mIsPressed ? mPressedBackgroundColor : mBackgroundColor;
        ds.setUnderlineText(!mIsPressed);
        ds.setTypeface(Typeface.create(ds.getTypeface(), Typeface.BOLD));

    }
}