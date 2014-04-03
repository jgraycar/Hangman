package hgm;

import java.lang.Integer;
import java.lang.NumberFormatException;

import java.io.InputStreamReader;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Hangman {

    public static void main(String... args) {
        System.out.println("Let's play a round of Hangman!");
        enterInteractive();
    }

    private static void enterInteractive() {
        System.out.println("How many letters should my word be?");
        boolean cont = true;
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        String numLetsStr = "";
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine();
            if (checkInt(line)) {
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
                }
            } else {
                System.out.printf("Sorry, %s is not a number!", line);
                System.out.println(" Please input a number between 2 and 14.");
            }
        }
        Words wordsMaker = new Words(numLetsStr);
        wordsMaker.makeWords();
        setSecret(wordsMaker.getWord());
        System.out.println(secret);
        System.out.println("And how many lives do you want?");
        cont = true;
        while (cont) {
            System.out.print("> ");
            String line = inp.nextLine();
            if (checkInt(line)) {
                try {
                    int num = Integer.parseInt(line);
                    if (num > 0 && num < 100) {
                        lives = num;
                        cont = false;
                    } else {
                        System.out.println("Please input a number between 1 and 99.");
                    }
                } catch (NumberFormatException num) {
                }
            } else {
                System.out.printf("Sorry, %s is not a number!", line);
                System.out.println(" Please input a number between 1 and 99.");
            }
        }
        playGame();
    }

    private static void playGame() {
        boolean cont = true;
        Scanner inp = new Scanner(new InputStreamReader(System.in));
        char[] guesses = new char[secret.length()];
        Arrays.fill(guesses, '_');
        while (cont) {
            ArrayList<Integer> indices = new ArrayList<Integer>();
            printGuesses(guesses);
            System.out.print("> ");
            String line = inp.nextLine();
            if (line.length() > 1) {
                System.out.println("You can only enter one letter at a time!");
                continue;
            } else if (line.length() == 0) {
                continue;
            }
            /// check line properly formatted!!
            char guess = line.toCharArray()[0];
            int index = secret.indexOf(guess);
            while (index != -1) {
                indices.add(index);
                index = secret.indexOf(guess, index + 1);
            }
            if (indices.size() > 0) {
                for (Integer i : indices) {
                    guesses[i.intValue()] = guess;
                }
            } else {
                lives -= 1;
                System.out.printf("\nNope! There are no %c's in my word.\n", guess);
                if (lives > 0) {
                    System.out.printf("You have %d lives left!\n\n", lives);
                }
            }
            if (finished(guesses)) {
                System.out.printf("You got it! My word was '%s'. Congratulations!\n",
                                  secret);
                cont = false;
            } else if (lives == 0) {
                System.out.printf("You've died! I win :D the word was '%s'.\n", secret);
                cont = false;
            }
        }
        afterGame();
    }

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
            } else if (line.equals("n") || line.equals("no")) {
                cont = false;
                System.out.println("Ok :( Goodbye!");
                System.exit(0);
            } else {
                System.out.println("Please answer yes or no.");
            }
        }
    }

    private static boolean finished(char[] guesses) {
        for (char c : guesses) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private static void printGuesses(char[] guesses) {
        for (char c : guesses) {
            System.out.print(c + " ");
        }
        System.out.println();
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
    private static int numLets = 0;
    private static int lives = 0;
}
