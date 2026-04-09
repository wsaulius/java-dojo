package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.services.BinarySelector;
import org.example.services.UnaryBigIntegerSelector;
import org.example.services.UnaryBooleanSelector;
import org.example.services.UnaryDoubleSelector;
import org.example.services.UnaryIntSelector;
import org.example.services.UnaryLongSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectorModuleTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(
                new BinaryOperationModule(),
                new UnaryIntOperationModule(),
                new UnaryDoubleOperationModule(),
                new UnaryLongOperationModule(),
                new UnaryBooleanOperationModule(),
                new UnaryBigIntegerOperationModule(),
                new SelectorModule()
        );
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveAllSelectors() {
        assertNotNull(injector.getInstance(BinarySelector.class));
        assertNotNull(injector.getInstance(UnaryIntSelector.class));
        assertNotNull(injector.getInstance(UnaryDoubleSelector.class));
        assertNotNull(injector.getInstance(UnaryLongSelector.class));
        assertNotNull(injector.getInstance(UnaryBooleanSelector.class));
        assertNotNull(injector.getInstance(UnaryBigIntegerSelector.class));
    }
}