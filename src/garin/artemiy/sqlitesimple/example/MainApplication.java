package garin.artemiy.sqlitesimple.example;

import android.app.Application;
import garin.artemiy.sqlitesimple.example.models.InternalRecord;
import garin.artemiy.sqlitesimple.example.models.Record;
import garin.artemiy.sqlitesimple.library.SQLiteSimple;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class MainApplication extends Application {

    public static final String DATABASE_NAME = "test.sqlite";

    @Override
    public void onCreate() {
        super.onCreate();
        if (SimpleDatabaseUtil.isFirstApplicationStart(this)) {
            SQLiteSimple databaseSimple = new SQLiteSimple(this);
            databaseSimple.create(Record.class);

            SQLiteSimple internalSimple = new SQLiteSimple(this, DATABASE_NAME);
            internalSimple.create(InternalRecord.class);
        }
    }

}
