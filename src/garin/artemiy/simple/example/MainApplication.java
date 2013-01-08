package garin.artemiy.simple.example;

import android.app.Application;
import garin.artemiy.simple.example.model.Record;
import garin.artemiy.simple.sqlite.SQLiteSimple;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteSimple databaseSimple = new SQLiteSimple(this);
        databaseSimple.create(Record.class);
    }

}
