package SlidePuzzle_HSJ;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SlidePuzzleBoard {
    private int size;
    private PuzzlePiece[][] board;
    private int empty_row;
    private int empty_col;
    private boolean on = false;
    private BufferedImage[] imagePieces;


    public SlidePuzzleBoard(int size) {
        this.size = size;
        board = new PuzzlePiece[size][size];
        imagePieces = new BufferedImage[size * size];

        String imagePath = selectImagePathForSize(size);

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (image == null) {
                System.out.println("Image could not be loaded from path: " + imagePath);
                return;
            }

            int pieceWidth = image.getWidth() / size;
            int pieceHeight = image.getHeight() / size;

            int number = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++, number++) {
                    imagePieces[number] = image.getSubimage(
                            col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        createPuzzleBoard();
    }


    private String selectImagePathForSize(int size) {
        switch (size) {
            case 3:
                return "C:/Git/SlidePuzzle_HapSaJi/SlidePuzzle_HSJ/src/SlidePuzzle_HSJ/Xmas3.jpg";
            case 4:
                return "C:/Git/SlidePuzzle_HapSaJi/SlidePuzzle_HSJ/src/SlidePuzzle_HSJ/Xmas4.png";
            case 5:
                return "C:/Git/SlidePuzzle_HapSaJi/SlidePuzzle_HSJ/src/SlidePuzzle_HSJ/Xmas5.jpg";
            // 기타 사이즈에 대한 경로...
            default:
                return "path/to/defaultImage.png";
        }
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

    public void createPuzzleBoard() {
        // 해결된 상태로 초기화
        int number = 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != size - 1 || col != size - 1) {
                    board[row][col] = new PuzzlePiece(number, imagePieces[number - 1]);
                    number++;
                } else {
                    board[row][col] = null; // 마지막 칸은 빈 칸
                    empty_row = row;
                    empty_col = col;
                }
            }
        }

        // 랜덤하게 퍼즐을 섞음
        Random random = new Random();
        int moveCount = 100; // 움직임의 수를 설정 (예: 100)
        for (int i = 0; i < moveCount; i++) {
            shufflePuzzle(random);
        }

        // 빈칸을 오른쪽 아래로 이동
        moveEmptyToBottomRight();

        on = true;
    }

    private void moveEmptyToBottomRight() {
        while (empty_row < size - 1) {
            move(empty_row + 1, empty_col); // 아래로 이동
        }
        while (empty_col < size - 1) {
            move(empty_row, empty_col + 1); // 오른쪽으로 이동
        }
    }


    private void shufflePuzzle(Random random) {
        int[] dx = {1, -1, 0, 0}; // 상하 이동
        int[] dy = {0, 0, 1, -1}; // 좌우 이동
        int move = random.nextInt(4); // 0~3 사이의 랜덤한 방향 선택

        int newRow = empty_row + dx[move];
        int newCol = empty_col + dy[move];

        // 새로운 위치가 보드의 경계 내에 있는지 확인
        if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
            board[empty_row][empty_col] = board[newRow][newCol];
            board[newRow][newCol] = null;
            empty_row = newRow;
            empty_col = newCol;
        }
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
}
