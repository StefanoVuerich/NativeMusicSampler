package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.vuric.nativemusicsampler.enums.SlotsContainerState;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/5/2016.
 */
public class PlayerView extends RelativeLayout {

    private int w, h;
    private View playView;
    private View topView;
    private View bottomView;
    SlotsContainerState _state;

    public PlayerView(Context context) {
        super(context);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PlayerView(Context context, SlotsContainerState state) {
        super(context);
        _state = state;
        init();
    }

    private void init() {

        setBackgroundColor(Color.parseColor("#0000FF"));
        setPadding(5, 5, 5, 5);

        playView = new View(getContext());
        playView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(Constants.APP_TAG, "Touch");
                return ((ViewGroup)getParent()).onTouchEvent(event);
            }
        });
        addView(playView);

        topView = new View(getContext());
        addView(topView);

        bottomView = new View(getContext());
        addView(bottomView);
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

        View playView = getChildAt(0);
        playView.setBackgroundColor(Color.parseColor("#000000"));
        playView.setLayoutParams(new RelativeLayout.LayoutParams(mHeight, mHeight));

        int currentWidth = _state == SlotsContainerState.CLOSE ? 0 : h / 2;

        View topView = getChildAt(1);
        topView.setBackgroundColor(Color.parseColor("#0000CC"));
        topView.setLayoutParams(new RelativeLayout.LayoutParams(currentWidth, mHeight / 2));

        View bottomView = getChildAt(2);
        bottomView.setBackgroundColor(Color.parseColor("#0000AA"));
        bottomView.setLayoutParams(new RelativeLayout.LayoutParams(currentWidth, mHeight / 2));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        View playView = getChildAt(0);
        playView.layout(0, 0, h, h);

        int currentWidth = _state == SlotsContainerState.CLOSE ? 0 : h / 2;

        View topView = getChildAt(1);
        topView.layout(h, 0, h + currentWidth, h / 2);

        View bottomView = getChildAt(2);
        bottomView.layout(h, h / 2, h + currentWidth, h);
    }
}
