package net.msonic.daggerdemor.ws.contract;

import net.msonic.daggerdemor.ws.data.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manuelzegarra on 11/18/16.
 */

public interface WeatherContract {

    //http://api.openweathermap.org/data/2.5/weather?q=London&id=524901&APPID=07145f4ed38d2dd403e363c86f40054a
    @GET("weather?id=524901&APPID=07145f4ed38d2dd403e363c86f40054a")
    rx.Observable<WeatherData> query(@Query("q") String city);

    @GET("weather?id=524901&APPID=07145f4ed38d2dd403e363c86f40054a")
    Call<WeatherData> query1(@Query("q") String city);

}
