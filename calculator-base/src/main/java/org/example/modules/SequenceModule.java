package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.example.consumers.CalculationPrinter;
import org.example.consumers.SequencePrinter;
import org.example.interfaces.SequenceConsumer;
import org.example.predicates.EvenPredicate;

import java.util.function.Predicate;

public class SequenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<SequenceConsumer<Long>>() {})
                .to(new TypeLiteral<SequencePrinter<Long>>() {});

        bind(new TypeLiteral<Predicate<Long>>() {})
                .to(EvenPredicate.class);
    }
}