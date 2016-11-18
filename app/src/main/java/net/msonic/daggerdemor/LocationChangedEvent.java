package net.msonic.daggerdemor;

import net.msonic.daggerdemor.ws.data.WeatherData;

/**
 * Created by manuelzegarra on 11/18/16.
 */

public class LocationChangedEvent {


    private final WeatherData mLocation;

    public LocationChangedEvent(WeatherData location) {
        mLocation = location;
    }

    public WeatherData getLocation() {
        return mLocation;
    }


}
