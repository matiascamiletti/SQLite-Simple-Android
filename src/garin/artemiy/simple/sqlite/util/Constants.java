package garin.artemiy.simple.sqlite.util;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
public class Constants {

    // shared preferences
    public static final String SHARED_PREFERENCES_DATABASE = "SQLiteSimpleDatabaseHelper";
    public static final String SHARED_DATABASE_TABLES = "SQLiteSimpleDatabaseTables";
    public static final String SHARED_DATABASE_QUERIES = "SQLiteSimpleDatabaseQueries";
    public static final String SHARED_DATABASE_VERSION = "SQLiteSimpleDatabaseVersion";
    public static final String SHARED_PREFERENCES_LIST = "List_%s_%s";
    public static final String SHARED_PREFERENCES_INDEX = "%s_Index";

    // SQL
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String DROP_TABLE_IF_EXIST_TEMPORARY = "DROP TABLE IF EXISTS temporary_table";
    public static final String CREATE_TABLE_IF_NOT_EXIST =
            "CREATE TABLE IF NOT EXISTS %s (_id INTEGER PRIMARY KEY AUTOINCREMENT"; // tableName, other columns
    public static final String ALTER_TABLE_ADD_COLUMN = "ALTER TABLE %s ADD COLUMN %s ";

    // String.format(..)
    public static final String FORMAT_GLUED = "%s%s";
    public static final String FORMAT_TWINS = "%s %s";
    public static final String SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS = ", %s %s";
    public static final String SQL_QUERY_APPEND_FORMAT_TWO_ARGUMENTS_LAST = ", %s %s);";

    // other
    public static final int FIRST_DATABASE_VERSION = 1;
    public static final String EMPTY = "";
    public static final String DIVIDER = ",";
    public static final String LAST_BRACKET = ");";
}
