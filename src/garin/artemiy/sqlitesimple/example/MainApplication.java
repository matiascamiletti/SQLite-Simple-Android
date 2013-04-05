package garin.artemiy.sqlitesimple.example;

import android.app.Application;
import garin.artemiy.sqlitesimple.example.model.Record;
import garin.artemiy.sqlitesimple.example.model.Test;
import garin.artemiy.sqlitesimple.example.model.TestSecond;
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

        SQLiteSimple databaseSimple = new SQLiteSimple(this, 2);
        databaseSimple.create(Record.class);

        // If sqlite data contain large amount, create task
        SQLiteSimple databaseSimpleLocal = new SQLiteSimple(this, LOCAL_DATABASE_NAME);
        databaseSimpleLocal.create(TestSecond.class, Test.class);

    }

}
