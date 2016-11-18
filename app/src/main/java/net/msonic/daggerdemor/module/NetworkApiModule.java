package net.msonic.daggerdemor.module;

import net.msonic.daggerdemor.NetworkApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by manuelzegarra on 11/17/16.
 */

@Module
public class NetworkApiModule {

    @Provides
    @Singleton
    @Named("netA")
    public NetworkApi getNetworkA(){

        NetworkApi networkApi = new NetworkApi();
        networkApi.setUrl("netA");
        return networkApi;
    }


    @Provides
    @Singleton
    @Named("netB")
    public NetworkApi getNetworkB(){

        NetworkApi networkApi = new NetworkApi();
        networkApi.setUrl("netB");
        return networkApi;
    }


}
