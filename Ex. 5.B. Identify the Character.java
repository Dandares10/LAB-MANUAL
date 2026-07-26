//Program:

//Ex. 5.B. Write a Java program that accepts a string from the user and identifies different types of characters
//The program should count and display vowels, consonants, digits, and special characters.

import java.util.Scanner;

public class CharacterCounter {
    public static void main(String[] args) {
        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        // Count different types of characters
        int vowels = 0;
        int consonants = 0;
        int digits = 0;
        int specialCharacters = 0;
        
        String vowelsList = "";
        String consonantsList = "";
        String digitsList = "";
        String specialCharsList = "";
        
        // Convert to lowercase for vowel checking
        String lowerInput = input.toLowerCase();
        
        // Iterate through each character
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            char lowerCh = lowerInput.charAt(i);
            
            if (Character.isLetter(ch)) {
                // Check if it's a vowel
                if (lowerCh == 'a' || lowerCh == 'e' || lowerCh == 'i' || lowerCh == 'o' || lowerCh == 'u') {
                    vowels++;
                    vowelsList += lowerCh + ", ";
                } else {
                    consonants++;
                    consonantsList += ch + ", ";
                }
            } else if (Character.isDigit(ch)) {
                digits++;
                digitsList += ch + ", ";
            } else if (!Character.isWhitespace(ch)) {
                // Count special characters (excluding spaces)
                specialCharacters++;
                specialCharsList += ch + ", ";
            }
        }
        
        // Remove trailing comma and space if present
        if (vowelsList.length() > 0) {
            vowelsList = vowelsList.substring(0, vowelsList.length() - 2);
        }
        if (consonantsList.length() > 0) {
            consonantsList = consonantsList.substring(0, consonantsList.length() - 2);
        }
        if (digitsList.length() > 0) {
            digitsList = digitsList.substring(0, digitsList.length() - 2);
        }
        if (specialCharsList.length() > 0) {
            specialCharsList = specialCharsList.substring(0, specialCharsList.length() - 2);
        }
        
        // Display the results
        System.out.println("\nVowels: " + vowels);
        System.out.println("Consonants: " + consonants);
        System.out.println("Digits: " + digits);
        System.out.println("Special Characters: " + specialCharacters);
        
        System.out.println("\nBreakdown for \"" + input + "\":");
        System.out.println("Vowels: " + (vowelsList.isEmpty() ? "none" : vowelsList) + "→ " + vowels);
        System.out.println("Consonants: " + (consonantsList.isEmpty() ? "none" : consonantsList) + "→ " + consonants);
        System.out.println("Digits: " + (digitsList.isEmpty() ? "none" : digitsList) + "→ " + digits);
        System.out.println("Special Characters: " + (specialCharsList.isEmpty() ? "none" : specialCharsList) + "→ " + specialCharacters);
        System.out.println("(spaces are excluded from special characters)");
        
        // Close the scanner resource
        scanner.close();
    }
}
