package com.example.demo.helper;

import java.util.Random;

public class Helper {

    public static String randomNameGenerator() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String randomEmailGenerator() {
        StringBuilder emailAddress = new StringBuilder();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        while (emailAddress.length() < 5) {
            int character = (int) (Math.random() * 26);
            emailAddress.append(alphabet.charAt(character));
        }
        emailAddress.append(Integer.valueOf((int) (Math.random() * 99))
                .toString());
        emailAddress.append("@").append("gmail.com");
        return emailAddress.toString();
    }
}
