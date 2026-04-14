package org.example.ui.commands;

import com.google.inject.Inject;
import org.example.interfaces.Command;

import java.util.Map;

public class HelpCommand implements Command {

    private final Map<String, Command> commands;

    @Inject
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args) {
        return "Commands: " + commands.keySet();
    }
}