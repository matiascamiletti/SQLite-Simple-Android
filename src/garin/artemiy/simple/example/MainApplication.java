package garin.artemiy.simple.example;

import android.app.Application;
import garin.artemiy.simple.example.model.Record;
import garin.artemiy.simple.sqlite.SQLiteDatabaseSimple;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabaseSimple databaseSimple = new SQLiteDatabaseSimple(this);
        databaseSimple.create(Record.class);
    }

}
