package com.example.demo;

public class UtilityClass {

    public static String createRandomWord(int wordLength) {
        String name = "";
        for (int i = 0; i < wordLength; i++) {
            int v = 1 + (int) (Math.random() * 26);
            char c = (char) (v + (i == 0 ? 'A' : 'a') - 1);
            name += c;
        }
        return name;
    }
}
