package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.modules.CalculatorApplicationModule;
import org.example.ui.ConsoleApp;

/**
 * Application entry point. Bootstraps the Guice injector and starts execution.
 */
public class Application {

    /**
     * Initializes dependency injection and runs the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new CalculatorApplicationModule());
        injector.getInstance(ConsoleApp.class).start();
    }
}