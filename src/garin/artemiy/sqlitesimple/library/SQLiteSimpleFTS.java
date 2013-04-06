package garin.artemiy.sqlitesimple.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;
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
    public SQLiteSimpleFTS(Context context) {
        init(context, null);
    }

    @SuppressWarnings("unused")
    public SQLiteSimpleFTS(Context context, String localDatabaseName) {
        init(context, localDatabaseName);
    }

    private void init(Context context, String localDatabaseName) {
        SQLiteSimpleHelper simpleHelper = new SQLiteSimpleHelper(context,
                new SimplePreferencesUtil(context).getDatabaseVersion(), localDatabaseName);
        database = simpleHelper.getWritableDatabase();

        tableName = SimpleDatabaseUtil.getFTSTableName(context);
        String createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE, tableName);
        database.execSQL(createVirtualFTSTable);
    }

    @SuppressWarnings("unused")
    public void create(FTSModel ftsModel) {
        database.execSQL(String.format(SimpleConstants.INSERT_INTO, tableName,
                ftsModel.getId(), ftsModel.getTable(), ftsModel.getData().toLowerCase()));
    }

    @SuppressWarnings("unused")
    public void createAll(List<FTSModel> ftsModels) {
        for (FTSModel ftsModel : ftsModels) {
            create(ftsModel);
        }
    }

    public Cursor search(String query) {
        if (query.contains(SimpleConstants.FTS_SQL_OR) ||
                query.contains(SimpleConstants.FTS_SQL_AND) ||
                query.length() < SimpleConstants.QUERY_LENGTH) {
            return null;
        }

        return database.rawQuery(String.format(SimpleConstants.FTS_SQL_FORMAT, tableName,
                tableName, query.toLowerCase()), null);
    }

}
