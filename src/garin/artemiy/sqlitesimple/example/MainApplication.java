package garin.artemiy.sqlitesimple.example;

import android.app.Application;
import garin.artemiy.sqlitesimple.example.model.Record;
import garin.artemiy.sqlitesimple.library.SQLiteSimple;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class MainApplication extends Application {

    public static final String LOCAL_DATABASE_NAME = "test.sqlite";

    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteSimple databaseSimple = new SQLiteSimple(this);
        databaseSimple.create(Record.class);

    }

}
