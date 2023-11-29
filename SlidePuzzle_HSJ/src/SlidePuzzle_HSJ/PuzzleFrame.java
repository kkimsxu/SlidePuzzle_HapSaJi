package SlidePuzzle_HSJ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleFrame extends JFrame {
    private SlidePuzzleBoard board;
    private PuzzleButton[][] button_board;
    private Timer timer;
    private long startTime;
    private JLabel timeLabel;

    public PuzzleFrame(SlidePuzzleBoard b) {
        board = b;
        button_board = new PuzzleButton[4][4];
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new StartButton(board, this));

        timeLabel = new JLabel("00:00:00");
        topPanel.add(timeLabel);
        cp.add(topPanel, BorderLayout.NORTH);

        JPanel p2 = new JPanel(new GridLayout(4, 4));
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                button_board[row][col] = new PuzzleButton(board, this);
                p2.add(button_board[row][col]);
            }
        cp.add(p2, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateElapsedTime();
            }
        });

        update();
        setTitle("Slide Puzzle");
        setSize(250, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void updateElapsedTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        timeLabel.setText(formatTime(elapsedTime / 1000));
    }

    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void update() {
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 4; col++) {
                PuzzlePiece pp = board.getPuzzlePiece(row, col);
                if (pp != null) {
                    String n = Integer.toString(pp.face());
                    button_board[row][col].setText(n);
                } else
                    button_board[row][col].setText("");
            }
    }

    public void finish() {
        button_board[3][3].setText("Done");
        stopTimer();

        long elapsedTime = System.currentTimeMillis() - startTime;
        String elapsedTimeStr = formatTime(elapsedTime / 1000);
        JOptionPane.showMessageDialog(this,"소요 시간 : "+ elapsedTimeStr);
    }
}
