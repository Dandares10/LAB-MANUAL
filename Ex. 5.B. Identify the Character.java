//Program:

//Ex. 5.B. Write a Java program that accepts two strings from the user and checks if they are anagrams of each other.
//Given two strings s and t, return true if t is an anagram of s, and false otherwise.

import java.util.Scanner;
import java.util.Arrays;

public class CharacterCounter {
    public static void main(String[] args) {
        // Initialize Scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Input s = ");
        String s = scanner.nextLine();
        
        System.out.print("Input t = ");
        String t = scanner.nextLine();
        
        // Check if the strings are anagrams
        boolean result = isAnagram(s, t);
        
        // Display the result
        System.out.println(result);
        
        // Close the scanner resource
        scanner.close();
    }
    
    // Method to check if two strings are anagrams
    public static boolean isAnagram(String s, String t) {
        // If lengths are different, they cannot be anagrams
        if (s.length() != t.length()) {
            return false;
        }
        
        // Convert both strings to character arrays and sort them
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        
        Arrays.sort(sArray);
        Arrays.sort(tArray);
        
        // Compare the sorted arrays
        return Arrays.equals(sArray, tArray);
    }
}
