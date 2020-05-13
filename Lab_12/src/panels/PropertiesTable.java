package panels;

import javax.swing.*;
import java.util.List;

public class PropertiesTable extends JPanel {
    private JTable jt;
    private String[] columns = {"Property name", "Details"};


    public PropertiesTable() {
        setLayout();
    }

    private void setLayout() {
        setLayout(null);
        setBounds(420, 5, 285, 200);

        String[][] data = {};
        jt = new JTable(data, columns);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(5, 5, 460, 290);
        add(sp);
    }

    public void populateProperties(List<String[]> data) {
        refresh();
        String[][] a = new String[data.size()][2];
        int index = 0;
        for (String[] x : data) {
            a[index][0] = x[0];
            a[index][1] = x[1];
            index++;
        }
        jt = new JTable(a, this.columns);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(5, 5, 285, 200);
        add(sp);
        revalidate();
    }

    private void refresh() {
        removeAll();
        repaint();
    }
}
