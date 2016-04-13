package com.vuric.nativemusicsampler.enums;

/**
 * Created by stefano on 4/9/2016.
 */
public enum PlayState {

    STOP(0), PLAY(1), PAUSE(2);

    public int value;

    PlayState(int value) {
        this.value = value;
    }
}
