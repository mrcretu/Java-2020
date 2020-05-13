package panels;

import com.company.MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DesignPanel extends JPanel {
    private MainFrame mainFrame;
    private JComponent currentComponent;

    private List<Object> objects;

    public DesignPanel(MainFrame mainFrame) {
        this.objects = new ArrayList<>();
        this.mainFrame = mainFrame;
        setLayout();
    }

    private void setLayout() {
        setLayout(null);

        setBackground(Color.white);
        setBounds(5, 220, 690, 500);

        String controlPanelTitle = "Design";
        Border controlPanelBorder = BorderFactory.createTitledBorder(controlPanelTitle);
        setBorder(controlPanelBorder);
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        refresh();
        this.objects = objects;
        handleElements();
        revalidate();
    }

    public void refresh() {
        this.removeAll();
        this.repaint();
    }

    private void handleElements() {
        for (Object obj : this.objects) {
            this.add((Component) obj);
            ((Component) obj).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        currentComponent = ((JComponent) obj);
                        BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
                        List<String[]> data = new ArrayList<>();
                        for (PropertyDescriptor prop : bi.getPropertyDescriptors()) {
                            String[] a = {prop.getName(), prop.getShortDescription()};
                            data.add(a);
                        }
                        mainFrame.setPropsTbl(data);
                    } catch (IntrospectionException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }


    public void set(String componentName, String defaultText) {
        try {
            Class<?> cls = Class.forName(componentName);
            Constructor<?> ctor = cls.getConstructor(String.class);
            Object obj = ctor.newInstance(defaultText);
            JComponent cmp = ((JComponent) obj);
            cmp.setBounds(100, 100, 50, 50);
            this.add(cmp);
            this.repaint();
            (cmp).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        currentComponent = cmp;
                        BeanInfo bi = Introspector.getBeanInfo(cmp.getClass());
                        List<String[]> data = new ArrayList<>();
                        for (PropertyDescriptor prop : bi.getPropertyDescriptors()) {
                            String[] a = {prop.getName(), prop.getShortDescription()};
                            data.add(a);
                        }
                        mainFrame.setPropsTbl(data);
                    } catch (IntrospectionException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            this.objects.add(cmp);
            this.mainFrame.sendMessage("");
        } catch (InstantiationException e) {
            System.out.println("Component can't be instantiate!");
        } catch (InvocationTargetException e) {
            System.out.println("Component can't be invoked!");
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found!");
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access!");
        } catch (ClassNotFoundException e) {
            this.mainFrame.sendMessage("Class name not found");
        }
    }

    public void applyChanges(String x, String y, String width, String height, String text) {
        int tempX = this.currentComponent.getX();
        int tempY = this.currentComponent.getY();
        int tempWidth = this.currentComponent.getWidth();
        int tempHeight = this.currentComponent.getHeight();

        if (!text.equals("")) {
            if (this.currentComponent instanceof JButton) {
                JButton jbtn = ((JButton) this.currentComponent);
                jbtn.setText(text);
            } else if (this.currentComponent instanceof JLabel) {
                JLabel jlbl = ((JLabel) this.currentComponent);
                jlbl.setText(text);
            } else if (this.currentComponent instanceof JTextField) {
                JTextField jtxt = ((JTextField) this.currentComponent);
                jtxt.setText(text);
            } else if (this.currentComponent instanceof JCheckBox) {
                JCheckBox jck = ((JCheckBox) this.currentComponent);
                jck.setText(text);
            }
        }

        if (!x.isEmpty()) {
            tempX = Integer.parseInt(x);
        }
        if (!y.isEmpty()) {
            tempY = Integer.parseInt(y);
        }
        if (!width.isEmpty()) {
            tempWidth = Integer.parseInt(width);
        }
        if (!height.isEmpty()) {
            tempHeight = Integer.parseInt(height);
        }

        this.currentComponent.setBounds(tempX, tempY, tempWidth, tempHeight);
        this.repaint();
    }
}
