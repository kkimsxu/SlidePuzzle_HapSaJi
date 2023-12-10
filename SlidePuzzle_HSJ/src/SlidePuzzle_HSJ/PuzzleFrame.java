package SlidePuzzle_HSJ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

public class PuzzleFrame extends JFrame {
    private SlidePuzzleBoard board;
    private PuzzleButton[][] button_board;
    private Timer timer;
    private long startTime;
    private JLabel timeLabel;
    private String playerName;

    public PuzzleFrame(SlidePuzzleBoard b, String playerName) {
        this.playerName = playerName;
        this.board = b;
        button_board = new PuzzleButton[board.getSize()][board.getSize()];
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new StartButton(board, this));

        timeLabel = new JLabel("00:00:00");
        topPanel.add(timeLabel);
        cp.add(topPanel, BorderLayout.NORTH);

        JPanel p2 = new JPanel(new GridLayout(b.getSize(), b.getSize()));
        for (int row = 0; row < b.getSize(); row++) {
            for (int col = 0; col < b.getSize(); col++) {
                // PuzzleButton 생성자에 row와 col 인덱스를 전달합니다.
                button_board[row][col] = new PuzzleButton(board, this, row, col);
                p2.add(button_board[row][col]);
            }
        }
        cp.add(p2, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateElapsedTime();
            }
        });

        HintButton hintButton = new HintButton(board, this);
        topPanel.add(hintButton);

        // 게임 시작 전에 정답 보드를 보여줍니다.
        showSolution();

        setTitle("Slide Puzzle");

        setSize(500, 550);
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
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                button_board[row][col].updateButton();
            }
        }
    }

    public void showSolution() {
        int number = 0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (row != board.getSize() - 1 || col != board.getSize() - 1) {
                    BufferedImage correctImagePiece = board.getImagePiece(number); // 올바른 이미지 조각
                    button_board[row][col].setIcon(new ImageIcon(correctImagePiece));
                    number++;
                } else {
                    button_board[row][col].setIcon(null); // 마지막 칸은 빈 칸
                }
            }
        }
    }


    public void finish() {
        // 게임 종료 시 마지막 퍼즐 조각을 표시
        BufferedImage lastPieceImage = board.getImagePiece(board.getSize() * board.getSize() - 1);
        button_board[board.getEmptyRow()][board.getEmptyCol()].setIcon(new ImageIcon(lastPieceImage));
        stopTimer();

        long elapsedTime = System.currentTimeMillis() - startTime; // 밀리초 단위
        String elapsedTimeStr = formatTime(elapsedTime / 1000); // 초 단위로 변환
        JOptionPane.showMessageDialog(this, "Congratulations! You completed the puzzle in " + elapsedTimeStr);

        // 플레이어 정보 업데이트
        Player currentPlayer = PlayerInput.findPlayerByUsername(playerName, PlayerInput.loadPlayersFromFile());
        if (currentPlayer != null) {
            currentPlayer.updateScores((int)elapsedTime / 1000, 0, 0); // 점수 업데이트 (경과 시간을 점수로 사용)
            PlayerInput.updatePlayerInfo(currentPlayer); // PlayerInput.txt 파일에 점수 업데이트
        } else {
            // 플레이어가 존재하지 않는 경우 (예외 처리)
            JOptionPane.showMessageDialog(this, "Error: Player not found.");
        }

        // 랭킹 조회 및 표시
        List<Player> ranking = PlayerInput.getRanking(board.getSize());
        StringBuilder sb = new StringBuilder("<html><table>");
        sb.append("<tr><th>Rank</th><th>Name</th><th>Time</th></tr>");
        int rank = 1;
        for (Player player : ranking) {
            int score;
            switch (board.getSize()) {
                case 3:
                    score = player.getLevel1Score();
                    break;
                case 4:
                    score = player.getLevel2Score();
                    break;
                case 5:
                    score = player.getLevel3Score();
                    break;
                default:
                    score = 0; // 기본값 또는 예외 처리
            }
            String formattedTime = formatTime(score);
            sb.append("<tr><td>").append(rank++).append("</td><td>")
                    .append(player.getUsername()).append("</td><td>")
                    .append(formattedTime).append("</td></tr>");
        }
        sb.append("</table></html>");
        JOptionPane.showMessageDialog(this, sb.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);

    }


}
