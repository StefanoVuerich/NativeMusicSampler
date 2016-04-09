package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by stefano on 4/6/2016.
 */
public class PlayButton extends View{

    public PlayButton(Context context) {
        super(context);
        init();
    }

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
