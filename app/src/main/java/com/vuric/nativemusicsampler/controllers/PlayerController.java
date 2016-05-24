package com.vuric.nativemusicsampler.controllers;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.enums.PlayState;
import com.vuric.nativemusicsampler.events.SampleLoadedEvt;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.models.SampleModel;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;
import com.vuric.nativemusicsampler.utils.Constants;

/**
 * Created by stefano on 4/6/2016.
 */
public class PlayerController implements IPlayer {

    private PlayerModel _model;

    public PlayerController(int id) {

        _model = new PlayerModel();
        _model.setID(id);

        init();
    }

    public PlayerController(PlayerModel model) {

        if(model != null) {
            _model = model;
        }

        init();
    }

    private void init() {

        BusStation.getBus().register(this);
    }

    @Subscribe
    public void receiveMessage(SampleLoadedEvt evt) {

        if(evt.getSlotID() == _model.getID()) {
            _model.setSampleInfo(evt.getModel());
            _model.setLoaded(true);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        BusStation.getBus().unregister(this);
    }

    @Override
    public void play() {

        if(!_model.isLoaded())
            return;

        if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getID(), PlayState.STOP.getValue());
            NativeWrapper.setPlayState(_model.getID(), PlayState.PLAY.getValue());
        } else {
            NativeWrapper.setPlayState(2, PlayState.PLAY.getValue());
            _model.setState(PlayState.PLAY);
        }
    }

    @Override
    public void pause() {

        if(!_model.isLoaded())
            return;

        if(_model.getState() == PlayState.PLAY) {
            NativeWrapper.setPlayState(_model.getID(), PlayState.PAUSE.getValue());
        }
        Log.d(Constants.APP_TAG, "Pause");
    }

    @Override
    public void stop() {

        if(!_model.isLoaded())
            return;

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

    @Override
    public PlayerModel getPlayerModel() {
        return _model;
    }

    @Override
    public void setSample(SampleModel model) {
        _model.setSampleInfo(model);
        _model.setLoaded(true);
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
