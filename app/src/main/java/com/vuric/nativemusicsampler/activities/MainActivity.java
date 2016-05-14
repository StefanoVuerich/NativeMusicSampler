package com.vuric.nativemusicsampler.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.CustomApplicationClass;
import com.vuric.nativemusicsampler.R;
import com.vuric.nativemusicsampler.enums.AppLayoutState;
import com.vuric.nativemusicsampler.events.SampleSlotSelectedEvt;
import com.vuric.nativemusicsampler.events.SlotsContainerEvt;
import com.vuric.nativemusicsampler.fragments.ConsoleFragment;
import com.vuric.nativemusicsampler.fragments.SamplerControlsFragment;
import com.vuric.nativemusicsampler.fragments.SamplerSlotsFragment;
import com.vuric.nativemusicsampler.utils.Constants;

public class MainActivity extends Activity {

    public static final String APP_LAYOUT_STATE = "APP_LAYOUT_STATE";
    private PowerManager.WakeLock _wakeLock;
    private ViewGroup _baseContainer;
    private Point _screenSize;
    private FrameLayout _controlsContainer;
    private int _controlsContainerWidth, _controlsContainerHeight;
    private AppLayoutState _state = AppLayoutState.CLOSE;

    @Override
    protected void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        _baseContainer = (ViewGroup) findViewById(R.id.samplerBaseContainer);

        if (savedInstanceState == null) {
            checkForAudioLowLatency();
            getRateAndFrames();
            // checkForNewFiles();
        }

        setWakeLock();
        getScreenSizeAndSendValueToApplicationClass();
        setFragmentsMeasure();
        setConsoleFragment();
        setSamplerSlotsFragment();
        setSamplerControlsFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this._wakeLock.release();
    }


    @Subscribe
    public void receiveMessage(SlotsContainerEvt evt) {

        AppLayoutState newState =  evt.getState();

        if(newState != _state) {
            _state = newState;
            SamplerSlotsFragment samplerSlotsFragment = (SamplerSlotsFragment) getFragmentManager().findFragmentByTag(SamplerSlotsFragment._TAG);
            SamplerControlsFragment samplerControlsFragment = (SamplerControlsFragment) getFragmentManager().findFragmentByTag(SamplerControlsFragment._TAG);
            //if(samplerSlotsFragment != null) {
            samplerSlotsFragment.setState(_state);
            if (samplerControlsFragment != null) {

                samplerControlsFragment.setState(_state);
            }
            //}
            setFragmentsMeasure();
            _baseContainer.invalidate();
        }
    }

    @Subscribe
    public void receiveMessage(SampleSlotSelectedEvt evt) {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.samplerControlsContainer, SamplerControlsFragment.getInstance(evt.getPlayerModel()), SamplerControlsFragment._TAG)
                .commit();
    }

    private void setFragmentsMeasure() {

        int slotBaseContainerWidth = _state == AppLayoutState.CLOSE ?
                _screenSize.y :
                _screenSize.y + (_screenSize.y / 3 / 2 * 3);

        FrameLayout left = (FrameLayout) findViewById(R.id.samplerSlotsContainer);
        left.setBackgroundColor(Color.parseColor("#0000FF"));
        left.setLayoutParams(new LinearLayout.LayoutParams(slotBaseContainerWidth, _screenSize.y));

        FrameLayout right = (FrameLayout) findViewById(R.id.samplerControlsContainer);
        right.setBackgroundColor(Color.parseColor("#CCCCCC"));
        right.setLayoutParams(new LinearLayout.LayoutParams(_screenSize.x - slotBaseContainerWidth, _screenSize.y));
    }

    private void setSamplerSlotsFragment() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(APP_LAYOUT_STATE, _state);
        Fragment sampleButtonFragment = SamplerSlotsFragment.getInstance();
        sampleButtonFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.samplerSlotsContainer, sampleButtonFragment, SamplerSlotsFragment._TAG);
        ft.commit();

        _controlsContainer = (FrameLayout) findViewById(R.id.samplerControlsContainer);
        ViewTreeObserver vto = _controlsContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                _controlsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                _controlsContainerWidth = _controlsContainer.getMeasuredWidth();
                _controlsContainerHeight = _controlsContainer.getMeasuredHeight();
            }
        });
    }

    private void setSamplerControlsFragment() {
        Fragment controlsFragment = SamplerControlsFragment.getInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.samplerControlsContainer, controlsFragment, SamplerControlsFragment._TAG);
    }

    private void setConsoleFragment() {
        ConsoleFragment consoleFragment = (ConsoleFragment) getFragmentManager().findFragmentByTag(ConsoleFragment._TAG);
        if(consoleFragment == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(ConsoleFragment.get(), ConsoleFragment._TAG);
            ft.commit();
        }
    }

    private void getScreenSizeAndSendValueToApplicationClass() {
        _screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(_screenSize);
        CustomApplicationClass.get().setScreenSize(_screenSize);
    }

    private void setWakeLock() {
        final PowerManager pom = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this._wakeLock = pom.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, Constants.WAKE_LOCK);
        this._wakeLock.acquire();
    }

    private void getRateAndFrames() {
        AudioManager am = (AudioManager) getSystemService(MainActivity.this.AUDIO_SERVICE);
        String sampleRate = am.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
        String framesPerBuffer = am.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
        Toast.makeText(this, "Rate: " + sampleRate + " - Frame: " + framesPerBuffer, Toast.LENGTH_LONG).show();
        am.setParameters(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE + "=" + sampleRate);
        am.setParameters(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER + "=" + framesPerBuffer);

        String a = am.getParameters(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
        String b = am.getParameters(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);

        Log.v(Constants.APP_TAG, String.format("Sample rate %s, Frame buffer %s", a, b));
    }

    private void checkForAudioLowLatency() {
        PackageManager pm = MainActivity.this.getPackageManager();
        boolean claimsFeature = pm.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY);
        if (claimsFeature)
            Log.v(Constants.APP_TAG, "Low Latency supported");
        else
            Log.v(Constants.APP_TAG, "Low Latency NOT supported");
    }

    public void updateFromNative() {

        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
    }
}
