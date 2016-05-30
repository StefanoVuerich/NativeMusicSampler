package com.vuric.nativemusicsampler.events;

/**
 * Created by stefano on 5/30/2016.
 */
public class SampleLoadedEvt {

    private int _slotID;

    public SampleLoadedEvt(int id) {
        _slotID = id;
    }

    public int getSlotID() {
        return _slotID;
    }
}
