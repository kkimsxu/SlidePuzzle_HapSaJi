package SlidePuzzle_HSJ;

import javax.swing.*;

public class PuzzleStarter {
    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Enter your name:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Unknown Player"; // 기본 이름 설정
        }

        String input = JOptionPane.showInputDialog("Enter the size of the puzzle (3, 4, 5):");
        try {
            int size = Integer.parseInt(input);
            if (size >= 3 && size <= 5) {
                String imagePath = "C:/Git/SlidePuzzle_HapSaJi/SlidePuzzle_HSJ/src/SlidePuzzle_HSJ/Xmas.png/"; // 이미지 파일 경로

                // playerName 매개변수를 전달합니다.
                new PuzzleFrame(new SlidePuzzleBoard(size, imagePath), playerName);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid size. Please enter 3, 4, or 5.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
        }
    }
}

