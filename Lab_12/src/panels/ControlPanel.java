package panels;

import com.company.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    private JLabel messageLabel;

    public ControlPanel(MainFrame mainFrame) {
        setLayout();

        addLabel("Component: ", 20);
        JTextField componentNameInput = addInput(100, 20, 200, 20);

        JButton createComponentBtn = addButton("Create", 320, 48);

        addLabel("Placeholder: ", 50);
        JTextField defaultTextName = addInput(100, 50, 200, 20);


        setListenerForCreateBtn(mainFrame, componentNameInput, defaultTextName, createComponentBtn);


        JButton saveButton = addButton("Save", 250, 160);
        setListenerForSaveBtn(mainFrame, saveButton);

        JButton loadButton = addButton("Load", 320, 160);
        setListenerForLoadBtn(mainFrame, loadButton);

        addSimpleLabel("X: ", 10, 110, 20);
        JTextField xField = addInput(30, 110, 40, 15);

        addSimpleLabel("Y: ", 10, 135, 20);
        JTextField yField = addInput(30, 135, 40, 15);

        addSimpleLabel("Text: ", 70, 110, 50);
        JTextField textField = addInput(120, 110, 200, 20);

        addSimpleLabel("Width: ", 70, 135, 50);
        JTextField widthField = addInput(120, 135, 40, 15);

        addSimpleLabel("Height: ", 200, 135, 60);
        JTextField heightField = addInput(250, 135, 40, 15);

        JButton resetButton = addButton("Reset", 10, 160);
        setListenerForLoadBtn(mainFrame, loadButton);

        addListenerForResetButton(mainFrame, resetButton);

        JButton applyBtn = addButton("Apply", 320, 120);
        addListenerForApplyBtn(mainFrame, xField, yField, textField, widthField, heightField, applyBtn);

        setErrorMessage();
    }

    private void addListenerForResetButton(MainFrame mainFrame, JButton resetButton) {

        resetButton.addActionListener(e -> {
            int result = displayConfirmation();
            if (result == 0) {
                mainFrame.reset();
            }
        });
    }

    private void setErrorMessage() {
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.white);
        add(messageLabel);
    }

    private void addListenerForApplyBtn(MainFrame mainFrame, JTextField xField, JTextField yField, JTextField textField, JTextField widthField, JTextField heightField, JButton applyBtn) {
        applyBtn.addActionListener(e ->
                mainFrame.apply(xField.getText(), yField.getText(), widthField.getText(), heightField.getText(), textField.getText()));
    }

    private void addSimpleLabel(String s, int i, int i2, int i3) {
        JLabel xLabel = new JLabel(s);
        xLabel.setBounds(i, i2, i3, 20);
        xLabel.setForeground(Color.white);
        add(xLabel);
    }

    private void setListenerForLoadBtn(MainFrame mainFrame, JButton loadButton) {
        loadButton.addActionListener(e -> {
            try {
                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("designPanel.xml")));
                mainFrame.setElements((ArrayList) decoder.readObject());
                decoder.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setListenerForSaveBtn(MainFrame mainFrame, JButton saveButton) {
        saveButton.addActionListener(e -> {
            try {
                XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("designPanel.xml")));
                encoder.writeObject(mainFrame.getElements());
                encoder.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setListenerForCreateBtn(MainFrame mainFrame, JTextField componentNameInput, JTextField defaultTextName, JButton createComponentBtn) {
        createComponentBtn.addActionListener(e ->
                mainFrame.sendElement(componentNameInput.getText(), defaultTextName.getText())
        );
    }

    private JButton addButton(String create, int i, int i2) {
        JButton createComponentBtn = new JButton(create);
        Dimension btnSize = createComponentBtn.getPreferredSize();
        createComponentBtn.setBounds(i, i2, btnSize.width, btnSize.height);
        add(createComponentBtn);
        return createComponentBtn;
    }

    private JTextField addInput(int i, int i2, int i3, int i4) {
        JTextField newComponentInput = new JTextField();
        newComponentInput.setBounds(i, i2, i3, i4);
        add(newComponentInput);
        return newComponentInput;
    }

    private void addLabel(String s, int i) {
        JLabel componentLbl = new JLabel(s);
        Dimension componentLabelSize = componentLbl.getPreferredSize();
        componentLbl.setBounds(10, i, componentLabelSize.width, componentLabelSize.height);
        componentLbl.setForeground(Color.white);
        add(componentLbl);
    }

    private void setLayout() {
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setBounds(10, 10, 410, 200);
        String cpTitle = "Control";
        Border controlPanelBorder = BorderFactory.createTitledBorder(cpTitle);
        setBorder(controlPanelBorder);
    }

    private int displayConfirmation() {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(250, 100));
        panel.setLayout(null);
        JLabel label1 = new JLabel("The changes will be lost.");
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setBounds(20, 20, 200, 30);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        JLabel label2 = new JLabel("Are you sure?");
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(20, 80, 200, 20);
        panel.add(label2);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 200));
        int res = JOptionPane.showConfirmDialog(null, panel, "File",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        return res;
    }

    public void setMessageFromDesignPanel(String message) {
        this.messageLabel.setText(message);
        Dimension messageLabelSize = messageLabel.getPreferredSize();
        messageLabel.setBounds(140, 150, messageLabelSize.width, messageLabelSize.height);
        this.revalidate();
    }
}
