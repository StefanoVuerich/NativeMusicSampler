package com.vuric.nativemusicsampler;

import com.vuric.nativemusicsampler.enums.SlotsContainerState;

/**
 * Created by stefano on 4/24/2016.
 */
public class SlotsContainerEvt {

    private SlotsContainerState _state;

    public SlotsContainerEvt(SlotsContainerState _state) {
        this._state = _state;
    }

    public SlotsContainerState getState() {
        return _state;
    }
}
