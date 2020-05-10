package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import manager.PlayerListManager;
import net.Header;
import ui.GameFrame;

public class ChallengeListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JList<String> list = PlayerListManager.getInstance().getPlayerList();
        if (Data.oppoId == 0) {
            if (!list.isSelectionEmpty()) {
                String s = list.getSelectedValue().split("-")[0];
                int targetId = Integer.parseInt(s);
                if (targetId != Data.myId) {
                    MessageManager.getInstance().addMessage("Waiting for the other party to accept the challenge");
                    IOManager.getInstance().getPs().println(Header.OPERATION + Header.CHALLENGE + targetId);
                } else {
                    JOptionPane.showMessageDialog(GameFrame.getInstance(), "Can't challenge yourself");
                }
            } else {
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "No opponent selected");
            }
        } else {
            MessageManager.getInstance().addMessage("Existing opponent");
        }
    }

}