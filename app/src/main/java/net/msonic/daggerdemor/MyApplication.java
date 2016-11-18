package net.msonic.daggerdemor;

import android.app.Application;

import net.msonic.daggerdemor.compoment.DaggerDiComponent;
import net.msonic.daggerdemor.compoment.DiComponent;

/**
 * Created by manuelzegarra on 11/17/16.
 */

public class MyApplication extends Application {

    DiComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerDiComponent.builder().build();
    }

    public DiComponent getComponent() {
        return component;
    }



}
