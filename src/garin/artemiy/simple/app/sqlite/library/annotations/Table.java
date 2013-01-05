package garin.artemiy.simple.app.sqlite.library.annotations;


import garin.artemiy.simple.app.sqlite.library.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: Artemiy Garin
 * date: 13.12.12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Table {

    String name() default Constants.EMPTY;
}
