package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import net.Header;
import ui.GameFrame;

public class ReadyListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Data.connected) {
            if (Data.oppoId != 0) {
                if (!Data.ready) {
                    MessageManager.getInstance().addMessage("Please wait");
                    IOManager.getInstance().getPs().println(Header.OPERATION + Header.START);

                    Data.ready = true;
                } else {
                    MessageManager.getInstance().addMessage("Already prepared");
                }
            } else {
                JOptionPane.showMessageDialog(GameFrame.getInstance(), "Please choose an opponent first");
            }
        }
    }

}