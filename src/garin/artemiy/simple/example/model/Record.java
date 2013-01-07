package garin.artemiy.simple.example.model;

import garin.artemiy.simple.sqlite.library.annotations.Column;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class Record {

    @Column(type = Column.INTEGER)
    public Long dateOfPublication;

    @Column(type = Column.INTEGER)
    public boolean isPublished;

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
