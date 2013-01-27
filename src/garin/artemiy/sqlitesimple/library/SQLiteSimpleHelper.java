package garin.artemiy.sqlitesimple.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import garin.artemiy.sqlitesimple.library.util.Constants;
import garin.artemiy.sqlitesimple.library.util.SharedPreferencesUtil;

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
public class SQLiteSimpleHelper extends SQLiteOpenHelper {

    private static final String DB_FORMAT = ".db";
    private SharedPreferencesUtil sharedPreferencesUtil;

    public SQLiteSimpleHelper(Context context, int databaseVersion) {
        super(context, String.format(Constants.FORMAT_GLUED, context.getPackageName(), DB_FORMAT), null, databaseVersion);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
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
        sqLiteDatabase.execSQL(Constants.DROP_TABLE_IF_EXIST_TEMPORARY);
        onCreate(sqLiteDatabase);
    }
}
