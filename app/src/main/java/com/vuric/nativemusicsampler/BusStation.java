package com.vuric.nativemusicsampler;

import com.squareup.otto.Bus;

/**
 * Created by stefano on 4/24/2016.
 */
public class BusStation {

    private static Bus _bus = new MainThreadBus();

    public static Bus getBus() {
        return _bus;
    }
}
