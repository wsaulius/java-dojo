package org.example.modules;

import com.google.inject.AbstractModule;

import org.example.services.*;

/**
 * Guice module that binds selector services used to resolve
 * operation implementations by type.
 */
public class SelectorModule extends AbstractModule {

    /**
     * Configures selector bindings.
     */
    @Override
    protected void configure() {
        bind(UnaryIntSelector.class);
        bind(UnaryDoubleSelector.class);
        bind(UnaryLongSelector.class);
        bind(UnaryBooleanSelector.class);
        bind(BinarySelector.class);
        bind(UnaryBigIntegerSelector.class);
    }
}