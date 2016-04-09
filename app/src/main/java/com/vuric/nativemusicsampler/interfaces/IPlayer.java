package com.vuric.nativemusicsampler.interfaces;

public interface IPlayer {
    void play();
    void pause();
    void stop();
    void load(String path);
    void applyEffect(IEffect effect);
    void loop();
}
