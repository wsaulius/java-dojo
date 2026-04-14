package org.example.ui.commands;

import org.example.interfaces.Command;

public class ExitCommand implements Command {

    @Override
    public String execute(String[] args) {
        System.exit(0);
        return "";
    }
}