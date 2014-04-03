package hgm;

import java.lang.Integer;
import java.lang.NumberFormatException;

import java.io.InputStreamReader;

import java.util.Scanner;

public class Hangman {

    public static void main(String... args) {
        enterInteractive();
    }

    private static void enterInteractive() {
        System.out.println("Let's play a round of Hangman!");
        System.out.println("How many letters should my word be?");
        boolean cont = true;
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        String numLets = "";
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine();
            if (checkInt(line)) {
                try {
                    int num = Integer.parseInt(line);
                    if (num > 1 && num < 15) {
                        numLets = line;
                        cont = false;
                    } else {
                        System.out.println("Please input a number between 2 and 14.");
                    }
                } catch (NumberFormatException num) {
                }
            } else {
                System.out.printf("Sorry, %s is not a number!", line);
                System.out.println(" Please input a number between 2 and 14.");
            }
        }
        Words wordsMaker = new Words(numLets);
        wordsMaker.makeWords();
        setSecret(wordsMaker.getWord());
        System.out.println(secret);
    }

    private static boolean checkInt(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void setSecret(String word) {
        secret = word;
    }

    private static String secret = "";
}
