package tool;

import java.util.HashMap;
import java.util.HashSet;

import ui.ServerFrame;

public class HashMapManager {

    private static HashMapManager instance = null;

    private HashMap<Integer, Player> players = null;
    private HashMap<Integer, Integer> matchs = null;
    private HashMap<Integer, Integer> matching = null;
    private HashMap<Integer, Game> fightManagers = null;
    private HashSet<Integer> readys = null;

    private HashMapManager() {

    }

    public static HashMapManager getInstance() {
        if (instance == null) {
            instance = new HashMapManager();
        }
        return instance;
    }

    public HashMap<Integer, Game> getFightManagers() {
        if (fightManagers == null) {
            fightManagers = new HashMap<Integer, Game>();
        }
        return fightManagers;
    }

    public HashSet<Integer> getReadys() {
        if (readys == null) {
            readys = new HashSet<Integer>();
        }
        return readys;
    }

    public HashMap<Integer, Integer> getMatching() {
        if (matching == null) {
            matching = new HashMap<Integer, Integer>();
        }
        return matching;
    }

    public void addMatchs(Integer uid1, Integer uid2) {
        getMatchs().put(uid1, uid2);
        ServerFrame.getInstance().getMatchsPanel().addMatchs(uid1, uid2);
    }

    public void removeMatchs(Integer uid) {
        ServerFrame.getInstance().getMatchsPanel().removeMatchs(uid);
        getMatchs().remove(uid);
    }

    public HashMap<Integer, Integer> getMatchs() {
        if (matchs == null) {
            matchs = new HashMap<Integer, Integer>();
        }
        return matchs;
    }

    public String getName(Integer uid) {
        return getPlayer(uid).name;
    }

    public void addPlayer(Integer uid, Player player) {
        getPlayers().put(uid, player);
        ServerFrame.getInstance().getClientPanel().addClient(uid);
    }

    public void removePlayer(Integer uid) {
        getPlayers().remove(uid);
        ServerFrame.getInstance().getClientPanel().removeClient(uid);
    }

    public Player getPlayer(Integer uid) {
        return getPlayers().get(uid);
    }

    public HashMap<Integer, Player> getPlayers() {
        if (players == null) {
            players = new HashMap<Integer, Player>();
        }
        return players;
    }

}