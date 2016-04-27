package com.vuric.nativemusicsampler;

/**
 * Created by stefano on 4/25/2016.
 */
public class SelectSamplesSlotEvt {

    private int _viewID;

    public SelectSamplesSlotEvt(int viewID) {

        _viewID = viewID;
    }

    public int getViewID() {
        return _viewID;
    }
}
