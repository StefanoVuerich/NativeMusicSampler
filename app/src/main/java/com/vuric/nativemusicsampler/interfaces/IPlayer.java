package com.vuric.nativemusicsampler.interfaces;

import com.vuric.nativemusicsampler.enums.PlayState;

public interface IPlayer {

    void play();
    void pause();
    void stop();
    boolean load(String path);
    boolean unload();
    PlayState getPlayState();
    void enableLoop();
    void disableeLoop();
}
