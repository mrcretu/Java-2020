package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import listener.ChallengeListener;
import manager.PlayerListManager;

public class PlayerListPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel opponentInfoLabel = null;
    private JPanel playerBodyPanel = new JPanel();

    private JPanel playerButtomPanel = new JPanel(new BorderLayout());

    private JScrollPane listPane = new JScrollPane();

    private JButton challengeButton = new JButton("challenge");

    PlayerListPanel() {
        listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listPane.setViewportView(PlayerListManager.getInstance().getPlayerList());
        playerBodyPanel.add(listPane);

        challengeButton.addActionListener(new ChallengeListener());

        playerButtomPanel.add(getOpponentInfo(), BorderLayout.CENTER);
        playerButtomPanel.add(challengeButton, BorderLayout.SOUTH);

        PlayerListManager.getInstance().getPlayerList().setFixedCellWidth(210);
        PlayerListManager.getInstance().getPlayerList().setVisibleRowCount(7);

        this.setLayout(new BorderLayout());
        this.setBorder(new TitledBorder(new EtchedBorder(), "Player list", TitledBorder.CENTER, TitledBorder.TOP));
        this.add(playerBodyPanel, BorderLayout.CENTER);
        this.add(playerButtomPanel, BorderLayout.SOUTH);
    }

    public JLabel getOpponentInfo() {
        if (opponentInfoLabel == null) {
            opponentInfoLabel = new JLabel("Current opponent: none");
        }
        return opponentInfoLabel;
    }
}