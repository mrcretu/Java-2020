package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JPanel gameBody = new JPanel();

    private ui.BoardCanvas boardCanvas = null;

    GamePanel() {
        gameBody.add(getBoardCanvas());

        this.setLayout(new BorderLayout());
        this.add(gameBody, BorderLayout.CENTER);
    }

    public ui.BoardCanvas getBoardCanvas() {
        if (boardCanvas == null) {
            boardCanvas = new ui.BoardCanvas();
        }
        return boardCanvas;
    }

}