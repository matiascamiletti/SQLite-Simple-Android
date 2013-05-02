package garin.artemiy.sqlitesimple.example.model;

import garin.artemiy.sqlitesimple.library.annotations.Column;
import garin.artemiy.sqlitesimple.library.annotations.Table;
import garin.artemiy.sqlitesimple.library.util.ColumnType;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
@Table
public class Record {

    public static final String COLUMN_RECORD_TEXT = "recordText";

    @Column(name = COLUMN_RECORD_TEXT, type = ColumnType.TEXT)
    private String recordText;

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }

}