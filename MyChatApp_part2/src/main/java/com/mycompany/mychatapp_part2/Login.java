/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp_part2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Login {

    // Variables to store user information
    private String username;
    private String password;
    private String phoneNumber;

    // Check username: must contain "_" and be max 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String password) {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // Loop through each character
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // Check South African phone number format
    public boolean checkPhoneNumber(String phone) {
        return phone.startsWith("+27") && phone.length() == 12;
    }

    // Register user
    public String registerUser(String username, String password, String phoneNumber) {

        // Validate username
        if (!checkUserName(username)) {
            return "Invalid username (must contain '_' and max 5 characters)";
        }

        // Validate password
        if (!checkPasswordComplexity(password)) {
            return "Invalid password (min 8 chars, uppercase, number, special char required)";
        }

        // Validate phone number
        if (!checkPhoneNumber(phoneNumber)) {
            return "Invalid phone number (+27 required and 12 digits)";
        }

        // Store values
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;

        // Save to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt"))) {
            writer.write(username + "," + password + "," + phoneNumber);
        } catch (IOException e) {
            return "Error saving user.";
        }

        return "User registration successful.";
    }

    // Login user
    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Return login status message
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}


    
    
      
 
 
    

    

