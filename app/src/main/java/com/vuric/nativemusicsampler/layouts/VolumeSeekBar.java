package com.vuric.nativemusicsampler.layouts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.vuric.nativemusicsampler.activities.MainActivity;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 6/1/2016.
 */
public class VolumeSeekBar extends SeekBar {

    private boolean canPublishProgress;

    public VolumeSeekBar(Context context) {
        super(context);
        init();
    }

    public VolumeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        canPublishProgress = false;
        setMax(100);
        setProgress(100);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isEnabled()) {
            return false;
        }

        setMax(100);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                canPublishProgress = true;
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                if (canPublishProgress) {

                    int i = super.getMax() - (int) (super.getMax() * event.getY() / getHeight());

                    setProgress(i);

                    /*int[] selectedSlots = CustomApplicationClass.get()
                            .getCurrentSelectedSlots();*/
                    int volume = getProgress();

                    // Set new volume to model

                    /*for (int slotID : selectedSlots) {
                        SoundHub.getInstance().getSamplesController()
                                .setSampleSlotVolume(slotID, volume);
                    }

                    // Set new volume to player
                    OpenSLES.setVolume(volume, selectedSlots);*/
                    NativeWrapper.setVolume(((MainActivity)getContext()).getSelectedSlotId(), volume);
                    Log.d(Constants.APP_TAG, "onTouchEvent: " + volume);
                    onSizeChanged(getWidth(), getHeight(), 0, 0);
                }
                break;

            case MotionEvent.ACTION_UP:
                canPublishProgress = false;
                setPressed(false);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    public synchronized void updateProgress(int progress) {

        setProgress(progress);
        onSizeChanged(getWidth(), getHeight() , 0, 0);
    }
}
