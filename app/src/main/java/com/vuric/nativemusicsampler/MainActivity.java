package com.vuric.nativemusicsampler;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.enums.SlotsContainerState;
import com.vuric.nativemusicsampler.fragments.ConsoleFragment;
import com.vuric.nativemusicsampler.fragments.SamplerControlsFragment;
import com.vuric.nativemusicsampler.fragments.SamplerSlotsFragment;
import com.vuric.nativemusicsampler.fragments.SamplesListFragment;
import com.vuric.nativemusicsampler.utils.Constants;

public class MainActivity extends Activity implements View.OnTouchListener {


    protected PowerManager.WakeLock mWakeLock;
    private Point screenSize;
    private FrameLayout controlsContainer;
    private int controlsContainerWidth, controlsContainerHeight;
    private SlotsContainerState _state = SlotsContainerState.CLOSE;
    private ViewGroup baseContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        baseContainer = (ViewGroup) findViewById(R.id.samplerBaseContainer);
        baseContainer.setOnTouchListener(this);

        if (savedInstanceState == null) {
            checkForAudioLowLatency();
            getRateAndFrames();

            // checkForNewFiles();
        }
        setWakeLock();
        getScreenSizeAndSendValueToApplicationClass();
        setContainerMeasure();
        //drawer = (CustomDrawer) findViewById(R.id.drawer);
        setConsoleFragment();
        setSamplerSlotsFragment();
        setSamplerControlsFragment();
    }

    @Subscribe
    public void receiveMessage(Message message) {

        SlotsContainerState state =  message.get_state();

        if(state != _state) {
            _state = state;
            SamplerSlotsFragment fr = (SamplerSlotsFragment) getFragmentManager().findFragmentByTag(SamplerSlotsFragment._TAG);
            if(fr != null) {
                fr.setState(_state);
            }
            setContainerMeasure();
            baseContainer.invalidate();
        }
    }

    @Subscribe
    public void receiveMessage(ShowSamplesListEvent evt) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.samplerControlsContainer, SamplesListFragment.getInstance(), SamplesListFragment._TAG);
        ft.commit();
    }

    private void setContainerMeasure() {

        int slotBaseContainerWidth = _state == SlotsContainerState.CLOSE ?
                screenSize.y :
                screenSize.y + (screenSize.y / 3 / 2 * 3);

        FrameLayout left = (FrameLayout) findViewById(R.id.samplerSlotsContainer);
        left.setBackgroundColor(Color.parseColor("#0000FF"));
        left.setLayoutParams(new LinearLayout.LayoutParams(slotBaseContainerWidth, screenSize.y));

        FrameLayout right = (FrameLayout) findViewById(R.id.samplerControlsContainer);
        right.setBackgroundColor(Color.parseColor("#CCCCCC"));
        right.setLayoutParams(new LinearLayout.LayoutParams(screenSize.x - slotBaseContainerWidth, screenSize.y));
    }

    private void setSamplerControlsFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("STATE", _state);
        Fragment sampleButtonFragment = SamplerSlotsFragment.getInstance();
        sampleButtonFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.samplerSlotsContainer, sampleButtonFragment, SamplerSlotsFragment._TAG);
        ft.commit();

        controlsContainer = (FrameLayout) findViewById(R.id.samplerControlsContainer);
        ViewTreeObserver vto = controlsContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                controlsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                controlsContainerWidth = controlsContainer.getMeasuredWidth();
                controlsContainerHeight = controlsContainer.getMeasuredHeight();
            }
        });
    }

    private void setSamplerSlotsFragment() {
        Fragment controlsFragment = SamplerControlsFragment.getInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.samplerControlsContainer, controlsFragment, SamplerControlsFragment._TAG);
    }

    private void setConsoleFragment() {
        ConsoleFragment consoleFragment = (ConsoleFragment) getFragmentManager().findFragmentByTag(Constants.CONSOLE_FRAGMENT);
        if(consoleFragment == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(ConsoleFragment.get(), Constants.CONSOLE_FRAGMENT);
            ft.commit();
        }
    }

    private void getScreenSizeAndSendValueToApplicationClass() {
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        CustomApplicationClass.get().setScreenSize(screenSize);
    }

    private void setWakeLock() {
        final PowerManager pom = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pom.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, Constants.WAKE_LOCK);
        this.mWakeLock.acquire();
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

        Log.v("jajaja", String.format("Sample rate %s, Frame buffer %s", a, b));
    }

    private void checkForAudioLowLatency() {
        PackageManager pm = MainActivity.this.getPackageManager();
        boolean claimsFeature = pm.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY);
        if (claimsFeature)
            Log.v("jajaja", "supported");
        else
            Log.v("jajaja", "NOT supported");
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("jajajaFM", "on saved instance state");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mWakeLock.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
        /*int index = 0;
        for (SampleSlot slot : SoundHub.getInstance().getSamplesController().getSampleSlots()) {
            if (slot != null && slot.isPlaying()) {
                SlotView slotView = (SlotView) ((ViewGroup) findViewById(R.string.slotCustomLayout)).getChildAt(index);
                PlayingRotationView rotateView = (PlayingRotationView) slotView.getChildAt(1);
                if (!rotateView.isPlaying())
                    rotateView.setPlaying(true);
            }
            index++;
        }*/
    }

    public void updateFromNative() {

        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*SetLoopDialogFragment fr = (SetLoopDialogFragment) getFragmentManager()
                .findFragmentByTag(SetLoopDialogFragment._TAG);
        if (fr != null) {
            fr.dismiss();

            return true;
        }*/
        return false;
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this, EffectsSamplerContentProvider.SAMPLES_URI, null, null, null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> data) {
        cursorAdapter.changeCursor(null);
    }

    @Override
    public void drawerChange(boolean visible) {
        isDrawerVisible = visible;
    }

    @Override
    public void onDeckCreated(String name) {

        ContentValues values = new ContentValues();
        values.put(DecksHelper.DECK_NAME, name);
        Uri newDeckID = getContentResolver().insert(EffectsSamplerContentProvider.DECKS_URI, values);
        // create deck empty slots in database
        DecksDatabaseHelper.get().setDeck(this, Integer.parseInt(newDeckID.toString()));

        MainControlsFragment fr = (MainControlsFragment) getFragmentManager()
                .findFragmentByTag(MainControlsFragment._TAG);
        if (fr != null)
            fr.setUpdated(true);
    }*/
}
