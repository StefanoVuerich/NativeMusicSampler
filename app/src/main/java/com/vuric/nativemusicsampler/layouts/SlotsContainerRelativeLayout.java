package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.vuric.nativemusicsampler.enums.SlotsContainerState;
import com.vuric.nativemusicsampler.listeners.SlotsContainerGestureListener;

/**
 * Created by stefano on 4/24/2016.
 */
public class SlotsContainerRelativeLayout extends RelativeLayout {

    private int w,h;
    private int childW, childH;
    private SlotsContainerState _state;

    public SlotsContainerRelativeLayout(Context context) {
        super(context);
    }

    public SlotsContainerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlotsContainerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlotsContainerRelativeLayout(Context context, int slots, SlotsContainerState state) {
        super(context);
        _state = state;
        init(slots);
    }

    public void setState(SlotsContainerState state) {
        if(state != _state) {
            _state = state;
        }
    }

    private void init(int slots) {

        OnTouchListener l =  new SlotsContainerGestureListener(getContext());

        setOnTouchListener(l);

        for(int i = 0; i < slots; ++i) {

            PlayerView v = new PlayerView(getContext(), _state);
            v.setOnTouchListener(l);
            addView(v);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int mHeight = h = hSpecSize;

        if(hSpecMode == MeasureSpec.EXACTLY) {
            mHeight = hSpecSize;
        } else if(hSpecMode == MeasureSpec.AT_MOST) {

        }

        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int mWidth = w = wSpecSize;

        if(wSpecMode == MeasureSpec.EXACTLY) {
            mWidth = wSpecSize;
        } else if(wSpecMode == MeasureSpec.AT_MOST) {

        }

        childW = w / 3;
        childH = h / 3;

        for(int i = 0; i < getChildCount(); ++i) {

            PlayerView v = (PlayerView) getChildAt(i);
            v.setLayoutParams(new RelativeLayout.LayoutParams(childW, childH));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int childIndex = 0;
        for(int y = 0; y < 3;) {

            for(int x = 0; x < 3; ++x) {

                int left = x * childW;
                int top = y * childH;
                int right = x * childW + childW;
                int bottom = y * childH + childH;

                PlayerView v = (PlayerView)getChildAt(childIndex++);
                v.layout(left, top, right, bottom);
            }
            ++y;
        }
    }
}
