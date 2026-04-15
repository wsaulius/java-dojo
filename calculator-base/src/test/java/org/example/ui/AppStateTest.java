package org.example.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppStateTest {

    @Test
    void shouldInitializeWithAsyncEnabled() {
        AppState state = new AppState();

        assertTrue(state.isAsyncMode());
    }

    @Test
    void shouldToggleAsyncFromTrueToFalse() {
        AppState state = new AppState();

        state.toggleAsync();

        assertFalse(state.isAsyncMode());
    }

    @Test
    void shouldToggleAsyncFromFalseToTrue() {
        AppState state = new AppState();
        state.setAsync(false);

        state.toggleAsync();

        assertTrue(state.isAsyncMode());
    }

    @Test
    void shouldSetAsyncToFalse() {
        AppState state = new AppState();

        state.setAsync(false);

        assertFalse(state.isAsyncMode());
    }

    @Test
    void shouldSetAsyncToTrue() {
        AppState state = new AppState();
        state.setAsync(false);

        state.setAsync(true);

        assertTrue(state.isAsyncMode());
    }
}