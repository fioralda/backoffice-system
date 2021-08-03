package finalproject;

import javax.swing.*;
import java.awt.*;

public class HelpModal extends JDialog {
    private JFrame frame;
    private JLabel text;

    public HelpModal(JFrame frame) {
        super(frame, "Help", true);
        this.frame = frame;
        initComponents();
    }

    private void initComponents() {
        text = new JLabel("<html>Final Project in Java Lab 2021<br />Fioralda Osmenai<br />AM: 4751</html>");

        this.add(text, BorderLayout.CENTER);
        this.setSize(400, 200);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
