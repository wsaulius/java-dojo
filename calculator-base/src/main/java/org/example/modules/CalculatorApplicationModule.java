package org.example.modules;

import com.google.inject.AbstractModule;
import org.jline.terminal.Terminal;

/**
 * Root Guice module that installs all calculator application modules.
 */
public class CalculatorApplicationModule extends AbstractModule {

    /**
     * Configures and installs all application submodules.
     */
    @Override
    protected void configure() {
        install(new UnaryIntOperationModule());
        install(new UnaryDoubleOperationModule());
        install(new UnaryLongOperationModule());
        install(new UnaryBooleanOperationModule());
        install(new UnaryBigIntegerOperationModule());
        install(new BinaryOperationModule());
        install(new SequenceModule());
        install(new SelectorModule());
        install(new CalculatorConsumerModule());
        install(new ExecutorModule());
        install(new UiModule());}
}