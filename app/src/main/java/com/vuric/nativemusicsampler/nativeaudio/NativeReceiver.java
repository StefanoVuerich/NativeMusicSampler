package com.vuric.nativemusicsampler.nativeaudio;

import com.vuric.nativemusicsampler.BusStation;
import com.vuric.nativemusicsampler.events.PlayerStatusUpdateEvt;

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

    public static void playerStatusUpdate(int index) {

        BusStation.getBus().post(new PlayerStatusUpdateEvt(index));
    }
}
