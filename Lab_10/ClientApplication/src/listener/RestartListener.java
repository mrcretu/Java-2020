package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import net.Header;
import ui.BoardCanvas;
import ui.GameFrame;

public class RestartListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (Data.connected) {
            if (!Data.started) {
                Data.last = -1;
                Data.turn = 0;
                Data.chessBoard = new int[15][15];
                Data.myChess = 0;
                Data.oppoChess = 0;
                BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
                boardCanvas.paintBoardImage();
                boardCanvas.repaint();

                IOManager.getInstance().getPs().println(Header.OPERATION + Header.RESTART);
            } else {
                MessageManager.getInstance().addMessage("The game is not over");
            }
        }
    }

}