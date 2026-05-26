package org.example;

import org.example.services.UserService;

public class DependencyInjectionDemo {
    public static void main(String[] args) {

        UserService userService = new UserService();
        userService.registerUser("John");

    }
}
