package finalproject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    private JFrame frame = new JFrame("Invoice Application 2021");
    private JMenuBar menuBar;
    private JMenu files;
    private JMenuItem files_inventory, files_customers, exit;

    private JMenu order;
    private JMenuItem place_order;

    private JMenu reports;
    private JMenuItem reports_customers, reports_inventory, reports_invoice;

    private JMenu help;
    private JMenuItem about;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        menuBar = new JMenuBar();
        files = new JMenu("Files");

        files_customers = new JMenuItem("Customers");
        files_customers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerModal(frame);
            }
        });

        files_inventory = new JMenuItem("Inventory");
        files_inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InventoryModal(frame);
            }
        });

        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        order = new JMenu("Order");
        place_order = new JMenuItem("Place Order");

        reports = new JMenu("Reports");
        reports_customers = new JMenuItem("Customers");
        reports_customers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportCustomers(frame);
            }
        });
        reports_inventory = new JMenuItem("Inventory");
        reports_inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportInventory(frame);
            }
        });
        reports_invoice = new JMenuItem("INVOICE");
        reports_invoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReportInvoice(frame);
            }
        });

        help = new JMenu("Help");
        about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpModal(frame);
            }
        });

        files.add(files_inventory);
        files.add(files_customers);
        files.add(exit);

        order.add(place_order);
        place_order.addActionListener(new PlaceOrderListener(frame));

        reports.add(reports_customers);
        reports.add(reports_inventory);
        reports.insertSeparator(2);
        reports.add(reports_invoice);

        help.add(about);

        menuBar.add(files);
        menuBar.add(order);
        menuBar.add(reports);
        menuBar.add(help);

        frame.setJMenuBar(menuBar);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
