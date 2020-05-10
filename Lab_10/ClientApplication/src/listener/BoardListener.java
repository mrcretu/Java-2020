package listener;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.Data;
import manager.MessageManager;
import net.PlayChess;
import ui.BoardCanvas;

public class BoardListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        BoardCanvas canvas = (BoardCanvas) e.getSource();
        if (Data.connected) {
            if (Data.oppoId != 0) {
                if (Data.ready) {
                    if (Data.started) {
                        if (Data.turn == Data.myChess) {
                            if (e.getX() < canvas.getMapWidth() - 6 && e.getY() < canvas.getHeight() - 7) {
                                int x = e.getX() / 35;
                                int y = e.getY() / 35;
                                if (Data.chessBoard[x][y] == 0) {
                                    new PlayChess().play(x, y, Data.myChess);
                                } else {
                                    MessageManager.getInstance().addMessage("Can't download here");
                                }
                            }
                        } else if (Data.turn == Data.oppoChess) {
                            MessageManager.getInstance().addMessage("Not your round");
                        }
                    } else {
                        MessageManager.getInstance().addMessage("Waiting for the other party to prepare");
                    }
                } else {
                    MessageManager.getInstance().addMessage("Please start the game first");
                }
            } else {
                MessageManager.getInstance().addMessage("Please start the game first");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        BoardCanvas canvas = (BoardCanvas) e.getSource();
        canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        BoardCanvas canvas = (BoardCanvas) e.getSource();
        canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

}