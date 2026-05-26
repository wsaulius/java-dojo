package org.example;

import org.example.interfaces.NotificationServiceInterface;
import org.example.services.EmailService;
import org.example.services.SmsService;
import org.example.services.UserService;

public class DependencyInjectionDemo {
    public static void main(String[] args) {

        //No need to change the userService
        //NotificationServiceInterface notificationService = new EmailService();
        NotificationServiceInterface notificationService = new SmsService();
        UserService userService = new UserService(notificationService);
        userService.registerUser("John");

    }
}
