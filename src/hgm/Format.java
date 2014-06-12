package hgm;

import java.util.ArrayList;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.lang.StringBuilder;

public class Format {

    public static void main(String... args) {
        try {
            BufferedReader file = getFile(args[0]);
            //format(file, args[0]);
            addCommas(file, args[0] + ".comma");
        } catch (FileNotFoundException err) {
            System.err.println("Error: file not found.");
            System.exit(1);
        }
    }

    protected static BufferedReader getFile(String filename)
        throws FileNotFoundException {
        BufferedReader str;
        filename = "/Users/Joel/CompSci/hgm/" + filename;
        str = new BufferedReader(new FileReader(filename));
        return str;
    }

    private static void format(BufferedReader file, String filename) {
        try {
            ArrayList<String> words = new ArrayList<String>();
            for (String line = file.readLine(); line != null; line = file.readLine()) {
                String[] parts = line.split("\\p{Blank}+");
                for (String s : parts) {
                    words.add(s.toLowerCase());
                }
            }
            FileWriter fileW = new FileWriter(filename);
            for (String word : words) {
                fileW.write(word + "\n");
            }
            fileW.close();
        } catch (IOException io) {
            System.err.println("Error while formatting");
            System.exit(1);
        }
    }

    private static void addCommas(BufferedReader file, String filename) {
        try {
            StringBuilder str = new StringBuilder();
            String line = file.readLine();
            str.append("\"" + line + "\"");
            for (line = file.readLine(); line != null; line = file.readLine()) {
                str.append(", ");
                str.append("\"" + line + "\"");
            }
            FileWriter fileW = new FileWriter(filename);
            fileW.write(str.toString());
            fileW.close();
        } catch (IOException io) {
            System.err.println("IOException occured");
            System.exit(1);
        }
    }
}
