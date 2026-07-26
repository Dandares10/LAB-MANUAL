//Program:

//Ex. 5.B. Write a Java program that accepts a string from the user and counts the number of vowels, consonants, digits, and special characters present in it.

import java.util.Scanner;

public class CharacterCounter {
    public static void main(String[] args) {
        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        // Counter variables
        int vowels = 0;
        int consonants = 0;
        int digits = 0;
        int specialChars = 0;
        
        // Convert to lowercase to make checking simple
        String lowerInput = input.toLowerCase();
        
        // Process each character in the string
        for (int i = 0; i < lowerInput.length(); i++) {
            char ch = lowerInput.charAt(i);
            
            // Check if the character is a letter
            if (ch >= 'a' && ch <= 'z') {
                // Determine if it is a vowel or a consonant
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            } 
            // Check if the character is a digit
            else if (ch >= '0' && ch <= '9') {
                digits++;
            } 
            // Everything else is treated as a special character (spaces, punctuation, etc.)
            else {
                specialChars++;
            }
        }
        
        // Display the results
       
        // Close the scanner resource
        scanner.close();
    }
}
