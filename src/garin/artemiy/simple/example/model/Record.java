package garin.artemiy.simple.example.model;

import garin.artemiy.simple.sqlite.annotations.Column;
import garin.artemiy.simple.sqlite.annotations.Table;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
@Table
public class Record {

    @Column(type = Column.NUMERIC)
    public boolean isPublished;

    @Column(type = Column.INTEGER)
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
