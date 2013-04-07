package garin.artemiy.sqlitesimple.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import garin.artemiy.sqlitesimple.library.model.FTSModel;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;
import garin.artemiy.sqlitesimple.library.util.SimplePreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artemiy Garin
 * Date: 04.04.13
 */
public class SQLiteSimpleFTS {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TABLE_CATEGORY = "tableCategory";
    private static final String COLUMN_DATA = "data";

    private SQLiteDatabase database;
    private String tableName;
    private boolean useTablesCategory;

    @SuppressWarnings("unused")
    public SQLiteSimpleFTS(Context context, boolean useTablesCategory) {
        this.useTablesCategory = useTablesCategory;
        SQLiteSimpleHelper simpleHelper = new SQLiteSimpleHelper(context,
                new SimplePreferencesUtil(context).getDatabaseVersion(), null);
        database = simpleHelper.getWritableDatabase();
        tableName = SimpleDatabaseUtil.getFTSTableName(context);

        String createVirtualFTSTable;
        if (useTablesCategory) {
            createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE_WITH_CATEGORY, tableName);
        } else {
            createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE, tableName);
        }

        database.execSQL(createVirtualFTSTable);
    }

    @SuppressWarnings("unused")
    public void dropTable() {
        database.execSQL(String.format(SimpleConstants.FTS_DROP_VIRTUAL_TABLE, tableName));
    }

    @SuppressWarnings("unused")
    public void create(FTSModel ftsModel) {
        if (useTablesCategory) {
            database.execSQL(String.format(SimpleConstants.FTS_INSERT_INTO_WITH_TABLE_CATEGORY, tableName,
                    ftsModel.getId(), ftsModel.getTableCategory(), ftsModel.getData().toLowerCase()));
        } else {
            database.execSQL(String.format(SimpleConstants.FTS_INSERT_INTO, tableName,
                    ftsModel.getId(), ftsModel.getData().toLowerCase()));
        }
    }

    @SuppressWarnings("unused")
    public void createAll(List<FTSModel> ftsModels) {
        for (FTSModel ftsModel : ftsModels) {
            create(ftsModel);
        }
    }

    @SuppressWarnings("unused")
    public List<FTSModel> search(String query) {
        List<FTSModel> ftsModels = new ArrayList<FTSModel>();

        if (query.contains(SimpleConstants.FTS_SQL_OR) ||
                query.contains(SimpleConstants.FTS_SQL_AND) ||
                query.length() < SimpleConstants.QUERY_LENGTH) {
            return ftsModels;
        }

        Cursor cursor = database.rawQuery(String.format(SimpleConstants.FTS_SQL_FORMAT, tableName,
                tableName, query.toLowerCase()), null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            FTSModel ftsModel;
            if (useTablesCategory) {
                ftsModel = new FTSModel(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TABLE_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
            } else {
                ftsModel = new FTSModel(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
            }
            ftsModels.add(ftsModel);
        }

        return ftsModels;
    }

}
