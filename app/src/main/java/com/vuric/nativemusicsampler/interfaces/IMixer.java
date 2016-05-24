package com.vuric.nativemusicsampler.interfaces;

import com.vuric.nativemusicsampler.models.MixerModel;

/**
 * Created by stefano on 4/9/2016.
 */
public interface IMixer {

    void setBandLevels(int[] levels);
    int[] getBandLevels();
    MixerModel getModel();
}
