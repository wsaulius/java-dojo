package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.example.consumers.SequencePrinter;
import org.example.predicates.EvenPredicate;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class SequenceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<BiConsumer<List<Long>, Predicate<Long>>>() {}).to(SequencePrinter.class);
        bind(new TypeLiteral<Predicate<Long>>() {}).to(EvenPredicate.class);
    }
}