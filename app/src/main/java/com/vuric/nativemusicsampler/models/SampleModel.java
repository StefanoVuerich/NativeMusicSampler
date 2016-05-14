package com.vuric.nativemusicsampler.models;

import java.io.Serializable;

/**
 * Created by stefano on 4/25/2016.
 */
public class SampleModel implements Serializable{

    private int _ID;
    private String _name;
    private String _path;
    private long _size;
    private float _rate;
    private int _played;
    private String _format;

    public SampleModel() {

    }

    public int getID() {
        return _ID;
    }

    public void setID(int ID) {
        this._ID = ID;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        this._path = path;
    }

    public long getSize() {
        return _size;
    }

    public void setSize(long size) {
        this._size = size;
    }

    public float getRate() {
        return _rate;
    }

    public void setRate(float rate) {
        this._rate = rate;
    }

    public int getPlayed() {
        return _played;
    }

    public void setPlayed(int played) {
        this._played = played;
    }

    public String getFormat() {
        return _format;
    }

    public void setFormat(String format) {
        this._format = format;
    }
}
