package com.vuric.nativemusicsampler.controllers;

import com.vuric.nativemusicsampler.interfaces.IMixer;

/**
 * Created by stefano on 4/9/2016.
 */
public class Mixer implements IMixer{

    @Override
    public void setBandLevels(int[] levels) {

    }

    @Override
    public int[] getBandLevels() {
        return new int[0];
    }
}
