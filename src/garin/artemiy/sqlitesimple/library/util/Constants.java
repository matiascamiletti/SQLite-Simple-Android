package garin.artemiy.sqlitesimple.library.util;

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
public class Constants {

    private Constants() {
    }

    // shared preferences
    public static final String SHARED_PREFERENCES_DATABASE = "SQLiteSimpleDatabaseHelper";
    public static final String SHARED_DATABASE_TABLES = "SQLiteSimpleDatabaseTables";
    public static final String SHARED_DATABASE_QUERIES = "SQLiteSimpleDatabaseQueries";
    public static final String SHARED_DATABASE_VERSION = "SQLiteSimpleDatabaseVersion";
    public static final String SHARED_PREFERENCES_LIST = "List_%s_%s";
    public static final String SHARED_PREFERENCES_INDEX = "%s_Index";

    // SQL
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String DROP_TABLE_IF_EXIST_TEMPORARY = "DROP TABLE IF EXISTS temporary_table";
    public static final String CREATE_TABLE_IF_NOT_EXIST =
            "CREATE TABLE IF NOT EXISTS %s (_id INTEGER PRIMARY KEY AUTOINCREMENT"; // tableName, other columns
    public static final String ALTER_TABLE_ADD_COLUMN = "ALTER TABLE %s ADD COLUMN %s ";

    // String.format(..)
    public static final String FORMAT_GLUED = "%s%s";
    public static final String FORMAT_TWINS = "%s %s";
    public static final String SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS = ", %s %s";
    public static final String SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS_LAST = ", %s %s);";

    // other
    public static final int FIRST_DATABASE_VERSION = 1;
    public static final String EMPTY = "";
    public static final String DIVIDER = ",";
    public static final String LAST_BRACKET = ");";
}
