package ua.madless.lingowl.bus;

import com.squareup.otto.Bus;

/**
 * Created by madless on 07.01.2016.
 */
public class LingllamaBus {
    private static Bus bus;
    public static Bus getBus() {
        if(bus == null) {
            bus = new Bus();
        }
        return bus;
    }
    private LingllamaBus() {
    }
}
