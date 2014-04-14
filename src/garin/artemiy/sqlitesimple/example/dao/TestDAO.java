package garin.artemiy.sqlitesimple.example.dao;

import android.content.Context;
import garin.artemiy.sqlitesimple.example.models.Test;
import garin.artemiy.sqlitesimple.library.SQLiteSimpleDAO;

/**
 * Author: Artemiy Garin
 * Date: 11.04.13
 */
public class TestDAO extends SQLiteSimpleDAO<Test> {

    private static transient final String DATABASE_NAME = "test.sqlite";

    public TestDAO(Context context) {
        super(Test.class, context, DATABASE_NAME);
    }

}
