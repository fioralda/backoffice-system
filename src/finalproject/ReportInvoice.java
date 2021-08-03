package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReportInvoice extends JDialog {
    private JFrame frame;
    private JButton button;
    private JPanel panel_button = new JPanel();
    private JPanel topPanel = new JPanel(new GridLayout(1, 2));
    private Connection con;
    private JLabel labelinvoice = new JLabel("Invoice For Customer : ");
    private JComboBox customers = new JComboBox();
    private ArrayList<String> items = new ArrayList<>();

    public ReportInvoice(JFrame frame) {
        super(frame, "Invoice Report", true);
        this.frame = frame;
        this.con = Main.con;

        try {
            Statement stmt = con.createStatement();
            String query = "select * from customer;";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("idcustomer");
                String lastname = rs.getString("lastname");
                String firstname = rs.getString("firstname");
                String row = lastname + ", " + firstname + ", " + id;
                items.add(row);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
    }

    private void initComponents() {
        button = new JButton("Print");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] splitCustomer = customers.getSelectedItem().toString().split(",");
                    String customerId = splitCustomer[splitCustomer.length - 1];
                    Statement stmt = con.createStatement();

                    String query1 = "select * from orders left join inventory on orders.invid = inventory.idinv where orders.custid=" + customerId + ";";
                    ResultSet rs = stmt.executeQuery(query1);
                    System.out.println("For Customer ID : " + customerId);
                    System.out.println("   Customer Name : " + splitCustomer[0] + " " + splitCustomer[1]);
                    System.out.format("===========================================================================\n");
                    System.out.format("%8s%16s%16s%16s%16s\n", "Order", "Category", "Description", "Quantity", "Price");
                    System.out.format("===========================================================================\n");
                    while (rs.next()) {
                        int id = rs.getInt("idOrder");
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

        for (String customer : items) {
            customers.addItem(customer);
        }

        topPanel.setBorder(new EmptyBorder(30, 10, 0, 10));
        topPanel.add(labelinvoice);
        topPanel.add(customers);

        panel_button.setBorder(new EmptyBorder(50, 10, 50, 10));
        panel_button.add(button);


        this.add(panel_button, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }
}
