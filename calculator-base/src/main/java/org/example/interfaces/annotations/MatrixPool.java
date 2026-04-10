package org.example.interfaces.annotations;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Guice binding annotation for the matrix executor thread pool.
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface MatrixPool {
}