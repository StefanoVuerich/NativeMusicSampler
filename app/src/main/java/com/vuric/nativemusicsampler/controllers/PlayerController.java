package com.vuric.nativemusicsampler.controllers;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/6/2016.
 */
public class PlayerController implements IPlayer {

    private PlayerModel _model;

    public PlayerController(PlayerModel model) {

        _model = model;
    }

    @Override
    public void play() {
        if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getID(), PlayState.STOP.getValue());
            NativeWrapper.setPlayState(_model.getID(), PlayState.PLAY.getValue());
        } else {
            NativeWrapper.setPlayState(_model.getID(), PlayState.PLAY.getValue());
            _model.setState(PlayState.PLAY);
        }
    }

    @Override
    public void pause() {
        if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getID(), PlayState.PAUSE.getValue());
        }
        Log.d(Constants.APP_TAG, "Pause");
    }

    @Override
    public void stop() {
        /*if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getIndex(), PlayState.STOP.value);
        }*/
        Log.d(Constants.APP_TAG, "Stop");
    }

    @Override
    public void load(String path) {
        //if(_model.isReady()) {
            NativeWrapper.loadSample(_model.getID(), path);
        //}
    }

    @Override
    public void unload() {
        /*if(_model.isLoaded()) {
            return NativeWrapper.unloadSample(_model.getIndex());
        } else {
            return false;
        }*/
    }

    @Override
    public PlayState getPlayState() {
        //return PlayState.values()[NativeWrapper.getPlayState(_model.getIndex())];
        return PlayState.PLAY;
    }

    @Override
    public void enableLoop() {
        /*if(!_model.isLooping()) {
            NativeWrapper.setLoop(_model.getIndex(), true);
            _model.setLoop(true);
        }*/
    }

    @Override
    public void disableeLoop() {
        /*if(_model.isLooping()) {
            NativeWrapper.setLoop(_model.getIndex(), false);
            _model.setLoop(false);
        }*/
    }

    public PlayerModel getModel() {
        return _model;
    }

    public void setColor(String color, View v) {
        v.setBackgroundColor(Color.parseColor(color));
    }

    public void setSelected(boolean selected) {
        if(selected != _model.isSelected()) {
            _model.setSelected(selected);
        }
    }
}
