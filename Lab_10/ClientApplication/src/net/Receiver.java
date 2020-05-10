package net;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import client.Data;
import manager.IOManager;
import manager.PlayerListManager;
import ui.GameFrame;

public class Receiver implements Runnable {

    @Override
    public void run() {
        BufferedReader br = IOManager.getInstance().getBr();
        String s;

        while (Data.connected) {
            try {
                // Received a command
                s = br.readLine();

                System.out.println("[RECEIVE]" + s);

                // initialization
                if (s.startsWith(Header.INIT)) {
                    new Resolver().init(s.substring(Header.INIT.length()));
                }

                // Game start message
                if (s.startsWith(Header.STARTMSG)) {
                    new Resolver().startMessage(s.substring(Header.STARTMSG.length()));
                }

                // The game starts now
                if (s.startsWith(Header.START)) {
                    new Resolver().start(s.substring(Header.START.length()));
                }

                // update list
                if (s.startsWith(Header.LIST)) {
                    new Resolver().updateList(s.substring(Header.LIST.length()));
                }

                // Add player
                if (s.startsWith(Header.ADDPLAYER)) {
                    new Resolver().addList(s.substring(Header.ADDPLAYER.length()));
                }

                // Remove player
                if (s.startsWith(Header.DELETEPLAYER)) {
                    new Resolver().delList(s.substring(Header.DELETEPLAYER.length()));
                }
                if (s.startsWith(Header.PLAY)) {
                    new Resolver().play(s.substring(Header.PLAY.length()));
                }

                // to chat with
                if (s.startsWith(Header.CHAT)) {
                    new Resolver().chat(s.substring(Header.CHAT.length()));
                }

                // operating
                if (s.startsWith(Header.OPERATION)) {
                    new Resolver().operation(s.substring(Header.OPERATION.length()));
                }

                // receipt
                if (s.startsWith(Header.REPLY)) {
                    new Resolver().reply(s.substring(Header.REPLY.length()));
                }

                // Win
                if (s.startsWith(Header.WIN)) {
                    new Resolver().win();
                }

                // Defeat
                if (s.startsWith(Header.LOSE)) {
                    new Resolver().lose();
                }

            } catch (IOException e) {
                // Server disconnected
                // Clear data
                Data.last = -1;
                Data.turn = -1;
                Data.myId = 0;
                Data.oppoId = 0;
                Data.myName = null;
                Data.myChess = 0;
                Data.oppoChess = 0;
                Data.ready = false;
                Data.started = false;
                Data.connected = false;
                Data.chessBoard = new int[15][15];
                PlayerListManager.getInstance().clearList();
                GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("Current opponent: none");
                GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");

                JOptionPane.showMessageDialog(GameFrame.getInstance(), "Lost connection with server");
                GameFrame.getInstance().showLoginPanel();
            }
        }
    }

}