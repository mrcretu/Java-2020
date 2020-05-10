package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class FunctionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    PlayerListPanel playerListPanel = null;
    MessagePanel messagePanel = null;
    OperationPanel operationPanel = null;

    FunctionPanel() {
        this.setLayout(new BorderLayout());
        this.add(getPlayerListPanel(), BorderLayout.NORTH);
        this.add(getMessagePanel(), BorderLayout.CENTER);
        this.add(getOperationPanel(), BorderLayout.SOUTH);
    }

    public PlayerListPanel getPlayerListPanel() {
        if (playerListPanel == null) {
            playerListPanel = new PlayerListPanel();
        }
        return playerListPanel;
    }

    public MessagePanel getMessagePanel() {
        if (messagePanel == null) {
            messagePanel = new MessagePanel();
        }
        return messagePanel;
    }

    public OperationPanel getOperationPanel() {
        if (operationPanel == null) {
            operationPanel = new OperationPanel();
        }
        return operationPanel;
    }
}