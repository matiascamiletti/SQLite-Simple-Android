package garin.artemiy.simple.app.sqlite.library;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class Constants {

    // shared preferences
    public static final String SHARED_PREFERENCES_DATABASE = "SQLiteSimpleDatabaseHelper";
    public static final String DATABASE_TABLES = "SQLiteSimpleDatabaseTables";
    public static final String DATABASE_QUERIES = "SQLiteSimpleDatabaseQueries";
    public static final String DATABASE_VERSION = "SQLiteSimpleDatabaseVersion";
    public static final String SHARED_PREFERENCES_LIST = "List_%s_%s";
    public static final String SHARED_PREFERENCES_INDEX = "%s_Index";

    // sql
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String CREATE_TABLE_IF_NOT_EXIST =
            "CREATE TABLE IF NOT EXISTS %s (_id integer primary key autoincrement"; // tableName, other columns
    public static final String notNull = "not null";

    // String.format(..)
    public static final String FORMAT_GLUED = "%s%s";
    public static final String SQL_QUERY_APPEND_FORMAT = ", %s %s %s";
    public static final String SQL_QUERY_APPEND_FORMAT_LAST = ", %s %s %s);";
    public static final String FORMAT_ARGUMENT = "%s = %s";

    // other
    public static final Object DB_FORMAT = ".db";
    public static final String EMPTY = "";
    public static final String COLUMN_ID = "_id";
    public static final int FIRST_DATABASE_VERSION = 1;
}
