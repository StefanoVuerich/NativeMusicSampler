package com.vuric.nativemusicsampler.controllers;

import android.util.Log;

import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.utils.Constants;

import java.lang.reflect.Method;

/**
 * Created by stefano on 4/6/2016.
 */
public class Player implements IPlayer {

    private PlayerModel _model;

    public Player() {

        String name = this.getClass().getName();
        Method[] methods = this.getClass().getMethods();

        _model = new PlayerModel();
    }

    @Override
    public void play() {
        /*if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getIndex(), PlayState.STOP.value);
            NativeWrapper.setPlayState(_model.getIndex(), PlayState.PLAY.value);
        } else {
            NativeWrapper.setPlayState(_model.getIndex(), PlayState.PLAY.value);
        }*/
        Log.d(Constants.APP_TAG, "Play");
    }

    @Override
    public void pause() {
        /*if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getIndex(), PlayState.PAUSE.value);
        }*/
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
    public boolean load(String path) {
        /*if(_model.isReady()) {
            return NativeWrapper.loadSample(_model.getIndex(), path);
        } else {
            return false;
        }*/
        Log.d(Constants.APP_TAG, "Load");
        return true;
    }

    @Override
    public boolean unload() {
        /*if(_model.isLoaded()) {
            return NativeWrapper.unloadSample(_model.getIndex());
        } else {
            return false;
        }*/
        return true;
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
}
