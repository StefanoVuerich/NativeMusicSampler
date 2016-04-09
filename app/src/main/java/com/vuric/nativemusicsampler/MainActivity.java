package com.vuric.nativemusicsampler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vuric.nativemusicsampler.fragments.SamplerControlsFragment;
import com.vuric.nativemusicsampler.fragments.SamplerSlotsFragment;

public class MainActivity extends Activity implements View.OnTouchListener {

    public static final String WAKE_LOCK = "WAKE_LOCK";
    protected PowerManager.WakeLock mWakeLock;
    private Point screenSize;
    //private CustomSampleListView samplesList;
    private FrameLayout controlsContainer;
    private int controlsContainerWidth, controlsContainerHeight;
    //private SamplesCursorAdapter cursorAdapter;
    //private static final int ITEMS_LOADER_ID = 0;
    //private CustomDrawer drawer;
    //private boolean isDrawerVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //CustomApplicationClass.get().setMainActivity(this);

        View samplerBaseContainer = findViewById(R.id.samplerBaseContainer);
        samplerBaseContainer.setOnTouchListener(this);

        if (savedInstanceState == null) {
            // checkForAudioLowLatency();
            // getRateAndFrames();
            getScreenSizeAndSendValueToApplicationClass();
            // checkForNewFiles();
        }
        setWakeLock();
        setButtonsAndControls();

        //drawer = (CustomDrawer) findViewById(R.id.drawer);
    }


    /*public boolean isDrawerVisible() {
        return isDrawerVisible;
    }*/


    private void getScreenSizeAndSendValueToApplicationClass() {
        screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        CustomApplicationClass.get().setScreenSize(screenSize);
    }

    private void setWakeLock() {
        final PowerManager pom = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pom.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, WAKE_LOCK);
        this.mWakeLock.acquire();
    }

    /**
     * Set right side fragment, set container layout listener, call check for
     * new files
     */

    private void setButtonsAndControls() {
        Fragment sampleButtonFragment, controlsFragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        sampleButtonFragment = SamplerSlotsFragment.getInstance();
        controlsFragment = SamplerControlsFragment.getInstance();
        ft.replace(R.id.samplerSlotsContainer, sampleButtonFragment, SamplerSlotsFragment._TAG);
        ft.replace(R.id.samplerControlsContainer, controlsFragment, SamplerControlsFragment._TAG);
        ft.commit();

        controlsContainer = (FrameLayout) findViewById(R.id.samplerControlsContainer);
        ViewTreeObserver vto = controlsContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                controlsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                controlsContainerWidth = controlsContainer.getMeasuredWidth();
                controlsContainerHeight = controlsContainer.getMeasuredHeight();
                checkForNewFiles();

            }
        });
    }

    private void checkForNewFiles() {
        // FilesChecker.get().checkFiles(getApplicationContext());
        //setEffectsList();
        // need to set listener for new files added when application is running
    }

    /*private void setEffectsList() {

        samplesList = (CustomSampleListView) findViewById(R.id.samplesListView);
        samplesList.addHeaderView(LayoutInflater.from(this).inflate(R.layout.download_center_image, null));

        // Set sampleList params
        DrawerLayout.LayoutParams paramsContainer = new android.support.v4.widget.DrawerLayout.LayoutParams(
                controlsContainerWidth, controlsContainerHeight);
        paramsContainer.width = controlsContainerWidth;
        paramsContainer.height = -1;
        paramsContainer.gravity = Gravity.END;
        paramsContainer.topMargin = findViewById(R.id.rightButtonsFragmentContainer).getMeasuredHeight();
        samplesList.setLayoutParams(paramsContainer);
        samplesList.requestLayout();

        // Set Adapter
        cursorAdapter = new SamplesCursorAdapter(this, null);
        samplesList.setAdapter(cursorAdapter);
        samplesList.setMyAdapter(cursorAdapter);
        // cursorAdapter.initializeSampleTouchListener();
        samplesList.setOnItemClickListener(new SampleListItemClickListener());
        // samplesList.setOnItemLongClickListener(new
        // SampleListItemLongClickListener());
        getLoaderManager().initLoader(ITEMS_LOADER_ID, null, this);

        final ViewGroup slotContainer = (ViewGroup) findViewById(R.string.slotCustomLayout);
        ViewTreeObserver vto = slotContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                controlsContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                controlsContainerWidth = controlsContainer.getMeasuredWidth();
                controlsContainerHeight = controlsContainer.getMeasuredHeight();
                samplesList.setListenerLayout(slotContainer);
            }
        });
    }*/

    @Override
    public void onBackPressed() {
        /*if (isDrawerVisible)
            closeDrawer();*/
    }

    /*public void closeDrawer() {
        drawer.closeDrawer(Gravity.END);
    }*/

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
        Log.v("jajajaFM", "on destroy view");
        // OpenSLES.shutdownEngine();
        this.mWakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
