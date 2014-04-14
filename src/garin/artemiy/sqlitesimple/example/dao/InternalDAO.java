package garin.artemiy.sqlitesimple.example.dao;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.MainApplication;
import garin.artemiy.sqlitesimple.example.models.InternalRecord;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * Author: Artemiy Garin
 * Date: 11.04.13
 */
public class InternalDAO extends SQLiteSimpleDAO<InternalRecord> {

    public InternalDAO(Context context) {
        super(InternalRecord.class, context, MainApplication.DATABASE_NAME);
    }

}
