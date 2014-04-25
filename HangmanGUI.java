package hgm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Arrays;
import java.util.ArrayList;

public class HangmanGUI {

    JButton[] letters, numbers;
    JFrame frame;
    LetPanelMain panelLets;
    NumPanelMain panelNums;
    DisplayPanel display;
    GamePanel game;
    LivesPanel lives;
    EndScreenPanel endScreen;
    JButton opening;
    LetPanelSub row1, row2, row3;
    NumPanelSub num1_3, num4_6, num7_9, num0;
    JButton confirm, playAgain, quit;
    JLabel pick, numSoFar, letSoFar, livesLeft, livesText, endMsg;
    JPanel endGameButtons;

    int numLets, numLives;
    int state, winState;
    String inProgress;
    String secret;
    StringBuilder guessInProgress;
    boolean firstTime;
    char[] guesses;

    ArrayList<Integer> indices;

    Words wordMaker;

    public static void main(String... args) {
        HangmanGUI keys = new HangmanGUI();
        keys.makeKeys();
        keys.go();
    }

    public void go() {
        frame = new JFrame();
        opening = new JButton("Hangman");
        Font bigFont = new Font("serif", Font.BOLD, 70);
        opening.setFont(bigFont);
        opening.addActionListener(new OpeningActionListener());
        panelLets = new LetPanelMain();
        row1 = new LetPanelSub();
        row2 = new LetPanelSub();
        row3 = new LetPanelSub();
        panelLets.setLayout(new BoxLayout(panelLets, BoxLayout.Y_AXIS));
        panelLets.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        panelLets.add(row1);
        panelLets.add(row2);
        panelLets.add(row3);
        for (JButton butt : letters) {
            butt.addActionListener(new LetterButtonListener());
        }
        for (int i = 0; i < 10; i += 1) {
            row1.add(letters[i]);
        }
        for (int i = 10; i < 20; i += 1) {
            row2.add(letters[i]);
        }
        for (int i = 20; i < 26; i += 1) {
            row3.add(letters[i]);
        }

        panelNums = new NumPanelMain();
        num1_3 = new NumPanelSub();
        num4_6 = new NumPanelSub();
        num7_9 = new NumPanelSub();
        num0 = new NumPanelSub();
        panelNums.setLayout(new BoxLayout(panelNums, BoxLayout.Y_AXIS));
        panelNums.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelNums.add(num1_3);
        panelNums.add(num4_6);
        panelNums.add(num7_9);
        panelNums.add(num0);
        for (JButton numButt : numbers) {
            numButt.addActionListener(new NumberButtonListener());
            numButt.setOpaque(true);
            numButt.setBackground(new Color(220, 220, 220));
        }
        for (int i = 1; i < 4; i += 1) {
            num1_3.add(numbers[i]);
        }
        for (int i = 4; i < 7; i += 1) {
            num4_6.add(numbers[i]);
        }
        for (int i = 7; i < 10; i += 1) {
            num7_9.add(numbers[i]);
        }
        num0.add(numbers[0]);

        frame.setSize(1000, 500);
        frame.getContentPane().add(opening);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void makeKeys() {
        this.letters = new JButton[26];
        char let = 'A';
        for (int i = 0; i < 26; i += 1) {
            this.letters[i] = makeLetterButton(let);
            let += 1;
        }
        this.numbers = new JButton[10];
        char num = '0';
        for (int i = 0; i < 10; i += 1) {
            this.numbers[i] = makeLetterButton(num);
            num += 1;
        }
    }

    private void setupGame() {
        inProgress = "0";
        state = 0;
        numLets = 0;
        numLives = 0;
        display = new DisplayPanel();
        display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
        pick = new JLabel("Pick how many letters you want!");
        pick.setAlignmentX(Component.CENTER_ALIGNMENT);
        pick.setFont(new Font("Verdana", Font.PLAIN, 30));
        pick.setBorder(BorderFactory.createEmptyBorder(70, 40, 20, 40));
        display.add(pick);
        numSoFar = new JLabel(inProgress);
        numSoFar.setAlignmentX(Component.CENTER_ALIGNMENT);
        numSoFar.setFont(new Font("Verdana", Font.PLAIN, 35));
        numSoFar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        display.add(numSoFar);
        confirm = new JButton("Confirm");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.addActionListener(new ConfirmActionListener());
        display.add(confirm);
        frame.getContentPane().add(BorderLayout.CENTER, display);
        frame.getContentPane().add(BorderLayout.SOUTH, panelNums);
    }

    private void playGame() {
        firstTime = true;
        panelNums.setVisible(false);
        frame.getContentPane().add(BorderLayout.SOUTH, panelLets);
        panelLets.setVisible(true);
        wordMaker = new Words(numLets + "");
        wordMaker.makeWords();
        secret = wordMaker.getWord();
        System.out.println(secret);
        guesses = new char[secret.length()];
        Arrays.fill(guesses, '_');
        display.setVisible(false);
        lives = new LivesPanel();
        lives.setLayout(new BoxLayout(lives, BoxLayout.Y_AXIS));
        livesLeft = new JLabel(numLives + "");
        livesLeft.setFont(new Font("Verdana", Font.PLAIN, 40));
        livesLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        livesText = new JLabel("Lives remaining:");
        livesText.setFont(new Font("Verdana", Font.PLAIN, 20));
        livesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        livesText.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
        lives.add(livesText);
        lives.add(livesLeft);
        frame.getContentPane().add(BorderLayout.EAST, lives);
        letSoFar = new JLabel();
        Font bigFont = new Font("Verdana", Font.BOLD, 50);
        letSoFar.setBorder(BorderFactory.createEmptyBorder(150, 50, 50, 50));
        letSoFar.setFont(bigFont);
        updateLetSoFar();
        game = new GamePanel();
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        letSoFar.setAlignmentX(Component.CENTER_ALIGNMENT);
        game.add(letSoFar);
        frame.getContentPane().add(BorderLayout.CENTER, game);
    }

    private void updateLetSoFar() {
        guessInProgress = new StringBuilder();
        guessInProgress.append(guesses[0]);
        for (int i = 1; i < guesses.length; i += 1) {
            guessInProgress.append(" " + guesses[i]);
        }
        letSoFar.setText(guessInProgress.toString());
    }


    private static JButton makeLetterButton(char let) {
        String str = Character.toString(let);
        return new JButton(str);
    }

    private void checkGuess(char guess) {
        indices = new ArrayList<Integer>();
        int index = secret.indexOf(guess);
        while (index != -1) {
            indices.add(index);
            index = secret.indexOf(guess, index + 1);
        }
        if (!firstTime) {
            wordMaker.clearWordsByLetter(guess);
        }
        if (indices.size() > 0) {
            if (!firstTime && wordMaker.canSwitch()) {
                secret = wordMaker.getWord();
                wrongGuess();
                System.out.println(secret);
            } else {
                firstTime = false;
                for (Integer i : indices) {
                    guesses[i.intValue()] = guess;
                }
                wordMaker.clearWordsBySpot(guesses);
                game.repaint();
                checkForWin();
            }
        } else {
            if (firstTime) {
                wordMaker.clearWordsByLetter(guess);
            }
            wrongGuess();
        }
    }

    private void wrongGuess() {
        numLives -= 1;
        if (numLives == 0) {
            winState = 0;
            endGame();
        }
    }

    private void checkForWin() {
        boolean finished = true;
        for (Character ch : guesses) {
            if (ch == '_') {
                finished = false;
            }
        }
        if (finished) {
            winState = 1;
            updateLetSoFar();
            endGame();
        }
    }

    private void endGame() {
        endScreen = new EndScreenPanel();
        endScreen.setLayout(new BoxLayout(endScreen, BoxLayout.Y_AXIS));
        letSoFar.setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
        endScreen.add(letSoFar);
        game.setVisible(false);
        panelLets.setVisible(false);
        lives.setVisible(false);
        JLabel endMsg = new JLabel();
        endMsg.setFont(new Font("Verdana", Font.PLAIN, 30));
        endMsg.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        endMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        endScreen.add(endMsg);
        if (winState == 0) {
            // player lost
            endMsg.setText("You lost! The word was \"" + secret + "\"");
        } else {
            // player won
            endMsg.setText("You won!");
        }
        playAgain = new JButton("Play again");
        quit = new JButton("Quit");
        playAgain.addActionListener(new PlayAgainButtonListener());
        quit.addActionListener(new QuitButtonListener());
        endGameButtons = new EndScreenPanel();
        endGameButtons.add(playAgain);
        endGameButtons.add(quit);
        endScreen.add(endGameButtons);
        frame.getContentPane().add(BorderLayout.CENTER, endScreen);
    }




    // ------------------------------- CLASSES ------------------------------------



    class ConfirmActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                int numProgress = Integer.parseInt(inProgress);
                if (state == 0) {
                    if (numProgress > 1 && numProgress < 15) {
                        numLets = numProgress;
                        if (numLets == 14) {
                            frame.setSize(1100, 500);
                        }
                        state = 1;
                        pick.setText("Now pick how many lives you want.");
                    } else {
                        pick.setText("Please pick a number between 2 and 14.");
                    }
                    inProgress = "0";
                    display.repaint();
                } else {
                    if (numProgress > 0 && numProgress < 26) {
                        numLives = numProgress;
                        playGame();
                    } else {
                        pick.setText("Please pick a number between 1 and 25.");
                        inProgress = "0";
                        display.repaint();
                    }
                }
            } catch (NumberFormatException num) {
                System.err.println("Error: inProgress was not int.");
                System.exit(1);
            }
        }
    }

    class OpeningActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            opening.setVisible(false);
            setupGame();
        }
    }

    class LetterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton pressed = (JButton) event.getSource();
            char guess = 'a';
            for (int i = 0; i < 26; i += 1) {
                if (letters[i] == pressed) {
                    if (!letters[i].isSelected()) {
                        guess += i;
                        letters[i].setSelected(true);
                        checkGuess(guess);
                    }
                }
            }
            lives.repaint();
        }
    }

    class NumberButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton pressed = (JButton) event.getSource();
            int num = -1;
            for (int i = 0; i < 10; i += 1) {
                if (numbers[i] == pressed) {
                    num = i;
                }
            }
            if (num == -1) {
                System.err.println("Error: NumberButtons did not work correctly.");
                System.exit(1);
            }
            if (state == 0) {
                if (inProgress.equals("1")) {
                    inProgress += num;
                } else {
                    inProgress = num + "";
                }
            } else {
                if (inProgress.equals("0")) {
                    inProgress = num + "";
                } else {
                    inProgress += num;
                }
            }
            display.repaint();
        }
    }

    class QuitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    class PlayAgainButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            for (JButton letButt : letters) {
                letButt.setSelected(false);
            }
            endScreen.setVisible(false);
            panelNums.setVisible(true);
            frame.setSize(1000, 500);
            setupGame();
        }
    }

    // ----------------------------------- Panels ----------------------------------

    class GamePanel extends JPanel {
        public void paintComponent(Graphics g) {
            updateLetSoFar();
            g.setColor(new Color(255, 239, 213));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    class LivesPanel extends JPanel {
        public void paintComponent(Graphics g) {
            livesLeft.setText(numLives + "");
            g.setColor(new Color(211, 211, 211));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    class DisplayPanel extends JPanel {
        public void paintComponent(Graphics g) {
            numSoFar.setText(inProgress);
        }
    }

    class LetPanelMain extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(new Color(245, 245, 245));
            g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
        }
    }

    class LetPanelSub extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(new Color(220, 220, 220));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

    class NumPanelMain extends JPanel {
        public void paintComponent(Graphics g) {
            int fWidth = frame.getWidth();
            g.setColor(new Color(220, 220, 220));
            g.fillRoundRect((this.getWidth() - 280) / 2,
                            0, 280, this.getHeight(), 50, 50);
        }
    }

    class NumPanelSub extends JPanel {
        public void paintComponent(Graphics g) {
            int fWidth = frame.getWidth();
            g.setColor(new Color(220, 220, 220));
            g.fillRect((this.getWidth() - 100) / 2,
                       0, 100, this.getHeight() - 100);
        }
    }

    class EndScreenPanel extends JPanel {
        public void paintComponent(Graphics g) {
            if (winState == 1) {
                g.setColor(new Color(240, 255, 240));
            } else {
                g.setColor(new Color(255, 228, 225));
            }
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

}
