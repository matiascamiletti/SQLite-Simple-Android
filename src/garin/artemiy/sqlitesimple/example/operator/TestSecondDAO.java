package garin.artemiy.sqlitesimple.example.operator;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.MainApplication;
import garin.artemiy.sqlitesimple.example.model.TestSecond;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * Author: Artemiy Garin
 * Date: 04.04.13
 */
public class TestSecondDAO extends SQLiteSimpleDAO<TestSecond> {

    public TestSecondDAO(Context context) {
        super(TestSecond.class, context, MainApplication.LOCAL_DATABASE_NAME);
    }

}
