package garin.artemiy.simple.app.sqlite.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import garin.artemiy.simple.app.sqlite.library.util.Constants;
import garin.artemiy.simple.app.sqlite.library.util.SharedPreferencesUtil;

import java.util.List;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_FORMAT = ".db";
    private SharedPreferencesUtil sharedPreferencesUtil;

    public SQLiteDatabaseHelper(Context context, int databaseVersion) {
        super(context, String.format(Constants.FORMAT_GLUED, context.getPackageName(), DB_FORMAT), null, databaseVersion);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
        if (databaseVersion > sharedPreferencesUtil.getDatabaseVersion()) {
            sharedPreferencesUtil.putDatabaseVersion(databaseVersion);
            sharedPreferencesUtil.commit();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> sqlQueries = sharedPreferencesUtil.getList(Constants.SHARED_DATABASE_QUERIES);
        if (sqlQueries != null) { // execute sql queries in order
            for (String sqlQuery : sqlQueries) {
                sqLiteDatabase.execSQL(sqlQuery);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        List<String> tables = sharedPreferencesUtil.getList(Constants.SHARED_DATABASE_TABLES);
        if (tables != null) { // drop tables in order
            for (String table : tables) {
                sqLiteDatabase.execSQL(String.format(Constants.FORMAT_GLUED, Constants.DROP_TABLE_IF_EXISTS, table));
            }
        }
        onCreate(sqLiteDatabase);
    }
}
