package org.example.modules;

import com.google.inject.AbstractModule;

import org.example.services.*;

public class SelectorModule extends AbstractModule {

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