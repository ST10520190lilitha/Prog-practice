/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp_part2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Message class -- represents a single chat message in ChatApp.
 * Handles message creation, validation, hashing, and JSON storage.
 * * ATTRIBUTION: 
 * Library used: Google Gson (version 2.10.1)
 * Purpose: Used to read, append, parse and write structured message lists to a JSON file.
 * Found via: Maven Repository (https://mvnrepository.com/artifact/com.google.gson/gson)
 */
public class Message {

    // Instance variables required by POE Section 4
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String sendStatus;

    // Static counter to track total session messages globally
    private static int totalMessages = 0;

    /**
     * Constructor to initialize a newly created Message object.
     */
    public Message(String messageID, String recipient, String messageText) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.sendStatus = "Disregarded"; // Default status until user actions it
    }

    /**
     * Validates if the message ID string is exactly 10 digits long.
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{10}");
    }

    /**
     * Validates if the recipient cell number starts with '+27' and is exactly 12 characters long.
     */
    public boolean checkRecipientCell() {
        if (recipient == null) {
            return false;
        }
        return recipient.matches("\\+27\\d{9}");
    }

    /**
     * Returns descriptive verification text for recipient captures.
     */
    public String checkRecipientCellStatus() {
        if (checkRecipientCell()) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Checks if the message content string length stays within bounds.
     */
    public String checkMessageLength(String message) {
        if (message == null) {
            return "Message ready to send.";
        }
        if (message.length() > 250) {
            int over = message.length() - 250;
            return "Message exceeds 250 characters by " + over + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    /**
     * Generates a unique string identity token matching instructions from Section 8.
     * Format: [First 2 digits of ID] : [Message Number] : [First Word + Last Word]
     */
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            this.messageHash = "";
            return this.messageHash;
        }

        // Step 1: Extract first two digits of the Message ID using substring
        String idPart = messageID.substring(0, 2);

        // Step 2 & 3: Split message text on whitespace to find bounding words
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // Step 4: Combine fragments separated by colons and convert to uppercase
        this.messageHash = (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    /**
     * Process decision flows regarding whether to Send, Disregard, or Archive the message text.
     */
    public String sentMessage(int option) {
        switch (option) {
            case 1:
                this.sendStatus = "Sent";
                totalMessages++;
                return "Message successfully sent.";
            case 2:
                this.sendStatus = "Disregarded";
                return "Press 0 to delete the message.";
            case 3:
                this.sendStatus = "Stored";
                storeMessageToFile(); 
                totalMessages++;
                return "Message successfully stored.";
            default:
                return "";
        }
    }

    /**
     * Groups structural attributes into an organized text presentation summary.
     */
    public String printMessages() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    public void storeMessage() {
        storeMessageToFile();
    }

    /**
     * Stores individual message instances safely inside a structural messages.json archive.
     */
    public void storeMessageToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filename = "messages.json";
        File messageFile = new File(filename);

        List<Message> messages = new ArrayList<>();

        if (messageFile.exists() && messageFile.length() > 0) {
            try (FileReader reader = new FileReader(messageFile)) {
                Type messageListType = new TypeToken<List<Message>>() {}.getType();
                messages = gson.fromJson(reader, messageListType);

                if (messages == null) {
                    messages = new ArrayList<>();
                }
            } catch (IOException e) {
                System.out.println("Error reading existing messages: " + e.getMessage());
            }
        }

        messages.add(this);

        try (FileWriter writer = new FileWriter(messageFile)) {
            gson.toJson(messages, writer);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to messages.json: " + e.getMessage());
        }
    }

    /**
     * Returns total message operations tracked throughout the active system lifetime.
     */
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // --- Getters & Setters ---
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    public int getMessageNumber() { return messageNumber; }
    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getSendStatus() { return sendStatus; }
}