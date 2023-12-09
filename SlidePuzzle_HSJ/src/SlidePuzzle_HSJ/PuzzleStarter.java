package SlidePuzzle_HSJ;

import javax.swing.*;

public class PuzzleStarter {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Enter the size of the puzzle (3, 4, 5):");
        try {
            int size = Integer.parseInt(input);
            if (size >= 3 && size <= 5) {
                new PuzzleFrame(new SlidePuzzleBoard(size));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid size. Please enter 3, 4, or 5.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
        }
    }
}

