package SlidePuzzle_HSJ;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HintButton extends JButton implements ActionListener {
    private final SlidePuzzleBoard board;
    private final PuzzleFrame frame;

    public HintButton(SlidePuzzleBoard board, PuzzleFrame frame) {
        super("Hint");
        this.board = board;
        this.frame = frame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.showSolution();
        Timer timer = new Timer(5000, evt -> frame.update());
        timer.setRepeats(false);
        timer.start();
        setEnabled(false); // Hint 버튼을 비활성화합니다.
    }
}
