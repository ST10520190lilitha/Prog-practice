/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp_part2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lilit
 */
public class LoginTest {
    
    Login login = new Login();
    
    
    //USERNAME TESTS
    @Test
    public void testvaildUsername(){ 
        //should be 5 charaters long
        assertTrue(login.checkUserName("kyl_1")); 
    }   
    @Test 
    public void testInvaildUsername_noUnderscore(){ 
        //check the username for underscore
       assertFalse(login.checkUserName("kyle!!!!!!!"));
    }
    @Test    
    public void testInvaildUsername_TooLong(){
    //reason why it is false its username is longer than 5 
   //it must be 5 characters
   assertFalse(login.checkUserName("kylelllll")); 
    }
    
    //PASSWORD CHECKS
   @Test
   public void testvaildPassword(){ 
       //the password should be more than 8 characters and must have special characters
    assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99"));
    } 
   
   @Test 
   public void testInvaildPassword_TooShort(){ 
        //check the Passwords length
       assertFalse(login.checkPasswordComplexity("password"));} 
   @Test 
   public void testInvaildPassword_DoesNotHAveSpecialCharacters(){ 
        //check the password if it has specail characters
       assertFalse(login.checkPasswordComplexity("password"));   
   } 
   
   //PHONENUMBER TESTS
   @Test  
   public void testvaildPhoneNumber(){ 
       //the PhoneNumber should start with +27
    assertTrue(login.checkPhoneNumber(
             "+27838968976"));} 
    
    @Test 
   public void testInvaildPhoneNumber_DoesNotHaveSouthAfricanCode(){ 
        //check if it has +27 in the beginning not a 0
       assertFalse(login.checkPhoneNumber("08966553"));}  
   
   @Test 
   public void testInvaildPhoneNumber_TooShort(){ 
        //check hiw long is the number
       assertFalse(login.checkPhoneNumber("08966553"));
   } 
   
   // LOGIN TESTS
    @Test
    public void testLoginSuccess() {
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99", "+27838968976");

        // Then attempt login with correct details
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99");

        assertTrue(result);
    }

    @Test
    public void testLoginFail() {
        // First register a user
        login.registerUser("kyl_1", "Ch&&sec@ke99", "+27838968976");

        // Attempt login with incorrect password
        boolean result = login.loginUser("kyl_1", "WrongPass1!");

        assertFalse(result);
    }
   
   
}
