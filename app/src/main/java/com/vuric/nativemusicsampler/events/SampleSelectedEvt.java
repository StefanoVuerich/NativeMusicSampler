package com.vuric.nativemusicsampler.events;

/**
 * Created by stefano on 5/1/2016.
 */
public class SampleSelectedEvt {

    private String _path;
    private int _slotID;

    public SampleSelectedEvt(String _path, int _slotID) {
        this._path = _path;
        this._slotID = _slotID;
    }

    public String getPath() {
        return _path;
    }

    public int getSlotID() {
        return _slotID;
    }
}
