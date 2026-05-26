package org.example.services;

import org.example.interfaces.NotificationServiceInterface;

public class UserService {

    private final NotificationServiceInterface notificationService;

    //Manual constructor injection
    public UserService(NotificationServiceInterface notificationService) {
        this.notificationService = notificationService;
    }

    public void registerUser(String username) {
        System.out.println("Registering user: " + username);
        notificationService.sendNotification();
    }
}
