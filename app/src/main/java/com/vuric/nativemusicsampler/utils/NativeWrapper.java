package com.vuric.nativemusicsampler.utils;

/**
 * Created by stefano on 4/9/2016.
 */
public class NativeWrapper {

    static public native boolean init(int slots);
    static public native boolean linkCallbackFunctions();
    static public native boolean shutdown();
    static public native boolean loadSample(int playerIndex, String path);
    static public native boolean unloadSample(int playerIndex);
    static public native int setPlayState(int playerIndex, int state);
    static public native int getPlayState(int playerIndex);
    static public native boolean setBandLevels(int[] levels);
    static public native int[] getBandLevels();
    static public native void setLoop(int playerIndex, boolean loop);
    static public native void isLooping(int playerIndex);
}
