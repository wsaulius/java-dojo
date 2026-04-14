package org.example.ui;

import com.google.inject.Singleton;

@Singleton
public class AppState {

    private boolean asyncMode = true;

    public boolean isAsyncMode() {
        return asyncMode;
    }

    public void toggleAsync() {
        asyncMode = !asyncMode;
    }

    public void setAsync(boolean value) {
        asyncMode = value;
    }
}