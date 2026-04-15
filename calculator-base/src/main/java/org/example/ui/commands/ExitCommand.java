package org.example.ui.commands;

import org.example.interfaces.Command;
import org.example.interfaces.ExitHandler;

public class ExitCommand implements Command {
    private final ExitHandler exitHandler;

    public ExitCommand(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    @Override
    public String execute(String[] args) {
        exitHandler.exit(0);
        return "";
    }
}