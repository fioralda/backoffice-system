package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryModal extends JDialog {
    private JToolBar toolBar;
    private JButton bFirst, bPrevious, bNext, bLast, bAdd, bModify, bDelete, bOk, bCancel, bClose;
    private JPanel panel = new JPanel(new GridLayout(5, 2));
    private JPanel panel_closeButton = new JPanel();
    private JLabel lId, lCategory, lDescription, lPrice, lQuantity;
    private JTextField tId, tCategory, tDescription, tPrice, tQuantity;

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    private int current = 0;
    private int mode = 0;

    public InventoryModal(JFrame frame) {
        super(frame, "Inventory", true);
        this.con = Main.con;

        initComponents();
        prepareForm();
        this.setVisible(true);
    }

    private void initComponents() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        bFirst = new JButton("first");
        bFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doFirst();
            }
        });

        bPrevious = new JButton("previous");
        bPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doPrevious();
            }
        });

        bNext = new JButton("next");
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNext();
            }
        });

        bLast = new JButton("last");
        bLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLast();
            }
        });

        bAdd = new JButton("add");
        bAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRegister();
            }
        });

        bModify = new JButton("modify");
        bModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doModify();
            }
        });

        bDelete = new JButton("delete");
        bDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });
        bOk = new JButton("OK");
        bOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doOk();
            }
        });
        bCancel = new JButton("cancel");
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });
        bClose = new JButton("close");
        bClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });

        lId = new JLabel("ID:", SwingConstants.RIGHT);
        tId = new JTextField();
        lCategory = new JLabel("Category:", SwingConstants.RIGHT);
        tCategory = new JTextField();
        lDescription = new JLabel("Description:", SwingConstants.RIGHT);
        tDescription = new JTextField();
        lPrice = new JLabel("Price:", SwingConstants.RIGHT);
        tPrice = new JTextField();
        lQuantity = new JLabel("Quantity:", SwingConstants.RIGHT);
        tQuantity = new JTextField();

        panel_closeButton.add(bClose);

        panel.add(lId);
        panel.add(tId);
        panel.add(lCategory);
        panel.add(tCategory);
        panel.add(lDescription);
        panel.add(tDescription);
        panel.add(lPrice);
        panel.add(tPrice);
        panel.add(lQuantity);
        panel.add(tQuantity);


        toolBar.add(bFirst);
        toolBar.add(bPrevious);
        toolBar.add(bNext);
        toolBar.add(bLast);
        toolBar.add(bAdd);
        toolBar.add(bModify);
        toolBar.add(bDelete);
        toolBar.add(bOk);
        toolBar.add(bCancel);


        this.add(toolBar, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.add(panel_closeButton, BorderLayout.SOUTH);
        this.setSize(400, 200);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void prepareForm() {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "select * from inventory;";
            rs = stmt.executeQuery(query);

            if (rs.first()) {
                data2Form();
                current = 1;
            } else {
                current = 0;
            }

            tId.setEditable(false);
            tCategory.setEditable(false);
            tDescription.setEditable(false);
            tQuantity.setEditable(false);
            tPrice.setEditable(false);
            bOk.setEnabled(false);
            bCancel.setEnabled(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void data2Form() {
        try {
            tId.setText(String.valueOf(rs.getInt("idinv")));
            tCategory.setText(rs.getString("category"));
            tDescription.setText(rs.getString("description"));
            tQuantity.setText(rs.getString("quantity"));
            tPrice.setText(rs.getString("price"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void space2Form() {
        tId.setText(null);
        tCategory.setText(null);
        tDescription.setText(null);
        tQuantity.setText(null);
        tPrice.setText(null);
    }

    private void form2DB() {
        try {
            if (mode == 0) { // register
                rs.moveToInsertRow();
            }

            rs.updateString("category", tCategory.getText());
            rs.updateString("description", tDescription.getText());
            rs.updateString("quantity", tQuantity.getText());
            rs.updateString("price", tPrice.getText());

            if (mode == 0) {
                rs.insertRow();
            } else { // modify
                rs.updateRow();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doFirst() {
        // first
        try {

            if (rs.first()) {
                data2Form();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void doPrevious() {
        // previous
        try {
            if (!rs.isFirst()) {
                if (rs.previous()) {
                    data2Form();

                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doNext() {
        // next
        try {
            if (!rs.isLast()) {
                if (rs.next()) {
                    data2Form();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doLast() {
        // last
        try {

            if (rs.last()) {
                data2Form();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }        // TODO add your handling code here:
    }

    private void doRegister() {
        // register
        mode = 0;
        if (current > 0) {
            try {
                current = rs.getInt(1);
            } catch (SQLException e) {
                System.out.println("doRegister: " + e.getMessage());
            }
        }

        tDescription.setEditable(true);
        tCategory.setEditable(true);
        tPrice.setEditable(true);
        tQuantity.setEditable(true);
        space2Form();
        bOk.setEnabled(true);
        bCancel.setEnabled(true);
    }

    private void doModify() {
        // modify
        mode = 1;
        if (current > 0) {
            try {
                current = rs.getInt(1);
            } catch (SQLException e) {
                System.out.println("doRegister: " + e.getMessage());
            }
        }

        tDescription.setEditable(true);
        tCategory.setEditable(true);
        tPrice.setEditable(true);
        tQuantity.setEditable(true);

        bOk.setEnabled(true);
        bCancel.setEnabled(true);
    }

    private void doOk() {
        // OK
        form2DB();
        if (mode == 0) {
            doLast();
        }
        current += 1;
        tDescription.setEditable(false);
        tCategory.setEditable(false);
        tPrice.setEditable(false);
        tQuantity.setEditable(false);
        bOk.setEnabled(false);
        bCancel.setEnabled(false);
    }

    private void doCancel() {
        if (current > 0) {
            try {
                doFirst();
                while (current != rs.getInt(1)) {
                    doNext();
                }
                data2Form();
            } catch (SQLException e) {
                System.out.println("doCancel: " + e.getMessage());
            }
        }
        tDescription.setEditable(false);
        tCategory.setEditable(false);
        tPrice.setEditable(false);
        tQuantity.setEditable(false);
        bOk.setEnabled(false);
        bCancel.setEnabled(false);
    }

    private void doExit() {
        this.dispose();
    }

    private void doDelete() {
        try {
            rs.deleteRow();
            doPrevious();
            doNext();
        } catch (SQLException e) {
            System.out.println("doDelete: " + e.getMessage());
        }
    }
}
