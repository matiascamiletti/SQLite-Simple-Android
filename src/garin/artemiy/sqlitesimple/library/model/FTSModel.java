package garin.artemiy.sqlitesimple.library.model;

/**
 * Author: Artemiy Garin
 * Date: 05.04.13
 */
public class FTSModel {

    private long id;
    private int tableCategory;
    private String data;

    @SuppressWarnings("unused")
    public FTSModel(long id, int tableCategory, String data) {
        this.id = id;
        this.tableCategory = tableCategory;
        this.data = data;
    }

    @SuppressWarnings("unused")
    public FTSModel(long id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public long getId() {
        return id;
    }

    public int getTableCategory() {
        return tableCategory;
    }

}
