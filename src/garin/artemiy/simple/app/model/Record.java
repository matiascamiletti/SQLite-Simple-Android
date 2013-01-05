package garin.artemiy.simple.app.model;

import garin.artemiy.simple.app.sqlite.library.annotations.Column;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class Record {

    //todo support many then one type - String
    @Column(type = Column.INTEGER, notNull = true)
    public String dateOfPublication;

    @Column(type = Column.INTEGER)
    public String dateOfChanges;

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setDateOfChanges(String dateOfChanges) {
        this.dateOfChanges = dateOfChanges;
    }
}
