package org.example.phase7.observers;

import org.example.phase7.interfaces.EventListener;

//Observer
public class SmsListener implements EventListener {

    @Override
    public void update(String event) {
        System.out.println("Sms sent: " + event);
    }
}
