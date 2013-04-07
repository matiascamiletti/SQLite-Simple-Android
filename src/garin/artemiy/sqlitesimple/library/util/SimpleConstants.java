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
public class SimpleConstants {

    private SimpleConstants() {
    }

    // FTS
    public static final int QUERY_LENGTH = 3;
    public static final String FTS_SQL_OR = "OR";
    public static final String FTS_SQL_AND = "AND";
    public static final String FTS_SQL_FORMAT = "SELECT * FROM %s WHERE %s MATCH 'data:\"%s\"*';";
    public static final String FTS_SQL_TABLE_NAME = "%s_FTS";
    public static final String FTS_CREATE_VIRTUAL_TABLE_WITH_CATEGORY =
            "CREATE VIRTUAL TABLE IF NOT EXISTS %s USING fts3(id, tableCategory, data, tokenize = porter);";
    public static final String FTS_CREATE_VIRTUAL_TABLE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS %s USING fts3(id, data, tokenize = porter);";
    public static final String FTS_DROP_VIRTUAL_TABLE = "DROP VIRTUAL TABLE IF EXISTS %s";
    public static final String FTS_INSERT_INTO_WITH_TABLE_CATEGORY = "INSERT INTO %s VALUES (%s, %s, '%s');";
    public static final String FTS_INSERT_INTO = "INSERT INTO %s VALUES (%s, '%s');";

    // Shared preferences
    public static final String SHARED_PREFERENCES_DATABASE = "SQLiteSimpleDatabaseHelper";
    public static final String SHARED_DATABASE_TABLES = "SQLiteSimpleDatabaseTables";
    public static final String SHARED_DATABASE_QUERIES = "SQLiteSimpleDatabaseQueries";
    public static final String SHARED_DATABASE_VERSION = "SQLiteSimpleDatabaseVersion";
    public static final String SHARED_PREFERENCES_LIST = "List_%s_%s";
    public static final String SHARED_PREFERENCES_INDEX = "%s_Index";

    // SQL
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";
    public static final String AUTOINCREMENT = "AUTOINCREMENT";
    public static final String PRIMARY_KEY = "PRIMARY KEY";
    public static final String WHERE_CLAUSE = "%s = '%s'";
    public static final String CREATE_TABLE_IF_NOT_EXIST = "CREATE TABLE IF NOT EXISTS %s (";
    public static final String ALTER_TABLE_ADD_COLUMN = "ALTER TABLE %s ADD COLUMN %s ";

    // String.format(..)
    public static final String FORMAT_GLUED = "%s%s";
    public static final String FORMAT_TWINS = "%s %s";
    public static final String FORMAT_ARGUMENT = "%s = %s";

    // Other
    public static final int FIRST_DATABASE_VERSION = 1;
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String DIVIDER = ",";
    public static final String LAST_BRACKET = ");";
    public static final String DOT = ".";
    public static final String UNDERSCORE = "_";

}
