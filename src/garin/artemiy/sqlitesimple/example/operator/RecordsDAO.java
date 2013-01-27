package garin.artemiy.sqlitesimple.example.operator;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.model.Record;
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
