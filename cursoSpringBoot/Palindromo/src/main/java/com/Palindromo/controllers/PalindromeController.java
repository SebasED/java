package com.Palindromo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to verify palindromes
 */
@RestController
public class PalindromeController {

    /**
     * Endpoint to verify if a word is a palindrome
     * @param word - Is the word to verify
     * @return Message that indicate if the word is a palindrome or not
     */
    @GetMapping("/validate-palindrome/{word}")
    public String Palindrome(@PathVariable String word){
        if (isPalindrome(word)) {
            return "The word " +  word + " is a palindrome";
        } else {
            return "The word " +  word + " is not a palindrome";
        }
    }

    /**
     * Method to verify if a word is a palindrome
     * @param word - Is the word to verify
     * @return True if the word is palindrome, false otherwise
     */
    private Boolean isPalindrome(String word) {
        int length = word.length();
        for (int i=0; i < length / 2; i++) {
            if (word.charAt(i) != word.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
