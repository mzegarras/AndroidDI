package net.msonic.daggerdemor.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by manuelzegarra on 11/17/16.
 */



@Module
public class RetrofitModule {


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {

        /*Interceptor interceptorHeader = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                String str = "foo:foosecret";
                String   base64EncodedUsernamePassword = Base64.encodeToString(str.getBytes("UTF-8"), Base64.NO_WRAP);


                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", String.format("Basic %s",base64EncodedUsernamePassword)); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);

            }
        };*/

        HttpLoggingInterceptor interceptorLogging = new HttpLoggingInterceptor();
        interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(interceptorHeader)
                .addInterceptor(interceptorLogging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        return client;
    }

    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    public ObjectMapper provideMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Provides
    @Singleton
    public RxJavaCallAdapterFactory rxJavaCallAdapterFactory(){
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        return rxAdapter;

    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(ObjectMapper mapper,
                                    OkHttpClient okHttpClient,
                                    RxJavaCallAdapterFactory rxJavaCallAdapterFactory) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                //.addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }


}
