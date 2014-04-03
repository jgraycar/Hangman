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
        String filename = this.numLets + "letters.txt";
        //filename = "/Users/Joel/CompSci/hgm/" + filename;
        String word;
        try {
            InputStream resource =
                hgm.Words.class.getClassLoader().getResourceAsStream(filename);
            BufferedReader wordFile =
                new BufferedReader(new InputStreamReader(resource));
            //BufferedReader wordFile = new BufferedReader(new FileReader(filename));
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

    public String getWord() {
        Random rand = new Random();
        int arrSize = this.words.size();
        return this.words.get(rand.nextInt(arrSize - 1));
    }

    private ArrayList<String> words;

}
