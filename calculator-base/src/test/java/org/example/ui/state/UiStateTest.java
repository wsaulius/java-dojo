package org.example.ui.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiStateTest {

    @Test
    void shouldInitializeWithMainScreen() {
        UiState uiState = new UiState();

        assertEquals(UiState.Screen.MAIN, uiState.getScreen());
    }

    @Test
    void shouldInitializeWithSyncMode() {
        UiState uiState = new UiState();

        assertFalse(uiState.isAsyncMode());
    }

    @Test
    void shouldSetScreenToUnary() {
        UiState uiState = new UiState();

        uiState.setScreen(UiState.Screen.UNARY);

        assertEquals(UiState.Screen.UNARY, uiState.getScreen());
    }

    @Test
    void shouldSetScreenToBinary() {
        UiState uiState = new UiState();

        uiState.setScreen(UiState.Screen.BINARY);

        assertEquals(UiState.Screen.BINARY, uiState.getScreen());
    }

    @Test
    void shouldSetScreenToMatrix() {
        UiState uiState = new UiState();

        uiState.setScreen(UiState.Screen.MATRIX);

        assertEquals(UiState.Screen.MATRIX, uiState.getScreen());
    }

    @Test
    void shouldToggleAsyncModeFromFalseToTrue() {
        UiState uiState = new UiState();

        uiState.toggleMode();

        assertTrue(uiState.isAsyncMode());
    }

    @Test
    void shouldToggleAsyncModeFromTrueToFalse() {
        UiState uiState = new UiState();
        uiState.toggleMode();

        uiState.toggleMode();

        assertFalse(uiState.isAsyncMode());
    }
}