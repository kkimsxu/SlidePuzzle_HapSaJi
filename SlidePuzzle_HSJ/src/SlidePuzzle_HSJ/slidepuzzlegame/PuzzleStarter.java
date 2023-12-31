package slidepuzzlegame;



import javax.swing.*;

public class PuzzleStarter {
    public static void main(String[] args) {
        PlayerInput playerInput = new PlayerInput();
        String playerName = playerInput.getUsername();

        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Unknown Player";
        }

        String input = JOptionPane.showInputDialog("Enter the size of the puzzle (3, 4, 5):");
        int size = Integer.parseInt(input);
        try {
            if (size >= 3 && size <= 5) {
                new PuzzleFrame(new SlidePuzzleBoard(size), playerName);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid size. Please enter 3, 4, or 5.");
                while (!(size >= 3 && size <= 5)) {
                    input = JOptionPane.showInputDialog("Enter the size of the puzzle (3, 4, 5):");
                    size = Integer.parseInt(input);
                }
                new PuzzleFrame(new SlidePuzzleBoard(size), playerName);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            while (!(size >= 3 && size <= 5)) {
                input = JOptionPane.showInputDialog("Enter the size of the puzzle (3, 4, 5):");
                size = Integer.parseInt(input);
            }
            new PuzzleFrame(new SlidePuzzleBoard(size), playerName);
        }
    }
}


