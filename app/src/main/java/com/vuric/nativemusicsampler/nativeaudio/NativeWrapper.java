package com.vuric.nativemusicsampler.nativeaudio;

/**
 * Created by stefano on 4/9/2016.
 */
public class NativeWrapper {

    static public native void init(int slots, int[] bands);
    static public native void shutdown();
    static public native boolean loadSample(int playerIndex, String path);
    static public native void unloadSample(int playerIndex);
    static public native int setPlayState(int playerIndex, int state);
    static public native int getPlayState(int playerIndex);
    static public native void setBandLevels(int[] levels);
    static public native int[] getBandLevels();
    static public native void setLoop(int playerIndex, boolean loop);
    static public native boolean isLooping(int playerIndex);
    static public native boolean setVolume(int playerIndex, int level);

    static public native void initLinker(String className);
    static public native void linkCallbackFunction(String name, String signature);
}
