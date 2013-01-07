package garin.artemiy.simple.sqlite.util;

import garin.artemiy.simple.sqlite.annotations.Column;
import garin.artemiy.simple.sqlite.annotations.Table;

import java.lang.reflect.Field;

/**
 * author: Artemiy Garin
 * date: 22.12.2012
 */
public class DatabaseUtil {

    public static String getColumnName(Field field) {
        Column annotationColumn = field.getAnnotation(Column.class);
        String column;
        if (annotationColumn.name().equals(Constants.EMPTY)) {
            column = field.getName();
        } else {
            column = annotationColumn.name();
        }
        return column;
    }

    public static String getTableName(Class<?> tClass) {
        Table annotationTable = tClass.getAnnotation(Table.class);
        String table = tClass.getSimpleName();
        if (annotationTable != null) {
            if (!annotationTable.name().equals(Constants.EMPTY)) {
                table = annotationTable.name();
            }
        }
        return table;
    }

}
