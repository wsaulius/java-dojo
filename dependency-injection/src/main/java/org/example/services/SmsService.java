package org.example.services;

import org.example.interfaces.NotificationServiceInterface;

public class SmsService implements NotificationServiceInterface {

    //Single responsibility
    @Override
    public void sendNotification() {
        System.out.println("Sending SMS... ");
    }
}
