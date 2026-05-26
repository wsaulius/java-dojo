package org.example.phase7.decorators;

import org.example.phase7.interfaces.DataService;

//Decorator
public class LoggingDecorator implements DataService {

    private final DataService wrapped;

    public LoggingDecorator(DataService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void save() {
        System.out.println("LOG: Before save -");
        wrapped.save();
        System.out.println("LOG: After save -");

    }
}
