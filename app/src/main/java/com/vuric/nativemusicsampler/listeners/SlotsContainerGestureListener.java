package com.vuric.nativemusicsampler.listeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.events.SampleSlotSelectedEvt;
import com.vuric.nativemusicsampler.events.SlotsContainerEvt;
import com.vuric.nativemusicsampler.layouts.PlayerView;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/24/2016.
 */
public class SlotsContainerGestureListener implements View.OnTouchListener {

    private final GestureDetector _gestureDetector;
    private PlayerView _lastTouchedView;


    public SlotsContainerGestureListener(Context context) {

        _gestureDetector = new GestureDetector(context, new GestureListener(context));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        _lastTouchedView = (PlayerView) v.getParent();

        return _gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private Context _context;

        public GestureListener(Context context) {

            _context = context;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            BusStation.getBus().post(new SampleSlotSelectedEvt(_lastTouchedView.getModel()));
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > Constants.SWIPE_THRESHOLD && Math.abs(velocityX) > Constants.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
                else if (Math.abs(diffY) > Constants.SWIPE_THRESHOLD && Math.abs(velocityY) > Constants.SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                }
                result = true;

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        public void onSwipeRight() {
            BusStation.getBus().post(new SlotsContainerEvt(AppLayoutState.OPEN));
        }

        public void onSwipeLeft() {
            BusStation.getBus().post(new SlotsContainerEvt(AppLayoutState.CLOSE));
        }

        public void onSwipeTop() {
            Log.d(Constants.APP_TAG, "swipe top");
        }

        public void onSwipeBottom() {
            Log.d(Constants.APP_TAG, "swipe bottom");
        }
    }
}
