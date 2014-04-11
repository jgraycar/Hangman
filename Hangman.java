package hgm;

import java.lang.Integer;
import java.lang.NumberFormatException;

import java.io.InputStreamReader;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Random;

public class Hangman {

    public static void main(String... args) {
        System.out.println("Let's play a round of Hangman!");
        enterInteractive();
        playGame();
    }

    /** Ask user for how many letters secret word should be,
     *  and how many lives they want. */
    private static void enterInteractive() {
        System.out.println("How many letters should my word be?");
        boolean cont = true;
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        String numLetsStr = "";
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine();
            try {
                int num = Integer.parseInt(line);
                if (num > 1 && num < 15) {
                    numLets = num;
                    numLetsStr = line;
                    cont = false;
                } else {
                    System.out.println("Please input a number between 2 and 14.");
                }
            } catch (NumberFormatException num) {
                System.out.printf("Sorry, %s is not a number!", line);
                System.out.println(" Please input a number between 2 and 14.");
            }
        }
        wordsMaker = new Words(numLetsStr);
        wordsMaker.makeWords();
        setSecret(wordsMaker.getWord());
        System.out.println("And how many lives do you want? Type '0' to let me choose.");
        cont = true;
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine();
            try {
                int num = Integer.parseInt(line);
                if (num > 0 && num < 100) {
                        lives = num;
                        cont = false;
                } else if (num == 0) {
                    Random rand = new Random();
                    lives = rand.nextInt(6) + 5;
                        System.out.printf("I'll be nice and give you %d lives.\n",
                                          lives);
                        cont = false;
                        
                } else {
                    System.out.println("Please input a number between 1 and 99.");
                }
            } catch (NumberFormatException num) {
                System.out.printf("Sorry, %s is not a number!", line);
                System.out.println(" Please input a number between 1 and 99.");
            }
        }
    }

    /** Play a round of Hangman! Continues until word is guessed completely
     *  or user runs out of lives. When game is complete, calls afterGame()
     *  to enquire if another round is desired.
     */
    private static void playGame() {
        ArrayList<Character> guessed = new ArrayList<Character>(26);
        boolean cont = true;
        boolean firstTime = true;
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        guesses = new char[secret.length()];
        Arrays.fill(guesses, '_');
        printGuesses();
        timesWrong = 0;
        while (cont) {
            ArrayList<Integer> indices = new ArrayList<Integer>();
            System.out.print("> ");
            String line = inp.nextLine().toLowerCase();
            if (line.length() > 1) {
                if (line.equals("quit")) {
                    System.out.println("Byebye!");
                    System.exit(0);
                } else if (line.equals("cheat")) {
                    System.out.println("secret word: " + secret);
                    continue;
                } else {
                    System.out.println("You can only enter one letter at a time!");
                    continue;
                }
            } else if (line.length() == 0) {
                continue;
            }
            char guess = line.toCharArray()[0];
            if (guessed.contains(guess)) {
                System.out.printf("You've already guessed %c!\n", guess);
                continue;
            }
            guessed.add(guess);
            if (!firstTime) {
                wordsMaker.clearWordsByLetter(guess);
            }
            int index = secret.indexOf(guess);
            while (index != -1) {
                indices.add(index);
                index = secret.indexOf(guess, index + 1);
            }
            if (!firstTime) {
                wordsMaker.clearWordsByLetter(guess);
            }
            if (indices.size() > 0) {
                if (!firstTime && wordsMaker.canSwitch()) {
                    setSecret(wordsMaker.getWord());
                    cont = wrongGuess(guess);
                    continue;
                }
                firstTime = false;
                for (Integer i : indices) {
                    guesses[i.intValue()] = guess;
                }
                wordsMaker.clearWordsBySpot(guesses);
                printGuesses();
                timesWrong = 0;
            } else {
                cont = wrongGuess(guess);
                continue;
            }
            if (finished()) {
                System.out.printf("You got it! My word was '%s'. Congratulations!\n\n",
                                  secret);
                cont = false;
            }
        }
        afterGame();
    }

    /** Returns false if game has ended, otherwise true. */
    private static boolean wrongGuess(char guess) {
        lives -= 1;
        timesWrong += 1;
        System.out.printf("Nope! There are no %c's in my word.\n", guess);
        if (lives > 0) {
            System.out.printf("You have %d lives left!\n", lives);
            if (timesWrong > 3) {
                printGuesses();
                timesWrong = 0;
            }
        } else {
            System.out.printf("You've died! I win :D the word was '%s'.\n\n", secret);
            return false;
        }
        return true;   
    }

    /** Asks user if they want to play another round.
     *  Called at the end of playGame(). */
    private static void afterGame() {
        System.out.println("One more round?");
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        boolean cont = true;
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine().toLowerCase();
            if (line.equals("y") || line.equals("yes")) {
                cont = false;
                System.out.print("Yay! ");
                enterInteractive();
                playGame();
            } else if (line.equals("n") || line.equals("no")) {
                cont = false;
                System.out.println("Ok :( Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Please answer yes or no.");
            }
        }
    }

    /** Checks GUESSES to see if there are any unfound letters remaining.
     *  @param guesses is the character array containing the letters found
     *         so far. Letters that haven't been found yet are _'s.
     *  @return returns true if all letters have been found, false otherwise. */
    private static boolean finished() {
        for (char c : guesses) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private static void cheat() {
        ArrayList<Integer> locations = new ArrayList<Integer>();
        for (int i = 0; i < guesses.length; i += 1) {
            if (guesses[i] == '_') {
                locations.add(i);
            }
        }
        Random rand = new Random();
        int size = locations.size();
        int randNum;
        if (size > 1) {
            randNum = rand.nextInt(size - 1);
        } else {
            randNum = 0;
        }
        int i = locations.get(randNum);
        System.out.printf("Try '%c'\n", secret.charAt(i));
    }

    private static void printGuesses() {
        for (char c : guesses) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void setSecret(String word) {
        secret = word;
    }

    private static String secret = "";
    private static int numLets = 0;
    private static int lives = 0;
    private static int timesWrong;
    private static Words wordsMaker;
    private static char[] guesses;
}
