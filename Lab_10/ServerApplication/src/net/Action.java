package net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import tool.Game;
import tool.HashMapManager;
import tool.Player;

public class Action {

    HashMapManager manager = HashMapManager.getInstance();

    public void newClient(int uid) {
        HashMap<Integer, Player> players = manager.getPlayers();
        Collection<Player> c = players.values();
        Iterator<Player> i = c.iterator();
        while (i.hasNext()) {
            Socket s = i.next().socket;
            try {
                PrintStream ps = new PrintStream(s.getOutputStream());
                String name = manager.getName(uid);
                ps.println(Header.ADDPLAYER + uid + "-" + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClient(int uid) {
        Set<Integer> p = manager.getPlayers().keySet();
        Iterator<Integer> i = p.iterator();
        while (i.hasNext()) {
            Socket s = manager.getPlayer(i.next()).socket;
            try {
                PrintStream ps = new PrintStream(s.getOutputStream());
                ps.println(Header.DELETEPLAYER + uid + "-" + manager.getPlayer(uid).name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getList(Socket s) {
        String list = null;
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            Set<Integer> ids = manager.getPlayers().keySet();
            Iterator<Integer> i = ids.iterator();
            while (i.hasNext()) {
                int id = (Integer) i.next();
                if (list == null) {
                    list = id + "-" + manager.getPlayer(id).name + "&";
                } else {
                    list = list + id + "-" + manager.getPlayer(id).name + "&";
                }
            }
            ps.println(Header.LIST + list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(int uid, String readLine) {
        String s[] = readLine.split("&");
        String message = s[0];
        int targetId = Integer.parseInt(s[1]);
        Socket socket = manager.getPlayer(targetId).socket;
        try {
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            printStream.println(Header.CHAT + message + "&" + uid + "-" + HashMapManager.getInstance().getName(uid));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendChallenge(int uid, int target) {
        HashMap<Integer, Player> players = manager.getPlayers();
        String name = HashMapManager.getInstance().getName(uid);
        Socket s = players.get(target).socket;
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println(Header.OPERATION + Header.CHALLENGE + uid + "-" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart(int uid) {
        if (manager.getFightManagers().containsKey(uid)) {
            manager.getFightManagers().get(uid).restart(uid);
        }
    }
    public void replyChallenge(int uid, String readLine) {
        String[] s = readLine.split("&");
        int challengerId = Integer.parseInt(s[0]);
        String choose = s[1];
        Socket socket = manager.getPlayer(challengerId).socket;
        try {
            PrintStream challengerPs = new PrintStream(socket.getOutputStream());
            if (choose.equals("YES")) {
                challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&YES");
                manager.getMatching().remove(challengerId);
                manager.addMatchs(challengerId, uid);
            } else {
                challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&NO");
                manager.getMatching().remove(challengerId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ready(int uid) {
        int oppoId = 0;
        manager.getReadys().add(uid);
        if (manager.getMatchs().containsKey(uid)) {
            oppoId = manager.getMatchs().get(uid);
        } else {
            Set<Integer> s = manager.getMatchs().keySet();
            Iterator<Integer> i = s.iterator();
            while (i.hasNext()) {
                int id = i.next();
                if (manager.getMatchs().get(id) == uid) {
                    oppoId = id;
                }
            }
        }
        if (manager.getReadys().contains(oppoId)) {
            Game publicManager = manager.getFightManagers().get(oppoId);
            manager.getFightManagers().put(uid, publicManager);
            publicManager.startPlay(uid, oppoId);
            publicManager.sendStartMessage();
        } else {
            manager.getFightManagers().put(uid, new Game());
        }
    }

    public void playChess(int from, int position) {
        manager.getFightManagers().get(from).sendPlay(from, position);
    }

    public void quit(int uid, int oppoId) {
        HashMapManager manager = HashMapManager.getInstance();
        Socket s = manager.getPlayer(oppoId).socket;
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println(Header.OPERATION + Header.QUIT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.getReadys().remove(uid);
        manager.getReadys().remove(oppoId);
        if (manager.getMatchs().containsKey(uid)) {
            manager.removeMatchs(uid);
        } else {
            manager.removeMatchs(oppoId);
        }
        manager.getFightManagers().remove(uid);
        manager.getFightManagers().remove(oppoId);
    }

    public void giveUp(int uid, int oppoId) {
        HashMapManager manager = HashMapManager.getInstance();
        Socket s = manager.getPlayer(oppoId).socket;
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println(Header.OPERATION + Header.GIVEUP);
        } catch (IOException e) {
            e.printStackTrace();
        }
        manager.getReadys().remove(uid);
        manager.getReadys().remove(oppoId);
        if (manager.getMatchs().containsKey(uid)) {
            manager.removeMatchs(uid);
        } else {
            manager.removeMatchs(oppoId);
        }
        manager.getFightManagers().remove(uid);
        manager.getFightManagers().remove(oppoId);
    }

}