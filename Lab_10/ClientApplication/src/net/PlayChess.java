package net;

import java.io.PrintStream;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import ui.BoardCanvas;
import ui.GameFrame;

public class PlayChess {

    public void play(int x, int y, int chess) {
        PrintStream ps = IOManager.getInstance().getPs();
        int position = 15 * y + x;
        Data.last = position;
        if (chess == Data.myChess) {
            Data.chessBoard[x][y] = Data.myChess;
            BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
            mapCanvas.paintBoardImage();
            mapCanvas.repaint();
            Data.turn = Data.oppoChess;
            ps.println(Header.PLAY + position);

            MessageManager.getInstance().addMessage("Wait for the opponent to move");
        }
        // Opponent playing chess
        if (chess == Data.oppoChess) {
            Data.chessBoard[x][y] = Data.oppoChess;
            // Redraw the chessboard
            BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
            mapCanvas.paintBoardImage();
            mapCanvas.repaint();
            // Substitution
            Data.turn = Data.myChess;

            MessageManager.getInstance().addMessage("Please drop your chess");
        }
    }

}