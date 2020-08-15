package com.demo.bookstore.helper;

import java.util.Random;

/**
 * @author Sujith Simon
 * Created on : 17/08/20
 */
public class RandomGenerator {
    private static final Random random = new Random();

    public static String getString() {
        return getString(7);
    }

    public static String getString(int size) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < size) {
            int index = (int) (random.nextFloat() * chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }

    public static Float getFloat() {
        return random.nextFloat();
    }
}
