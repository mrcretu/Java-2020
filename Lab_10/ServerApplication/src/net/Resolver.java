package net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import tool.HashMapManager;
import tool.MessageManager;

public class Resolver {

    Socket socket;
    int uid;
    String readLine;

    public void resolve(int _uid, Socket s, String _readLine) {
        this.socket = s;
        this.uid = _uid;
        this.readLine = _readLine;
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            if (readLine.startsWith(Header.LIST)) {
                new Action().getList(socket);
            }
            if (readLine.startsWith(Header.REPLY)) {
                String str = readLine.substring(Header.REPLY.length());
                if (str.startsWith(Header.CHALLENGE)) {
                    new Action().replyChallenge(uid, str.substring(Header.CHALLENGE.length()));
                }
            }
            if (readLine.startsWith(Header.PLAY)) {
                String str = readLine.substring(Header.PLAY.length());
                int position = Integer.parseInt(str);
                new Action().playChess(uid, position);
            }
            if (readLine.startsWith(Header.CHAT)) {
                String str = readLine.substring(Header.CHAT.length());
                new Action().sendMessage(uid, str);
            }
            if (readLine.startsWith(Header.OPERATION)) {
                String str = readLine.substring(Header.OPERATION.length());
                if (str.startsWith(Header.CHALLENGE)) {
                    str = str.substring(Header.CHALLENGE.length());
                    int target = Integer.parseInt(str);
                    new Action().sendChallenge(uid, target);
                    HashMapManager.getInstance().getMatching().put(uid, target);
                }
                if (str.startsWith(Header.START)) {
                    new Action().ready(uid);
                }
                if (str.startsWith(Header.RESTART)) {
                    new Action().restart(uid);
                }
                if (str.startsWith(Header.QUIT)) {
                    int oppoId = Integer.parseInt(str.substring(Header.QUIT.length()));
                    new Action().quit(uid, oppoId);
                }
                if (str.startsWith(Header.GIVEUP)) {
                    int oppoId = Integer.parseInt(str.substring(Header.GIVEUP.length()));
                    new Action().giveUp(uid, oppoId);
                }
            }
            if (readLine.startsWith(Header.INIT)) {
                HashMapManager.getInstance().getPlayer(uid).setName(readLine.substring(Header.INIT.length()));
                new Action().newClient(uid);
                ps.println(Header.INIT + uid + "-" + readLine.substring(Header.INIT.length()));
            }
        } catch (IOException e) {
            MessageManager.getInstance().addMessage("Error getting output stream during parsing");
            e.printStackTrace();
        }
    }

}