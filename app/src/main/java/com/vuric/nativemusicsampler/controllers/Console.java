package com.vuric.nativemusicsampler.controllers;

import com.vuric.nativemusicsampler.interfaces.IConsole;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/9/2016.
 */
public class Console implements IConsole {

    static {
        System.loadLibrary("native-audio-jni");
    }

    @Override
    public void init() {
        NativeWrapper.init(Constants.SLOTS, new int[] {0,0,0,0,0});
    }

    @Override
    public void shutdown() {
        NativeWrapper.shutdown();
    }
}
