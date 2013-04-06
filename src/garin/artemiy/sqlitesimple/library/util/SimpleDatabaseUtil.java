package garin.artemiy.sqlitesimple.library.util;

import android.content.Context;
import garin.artemiy.sqlitesimple.library.annotations.Column;
import garin.artemiy.sqlitesimple.library.annotations.Table;

import java.lang.reflect.Field;

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
public class SimpleDatabaseUtil {

    private static final String DB_FORMAT = ".db";
    private static final String DATABASE_PATH = "/data/data/%s/databases/%s";

    public static String getColumnName(Field field) {
        Column annotationColumn = field.getAnnotation(Column.class);
        String column = null;
        if (annotationColumn != null) {
            if (annotationColumn.name().equals(SimpleConstants.EMPTY)) {
                column = field.getName();
            } else {
                column = annotationColumn.name();
            }
        }
        return column;
    }

    public static String getTableName(Class<?> tClass) {
        Table annotationTable = tClass.getAnnotation(Table.class);
        String table = tClass.getSimpleName();
        if (annotationTable != null) {
            if (!annotationTable.name().equals(SimpleConstants.EMPTY)) {
                table = annotationTable.name();
            }
        }
        return table;
    }

    public static String getFullDatabasePath(Context context, String databaseName) {
        return String.format(DATABASE_PATH, context.getPackageName(), databaseName);
    }

    public static String getFullDatabaseName(String localDatabaseName, Context context) {
        if (localDatabaseName == null) {
            return String.format(SimpleConstants.FORMAT_GLUED, context.getPackageName(), DB_FORMAT).
                    replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE).toUpperCase();
        } else {
            return localDatabaseName;
        }
    }

    public static String getFTSTableName(Context context) {
        return String.format(SimpleConstants.FTS_SQL_TABLE_NAME, context.getPackageName()).
                replace(SimpleConstants.DOT, SimpleConstants.UNDERSCORE).toUpperCase();
    }

}
