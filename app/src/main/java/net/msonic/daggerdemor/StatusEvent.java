package net.msonic.daggerdemor;

import net.msonic.daggerdemor.ws.data.WeatherData;

/**
 * Created by manuelzegarra on 11/18/16.
 */

public class StatusEvent {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public WeatherData getWeatherData() {
        return weatherData;
    }

    final private WeatherData weatherData;
    final private int status;

    public StatusEvent(int status,WeatherData weatherData) {
        this.status=status;
        this.weatherData = weatherData;
    }


    public int getStatus() {
        return status;
    }
}
