package finalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaceOrderListener implements ActionListener {
    private JFrame frame;
    private PlaceOrderModal modal;

    public PlaceOrderListener(JFrame frame) {
        this.frame = frame;
        this.modal = new PlaceOrderModal(this.frame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.modal.setVisible(true);
    }
}
