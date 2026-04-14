package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {

    @Test
    void shouldCreateApplication() {
        assertDoesNotThrow(Application::new);
    }

    @Test
    void shouldRunMain() {
        assertDoesNotThrow(() -> Application.main(new String[0]));
    }
}