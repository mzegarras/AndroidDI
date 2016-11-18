package net.msonic.daggerdemor.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import net.msonic.daggerdemor.StatusEvent;
import net.msonic.daggerdemor.MyApplication;
import net.msonic.daggerdemor.bus.EventBus;
import net.msonic.daggerdemor.ws.data.WeatherData;
import net.msonic.daggerdemor.ws.service.WeatherService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by manuelzegarra on 11/18/16.
 */

public class DownloadService extends IntentService {


    @Inject
    WeatherService weatherService;

    //private static final EventBus<Object> sAppBus = EventBus.createWithLatest();//singleton bus

    @Inject
    EventBus<Object> sAppBus;


    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((MyApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String city = intent.getStringExtra("city");

        if (!TextUtils.isEmpty(city)) {

            StatusEvent clickEvent = new StatusEvent(StatusEvent.STATUS_RUNNING,null);
            sAppBus.post(clickEvent);


            weatherService.call1(city).enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    if(response.isSuccessful()){

                        StatusEvent clickEvent = new StatusEvent(StatusEvent.STATUS_FINISHED,response.body());
                        sAppBus.post(clickEvent);

                    }else{

                        StatusEvent clickEvent = new StatusEvent(StatusEvent.STATUS_ERROR,response.body());
                        sAppBus.post(clickEvent);

                        Log.d(DownloadService.class.getCanonicalName(), "Error");
                    }

                    Log.d(TAG, "Service Stopping!");
                    DownloadService.this.stopSelf();

                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    Log.d(DownloadService.class.getCanonicalName(), t.getMessage());
                    StatusEvent clickEvent = new StatusEvent(StatusEvent.STATUS_ERROR,null);
                    sAppBus.post(clickEvent);

                }
            });


        }






        /*weatherService.call("Islamabad")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new WeatherDataSubscriber());*/




    }


    private class WeatherDataSubscriber extends Subscriber<WeatherData> {


        @Override
        public void onCompleted() {
            // mLoginView.whenLoginSucess();
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {

            }

            e.printStackTrace();
        }

        @Override
        public void onNext(WeatherData weatherData) {

            StatusEvent clickEvent = new StatusEvent(StatusEvent.STATUS_FINISHED,weatherData);
            sAppBus.post(clickEvent);


            Log.d(TAG, "Service Stopping!");
            DownloadService.this.stopSelf();


        }
    }
}
