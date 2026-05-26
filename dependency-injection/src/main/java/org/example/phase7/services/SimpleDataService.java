package org.example.phase7.services;

import org.example.phase7.interfaces.DataService;

//Common Decorator interface implementation
public class SimpleDataService implements DataService {
    @Override
    public void save() {
        System.out.println("Saving data...");
    }
}
