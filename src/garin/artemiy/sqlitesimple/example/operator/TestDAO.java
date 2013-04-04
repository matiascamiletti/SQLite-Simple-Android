package garin.artemiy.sqlitesimple.example.operator;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.MainApplication;
import garin.artemiy.sqlitesimple.example.model.Test;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * Author: Artemiy Garin
 * Date: 03.04.13
 */
public class TestDAO extends SQLiteSimpleDAO<Test> {

    public TestDAO(Context context) {
        super(Test.class, context, MainApplication.LOCAL_DATABASE_NAME);
    }

}
