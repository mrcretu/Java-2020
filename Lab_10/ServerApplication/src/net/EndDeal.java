package net;

import tool.HashMapManager;
import tool.MessageManager;

public class EndDeal {

    public void clientOff(int uid) {
        HashMapManager manager = HashMapManager.getInstance();
        if (manager.getMatchs().containsKey(uid)) {
            int oppoId = manager.getMatchs().get(uid);
            if (manager.getReadys().contains(uid)) {
                manager.getReadys().remove(uid);
            }
            if (manager.getReadys().contains(oppoId)) {
                manager.getReadys().remove(oppoId);
            }
            if (manager.getFightManagers().containsKey(uid)) {
                if (manager.getFightManagers().containsKey(oppoId)) {
                    manager.getFightManagers().remove(oppoId);
                }
                manager.getFightManagers().remove(uid);
            }
            manager.removeMatchs(uid);
        }
        if (manager.getMatching().containsKey(uid)) {
            manager.getMatching().remove(uid);
        }
        new Action().removeClient(uid);
        MessageManager.getInstance().addMessage("Player" + uid + "offline");
        manager.removePlayer(uid);
    }

}