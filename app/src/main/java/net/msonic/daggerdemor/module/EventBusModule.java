package net.msonic.daggerdemor.module;

import net.msonic.daggerdemor.bus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by manuelzegarra on 11/18/16.
 */


@Module
public class EventBusModule {



    @Provides
    @Singleton
    public EventBus<Object>  provideEventBus() {

        EventBus<Object> appBus = EventBus.createWithLatest();
        return appBus;
    }

}
