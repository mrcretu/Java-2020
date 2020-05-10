package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import manager.IOManager;
import net.Connecter;
import net.Header;
import net.Receiver;
import ui.GameFrame;

public class ConnectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Data.connected) {
            new Connecter().connect();
            if (Data.connected) {
                new Thread(new Receiver()).start();
                String name = GameFrame.getInstance().getLoginPanel().getNameTextField().getText();
                IOManager.getInstance().getPs().println(Header.INIT + name);
            }
        }
    }

}