package com.vuric.nativemusicsampler.events;

/**
 * Created by stefano on 5/2/2016.
 */
public class SampleLoadedEvt {

    private String _title;
    private long _size;

    public SampleLoadedEvt(String title, long size) {
        _title = title;
        _size = size;
    }

    public String getTitle() {
        return _title;
    }

    public long getSize() {
        return _size;
    }
}
