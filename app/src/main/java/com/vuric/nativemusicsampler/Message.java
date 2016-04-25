package com.vuric.nativemusicsampler;

import com.vuric.nativemusicsampler.enums.SlotsContainerState;

/**
 * Created by stefano on 4/24/2016.
 */
public class Message {

    private SlotsContainerState _state;

    public Message(SlotsContainerState _state) {
        this._state = _state;
    }

    public SlotsContainerState get_state() {
        return _state;
    }
}
