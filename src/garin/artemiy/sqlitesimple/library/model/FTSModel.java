package garin.artemiy.sqlitesimple.library.model;

/**
 * Author: Artemiy Garin
 * Date: 05.04.13
 */
public class FTSModel {

    private long id;
    private int table;
    private String data;

    public FTSModel(long id, int table, String data) {
        this.id = id;
        this.table = table;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public long getId() {
        return id;
    }

    public int getTable() {
        return table;
    }

}
