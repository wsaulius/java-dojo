package org.example.ui.commands;

import com.google.inject.Inject;
import org.example.interfaces.Command;
import org.example.ui.AppState;

public class ToggleCommand implements Command {

    private final AppState state;

    @Inject
    public ToggleCommand(AppState state) {
        this.state = state;
    }

    @Override
    public String execute(String[] args) {

        if (args.length < 2) {
            state.toggleAsync();
        } else {
            switch (args[1].toLowerCase()) {
                case "async" -> state.setAsync(true);
                case "sync" -> state.setAsync(false);
                default -> {
                    return "Usage: toggle [async|sync]";
                }
            }
        }

        return "Mode: " + (state.isAsyncMode() ? "ASYNC" : "SYNC");
    }
}