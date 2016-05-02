package com.vuric.nativemusicsampler.models;

import com.vuric.nativemusicsampler.enums.PlayState;

import java.io.Serializable;

/**
 * Created by stefano on 4/7/2016.
 */
public class PlayerModel implements Serializable {

    private int _ID;
    private boolean _loop;
    private PlayState _state;
    private boolean _ready;
    private boolean _loaded;
    private boolean _selected;
    private SampleObj _sampleInfo;

    public PlayerModel() {}

    public PlayerModel(int index, boolean loop, PlayState state, boolean ready, boolean loaded, boolean selected, SampleObj sampleInfo) {
        _ID = index;
        _loop = loop;
        _state = state;
        _ready = ready;
        _loaded = loaded;
        _selected = selected;
        _sampleInfo = sampleInfo;
    }

    public SampleObj getSampleInfo() {
        return _sampleInfo;
    }

    public boolean isLoaded() {
        return _loaded;
    }

    public void setLoaded(boolean _loaded) {
        this._loaded = _loaded;
    }

    public boolean isReady() {
        return _ready;
    }

    public void setReady(boolean _ready) {
        this._ready = _ready;
    }

    public PlayState getState() {
        return _state;
    }

    public void setState(PlayState _state) {
        this._state = _state;
    }

    public boolean isLooping() {
        return _loop;
    }

    public void setLoop(boolean _loop) {
        this._loop = _loop;
    }

    public int getID() {
        return _ID;
    }

    public void setID(int _index) {
        this._ID = _index;
    }

    public boolean isSelected() {
        return _selected;
    }

    public void setSelected(boolean _selected) {
        this._selected = _selected;
    }

    public void setSampleInfo(SampleObj sampleInfo) {
        _sampleInfo = sampleInfo;
    }
}
