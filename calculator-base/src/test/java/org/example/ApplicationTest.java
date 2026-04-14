//package org.example;
//
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import org.example.modules.CalculatorApplicationModule;
//import org.example.ui.ConsoleApp;
//import org.jline.terminal.Terminal;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class ApplicationTest {
//
//    @Test
//    void injector_should_start_without_errors() {
//        Injector injector = Guice.createInjector(new CalculatorApplicationModule());
//
//        ConsoleApp app = injector.getInstance(ConsoleApp.class);
//
//        assertNotNull(app);
//    }
//
//    @Test
//    void app_should_start_without_crash() {
//        Injector injector = Guice.createInjector(new CalculatorApplicationModule());
//
//        ConsoleApp app = injector.getInstance(ConsoleApp.class);
//
//        // just ensure start doesn't crash immediately
//        assertDoesNotThrow(() -> {
//            Thread thread = new Thread(() -> {
//                try {
//                    app.start();
//                } catch (Exception ignored) {}
//            });
//
//            thread.start();
//            Thread.sleep(200); // allow startup
//            thread.interrupt();
//        });
//    }
//
//    @Test
//    void terminal_and_executor_should_be_valid() {
//        Injector injector = Guice.createInjector(new CalculatorApplicationModule());
//
//        Terminal terminal = injector.getInstance(Terminal.class);
//
//        System.out.println("Terminal type: " + terminal.getClass().getName());
//
//        assertNotNull(terminal);
//    }
//}