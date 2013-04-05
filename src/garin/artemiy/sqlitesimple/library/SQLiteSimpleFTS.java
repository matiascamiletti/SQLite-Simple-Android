package garin.artemiy.sqlitesimple.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;
import garin.artemiy.sqlitesimple.library.util.SimplePreferencesUtil;

import java.util.List;

/**
 * Author: Artemiy Garin
 * Date: 04.04.13
 */
public class SQLiteSimpleFTS {

    private SQLiteDatabase database;
    private String tableName;

    @SuppressWarnings("unused")
    public SQLiteSimpleFTS(Context context, List<FTSModel> ftsModels) {
        init(context, ftsModels, null);
    }

    @SuppressWarnings("unused")
    public SQLiteSimpleFTS(Context context, List<FTSModel> ftsModels, String localDatabaseName) {
        init(context, ftsModels, localDatabaseName);
    }

    private void init(Context context, List<FTSModel> ftsModels, String localDatabaseName) {
        tableName = String.format(SimpleConstants.FTS_SQL_TABLE_NAME, context.getPackageName().
                replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE));

        SQLiteSimpleHelper simpleHelper = new SQLiteSimpleHelper(context,
                new SimplePreferencesUtil(context).getDatabaseVersion(), localDatabaseName);
        database = simpleHelper.getWritableDatabase();

        String createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE, tableName);
        database.execSQL(createVirtualFTSTable);

        for (FTSModel ftsModel : ftsModels) {
            database.execSQL(String.format(SimpleConstants.INSERT_INTO, tableName,
                    ftsModel.getId(), ftsModel.getTable(), ftsModel.getData().toLowerCase()));
        }

    }

    public Cursor search(String query, String searchedColumn) {
        if (query.contains(SimpleConstants.FTS_SQL_OR) ||
                query.contains(SimpleConstants.FTS_SQL_AND) ||
                query.length() < SimpleConstants.QUERY_LENGTH) {
            return null;
        }

        Cursor cursor;

        if (searchedColumn == null) {
            cursor = database.rawQuery(String.format(SimpleConstants.FTS_SQL_FORMAT, tableName,
                    tableName, query.toLowerCase()), null);
        } else {
            cursor = database.rawQuery(String.format(SimpleConstants.FTS_SQL_FORMAT, tableName,
                    searchedColumn, query.toLowerCase()), null);
        }

        return cursor;
    }

}
