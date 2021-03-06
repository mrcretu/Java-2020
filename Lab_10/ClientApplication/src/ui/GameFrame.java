package ui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import net.Connecter;

public class GameFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private static GameFrame instance = null;

    LoginPanel loginPanel = null;
    GamePanel gamePanel = null;
    FunctionPanel functionPanel = null;

    private GameFrame() {
        super("Gobang game");
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }
    public void showLoginPanel() {
        this.setVisible(false);
        this.remove(getGamePanel());

        this.setLayout(new BorderLayout());
        this.add(getLoginPanel(), BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new Connecter().disconnect();
                super.windowClosed(e);
            }
        });
        this.setVisible(true);
    }

    public void showGamePanel() {
        this.setVisible(false);
        this.remove(getLoginPanel());

        this.setLayout(new BorderLayout());
        this.add(getGamePanel(), BorderLayout.CENTER);
        this.add(getFunctionPanel(), BorderLayout.EAST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new Connecter().disconnect();
                super.windowClosed(e);
            }
        });
        this.getFunctionPanel().getOperationPanel().setQuitButtonText("Quit");
        this.setVisible(true);
    }

    public LoginPanel getLoginPanel() {
        if (loginPanel == null) {
            loginPanel = new LoginPanel();
        }
        return loginPanel;
    }

    public GamePanel getGamePanel() {
        if (gamePanel == null) {
            gamePanel = new GamePanel();
        }
        return gamePanel;
    }

    public FunctionPanel getFunctionPanel() {
        if (functionPanel == null) {
            functionPanel = new FunctionPanel();
        }
        return functionPanel;
    }

}