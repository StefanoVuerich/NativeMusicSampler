package com.vuric.nativemusicsampler.controllers;

import android.util.Log;

import com.vuric.nativemusicsampler.interfaces.IConsole;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/9/2016.
 */
public class Console implements IConsole {

    @Override
    public boolean init() {
        Log.d(Constants.APP_TAG, "Init console");
        return true;
        //return NativeWrapper.init(Constants.SLOTS);
    }

    @Override
    public boolean shutdown() {
        Log.d(Constants.APP_TAG, "Shutdown console");
        return true;
        //return NativeWrapper.shutdown();
    }
}
