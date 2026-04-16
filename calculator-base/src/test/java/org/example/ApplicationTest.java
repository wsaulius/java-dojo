package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.modules.CalculatorApplicationModule;
import org.example.ui.ConsoleApp;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    @Test
    void constructor_createsInstance() {
        Application application = new Application();
        assertNotNull(application);
    }

    @Test
    void main_createsInjector_andStartsConsoleApp() throws Exception {
        Injector injector = mock(Injector.class);
        ConsoleApp consoleApp = mock(ConsoleApp.class);

        when(injector.getInstance(ConsoleApp.class)).thenReturn(consoleApp);

        try (MockedStatic<Guice> guice = mockStatic(Guice.class)) {
            guice.when(() -> Guice.createInjector(any(CalculatorApplicationModule.class)))
                    .thenReturn(injector);

            Application.main(new String[0]);

            guice.verify(() -> Guice.createInjector(any(CalculatorApplicationModule.class)));
            verify(injector).getInstance(ConsoleApp.class);
            verify(consoleApp).start();
        }
    }
}