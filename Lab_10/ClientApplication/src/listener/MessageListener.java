package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import manager.IOManager;
import manager.MessageManager;
import net.Header;
import ui.GameFrame;

public class MessageListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().getText();
        int oppoId = Data.oppoId;
        if (oppoId != 0) {
            IOManager.getInstance().getPs().println(Header.CHAT + message + "&" + oppoId);
            GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
            MessageManager.getInstance().addMessage("I：" + message);
        } else {
            MessageManager.getInstance().addMessage("Can't speak without opponent");
            GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().selectAll();
        }
    }

}