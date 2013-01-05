package garin.artemiy.simple.app.sqlite.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import garin.artemiy.simple.app.sqlite.library.util.DatabaseUtil;
import garin.artemiy.simple.app.sqlite.library.util.SharedPreferencesUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Artemiy Garin
 * date: 17.12.2012
 */
public abstract class SQLiteDatabaseCRUD<T> extends SQLiteDatabaseHelper {

    private Class<T> tClass;

    public SQLiteDatabaseCRUD(Class<T> tClass, Context context) {
        super(context, new SharedPreferencesUtil(context).getDatabaseVersion());
        this.tClass = tClass;
    }

    private String[] getAllColumns(Class<T> tClass) {
        List<String> columnsList = new ArrayList<String>();

        columnsList.add(Constants.COLUMN_ID); // default first column in android
        for (Field field : tClass.getDeclaredFields()) {
            columnsList.add(DatabaseUtil.getColumnName(field));
        }

        String[] columnsArray = new String[columnsList.size()];

        return columnsList.toArray(columnsArray);
    }

    private void bindObject(T newTObject, Cursor cursor) throws NoSuchFieldException, IllegalAccessException {
        for (Field field : tClass.getDeclaredFields()) {
            Field classField = tClass.getDeclaredField(field.getName());
            classField.set(newTObject, cursor.getString(cursor.
                    getColumnIndex(DatabaseUtil.getColumnName(field))));
        }
    }

    @SuppressWarnings("unused")
    public long getLastRowId() {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = getAllColumns(tClass);
        String table = DatabaseUtil.getTableName(tClass);
        Cursor cursor = database.query(table, columns, null, null, null, null, null);
        cursor.moveToLast();
        long id = cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_ID));
        cursor.close();
        return id;
    }

    @SuppressWarnings("unused")
    public Cursor selectCursorFromTable() {
        SQLiteDatabase database = getReadableDatabase();
        String[] columns = getAllColumns(tClass);
        String table = DatabaseUtil.getTableName(tClass);
        Cursor cursor = database.query(table, columns, null, null, null, null, null);
        cursor.moveToFirst();
        database.close();

        return cursor;
    }

    @SuppressWarnings("unused")
    public long create(T object) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();

            for (Field field : object.getClass().getDeclaredFields()) {
                contentValues.put(DatabaseUtil.getColumnName(field), (String) field.get(object));
            }

            return database.insert(DatabaseUtil.getTableName(object.getClass()), null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            database.close();
        }
    }

    @SuppressWarnings("unused")
    public T read(long id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(DatabaseUtil.getTableName(tClass), getAllColumns(tClass),
                String.format(Constants.FORMAT_ARGUMENT, Constants.COLUMN_ID, Long.toString(id)), null, null, null, null);
        try {
            T newTObject = tClass.newInstance();
            bindObject(newTObject, cursor);

            return newTObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            database.close();
            cursor.close();
        } // in this case we close cursor, because we use database.query, NOT incoming cursor
    }

    @SuppressWarnings("unused")
    public T read(Cursor cursor) {
        try {
            T newTObject = tClass.newInstance();
            bindObject(newTObject, cursor);
            return newTObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } // don't close cursor, because CursorAdapter use it!
    }

    @SuppressWarnings("unused")
    public List<T> readAll() {
        Cursor cursor = selectCursorFromTable();
        try {
            List<T> list = new ArrayList<T>();

            for (int i = 0; i < cursor.getCount(); i++) {
                T newTObject = tClass.newInstance();
                bindObject(newTObject, cursor);
                list.add(newTObject);
                cursor.moveToNext();
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            cursor.close();
        }
    }

    @SuppressWarnings("unused")
    public long update(long id, T newObject) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();

            for (Field field : newObject.getClass().getDeclaredFields()) {
                contentValues.put(DatabaseUtil.getColumnName(field), (String) field.get(newObject));
            }

            return database.update(DatabaseUtil.getTableName(newObject.getClass()), contentValues,
                    String.format(Constants.FORMAT_ARGUMENT, Constants.COLUMN_ID, id), null);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            database.close();
        }
    }

    @SuppressWarnings("unused")
    public int delete(long id) {
        SQLiteDatabase database = getWritableDatabase();

        int deletedRow = database.delete(
                DatabaseUtil.getTableName(tClass), String.format(Constants.FORMAT_ARGUMENT, Constants.COLUMN_ID, id), null);

        database.close();
        return deletedRow;
    }

    @SuppressWarnings("unused")
    public int deleteAll() {
        SQLiteDatabase database = getWritableDatabase();

        int deletedRow = database.delete(DatabaseUtil.getTableName(tClass), null, null);

        database.close();
        return deletedRow;
    }

}
