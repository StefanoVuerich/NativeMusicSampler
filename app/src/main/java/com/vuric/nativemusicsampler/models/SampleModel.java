package com.vuric.nativemusicsampler.models;

import java.io.Serializable;

/**
 * Created by stefano on 4/25/2016.
 */
public class SampleModel implements Serializable{

    private int _ID;
    private String _name;
    private String _artist;
    private String _title;
    private String _path;
    private int _hours;
    private int _minutes;
    private int _seconds;
    private int _bitRate;
    private long _size;
    private float _rate;
    private int _played;
    private String _format;

    public SampleModel() {

    }

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getArtist() {
        return _artist;
    }

    public void setArtist(String _artist) {
        this._artist = _artist;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }

    public int getHours() {
        return _hours;
    }

    public void setHours(int _hours) {
        this._hours = _hours;
    }

    public int getMinutes() {
        return _minutes;
    }

    public void setMinutes(int _minutes) {
        this._minutes = _minutes;
    }

    public int getSeconds() {
        return _seconds;
    }

    public void setSeconds(int _seconds) {
        this._seconds = _seconds;
    }

    public int getBitrate() {
        return _bitRate;
    }

    public void setBitrate(int bitrate) {
        _bitRate = bitrate;
    }

    public long getSize() {
        return _size;
    }

    public void setSize(long _size) {
        this._size = _size;
    }

    public float getRate() {
        return _rate;
    }

    public void setRate(float _rate) {
        this._rate = _rate;
    }

    public int getPlayed() {
        return _played;
    }

    public void setPlayed(int _played) {
        this._played = _played;
    }

    public String getFormat() {
        return _format;
    }

    public void setFormat(String _format) {
        this._format = _format;
    }
}
