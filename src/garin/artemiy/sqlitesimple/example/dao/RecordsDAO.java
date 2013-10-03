package garin.artemiy.sqlitesimple.example.dao;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.models.Record;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class RecordsDAO extends SQLiteSimpleDAO<Record> {

    public RecordsDAO(Context context) {
        super(Record.class, context);
    }

}