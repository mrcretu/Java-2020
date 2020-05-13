package com.company;

import panels.ControlPanel;
import panels.DesignPanel;
import panels.PropertiesTable;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {
    private ControlPanel ctrlPnl;
    private DesignPanel designPnl;
    private PropertiesTable propTbl;

    public MainFrame() {
        super("Dynamic Swing Designer");
        ctrlPnl = new ControlPanel(this);
        designPnl = new DesignPanel(this);
        propTbl = new PropertiesTable();
        setLayout();
    }

    private void setLayout() {
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);

        add(ctrlPnl);
        add(designPnl);
        add(propTbl);

        setVisible(true);
    }


    public void sendElement(String componentName, String defaultText) {
        designPnl.set(componentName, defaultText);
    }

    public void sendMessage(String message) {
        ctrlPnl.setMessageFromDesignPanel(message);
    }

    public List<Object> getElements() {
        return this.designPnl.getObjects();
    }

    public void setElements(List<Object> elements) {
        this.designPnl.setObjects(elements);
    }

    public void setPropsTbl(List<String[]> data) {
        propTbl.populateProperties(data);
    }

    public void apply(String x, String y, String width, String height, String text) {
        this.designPnl.applyChanges(x, y, width, height, text);
    }

    public void reset() {
        this.designPnl.refresh();
    }
}
