package com.vuric.nativemusicsampler.layouts;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.vuric.nativemusicsampler.controllers.PlayerController;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.fragments.ConsoleFragment;
import com.vuric.nativemusicsampler.listeners.SlotsContainerGestureListener;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.models.GlobalPlayersState;

public class SlotsContainerRelativeLayout extends RelativeLayout {

    private int w,h;
    private int childW, childH;
    private AppLayoutState _state;
    private OnTouchListener _listener;
    private int _slots;
    private ConsoleFragment _consoleFragment;

    public SlotsContainerRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlotsContainerRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlotsContainerRelativeLayout(Context context, int slots, AppLayoutState state, GlobalPlayersState _playerState) {
        super(context);
        _state = state;
        init(slots, _playerState.getPlayersModel());
    }

    public void setState(AppLayoutState state) {
        if(state != _state) {
            _state = state;
            _listener = null;

            PlayerModel[] states = new PlayerModel[getChildCount()];
            for(int i = 0; i < getChildCount(); ++i) {
                states[i] = ((PlayerView)getChildAt(i)).getModel();
            }

            removeAllViews();
            init(_slots, states);

        }
    }

    private void init(int slots, PlayerModel[] models) {

        _consoleFragment = (ConsoleFragment) ((Activity)getContext()).getFragmentManager().findFragmentByTag(ConsoleFragment._TAG);

        setOnTouchListener(_listener);

        _slots = slots;
        _listener =  new SlotsContainerGestureListener(getContext());
        PlayerController[] controllers = new PlayerController[slots];

        for(int i = 0; i < slots; ++i) {

            PlayerView v;

            if(models != null) {
                v = new PlayerView(getContext(), _state, models[i], _listener, i);
            } else {
                v = new PlayerView(getContext(), _state, _listener, i);
            }

            v.setOnTouchListener(_listener);
            addView(v);
            controllers[i] = v.getController();
        }

        _consoleFragment.setPlayers(controllers);
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
