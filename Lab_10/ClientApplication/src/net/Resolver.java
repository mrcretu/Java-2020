package net;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import manager.PlayerListManager;
import ui.BoardCanvas;
import ui.GameFrame;

public class Resolver {

    // Initialization
    public void init(String s) {
        String str[] = s.split("-");
        String name = str[1];
        int id = Integer.parseInt(str[0]);
        Data.myId = id;
        Data.myName = name;
        // Show game interface
        GameFrame.getInstance().showGamePanel();
        // Update player list
        IOManager.getInstance().getPs().println(Header.LIST);
    }

    // Game start message
    public void startMessage(String s) {
        MessageManager.getInstance().addMessage(s);
    }

    // The game starts now
    public void start(String s) {
        if (s.substring(0, 5).equals("BLACK")) {
            Data.myChess = Data.BLACK;
            Data.oppoChess = Data.WHITE;
            Data.turn = Data.BLACK;
            Data.started = true;
        }
        if (s.substring(0, 5).equals("WHITE")) {
            Data.myChess = Data.WHITE;
            Data.oppoChess = Data.BLACK;
            Data.turn = Data.BLACK;
            Data.started = true;
        }
        GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("Confess");
    }

    // update list
    public void updateList(String s) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    PlayerListManager playerListManager = PlayerListManager.getInstance();
                    playerListManager.clearList();
                    String[] players = s.split("&");
                    for (String player : players) {
                        playerListManager.addPlayer(player);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // Add player
    public void addList(String s) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    PlayerListManager.getInstance().addPlayer(s);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // Remove player
    public void delList(String s) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    PlayerListManager.getInstance().removePlayer(s);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void play(String s) {
        int position = Integer.parseInt(s);
        int x = position % 15;
        int y = position / 15;
        new PlayChess().play(x, y, Data.oppoChess);
    }

    public void chat(String s) {
        String str[] = s.split("&");
        String message = str[0];
        String who = str[1];
        MessageManager.getInstance().addMessage(who + "Say：" + message);
    }

    public void operation(String s) {
        if (s.startsWith(Header.GIVEUP)) {
            JOptionPane.showMessageDialog(GameFrame.getInstance(), "The opponent has conceded, please choose your opponent again");
            Data.last = -1;
            Data.oppoId = 0;
            Data.myChess = 0;
            Data.oppoChess = 0;
            Data.ready = false;
            Data.started = false;
            Data.chessBoard = new int[15][15];
            GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent: none");
            BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
            mapCanvas.paintBoardImage();
            mapCanvas.repaint();
        }
        if (s.startsWith(Header.QUIT)) {
            Data.last = -1;
            Data.oppoId = 0;
            Data.myChess = 0;
            Data.oppoChess = 0;
            Data.ready = false;
            Data.started = false;
            Data.chessBoard = new int[15][15];
            GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent: none");
            BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
            mapCanvas.paintBoardImage();
            mapCanvas.repaint();

            JOptionPane.showMessageDialog(GameFrame.getInstance(), "The other party left, please choose the opponent again");
        }
        if (s.startsWith(Header.CHALLENGE)) {
            s = s.substring(Header.CHALLENGE.length());
            String[] str = s.split("-");
            int targetId = Integer.parseInt(str[0]);
            int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "Player“" + s + "”Challenge you, accept?", "Received a challenge",
                    JOptionPane.YES_NO_OPTION);
            if (value == JOptionPane.YES_OPTION) {
                IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&YES");
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "You have accepted the challenge from the other party, please click \"Ready\" to start the game, or click \"Leave\" to re-select your opponent");
                Data.oppoId = targetId;
                GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent:" + s);
                MessageManager.getInstance().addMessage("You have accepted the challenge from the other party, please click \"Ready\" to start the game, or click \"Leave\" to re-select your opponent");
            }
            else {
                IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&NO");
                MessageManager.getInstance().addMessage("You rejected the other party's challenge");
            }
        }

    }

    public void reply(String s) {
        if (s.startsWith(Header.CHALLENGE)) {
            s = s.substring(Header.CHALLENGE.length());
            String str[] = s.split("&");
            String challenged = str[0];
            String result = str[1];
            if (result.equals("YES")) {
                int uid = Integer.parseInt(challenged.split("-")[0]);
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "The other party accepted your challenge, please click \"Ready\" to start the game, or click \"Leave\" to re-select the opponent");
                Data.oppoId = uid;
                GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo()
                        .setText("Current opponent：" + challenged);
                MessageManager.getInstance().addMessage("The other party accepted your challenge, please click \"Ready\" to start the game, or click \"Leave\" to re-select the opponent");
            }
            else if (result.equals("NO")) {
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "Player“" + challenged + "”Rejected your challenge");
            }
        }
    }
    public void win() {
        JOptionPane.showMessageDialog(GameFrame.getInstance(), "You win！");
        MessageManager.getInstance().addMessage("You win！");
        Data.turn = 0;
        Data.ready = false;
        Data.started = false;
        GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("Quit");
    }
    public void lose() {
        JOptionPane.showMessageDialog(GameFrame.getInstance(), "You lose！");
        MessageManager.getInstance().addMessage("You lose！");
        Data.turn = 0;
        Data.ready = false;
        Data.started = false;
        GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("Quit");
    }

}