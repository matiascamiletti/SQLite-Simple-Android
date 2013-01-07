package garin.artemiy.simple.sqlite.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import garin.artemiy.simple.sqlite.library.annotations.Column;
import garin.artemiy.simple.sqlite.library.util.Constants;
import garin.artemiy.simple.sqlite.library.util.DatabaseUtil;
import garin.artemiy.simple.sqlite.library.util.SharedPreferencesUtil;

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
    private int databaseVersion;

    public SQLiteDatabaseSimple(Context context, int databaseVersion) {
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(context, databaseVersion);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
        this.databaseVersion = databaseVersion;
    }

    public SQLiteDatabaseSimple(Context context) {
        sqLiteDatabaseHelper = new SQLiteDatabaseHelper(context, Constants.FIRST_DATABASE_VERSION);
        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    private String makeFormatForColumnsTwoArguments(int index, Class classEntity) {
        String format;
        if (index == classEntity.getDeclaredFields().length - 1) { // check need default or ");" ending
            format = Constants.SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS_LAST;
        } else {
            format = Constants.SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS;
        }
        return format;
    }

    private String makeFormatForColumnsThreeArguments(int index, Class classEntity) {
        String format;
        if (index == classEntity.getDeclaredFields().length - 1) { // check need default or ");" ending
            format = Constants.SQL_QUERY_APPEND_FORMAT_THREE_ARGUMENTS_LAST;
        } else {
            format = Constants.SQL_QUERY_APPEND_FORMAT_THREE_ARGUMENTS;
        }
        return format;
    }

    private String notNullOrNot(boolean notNull) {
        if (notNull) {
            return Constants.NOT_NULL;
        } else {
            return Constants.EMPTY;
        }
    }

    public void create(Class<?>... classes) { // create table if needs
        List<String> savedTables = sharedPreferencesUtil.getList(Constants.SHARED_DATABASE_TABLES);
        List<String> savedSQLQueries = sharedPreferencesUtil.getList(Constants.SHARED_DATABASE_QUERIES);

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
                    String format;
                    String column = DatabaseUtil.getColumnName(fieldEntity);
                    if (fieldEntityAnnotation.notNull()) {
                        format = makeFormatForColumnsThreeArguments(i, classEntity);
                        sqlQuery.append(String.format(format, column, fieldEntityAnnotation.type(),
                                notNullOrNot(fieldEntityAnnotation.notNull())));
                    } else {
                        format = makeFormatForColumnsTwoArguments(i, classEntity);
                        sqlQuery.append(String.format(format, column, fieldEntityAnnotation.type()));
                    }
                }
            }

            tables.add(table);
            sqlQueries.add(sqlQuery.toString());
        }

        boolean newDatabaseVersion = false;
        if (databaseVersion > sharedPreferencesUtil.getDatabaseVersion()) // check whether or not call getWritableDatabase();
            newDatabaseVersion = true;
        commitAndRebase(savedTables, tables, sqlQueries, savedSQLQueries, newDatabaseVersion);
    }

    // commit, add columns to table if need
    private void commitAndRebase(List<String> savedTables, List<String> tables,
                                 List<String> sqlQueries, List<String> savedSQLQueries, boolean newDatabaseVersion) {

        // commit
        if (savedTables.hashCode() != tables.hashCode() || newDatabaseVersion) {
            sharedPreferencesUtil.putList(Constants.SHARED_DATABASE_TABLES, tables);
            sharedPreferencesUtil.putList(Constants.SHARED_DATABASE_QUERIES, sqlQueries);
            sharedPreferencesUtil.commit();
            sqLiteDatabaseHelper.getWritableDatabase(); // call onCreate() on database
        }

        // if change name of column or add new column
        if (savedSQLQueries.hashCode() != sqlQueries.hashCode() && savedSQLQueries.hashCode() != 1) {
            for (int i = 0; i < sqlQueries.size(); i++) {
                String table = tables.get(i);
                String[] columnsArray = getColumnsArray(sqlQueries, table, i);
                String[] savedColumnsArray = getColumnsArray(savedSQLQueries, savedTables.get(i), i);

                for (String column : columnsArray) {
                    if (!column.equals(Constants.EMPTY)) {
                        boolean isNewColumn = true;

                        for (String savedColumn : savedColumnsArray) {
                            if (column.equals(savedColumn)) {
                                isNewColumn = false;
                                break;
                            }
                        }

                        if (isNewColumn) {
                            SQLiteDatabase sqLiteDatabase = sqLiteDatabaseHelper.getWritableDatabase();
                            sqLiteDatabase.execSQL(String.format(Constants.ALTER_TABLE, table, column));
                            sqLiteDatabase.close();
                        }
                    }
                }
            }
        }
    }

    private String[] getColumnsArray(List<String> sqlQueries, String table, int i) {
        String queries = sqlQueries.get(i);
        String columns = queries.replace(Constants.CREATE_TABLE_IF_NOT_EXIST.
                replace(Constants.FORMAT_SINGLE, table), Constants.EMPTY).
                replace(Constants.SQL_PARENTHESES_ENDING, Constants.EMPTY);
        return columns.split(Constants.DIVIDER);
    }
}
