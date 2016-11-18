package net.msonic.daggerdemor.module;

import net.msonic.daggerdemor.ws.service.WeatherService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by manuelzegarra on 11/18/16.
 */

@Module
public class RestServiceModule {

    @Provides
    @Singleton
    public WeatherService provideWeatherService(Retrofit retrofit) {

        WeatherService weatherService = new WeatherService(retrofit);
        return weatherService;
    }
}
