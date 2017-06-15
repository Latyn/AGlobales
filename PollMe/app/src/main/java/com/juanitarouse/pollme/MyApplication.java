package com.juanitarouse.pollme;

/**
 * Created by user on 5/23/2017.
 */
import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


//Hereda de aplication la cual se va a encargar del manejo de datos
public class MyApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("mySecondRealm.realm")//Defaut nombre es default.realm
                .build(); //instancia the realm configuration

        Realm.setDefaultConfiguration(configuration); //De esta manera apuntamos la configuracion a la nueva base de datos que creamos

    }
}
