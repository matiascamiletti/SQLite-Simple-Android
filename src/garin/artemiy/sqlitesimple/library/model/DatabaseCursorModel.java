package garin.artemiy.sqlitesimple.library.model;

import android.database.Cursor;

/**
 * Author: Artemiy Garin
 * Date: 11.06.13
 */
public class DatabaseCursorModel {

    private Cursor cursor; // todo fix

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void close() {
        cursor.close();
    }

}
