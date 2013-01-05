package garin.artemiy.simple.app.sqlite.library;

import android.content.Context;
import garin.artemiy.simple.app.sqlite.library.annotations.Column;
import garin.artemiy.simple.app.sqlite.library.util.DatabaseUtil;
import garin.artemiy.simple.app.sqlite.library.util.SharedPreferencesUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class SQLiteDatabaseSimple {

    private SQLiteDatabaseHelper sqLiteDatabaseHelper;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public SQLiteDatabaseSimple(Context context, int databaseVersion) {
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(context, databaseVersion);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    public SQLiteDatabaseSimple(Context context) {
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(context, Constants.FIRST_DATABASE_VERSION);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    private String makeFormatForColumns(int index, Class classEntity) {
        String format;
        if (index == classEntity.getDeclaredFields().length - 1) { // check need default or ");" ending
            format = Constants.SQL_QUERY_APPEND_FORMAT_LAST;
        } else {
            format = Constants.SQL_QUERY_APPEND_FORMAT;
        }
        return format;
    }

    private String notNullOrNot(boolean notNull) {
        if (notNull) {
            return Constants.notNull;
        } else {
            return Constants.EMPTY;
        }
    }

    public void create(Class<?>... classes) { // create table if needs
        List<String> savedTables = sharedPreferencesUtil.getList(Constants.DATABASE_TABLES);
        List<String> savedSQLQueries = sharedPreferencesUtil.getList(Constants.DATABASE_QUERIES);

        sharedPreferencesUtil.clearAllPreferences();

        List<String> tables = new ArrayList<String>();
        List<String> sqlQueries = new ArrayList<String>();

        for (Class classEntity : classes) {
            StringBuilder sqlQuery = new StringBuilder();

            String table = DatabaseUtil.getTableName(classEntity);
            sqlQuery.append(String.format(Constants.CREATE_TABLE_IF_NOT_EXIST, table));

            for (int i = 0; i < classEntity.getDeclaredFields().length; i++) {
                Field fieldEntity = classEntity.getDeclaredFields()[i];
                Column fieldEntityAnnotation = fieldEntity.getAnnotation(Column.class);

                if (fieldEntityAnnotation != null) { // if field what we need not annotated
                    String format = makeFormatForColumns(i, classEntity);
                    String column = DatabaseUtil.getColumnName(fieldEntity);
                    sqlQuery.append(String.format(format, column, fieldEntityAnnotation.type(),
                            notNullOrNot(fieldEntityAnnotation.notNull())));
                }
            }

            tables.add(table);
            sqlQueries.add(sqlQuery.toString());
        }

        sharedPreferencesUtil.putList(Constants.DATABASE_TABLES, tables);
        sharedPreferencesUtil.putList(Constants.DATABASE_QUERIES, sqlQueries);
        sharedPreferencesUtil.commit();
        sqLiteDatabaseHelper.getWritableDatabase(); // call onCreate() on database
    }
}
