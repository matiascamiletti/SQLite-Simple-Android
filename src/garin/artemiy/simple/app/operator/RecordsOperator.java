package garin.artemiy.simple.app.operator;

import android.content.Context;
import garin.artemiy.simple.app.model.Record;
import garin.artemiy.simple.app.sqlite.library.SQLiteDatabaseCRUD;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class RecordsOperator extends SQLiteDatabaseCRUD<Record> {

    public RecordsOperator(Context context) {
        super(Record.class, context);
    }

}
