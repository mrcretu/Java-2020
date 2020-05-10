package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import listener.QuitListener;
import listener.ReadyListener;
import listener.RestartListener;

public class OperationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel operationPanel = new JPanel(new BorderLayout());

    private JButton quitButton = new JButton("Quit");
    private JButton restartButton = new JButton("Again");
    private JButton readyButton = new JButton("Ready");

    OperationPanel() {
        quitButton.addActionListener(new QuitListener());
        restartButton.addActionListener(new RestartListener());
        readyButton.addActionListener(new ReadyListener());

        operationPanel.add(quitButton, BorderLayout.WEST);
        operationPanel.add(restartButton, BorderLayout.CENTER);
        operationPanel.add(readyButton, BorderLayout.EAST);
        this.add(operationPanel);
    }

    public void setQuitButtonText(String s) {
        quitButton.setText(s);
    }

}