package com.mycompany.mychatapp_part2;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Unit Test class for validating the behavior of the Message class methods.
 */
public class MessageTest {

    @Test
    public void testValidMessageID() {
        String[] testIDs = {"1234567890", "0987654321", "1122334455"};
        boolean passed = false;

        for (String id : testIDs) {
            Message msg = new Message(id, "+27831112222", "Hello!");
            if (msg.checkMessageID()) {
                passed = true;
                break;
            }
        }
        assertTrue("At least one valid message ID should pass", passed);
    }

    @Test
    public void testInvalidMessageID() {
        String[] invalidIDs = {"12345", "abc", "99"};
        boolean allFailed = true;

        for (String id : invalidIDs) {
            Message msg = new Message(id, "+27831112222", "Hi!");
            if (msg.checkMessageID()) {
                allFailed = false;
                break;
            }
        }
        assertTrue("All invalid message IDs should fail", allFailed);
    }

    @Test
    public void testValidRecipient() {
        String[] validRecipients = {"+27831112222", "+27721234567", "+27611234567"};
        boolean passed = false;

        for (String recipient : validRecipients) {
            Message msg = new Message("1234567890", recipient, "Hey!");
            if (msg.checkRecipientCell()) {
                passed = true;
                break;
            }
        }
        assertTrue("At least one valid recipient should pass", passed);
    }

    @Test
    public void testInvalidRecipient() {
        String[] invalidRecipients = {"0831112222", "12345", "NotAValidNumber"};
        boolean allFailed = true;

        for (String recipient : invalidRecipients) {
            Message msg = new Message("1234567890", recipient, "Hi!");
            if (msg.checkRecipientCell()) {
                allFailed = false;
                break;
            }
        }
        assertTrue("All invalid recipients should fail", allFailed);
    }

    @Test
    public void testMessageHashGeneration() {
        int maxAttempts = 3;
        boolean hashValid = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            Message msg = new Message("1234567890", "+27831112222", "Test Message");
            msg.setMessageNumber(1); // Set the required loop counter sequence variable
            String hash = msg.createMessageHash();

            if (hash != null && hash.equals(msg.getMessageHash())) {
                hashValid = true;
                System.out.println("Hash generated successfully on attempt " + attempt + ": " + hash);
                break;
            } else {
                System.out.println("Attempt " + attempt + " failed. Retrying...");
            }
        }
        assertTrue("Hash should be valid and match stored hash after retries", hashValid);
    }

    @Test
    public void testStoreMessageToFile() {
        int maxAttempts = 3;
        boolean fileExists = false;
        boolean fileHasContent = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            // Create message and generate hash
            Message msg = new Message("1234567890", "+27831112222", "Stored message test");
            msg.createMessageHash();
            msg.storeMessageToFile();

            // Check if file exists
            File file = new File("messages.json");

            if (!file.exists()) {
                System.out.println("Attempt " + attempt + ": File not found. Retrying...");
                continue;
            }

            fileExists = true;

            // Verify JSON content
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type messageListType = new TypeToken<List<Message>>() {}.getType();
                List<Message> messages = gson.fromJson(reader, messageListType);

                if (messages != null && !messages.isEmpty()) {
                    fileHasContent = true;
                    System.out.println("Attempt " + attempt + ": File contains " + messages.size() + " message(s).");
                    break;
                } else {
                    System.out.println("Attempt " + attempt + ": File is empty. Retrying...");
                }

            } catch (Exception e) {
                System.out.println("Attempt " + attempt + ": Error reading file - " + e.getMessage());
            }
        }

        assertTrue("JSON file should exist after storing message", fileExists);
        assertTrue("JSON file should contain at least one message", fileHasContent);
    }
}