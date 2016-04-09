package com.vuric.nativemusicsampler.controllers;

import android.content.Context;
import android.widget.Toast;

import com.vuric.nativemusicsampler.interfaces.IEffect;
import com.vuric.nativemusicsampler.interfaces.IPlayer;
import com.vuric.nativemusicsampler.models.PlayerModel;

/**
 * Created by stefano on 4/6/2016.
 */
public class Player implements IPlayer {

    private Context _context;
    private PlayerModel _model;

    public Player(Context context) {

        _context = context;
        _model = new PlayerModel();
    }

    @Override
    public void play() {

        if(_model.state == 0) {
            Toast.makeText(_context, "Play", Toast.LENGTH_SHORT).show();
            _model.state = 1;
        } else if(_model.state == 1) {
            Toast.makeText(_context, "REPlay", Toast.LENGTH_SHORT).show();
            _model.state = 0;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void load(String path) {
        Toast.makeText(_context, "Load", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyEffect(IEffect effect) {

    }

    @Override
    public void loop() {
        if(!_model.loop) {
            Toast.makeText(_context, "Enable Loop", Toast.LENGTH_SHORT).show();
            _model.loop = true;
        } else {
            Toast.makeText(_context, "Disable Loop", Toast.LENGTH_SHORT).show();
            _model.loop = false;
        }
    }
}
