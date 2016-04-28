package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.SampleSlotSelected;
import com.vuric.nativemusicsampler.enums.SlotsContainerState;
import com.vuric.nativemusicsampler.models.PlayerModel;

/**
 * Created by stefano on 4/5/2016.
 */
public class PlayerView extends RelativeLayout {

    private int w, h;
    private View playView;
    private View topView;
    private View bottomView;
    private SlotsContainerState _state;
    private PlayerModel _model;
    private OnTouchListener _listener;

    public PlayerView(Context context, SlotsContainerState state, OnTouchListener listener, int id) {
        super(context);
        _model = new PlayerModel();
        _state = state;
        _listener = listener;
        setId(id);
        init();
    }

    public PlayerView(Context context, SlotsContainerState state, PlayerModel model, OnTouchListener listener, int id) {
        super(context);
        _model = model;
        _state = state;
        _listener = listener;
        setId(id);
        init();
    }

    public PlayerModel getModel() {
        return _model;
    }

    private void init() {

        setBackgroundColor(Color.parseColor("#0000FF"));
        setPadding(5, 5, 5, 5);


        playView = new View(getContext());
        playView.setId(getId());
        playView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return _listener.onTouch(v,event);
            }
        });
        playView.setTag("PlayView");
        addView(playView);

        topView = new View(getContext());
        topView.setId(getId());
        topView.setTag("TopView");
        addView(topView);

        bottomView = new View(getContext());
        bottomView.setId(getId());
        bottomView.setTag("BottomView");
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
        playView.setBackgroundColor(_model.isSelected() ? Color.parseColor("#FFFFFF") : Color.parseColor("#000000"));
        playView.setLayoutParams(new RelativeLayout.LayoutParams(mHeight, mHeight));

        int currentWidth = _state == SlotsContainerState.CLOSE ? 0 : h / 2;

        View topView = getChildAt(1);
        topView.setBackgroundColor(Color.parseColor("#0000CC"));
        topView.setLayoutParams(new RelativeLayout.LayoutParams(currentWidth, mHeight / 2));
        topView.requestLayout();

        View bottomView = getChildAt(2);
        bottomView.setBackgroundColor(Color.parseColor("#0000AA"));
        bottomView.setLayoutParams(new RelativeLayout.LayoutParams(currentWidth, mHeight / 2));
        bottomView.requestLayout();
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        BusStation.getBus().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void receiveMessage(SampleSlotSelected evt) {
        if(evt.getViewID() == getId()) {
            _model.setSelected(true);
            playView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            if(_model.isSelected()) {
                _model.setSelected(false);
                playView.setBackgroundColor(Color.parseColor("#000000"));
            }
        }
    }
}
