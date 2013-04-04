package garin.artemiy.sqlitesimple.example.operator;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.MainApplication;
import garin.artemiy.sqlitesimple.example.model.Example;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * Author: Artemiy Garin
 * Date: 03.04.13
 */
public class ExamplesDAO extends SQLiteSimpleDAO<Example> {

    public ExamplesDAO(Context context) {
        super(Example.class, context, MainApplication.LOCAL_DATABASE_NAME);
    }

}
