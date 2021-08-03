package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReportCustomers extends JDialog {
    private JFrame frame;
    private JButton button;
    private JPanel panel_button = new JPanel();
    private Connection con;

    public ReportCustomers(JFrame frame) {
        super(frame, "Customers Report", true);
        this.frame = frame;
        this.con = Main.con;
        initComponents();

    }

    private void initComponents() {
        button = new JButton("Print Customers");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement stmt = con.createStatement();

                    String query1 = "select * from customer;";
                    ResultSet rs = stmt.executeQuery(query1);
                    System.out.format("%8s%16s%16s%16s%16s\n", "Code", "Lastname", "Firstname", "AFM", "Telephone");
                    System.out.format("===========================================================================\n");
                    while (rs.next()) {
                        int id = rs.getInt("idcustomer");
                        String lastname = rs.getString("lastname");
                        String firstname = rs.getString("firstname");
                        String afm = rs.getString("afm");
                        String telephone = rs.getString("telephone");
                        System.out.format("%8d%16s%16s%16s%16s\n", id, lastname, firstname, afm, telephone);
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
