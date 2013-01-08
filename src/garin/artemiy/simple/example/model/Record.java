package garin.artemiy.simple.example.model;

import garin.artemiy.simple.sqlite.annotations.Column;
import garin.artemiy.simple.sqlite.annotations.Table;
import garin.artemiy.simple.sqlite.util.ColumnType;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
@Table
public class Record {

    @Column(type = ColumnType.NUMERIC)
    public boolean isPublished;

    @Column(type = ColumnType.INTEGER)
    public Long dateOfPublication;

    public Long getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(Long dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    @SuppressWarnings("unused")
    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}
