package org.example.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.example.interfaces.SequenceConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class SequenceModuleTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = Guice.createInjector(new SequenceModule());
    }

    @Test
    void shouldCreateInjectorSuccessfully() {
        assertNotNull(injector);
    }

    @Test
    void shouldResolveSequenceBindings() {
        SequenceConsumer<Long> sequenceConsumer =
                injector.getInstance(Key.get(new TypeLiteral<SequenceConsumer<Long>>() {}));

        Predicate<Long> evenPredicate =
                injector.getInstance(Key.get(new TypeLiteral<Predicate<Long>>() {}));

        assertNotNull(sequenceConsumer);
        assertNotNull(evenPredicate);
    }

    @Test
    void shouldUseBoundPredicateCorrectly() {
        Predicate<Long> evenPredicate =
                injector.getInstance(Key.get(new TypeLiteral<Predicate<Long>>() {}));

        assertTrue(evenPredicate.test(8L));
        assertFalse(evenPredicate.test(7L));
    }
}