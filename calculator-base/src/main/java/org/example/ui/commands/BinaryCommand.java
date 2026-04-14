package org.example.ui.commands;

import com.google.inject.Inject;
import org.example.enums.BinaryType;
import org.example.interfaces.Command;
import org.example.services.CalculatorService;

public class BinaryCommand implements Command {

    private final CalculatorService calculator;

    @Inject
    public BinaryCommand(CalculatorService calculator) {
        this.calculator = calculator;
    }

    @Override
    public String execute(String[] args) {

        if (args.length < 4) {
            return "Usage: binary <type> <a> <b>";
        }

        BinaryType type = BinaryType.valueOf(args[1].toUpperCase());
        double a = Double.parseDouble(args[2]);
        double b = Double.parseDouble(args[3]);

        return String.valueOf(calculator.runBinary(type, a, b));
    }
}