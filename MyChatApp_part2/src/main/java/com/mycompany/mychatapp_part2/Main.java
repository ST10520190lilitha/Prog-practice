/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp_part2;

import java.util.Scanner;

/**
 *
 * @author lilit
 */
public class Main { 
    public static void main(String[]args){
        
     //Scanner will allow the user to input thair infomation
     Scanner input = new Scanner(System.in);
     
     //creating an object for the login class so we can call its methods
     Login login= new Login();
     
    
    // REGISTRATION PART 
    {
    System.out.println("===USER REGISTRATION===");
    
    System.out.print("Enter a name:"); 
   String name=input.nextLine(); 
   
   System.out.print("Enter a surname:"); 
   String surname=input.nextLine();
    
   System.out.print("Enter a username:"); 
   String username=input.nextLine(); 
   
   System.out.print("Enter password:");
   String password=input.nextLine(); 
   
   System.out.print("Enter Your South African phone number (+27...):");
   String phone=input.nextLine(); 
   
   //call the registerUser to store the message it returns 
   String response=login.registerUser(username,password,phone);
   
   //It should show the registration message 
   System.out.println(response); 
   
}
 //Login Section 
    
 System.out.println("===USER LOGIN==="); 
 
System.out.print("Enter a username:");
  String loginUsername=input.nextLine(); 
  
  System.out.print("Enter password:");
   String loginPassword=input.nextLine(); 
   
   //must call to see if the details match with the stored ones 
   boolean loggedIn = login.loginUser(loginUsername, loginPassword); 
   
   //print out the correct login message
   
   String loginMessage=login.returnLoginStatus(loggedIn);
   System.out.println(loginMessage);
} 
}

