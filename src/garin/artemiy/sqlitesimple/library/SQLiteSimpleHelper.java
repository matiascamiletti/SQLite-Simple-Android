package garin.artemiy.sqlitesimple.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import garin.artemiy.sqlitesimple.library.util.SimpleConstants;
import garin.artemiy.sqlitesimple.library.util.SimpleDatabaseUtil;
import garin.artemiy.sqlitesimple.library.util.SimplePreferencesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    private String localDatabaseName;
    private Context context;
    private SimplePreferencesUtil sharedPreferencesUtil;
    private String sharedPreferencesPlace;

    /**
     * @param localDatabaseName - load local sqlite if need
     */
    public SQLiteSimpleHelper(Context context, String sharedPreferencesPlace,
                              int databaseVersion, String localDatabaseName) {
        super(context, SimpleDatabaseUtil.getFullDatabaseName(localDatabaseName, context), null, databaseVersion);
        this.localDatabaseName = localDatabaseName;
        this.context = context;
        this.sharedPreferencesPlace = sharedPreferencesPlace;
        sharedPreferencesUtil = new SimplePreferencesUtil(context);
    }

    public SQLiteSimpleHelper(Context context,
                              int databaseVersion, String localDatabaseName) {
        super(context, SimpleDatabaseUtil.getFullDatabaseName(localDatabaseName, context), null, databaseVersion);
        this.localDatabaseName = localDatabaseName;
        this.context = context;
        sharedPreferencesUtil = new SimplePreferencesUtil(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> sqlQueries = sharedPreferencesUtil.getList(
                String.format(SimpleConstants.SHARED_DATABASE_QUERIES, sharedPreferencesPlace));
        if (sqlQueries != null) { // execute sql queries in order
            for (String sqlQuery : sqlQueries) {
                sqLiteDatabase.execSQL(sqlQuery);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // todo: Delete all data when database version upgrade
        List<String> tables = sharedPreferencesUtil.getList(
                String.format(SimpleConstants.SHARED_DATABASE_TABLES, sharedPreferencesPlace));
        if (tables != null) { // drop tables in order
            for (String table : tables) {
                sqLiteDatabase.execSQL(String.format(SimpleConstants.FORMAT_TWINS,
                        SimpleConstants.DROP_TABLE_IF_EXISTS, table));
            }
        }

        onCreate(sqLiteDatabase);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        checkDatabaseFromAssets();
        if (localDatabaseName == null) {
            return super.getWritableDatabase();
        } else {
            return SQLiteDatabase.openDatabase(SimpleDatabaseUtil.getFullDatabasePath(context, localDatabaseName),
                    null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        checkDatabaseFromAssets();
        if (localDatabaseName == null) {
            return super.getReadableDatabase();
        } else {
            return SQLiteDatabase.openDatabase(SimpleDatabaseUtil.getFullDatabasePath(context, localDatabaseName),
                    null, SQLiteDatabase.OPEN_READONLY);
        }
    }

    private void checkDatabaseFromAssets() {
        if (localDatabaseName != null) {
            if (!isDatabaseExist()) {
                super.getWritableDatabase(); // create empty database
                super.close();
                copyDatabaseFromAssets();
            }
        }
    }

    private void copyDatabaseFromAssets() {
        try {

            InputStream inputStream = context.getAssets().open(localDatabaseName);
            OutputStream outputStream = new FileOutputStream(SimpleDatabaseUtil.getFullDatabasePath(context,
                    localDatabaseName));

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDatabaseExist() {
        return new File(SimpleDatabaseUtil.getFullDatabasePath(context, localDatabaseName)).exists();
    }

}
