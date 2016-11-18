package net.msonic.daggerdemor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.msonic.daggerdemor.bus.EventBus;
import net.msonic.daggerdemor.service.DownloadService;
import net.msonic.daggerdemor.ws.data.Main;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {



    //private PublishSubject<String> stringBus = PublishSubject.create();
    //private static final EventBus<Object> sAppBus = EventBus.createWithLatest();//singleton bus
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Subscription mLocationChangesSubscription;

   /* @Inject
    @Named("netB")
    NetworkApi networkApi;

    @Inject
    WeatherService weatherService;*/

    @Inject
    EventBus<Object> sAppBus;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.lblSearch)
    TextView lblSearch;


    @InjectView(R.id.txtSearch)
    EditText txtSearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication) getApplication()).getComponent().inject(this);
        ButterKnife.inject(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final TextView lblSearch = (TextView)findViewById(R.id.lblSearch);


        mLocationChangesSubscription = sAppBus.observeEvents(StatusEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<StatusEvent>(){
                    @Override
                    public void call(StatusEvent locationChangedEvent) {
                        Log.d(MainActivity.class.getCanonicalName(),"sss");


                        switch (locationChangedEvent.getStatus()){
                            case StatusEvent.STATUS_RUNNING:
                                Log.d(Main.class.getCanonicalName(),"STATUS_RUNNING");
                                break;
                            case StatusEvent.STATUS_FINISHED:
                                String data = locationChangedEvent.getWeatherData().getWeather().get(0).getDescription();
                                lblSearch.setText(data);
                                Log.d(Main.class.getCanonicalName(),"STATUS_FINISHED");
                                break;
                            case StatusEvent.STATUS_ERROR:
                                Log.d(Main.class.getCanonicalName(),"STATUS_ERROR");
                                break;
                        }



                    }
                });


        mSubscriptions.add(mLocationChangesSubscription);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                fetchData();

                //stringBus.onNext("event1");
                //stringBus.onNext("event2");


                /*Snackbar.make(view, networkApi.getUrl(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });


        /*stringBus.subscribe(new Action1<String>() {
            @Override
            public void call(String event) {


                Log.d(MainActivity.class.getCanonicalName(), "Event happened: " + event);
            }
        });*/

       /* stringBus.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String event) {
                        Log.d(MainActivity.class.getCanonicalName(), "Event happened: " + event);
                    }
                });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mSubscriptions.unsubscribe();
    }

    private void fetchData() {

        //EditText txtSearch = (EditText)findViewById(R.id.txtSearch);
        String param = txtSearch.getText().toString();

       /* mSubscriptions.add(
                weatherService.call(param)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new WeatherDataSubscriber())
        );*/


        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        intent.putExtra("city", txtSearch.getText().toString());
        startService(intent);

    }


   /* private Subscriber<? super Object> mainBusSubscriber = new Subscriber<Object>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Object o) {
            if (o instanceof  ClickEvent) {
                ClickEvent event = (ClickEvent) o;

                TextView lblSearch = (TextView)findViewById(R.id.lblSearch);

                String data = ((ClickEvent) o).getWeatherData().getWeather().get(0).getDescription();

                lblSearch.setText(data);


                Snackbar.make(event.getView(), "Event Received", Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .show();
            }
        }
    };*/




    /*
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

            TextView lblSearch = (TextView)findViewById(R.id.lblSearch);


            lblSearch.setText(weatherData.getWeather()
                    .get(0)
                    .getDescription());

            Log.v(MainActivity.class.getCanonicalName(), weatherData.getWeather()
                    .get(0)
                    .getDescription());

        }
    }*/
}
