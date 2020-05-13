package com.company;

import panels.ControlPanel;
import panels.DesignPanel;
import panels.PropertiesTable;

import javax.swing.*;

public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private DesignPanel designPanel;
    private PropertiesTable propertiesTable;

    public DesignPanel getDesignPanel() {
        return designPanel;
    }

    public MainFrame()  {
        super("Dynamic Swing Designer");

        controlPanel = new ControlPanel();
        designPanel = new DesignPanel();
        propertiesTable = new PropertiesTable();
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);

        add(controlPanel);
        add(designPanel);
        add(propertiesTable);
        setVisible(true);
    }
}
