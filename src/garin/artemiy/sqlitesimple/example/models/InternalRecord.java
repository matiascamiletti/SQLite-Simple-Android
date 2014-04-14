package garin.artemiy.sqlitesimple.example.models;

import garin.artemiy.sqlitesimple.library.annotations.Column;
import garin.artemiy.sqlitesimple.library.annotations.Table;

/**
 * Author: Artemiy Garin
 * Date: 11.04.13
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "test")
public class InternalRecord {

    @Column
    private String title;

}
