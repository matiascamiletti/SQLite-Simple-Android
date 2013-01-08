package garin.artemiy.simple.example.operator;

import android.content.Context;
import garin.artemiy.simple.example.model.Record;
import garin.artemiy.simple.sqlite.SQLiteSimpleDAO;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class RecordsDAO extends SQLiteSimpleDAO<Record> {

    public RecordsDAO(Context context) {
        super(Record.class, context);
    }

}
