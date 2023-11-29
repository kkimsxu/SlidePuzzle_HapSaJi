package SlidePuzzle_HSJ;

import java.util.Random;

public class SlidePuzzleBoard {
    private PuzzlePiece[][] board;
    private int empty_row;
    private int empty_col;
    private boolean on = false;

    public SlidePuzzleBoard() {
        board = new PuzzlePiece[4][4];

        int number = 1;
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                board[row][col] = new PuzzlePiece(number);
                number += 1;
            }
        board[3][3] = null;
        empty_row = 3;
        empty_col = 3;
    }

    public PuzzlePiece getPuzzlePiece(int row, int col) {
        return board[row][col];
    }

    public boolean move(int w) {
        int row, col;

        if (found(w, empty_row - 1, empty_col)) {
            row = empty_row - 1;
            col = empty_col;
        } else if (found(w, empty_row + 1, empty_col)) {
            row = empty_row + 1;
            col = empty_col;
        } else if (found(w, empty_row, empty_col - 1)) {
            row = empty_row;
            col = empty_col - 1;
        } else if (found(w, empty_row, empty_col + 1)) {
            row = empty_row;
            col = empty_col + 1;
        } else
            return false;

        board[empty_row][empty_col] = board[row][col];

        empty_row = row;
        empty_col = col;
        board[empty_row][empty_col] = null;
        return true;
    }

    private boolean found(int v, int row, int col) {
        if (row >= 0 && row <= 3 && col >= 0 && col <= 3)
            return board[row][col].face() == v;
        else
            return false;
    }

    public void createPuzzleBoard() {
        int[] numbers = generateRandomPermutation(15);
        int i = 0;
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                if (row != 3 || col != 3) {
                    board[row][col] = new PuzzlePiece(numbers[i] + 1);
                    i += 1;
                } else {
                    board[row][col] = null;
                    empty_row = 3;
                    empty_col = 3;
                }
            }
        on = true;
    }

    public boolean on() {
        return on;
    }

    public boolean gameOver() {
        if (empty_row != 3 || empty_col != 3)
            return false;
        else {
            int number = 1;
            for (int row = 0; row < 4; row++)
                for (int col = 0; col < 4; col++) {
                    if (row != 3 || col != 3) {
                        if (board[row][col].face() != number)
                            return false;
                        else
                            number += 1;
                    }
                }
            on = false;
            return true;
        }
    }
    private int[] generateRandomPermutation(int n) {
        Random random = new Random();
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) {
            int d = random.nextInt(i + 1);
            permutation[i] = permutation[d];
            permutation[d] = i;
        }
        return permutation;
    }
}
