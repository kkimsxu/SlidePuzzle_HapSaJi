package SlidePuzzle_HSJ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SlidePuzzleBoard {
    private int size; // 퍼즐 크기를 저장할 변수
    private PuzzlePiece[][] board;
    private int empty_row;
    private int empty_col;
    private boolean on = false;
    private BufferedImage[] imagePieces; // 이미지 조각을 저장할 배열


    public SlidePuzzleBoard(int size, String imagePath) {
        this.size = size;
        board = new PuzzlePiece[size][size];
        imagePieces = new BufferedImage[size * size];

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int pieceWidth = image.getWidth() / size;
            int pieceHeight = image.getHeight() / size;

            int number = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++, number++) {
                    BufferedImage pieceImage = image.getSubimage(
                            col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                    imagePieces[number] = pieceImage;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        createPuzzleBoard(); // 보드 초기화
    }

    public int getSize() {
        return size;
    }

    public PuzzlePiece getPuzzlePiece(int row, int col) {
        return board[row][col];
    }

    public boolean move(int targetRow, int targetCol) {
        // 이동할 조각이 빈 칸과 인접해 있는지 확인합니다.
        if ((Math.abs(targetRow - empty_row) == 1 && targetCol == empty_col) ||
                (Math.abs(targetCol - empty_col) == 1 && targetRow == empty_row)) {

            board[empty_row][empty_col] = board[targetRow][targetCol];
            board[targetRow][targetCol] = null;
            empty_row = targetRow;
            empty_col = targetCol;
            return true;
        }
        return false;
    }

    private boolean found(int v, int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return board[row][col] != null && board[row][col].face() == v;
        } else {
            return false;
        }
    }

    public void createPuzzleBoard() {
        int[] numbers = generateRandomPermutation(size * size - 1);
        int i = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != size - 1 || col != size - 1) {
                    board[row][col] = new PuzzlePiece(numbers[i] + 1, imagePieces[numbers[i]]);
                    i += 1;
                } else {
                    board[row][col] = null; // 빈 칸에는 null을 할당합니다.
                    empty_row = row;
                    empty_col = col;
                }
            }
        }
        on = true;
    }

    public int getEmptyRow() {
        return empty_row;
    }

    public int getEmptyCol() {
        return empty_col;
    }
    public BufferedImage getImagePiece(int number) {
        return imagePieces[number];
    }

    public boolean on() {
        return on;
    }

    public boolean gameOver() {
        if (empty_row != size - 1 || empty_col != size - 1)
            return false;
        else {
            int number = 1;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (row != size - 1 || col != size - 1) {
                        if (board[row][col].face() != number)
                            return false;
                        number += 1;
                    }
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
