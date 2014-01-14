package garin.artemiy.sqlitesimple.example;

import android.app.Application;
import garin.artemiy.sqlitesimple.example.models.Record;
import garin.artemiy.sqlitesimple.example.models.Test;
import garin.artemiy.sqlitesimple.library.SQLiteSimple;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class MainApplication extends Application {

    private static final String DATABASE_NAME = "test.sqlite";

    @Override
    public void onCreate() {
        super.onCreate();

        if (SimpleDatabaseUtil.isFirstApplicationStart(this)) {
            SQLiteSimple databaseSimple = new SQLiteSimple(this);
            databaseSimple.create(Record.class);

            SQLiteSimple localSimple = new SQLiteSimple(this, DATABASE_NAME);
            localSimple.create(Test.class);
        }

    }

}
