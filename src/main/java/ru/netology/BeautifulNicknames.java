package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BeautifulNicknames {

    static AtomicInteger count3 = new AtomicInteger();
    static AtomicInteger count4 = new AtomicInteger();
    static AtomicInteger count5 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread sameLettersThread = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetters(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        Thread increasingLettersThread = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingLetters(text)) {
                    incrementCounter(text.length());
                }
            }
        });

        palindromeThread.start();
        sameLettersThread.start();
        increasingLettersThread.start();

        try {
            palindromeThread.join();
            sameLettersThread.join();
            increasingLettersThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return new StringBuilder(text).reverse().toString().equals(text);
    }

    public static boolean isSameLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasingLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void incrementCounter(int length) {
        switch (length) {
            case 3:
                count3.incrementAndGet();
                break;
            case 4:
                count4.incrementAndGet();
                break;
            case 5:
                count5.incrementAndGet();
                break;
        }
    }
}