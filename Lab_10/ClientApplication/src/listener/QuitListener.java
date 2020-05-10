package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import net.Header;
import ui.BoardCanvas;
import ui.GameFrame;

public class QuitListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Data.oppoId != 0) {
            if (Data.started) {
                int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "The game is not over yet, you are sure to admit defeat？", "Confess",
                        JOptionPane.YES_NO_OPTION);
                if (value == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(GameFrame.getInstance(), "You admit defeat！");
                    IOManager.getInstance().getPs().println(Header.OPERATION + Header.GIVEUP + Data.oppoId);
                    Data.last = -1;
                    Data.oppoId = 0;
                    Data.myChess = 0;
                    Data.oppoChess = 0;
                    Data.ready = false;
                    Data.started = false;
                    Data.chessBoard = new int[15][15];
                    GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent: none");
                    BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
                    boardCanvas.paintBoardImage();
                    boardCanvas.repaint();

                    MessageManager.getInstance().addMessage("You can choose your opponent again");
                }
            } else {
                IOManager.getInstance().getPs().println(Header.OPERATION + Header.QUIT + Data.oppoId);
                Data.last = -1;
                Data.oppoId = 0;
                Data.myChess = 0;
                Data.oppoChess = 0;
                Data.ready = false;
                Data.started = false;
                Data.chessBoard = new int[15][15];
                GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent: none");
                BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
                boardCanvas.paintBoardImage();
                boardCanvas.repaint();

                MessageManager.getInstance().addMessage("You can choose your opponent again");
            }
        }
    }

}