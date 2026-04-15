package org.example.ui.commands;

import org.example.ui.AppState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ToggleCommandTest {

    @Test
    void shouldToggleWhenNoModeProvided() {
        AppState state = mock(AppState.class);
        when(state.isAsyncMode()).thenReturn(true);

        ToggleCommand command = new ToggleCommand(state);

        String result = command.execute(new String[]{"toggle"});

        verify(state).toggleAsync();
        verify(state).isAsyncMode();
        assertEquals("Mode: ASYNC", result);
    }

    @Test
    void shouldSetAsyncMode() {
        AppState state = mock(AppState.class);
        when(state.isAsyncMode()).thenReturn(true);

        ToggleCommand command = new ToggleCommand(state);

        String result = command.execute(new String[]{"toggle", "async"});

        verify(state).setAsync(true);
        verify(state).isAsyncMode();
        assertEquals("Mode: ASYNC", result);
    }

    @Test
    void shouldSetSyncMode() {
        AppState state = mock(AppState.class);
        when(state.isAsyncMode()).thenReturn(false);

        ToggleCommand command = new ToggleCommand(state);

        String result = command.execute(new String[]{"toggle", "sync"});

        verify(state).setAsync(false);
        verify(state).isAsyncMode();
        assertEquals("Mode: SYNC", result);
    }

    @Test
    void shouldReturnUsageForInvalidMode() {
        AppState state = mock(AppState.class);

        ToggleCommand command = new ToggleCommand(state);

        String result = command.execute(new String[]{"toggle", "bad"});

        verify(state, never()).toggleAsync();
        verify(state, never()).setAsync(anyBoolean());
        verify(state, never()).isAsyncMode();
        assertEquals("Usage: toggle [async|sync]", result);
    }
}