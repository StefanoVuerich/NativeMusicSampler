package com.vuric.nativemusicsampler;

import android.app.Application;
import android.graphics.Point;

import com.facebook.stetho.Stetho;

/**
 * Created by stefano on 4/5/2016.
 */
public class CustomApplicationClass extends Application {

    private static CustomApplicationClass mInstance;
    private Point screenSize;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        initializeStetho();

        /*soundHub = SoundHub.getInstance();
        soundHub.initialize(Constants.SLOTS, getApplicationContext());

        checkSamplerEffectsFolder();
        registerActivityLifecycleCallbacks(new ActivityCallBacks());

        startWatchFolderForChanges();

        this.currentSelectedSlots = new LinkedList<Integer>();
        this.selectedObservers = new LinkedList<AbstractSelectedObserver>();*/
    }

    public static CustomApplicationClass get() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //OpenSLES.shutdownEngine();
    }

    private void initializeStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public Point getScreenSize() {
        return this.screenSize;
    }

    public void setScreenSize(Point size) {
        this.screenSize = size;
    }
}
