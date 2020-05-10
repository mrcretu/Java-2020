package tool;

import java.io.IOException;
import java.io.PrintStream;

import net.Header;

public class Game {

    boolean restartA = false;
    boolean restartB = false;

    int turn = 0;

    int BLACK = 1;
    int WHITE = -1;

    int playerA;
    int playerB;

    PrintStream psA = null;
    PrintStream psB = null;

    int[][] chessBoard = new int[15][15];

    public PrintStream getPsA() {
        if (psA == null) {
            try {
                psA = new PrintStream(HashMapManager.getInstance().getPlayer(playerA).socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return psA;
    }

    public PrintStream getPsB() {
        if (psB == null) {
            try {
                psB = new PrintStream(HashMapManager.getInstance().getPlayer(playerB).socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return psB;
    }

    public void setPlayerA(int playerA) {
        this.playerA = playerA;
    }

    public void setPlayerB(int playerB) {
        this.playerB = playerB;
    }

    public void sendStartMessage() {
        this.getPsA().println(Header.STARTMSG + "The game starts, please drop");
        this.getPsB().println(Header.STARTMSG + "The game starts, waiting for the opponent to drop");
        turn = BLACK;
    }

    public void startPlay(int playerA, int playerB) {
        this.setPlayerA(playerA);
        this.setPlayerB(playerB);
        this.getPsA().println(Header.START + "BLACK");
        this.getPsB().println(Header.START + "WHITE");
    }

    public void sendPlay(int from, int position) {
        if (from == playerA) {
            int x = position % 15;
            int y = position / 15;
            chessBoard[x][y] = BLACK;
            turn = WHITE;
            this.getPsB().println(Header.PLAY + position);
            if (this.checkWin(x, y, BLACK)) {
                this.getPsA().println(Header.WIN);
                this.getPsB().println(Header.LOSE);
                HashMapManager.getInstance().getReadys().remove(playerA);
                HashMapManager.getInstance().getReadys().remove(playerB);
            }
        } else if (from == playerB) {
            int x = position % 15;
            int y = position / 15;
            chessBoard[x][y] = WHITE;
            turn = BLACK;
            this.getPsA().println(Header.PLAY + position);
            if (this.checkWin(x, y, WHITE)) {
                this.getPsB().println(Header.WIN);
                this.getPsA().println(Header.LOSE);
                HashMapManager.getInstance().getReadys().remove(playerA);
                HashMapManager.getInstance().getReadys().remove(playerB);
            }
        } else {
            MessageManager.getInstance().addMessage("Error sending rookie message：" + Header.PLAY + position);
            System.out.println("Error sending rookie message：" + Header.PLAY + position);
            System.out.println("Source ID：" + from);
            System.out.println("Player A ID:" + playerA);
            System.out.println("Player B ID:" + playerB);
        }
    }

    public boolean checkWin(int x, int y, int id) {
        return (new Board()).check(x, y, id, chessBoard);
    }

    public void restart(int uid) {
        if (uid == playerA) {
            if (!restartA) {
                restartA = true;
            }
        } else {
            if (!restartB) {
                restartB = true;
            }
        }
        if (restartA && restartB) {
            psA = null;
            psB = null;
            chessBoard = new int[15][15];
            this.startPlay(playerB, playerA);
            this.sendStartMessage();
            restartA = false;
            restartB = false;
        }
    }

}