package tool;

import javax.swing.JTextArea;

public class MessageManager {

    private static MessageManager instance = null;

    private JTextArea messageArea = null;

    private MessageManager() {

    }

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public JTextArea getMessageArea() {
        if (messageArea == null) {
            messageArea = new JTextArea("", 10, 33);
            messageArea.setEditable(false);
        }
        return messageArea;
    }

    public void addMessage(String s) {
        messageArea.append("•" + s + System.lineSeparator());
    }

}