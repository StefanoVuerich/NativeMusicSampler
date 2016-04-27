package com.vuric.nativemusicsampler.interfaces;

import com.vuric.nativemusicsampler.enums.PlayState;

public interface IPlayer {

    void play();
    void pause();
    void stop();
    void load(String path);
    void unload();
    PlayState getPlayState();
    void enableLoop();
    void disableeLoop();
}
