package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.controllers.PlayerController;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.events.PlayerStatusUpdateEvt;
import com.vuric.nativemusicsampler.events.SampleLoadedEvt;
import com.vuric.nativemusicsampler.events.SampleSlotSelectedEvt;
import com.vuric.nativemusicsampler.models.PlayerModel;

/**
 * Created by stefano on 4/5/2016.
 */
public class PlayerView extends RelativeLayout implements View.OnTouchListener {

    private int w, h;
    private View playView;
    private View topView;
    private View bottomView;
    private AppLayoutState _state;
    private PlayerController _playerController;
    private OnTouchListener _listener;

    public PlayerView(Context context, AppLayoutState state, OnTouchListener listener, int id) {
        super(context);

        PlayerModel model = new PlayerModel();
        model.setID(id);
        model.setState(PlayState.STOP);
        _playerController = new PlayerController(model);

        _state = state;
        _listener = listener;
        init();
    }

    public PlayerView(Context context, AppLayoutState state, PlayerModel model, OnTouchListener listener, int id) {
        super(context);

        _playerController = new PlayerController(model);

        _state = state;
        _listener = listener;
        init();
    }

    public PlayerModel getModel() {
        return _playerController.getModel();
    }

    public PlayerController getController() {
        return _playerController;
    }

    private void init() {

        setBackgroundColor(Color.parseColor("#0000FF"));
        setPadding(5, 5, 5, 5);

        playView = new View(getContext());
        playView.setId(getId());
        playView.setTag("PlayView");
        playView.setOnTouchListener(this);
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
        playView.setBackgroundColor(_playerController.getModel().isSelected() ? Color.parseColor("#FFFFFF") : Color.parseColor("#000000"));
        playView.setLayoutParams(new RelativeLayout.LayoutParams(mHeight, mHeight));

        int currentWidth = _state == AppLayoutState.CLOSE ? 0 : h / 2;

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

        int currentWidth = _state == AppLayoutState.CLOSE ? 0 : h / 2;

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getAction()) {

            case MotionEvent.ACTION_UP:
               if(_playerController.play()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            _playerController.setColor("#FF0000", playView);
                        }
                    }).run();
               };
            break;
        }

        return _listener.onTouch(v,event);
    }

    // Events

    @Subscribe
    public void receiveMessage(PlayerStatusUpdateEvt evt) {
        if(evt.getSlotID() == getModel().getID()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    _playerController.setColor(_playerController.getModel().isSelected() ? "#FFFFFF" : "#000000", playView);
                }
            }).run();
        }
    }

    @Subscribe
    public void receiveMessage(SampleSlotSelectedEvt evt) {
        if(evt.getPlayerModel().getID() == getModel().getID()) {
            _playerController.setSelected(true);
            playView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            _playerController.setSelected(false);
            playView.setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    @Subscribe
    public void receiveMessage(SampleLoadedEvt evt) {

        if(evt.getSlotID() == getModel().getID()) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    post(new Runnable() {
                        @Override
                        public void run() {
                            _playerController.setColor("#0000FF", playView);
                        }
                    });

                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            _playerController.setColor(_playerController.getModel().isSelected() ? "#FFFFFF" : "#000000", playView);
                        }
                    }, 200);
                }
            }).run();
        }
    }
}
