package org.example.services;

import org.example.interfaces.NotificationServiceInterface;

public class EmailService implements NotificationServiceInterface {

    //Single responsibility
    @Override
    public void sendNotification() {
        System.out.println("Sending welcome email...");
    }
}
