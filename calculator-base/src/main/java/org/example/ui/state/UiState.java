package org.example.ui.state;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UiState {

    public enum Screen {
        MAIN,
        UNARY,
        BINARY,
        MATRIX,
        THREADPOOL
    }

    @Setter
    private Screen screen = Screen.MAIN;
    private boolean asyncMode = false;

    public void toggleMode() {
        asyncMode = !asyncMode;
    }
}