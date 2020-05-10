package ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.ServerThread;
import tool.MessageManager;

public class ServerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static ServerFrame instance = null;

    ServerSocket serversocket = null; // 服务端Socket

    private ClientPanel clientPanel = null;
    private MatchsPanel matchsPanel = null;
    private MessagePanel messagePanel = null;

    private ServerFrame() {
        super("Welcome to Gomuku");

        this.setLayout(new BorderLayout());
        this.add(getMatchsPanel(), BorderLayout.EAST);
        this.add(getClientPanel(), BorderLayout.WEST);
        this.add(getMessagePanel(), BorderLayout.SOUTH);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void launchFrame() {
        try {
            serversocket = new ServerSocket(46666);
            MessageManager.getInstance().addMessage("Server is listening for clients  ...");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Port is occupied! Please check the port");
            System.exit(0);
        }
        while (true) {
            try {
                Socket socket = serversocket.accept();
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientPanel getClientPanel() {
        if (clientPanel == null) {
            clientPanel = new ClientPanel();
        }
        return clientPanel;
    }

    public MatchsPanel getMatchsPanel() {
        if (matchsPanel == null) {
            matchsPanel = new MatchsPanel();
        }
        return matchsPanel;
    }

    public MessagePanel getMessagePanel() {
        if (messagePanel == null) {
            messagePanel = new MessagePanel();
        }
        return messagePanel;
    }

    public static ServerFrame getInstance() {
        if (instance == null) {
            instance = new ServerFrame();
        }
        return instance;
    }

}