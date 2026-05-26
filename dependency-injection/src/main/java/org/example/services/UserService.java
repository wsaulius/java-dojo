package org.example.services;

public class UserService {

    private EmailService emailService = new EmailService();

    public void registerUser(String username) {

        System.out.println("Registering user: " + username);
        emailService.sendEmail();
    }
}
