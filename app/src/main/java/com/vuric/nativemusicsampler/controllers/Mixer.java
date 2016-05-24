package com.vuric.nativemusicsampler.controllers;

import com.vuric.nativemusicsampler.interfaces.IMixer;
import com.vuric.nativemusicsampler.models.MixerModel;

/**
 * Created by stefano on 4/9/2016.
 */
public class Mixer implements IMixer{

    MixerModel _model;

    public Mixer() {

        _model = new MixerModel();
    }

    public Mixer(MixerModel model) {

        if(model == null) {
            model = new MixerModel();
        }

        _model = model;
    }

    @Override
    public void setBandLevels(int[] levels) {

    }

    @Override
    public int[] getBandLevels() {
        return new int[0];
    }

    @Override
    public MixerModel getModel() {
        return _model;
    }
}
