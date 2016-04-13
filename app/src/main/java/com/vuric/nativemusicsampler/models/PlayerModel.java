package com.vuric.nativemusicsampler.models;

import com.vuric.nativemusicsampler.enums.PlayState;

/**
 * Created by stefano on 4/7/2016.
 */
public class PlayerModel {

    private int _index;
    private boolean _loop;
    private PlayState _state;
    private boolean _ready;
    private boolean _loaded;

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

    public int getIndex() {
        return _index;
    }

    public void setIndex(int _index) {
        this._index = _index;
    }


}
