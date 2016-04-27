package com.vuric.nativemusicsampler;

import android.app.Application;
import android.graphics.Point;

import com.facebook.stetho.Stetho;

/**
 * Created by stefano on 4/5/2016.
 */
public class CustomApplicationClass extends Application {

    private static CustomApplicationClass _mInstance;
    private Point _screenSize;

    @Override
    public void onCreate() {
        super.onCreate();

        _mInstance = this;
        initializeStetho();
    }

    public static CustomApplicationClass get() {
        return _mInstance;
    }

    private void initializeStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    public Point getScreenSize() {
        return this._screenSize;
    }

    public void setScreenSize(Point size) {
        this._screenSize = size;
    }
}
