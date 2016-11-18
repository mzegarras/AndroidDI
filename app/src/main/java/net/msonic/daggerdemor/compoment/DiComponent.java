package net.msonic.daggerdemor.compoment;

import net.msonic.daggerdemor.MainActivity;
import net.msonic.daggerdemor.module.EventBusModule;
import net.msonic.daggerdemor.module.NetworkApiModule;
import net.msonic.daggerdemor.module.RestServiceModule;
import net.msonic.daggerdemor.module.RetrofitModule;
import net.msonic.daggerdemor.service.DownloadService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by manuelzegarra on 11/17/16.
 */

@Singleton
@Component(modules = {NetworkApiModule.class,
                      RetrofitModule.class,
                        RestServiceModule.class, EventBusModule.class})
public interface DiComponent {

    void inject(MainActivity activity);
    void inject(DownloadService downloadService);

}
