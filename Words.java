package hgm;

import java.lang.Integer;
import java.lang.NumberFormatException;

import java.util.ArrayList;
import java.util.Random;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Words {

    private int numLets;

    public Words(String num) {
        this.words = new ArrayList<String>();
        try {
            this.numLets = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.err.println("Error while creating Words class.");
            System.exit(1);
        }
    }

    public static void main(String... args) {

    }

    public void makeWords() {
        WordBank wordBank = new WordBank2();
        wordBank.giveWords();
        String filename = this.numLets + "letters.txt";
        String word;
        try {
            InputStream resource =
                hgm.Words.class.getClassLoader().getResourceAsStream(filename);
            BufferedReader wordFile =
                new BufferedReader(new InputStreamReader(resource));
            for (word = wordFile.readLine(); word != null; word = wordFile.readLine()) {
                this.words.add(word);
            }
        } catch (FileNotFoundException err) {
            System.err.printf("Error: file %s not found.\n", filename);
            System.exit(1);
        } catch (IOException io) {
            System.err.printf("Error: IOException arose while creating wordbank.\n");
            System.exit(1);
        }
    }

    protected void clearWordsByLetter(char guess) {
        ArrayList<String> wordsToRemove = new ArrayList<String>();
        for (String wrd : this.words) {
            if (wrd.contains(guess + "")) {
                wordsToRemove.add(wrd);
            }
        }
        for (String word : wordsToRemove) {
            this.words.remove(word);
        }
    }

    protected void clearWordsBySpot(char[] guesses) {
        ArrayList<Integer> letSpots = new ArrayList<Integer>(14);
        for (int i = 0; i < guesses.length; i += 1) {
            if (guesses[i] != '_') {
                letSpots.add(i);
            }
        }
        ArrayList<String> wordsToRemove = new ArrayList<String>();
        for (String wrd : this.words) {
            wordLoop:
            for (Integer in : letSpots) {
                if (wrd.charAt(in) != guesses[in]) {
                    wordsToRemove.add(wrd);
                    break;
                } else {
                    for (int k = 0; k < wrd.length(); k += 1) {
                        if (k != in && wrd.charAt(k) == guesses[in]) {
                            wordsToRemove.add(wrd);
                            break wordLoop;
                        }
                    }
                }
            }
        }
        for (String wrdR : wordsToRemove) {
            this.words.remove(wrdR);
        }
    }

    protected boolean canSwitch() {
        if (this.words.size() > 0) {
            return true;
        }
        return false;
    }

    protected String getWord() {
        Random rand = new Random();
        int arrSize = this.words.size();
        String word;
        if (arrSize > 1) {
            word = this.words.get(rand.nextInt(arrSize - 1));
        } else {
            word = this.words.get(0);
        }
        this.words.remove(word);
        return word;
    }

    private ArrayList<String> words;

}
