package com.vuric.nativemusicsampler.events;

import com.vuric.nativemusicsampler.models.SampleModel;

/**
 * Created by stefano on 5/2/2016.
 */
public class SampleLoadedEvt {

    private int _slotID;
    private SampleModel _model;

    public SampleLoadedEvt(int id, SampleModel model) {
        _slotID = id;
        _model = model;
    }

    public int getSlotID() {
        return _slotID;
    }

    public SampleModel getModel() {
        return _model;
    }
}
