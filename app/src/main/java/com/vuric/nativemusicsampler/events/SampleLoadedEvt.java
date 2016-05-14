package com.vuric.nativemusicsampler.events;

/**
 * Created by stefano on 5/2/2016.
 */
public class SampleLoadedEvt {

    private String _title;
    private long _size;
    private int _ID;

    public SampleLoadedEvt(String title, long size, int id) {
        _title = title;
        _size = size;
        _ID = id;
    }

    public String getTitle() {
        return _title;
    }

    public long getSize() {
        return _size;
    }

    public int getID() {
        return _ID;
    }
}
