package net.msonic.daggerdemor.ws.service;

import net.msonic.daggerdemor.ws.contract.WeatherContract;
import net.msonic.daggerdemor.ws.data.WeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by manuelzegarra on 11/18/16.
 */

public class WeatherService {

    private Retrofit retrofit;

    public WeatherService(Retrofit retrofit){
        this.retrofit=retrofit;
    }

    public Observable<WeatherData> call(String query){

        WeatherContract weatherService = retrofit.create(WeatherContract.class);
        Observable<WeatherData> observable = weatherService.query(query);//"Islamabad");

        return observable;
    }

    public Call<WeatherData> call1(String query){

        WeatherContract weatherService = retrofit.create(WeatherContract.class);
        Call<WeatherData> call = weatherService.query1(query);//"Islamabad");

        return call;
    }



}

