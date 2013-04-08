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
 * author: Artemiy Garin
 * Copyright (C) 2013 SQLite Simple Project
 * *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * *
 * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
            createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE_WITH_CATEGORY, tableName,
                    COLUMN_ID, COLUMN_TABLE_CATEGORY, COLUMN_DATA);
        } else {
            createVirtualFTSTable = String.format(SimpleConstants.FTS_CREATE_VIRTUAL_TABLE, tableName,
                    COLUMN_ID, COLUMN_DATA);
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
    public List<FTSModel> search(String query, boolean resultDesc) {
        List<FTSModel> ftsModels = new ArrayList<FTSModel>();

        if (query.contains(SimpleConstants.FTS_SQL_OR) ||
                query.contains(SimpleConstants.FTS_SQL_AND) ||
                query.length() < SimpleConstants.QUERY_LENGTH) {
            return ftsModels;
        }

        String format;
        if (resultDesc) {
            format = String.format(SimpleConstants.FTS_SQL_FORMAT, tableName, tableName, COLUMN_DATA, query.toLowerCase(),
                    COLUMN_ID, SimpleConstants.DESC);
        } else {
            format = String.format(SimpleConstants.FTS_SQL_FORMAT, tableName, tableName, COLUMN_DATA, query.toLowerCase(),
                    COLUMN_ID, SimpleConstants.ASC);
        }

        Cursor cursor = database.rawQuery(format, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

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

            cursor.moveToNext();
            ftsModels.add(ftsModel);
        }

        return ftsModels;
    }

}
