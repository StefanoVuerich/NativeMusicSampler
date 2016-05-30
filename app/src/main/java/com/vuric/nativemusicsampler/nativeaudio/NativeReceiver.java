package com.vuric.nativemusicsampler.nativeaudio;

import com.vuric.nativemusicsampler.BusStation;

/**
 * Created by stefano on 5/1/2016.
 */
public class NativeReceiver {

    private static NativeReceiver mInstance;

    private NativeReceiver() {}

    public static NativeReceiver getInstace() {

        if(mInstance == null) {
            mInstance = new NativeReceiver();
            BusStation.getBus().register(mInstance);
        }

        return mInstance;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        BusStation.getBus().unregister(mInstance);
    }

    public static void sampleLoaded(final int index) {


    }
}
