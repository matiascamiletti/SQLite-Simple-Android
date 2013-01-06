package garin.artemiy.simple.app.sqlite.library.annotations;


import garin.artemiy.simple.app.sqlite.library.util.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Column {

    @SuppressWarnings("unused")
    public static final String TEXT = "TEXT";
    @SuppressWarnings("unused")
    public static final String REAL = "REAL";
    @SuppressWarnings("unused")
    public static final String NUMERIC = "NUMERIC";
    @SuppressWarnings("unused")
    public static final String INTEGER = "INTEGER";

    String name() default Constants.EMPTY;

    String type();

    boolean notNull() default false;
}
