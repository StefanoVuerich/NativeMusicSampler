package com.vuric.nativemusicsampler.events;

import com.vuric.nativemusicsampler.enums.AppLayoutState;

/**
 * Created by stefano on 4/24/2016.
 */
public class SlotsContainerEvt {

    private AppLayoutState _state;

    public SlotsContainerEvt(AppLayoutState _state) {
        this._state = _state;
    }

    public AppLayoutState getState() {
        return _state;
    }
}
