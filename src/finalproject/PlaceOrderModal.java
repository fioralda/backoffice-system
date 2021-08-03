package finalproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PlaceOrderModal extends JDialog {
    private JComboBox customerBox, itemBox;
    private String column[] = {"OrderID", "Customer", "Category", "Description", "Price"};
    private DefaultTableModel model = new DefaultTableModel(column, 0);
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> customers = new ArrayList<>();

    private JSplitPane generalPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JPanel topPanel = new JPanel(new BorderLayout());
    private JPanel buttonPanel = new JPanel(new BorderLayout());
    private JPanel panel_form = new JPanel(new GridLayout(6, 2));
    private JPanel panel_buttons = new JPanel();
    private JLabel lCustomer, lItem, lPrice, lQuantity, lTotalPrice, l1, l2;
    private JTextField tPrice, tQuantity, tTotalPrice;
    private JButton bAdd, bDelete, bExit;
    private JTable databaseTable = new JTable(model);
    private JScrollPane scroll = new JScrollPane(databaseTable);
    private JToolBar exitToolbar = new JToolBar();
    private Connection con;
    private JFrame frame;

    public PlaceOrderModal(JFrame frame) {
        super(frame, "Place Order", true);
        this.con = Main.con;
        this.frame = frame;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);


        try {
            Statement stmt = con.createStatement();
            String query = "select * from inventory;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String category = rs.getString("category");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int idinv = rs.getInt("idinv");
                String row = category + ", " + description + ", " + price + ", " + idinv;
                items.add(row);
            }

            String query1 = "select * from customer;";
            rs = stmt.executeQuery(query1);
            while (rs.next()) {
                int id = rs.getInt("idcustomer");
                String lastname = rs.getString("lastname");
                String firstname = rs.getString("firstname");
                String row = lastname + ", " + firstname + ", " + id;
                customers.add(row);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();

        try {
            Statement stmt = con.createStatement();

            String query = "select * from orders left join finalproject.customer on custid=idcustomer left join finalproject.inventory on invid=idinv;";
            ResultSet rs1 = stmt.executeQuery(query);
            DefaultTableModel model = (DefaultTableModel) databaseTable.getModel();
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            while (rs1.next()) {
                String idOrder = rs1.getString("idOrder");
                String Customer = rs1.getString("lastname");
                String Category = rs1.getString("category");
                String Description = rs1.getString("description");
                String Price = String.valueOf(rs1.getInt("price"));
                model.addRow(new Object[]{idOrder, Customer, Category, Description, Price});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {

        lCustomer = new JLabel("Customer:", SwingConstants.RIGHT);
        customerBox = new JComboBox();
        for (String customer : customers) {
            customerBox.addItem(customer);
        }
        lItem = new JLabel("Inventory Item:", SwingConstants.RIGHT);
        itemBox = new JComboBox();
        for (String item : items) {
            itemBox.addItem(item);
        }
        itemBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemBox.getSelectedItem();
                tPrice.setText(selectedItem.split(",")[2]);
                tTotalPrice.setText(selectedItem.split(",")[2]);
                tQuantity.setText(selectedItem.split(",")[3]);
            }
        });
        lPrice = new JLabel("Item Price:", SwingConstants.RIGHT);
        tPrice = new JTextField();
        tPrice.setEditable(false);
        tPrice.setText(items.get(0).split(",")[2]);
        lQuantity = new JLabel("Quantity:", SwingConstants.RIGHT);
        tQuantity = new JTextField();
        tQuantity.setEditable(false);
        tQuantity.setText(items.get(0).split(",")[3]);
        lTotalPrice = new JLabel("Total Price:", SwingConstants.RIGHT);
        tTotalPrice = new JTextField();
        tTotalPrice.setEditable(false);
        tTotalPrice.setText(items.get(0).split(",")[2]);
        l1 = new JLabel();
        l2 = new JLabel();

        bAdd = new JButton("Add Line");
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer = (String) customerBox.getSelectedItem();
                String custid = customer.split(", ")[2];

                String item = (String) itemBox.getSelectedItem();
                int price = Integer.parseInt(item.split(", ")[2]);
                int invid = Integer.parseInt(item.split(", ")[3]);

                int quantity = 0;
                try {
                    Statement stmt = con.createStatement();
                    String query = "insert into orders values(null, ?, ?, 1, ?);";
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString(1, custid);
                    preparedStmt.setInt(2, invid);
                    preparedStmt.setInt(3, price);
                    int result = preparedStmt.executeUpdate();

                    if (result != 0) {
                        JOptionPane.showMessageDialog(frame, "Successful order!");
                        query = "select * from orders left join finalproject.customer on custid=idcustomer left join finalproject.inventory on invid=idinv;";
                        ResultSet rs = stmt.executeQuery(query);
                        // delete all table rows
                        DefaultTableModel model = (DefaultTableModel) databaseTable.getModel();
                        int rowCount = model.getRowCount();
                        for (int i = rowCount - 1; i >= 0; i--) {
                            model.removeRow(i);
                        }
                        while (rs.next()) {
                            String idOrder = rs.getString("idOrder");
                            String Customer = rs.getString("lastname");
                            String Category = rs.getString("category");
                            String Description = rs.getString("description");
                            String Price = String.valueOf(rs.getInt("price"));
                            model.addRow(new Object[]{idOrder, Customer, Category, Description, Price});
                        }

                    } else {
                        JOptionPane.showMessageDialog(frame, "Object not available!");
                    }
                    stmt.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        bDelete = new JButton("Delete");
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = 0;
                int row = databaseTable.getSelectedRow();
                String idOrder = databaseTable.getModel().getValueAt(row, column).toString();

                try {
                    Statement stmt = con.createStatement();
                    String query = "delete from orders where idOrder=" + idOrder + ";";
                    int rs = stmt.executeUpdate(query);
                    if (rs != 0) {
                        query = "select * from orders left join finalproject.customer on custid=idcustomer left join finalproject.inventory on invid=idinv;";
                        ResultSet rs1 = stmt.executeQuery(query);
                        // delete all table rows
                        DefaultTableModel model = (DefaultTableModel) databaseTable.getModel();
                        int rowCount = model.getRowCount();
                        for (int i = rowCount - 1; i >= 0; i--) {
                            model.removeRow(i);
                        }
                        while (rs1.next()) {
                            idOrder = rs1.getString("idOrder");
                            String Customer = rs1.getString("lastname");
                            String Category = rs1.getString("category");
                            String Description = rs1.getString("description");
                            String Price = String.valueOf(rs1.getInt("price"));
                            model.addRow(new Object[]{idOrder, Customer, Category, Description, Price});
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        bExit = new JButton("Exit");
        bExit.addActionListener(e -> {
            this.setVisible(false);
        });

        panel_form.add(lCustomer);
        panel_form.add(customerBox);
        panel_form.add(lItem);
        panel_form.add(itemBox);
        panel_form.add(lPrice);
        panel_form.add(tPrice);
        panel_form.add(lQuantity);
        panel_form.add(tQuantity);
        panel_form.add(lTotalPrice);
        panel_form.add(tTotalPrice);
        panel_form.add(l1);
        panel_form.add(l2);

        panel_buttons.add(bAdd);
        panel_buttons.add(bDelete);

        exitToolbar.add(bExit);
        exitToolbar.setFloatable(false);
        topPanel.add(panel_form, BorderLayout.CENTER);
        topPanel.add(panel_buttons, BorderLayout.SOUTH);

        buttonPanel.add(scroll, BorderLayout.CENTER);
        buttonPanel.add(exitToolbar, BorderLayout.SOUTH);

        generalPanel.add(topPanel);
        generalPanel.add(buttonPanel);

        this.add(generalPanel);
        this.setSize(400, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
