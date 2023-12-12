package slidepuzzlegame;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PuzzleButton extends JButton implements ActionListener {
    private SlidePuzzleBoard board;
    private PuzzleFrame frame;
    private int row; // 퍼즐 보드에서의 행 위치
    private int col; // 퍼즐 보드에서의 열 위치

    public PuzzleButton(SlidePuzzleBoard b, PuzzleFrame f, int row, int col) {
        this.board = b;
        this.frame = f;
        this.row = row;
        this.col = col;
        addActionListener(this);
    }
    public void updateButton() {
        PuzzlePiece pp = board.getPuzzlePiece(row, col);
        if (pp != null) {
            setIcon(new ImageIcon(pp.getImage()));
            setText(""); // 숫자는 표시하지 않음
        } else {
            setIcon(null);
            setText("");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (board.on()) {
            if (board.move(row, col)) { // row와 col을 이용하여 이동 처리
                frame.update();
                if (board.gameOver()) {
                    frame.finish();
                    frame.stopTimer();
                }
            }
        }
    }
}
