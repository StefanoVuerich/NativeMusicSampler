package com.vuric.nativemusicsampler.enums;

/**
 * Created by stefano on 4/24/2016.
 */
public enum AppLayoutState {

    CLOSE(0), OPEN(1);

    private int value;

    AppLayoutState(int value) { this.value = value; }

    public int getValue() {
        return value;
    }
}
