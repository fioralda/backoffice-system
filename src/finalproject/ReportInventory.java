package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportInventory extends JDialog {
    private JFrame frame;
    private JButton button;
    private JPanel panel_button = new JPanel();
    private Connection con;

    public ReportInventory(JFrame frame) {
        super(frame, "Inventory Report", true);
        this.frame = frame;
        this.con = Main.con;
        initComponents();
    }

    private void initComponents() {
        button = new JButton("Print Inventory");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement stmt = con.createStatement();

                    String query1 = "select * from inventory;";
                    ResultSet rs = stmt.executeQuery(query1);
                    System.out.format("%8s%16s%16s%16s%16s\n", "Id", "Category", "Description", "Price", "Quantity");
                    System.out.format("===========================================================================\n");
                    while (rs.next()) {
                        int id = rs.getInt("idinv");
                        String category = rs.getString("category");
                        String description = rs.getString("description");
                        int price = rs.getInt("price");
                        int quantity = rs.getInt("quantity");
                        System.out.format("%8d%16s%16s%16d%16d\n", id, category, description, price, quantity);
                    }
                    System.out.format("===========================================================================\n");
                    stmt.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        panel_button.setBorder(new EmptyBorder(50, 10, 50, 10));
        panel_button.add(button);
        this.add(panel_button, BorderLayout.CENTER);
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
