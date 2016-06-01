package com.vuric.nativemusicsampler.events;

/**
 * Created by stefano on 5/30/2016.
 */
public class PlayerStatusUpdateEvt {

    private int _slotID;

    public PlayerStatusUpdateEvt(int id) {
        _slotID = id;
    }

    public int getSlotID() {
        return _slotID;
    }
}
