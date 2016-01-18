package ua.madless.lingowl.manager;

import com.squareup.otto.Bus;

/**
 * Created by madless on 07.01.2016.
 */
public class EventBusManager {
    private static Bus bus;
    public static Bus getBus() {
        if(bus == null) {
            bus = new Bus();
        }
        return bus;
    }
    private EventBusManager() {
    }
}
