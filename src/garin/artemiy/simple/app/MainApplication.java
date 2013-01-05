package garin.artemiy.simple.app;

import android.app.Application;
import garin.artemiy.simple.app.model.Record;
import garin.artemiy.simple.app.sqlite.library.SQLiteDatabaseSimple;

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
