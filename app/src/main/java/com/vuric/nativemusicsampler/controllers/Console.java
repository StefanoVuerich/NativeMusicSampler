package com.vuric.nativemusicsampler.controllers;

import com.vuric.nativemusicsampler.interfaces.IConsole;
import com.vuric.nativemusicsampler.interfaces.IMixer;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.models.MixerModel;
import com.vuric.nativemusicsampler.models.PlayerModel;
import com.vuric.nativemusicsampler.nativeaudio.NativeReceiver;
import com.vuric.nativemusicsampler.nativeaudio.NativeWrapper;

/**
 * Created by stefano on 4/9/2016.
 */
public class Console implements IConsole {

    private IMixer _mixer;
    private PlayerController[] _players;
    private int _slots;

    public Console(int slots) {

        _mixer = new Mixer();
        _players = new PlayerController[slots];
        _slots = slots;

        initPlayers(slots, null);
        System.loadLibrary("native-audio-jni");
    }

    public Console(int slots, MixerModel mixerModel, PlayerModel[]playerModels) {

        _mixer = new Mixer(mixerModel);
        _players = new PlayerController[slots];
        _slots = slots;

        initPlayers(slots, playerModels);
    }

    private void initPlayers(int slots, PlayerModel[] playerModels) {

        for(int i = 0; i < slots; ++i) {

            if(playerModels != null && playerModels[i].getSampleInfo() != null) {

                _players[i] = new PlayerController(playerModels[i]);
            } else {
                _players[i] = new PlayerController(i);
            }
        }
    }

    @Override
    public void init() {

        NativeWrapper.init(_slots, new int[] {0,0,0,0,0});

        NativeWrapper.initLinker(NativeReceiver.class.getName().replace(".", "/"));
        NativeWrapper.linkCallbackFunction("sampleLoaded", "(I)V");
    }

    @Override
    public void shutdown() {
        NativeWrapper.shutdown();
    }

    @Override
    public IMixer getMixer() {
        return _mixer;
    }

    @Override
    public IPlayer[] getPlayers() {
        return _players;
    }

    @Override
    public PlayerModel[] getPlayerModels() {
        PlayerModel[] models = new PlayerModel[_slots];

        for(int i = 0; i < _slots; ++i) {
            models[i] = _players[i].getModel();
        }

        return models;
    }
}
