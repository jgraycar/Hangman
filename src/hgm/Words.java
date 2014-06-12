package hgm;

import words.*;

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
        WordBank wordBank = null;
        switch(this.numLets) {
        case 2:
            wordBank = new WordBank2();
            break;
        case 3:
            wordBank = new WordBank3();
            break;
        case 4:
            wordBank = new WordBank4();
            break;
        case 5:
            wordBank = new WordBank5_1();
            String[] w5 = wordBank.giveWords();
            for (String w : w5) {
                words.add(w);
            }
            wordBank = new WordBank5_2();
            break;
        case 6:
            wordBank = new WordBank6_1();
            String[] w6 = wordBank.giveWords();
            for (String w : w6) {
                words.add(w);
            }
            wordBank = new WordBank6_2();
            w6 = wordBank.giveWords();
            for (String wr : w6) {
                words.add(wr);
            }
            wordBank = new WordBank6_3();
            break;
        case 7:
            wordBank = new WordBank7_1();
            String[] w7 = wordBank.giveWords();
            for (String w : w7) {
                words.add(w);
            }
            wordBank = new WordBank7_2();
            w7 = wordBank.giveWords();
            for (String w : w7) {
                words.add(w);
            }
            wordBank = new WordBank7_3();
            w7 = wordBank.giveWords();
            for (String w : w7) {
                words.add(w);
            }
            wordBank = new WordBank7_4();
            w7 = wordBank.giveWords();
            for (String w : w7) {
                words.add(w);
            }
            wordBank = new WordBank7_5();
            break;
        case 8:
            wordBank = new WordBank8_1();
            String[] w8 = wordBank.giveWords();
            for (String w : w8) {
                words.add(w);
            }
            wordBank = new WordBank8_2();
            w8 = wordBank.giveWords();
            for (String w : w8) {
                words.add(w);
            }
            wordBank = new WordBank8_3();
            w8 = wordBank.giveWords();
            for (String w : w8) {
                words.add(w);
            }
            wordBank = new WordBank8_4();
            w8 = wordBank.giveWords();
            for (String w : w8) {
                words.add(w);
            }
            wordBank = new WordBank8_5();
            break;
        case 9:
            wordBank = new WordBank9_1();
            String[] w9 = wordBank.giveWords();
            for (String w : w9) {
                words.add(w);
            }
            wordBank = new WordBank9_2();
            w9 = wordBank.giveWords();
            for (String w : w9) {
                words.add(w);
            }
            wordBank = new WordBank9_3();
            w9 = wordBank.giveWords();
            for (String w : w9) {
                words.add(w);
            }
            wordBank = new WordBank9_4();
            break;
        case 10:
            wordBank = new WordBank10_1();
            String[] w10 = wordBank.giveWords();
            for (String w : w10) {
                words.add(w);
            }
            wordBank = new WordBank10_2();
            w10 = wordBank.giveWords();
            for (String w : w10) {
                words.add(w);
            }
            wordBank = new WordBank10_3();
            break;
        case 11:
            wordBank = new WordBank11_1();
            String[] w11 = wordBank.giveWords();
            for (String w : w11) {
                words.add(w);
            }
            wordBank = new WordBank11_2();
            break;
        case 12:
            wordBank = new WordBank12_1();
            String[] w12 = wordBank.giveWords();
            for (String w : w12) {
                words.add(w);
            }
            wordBank = new WordBank12_2();
            break;
        case 13:
            wordBank = new WordBank13();
            break;
        case 14:
            wordBank = new WordBank14();
            break;
        default:
            System.err.println("Error: number of letters not 2-14.");
            System.exit(1);
            break;
        }
        String[] wordsArray = wordBank.giveWords();
        for (String wrd : wordsArray) {
            words.add(wrd);
        }
        /**String filename = this.numLets + "letters.txt";
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
            }*/
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
