package garin.artemiy.simple.example.operator;

import android.content.Context;
import garin.artemiy.simple.example.model.Record;
import garin.artemiy.simple.sqlite.library.SQLiteDatabaseCRUD;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class RecordsOperator extends SQLiteDatabaseCRUD<Record> {

    public RecordsOperator(Context context) {
        super(Record.class, context);
    }

}
