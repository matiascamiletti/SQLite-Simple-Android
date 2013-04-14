package garin.artemiy.sqlitesimple.example.model;

import garin.artemiy.sqlitesimple.library.annotations.Column;
import garin.artemiy.sqlitesimple.library.util.ColumnType;

/**
 * Author: Artemiy Garin
 * Date: 11.04.13
 */
public class Test {

    @Column(type = ColumnType.TEXT)
    @SuppressWarnings("unused")
    private String title;

}
