package finalproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerModal extends JDialog {

    private JToolBar toolBar;
    private JButton bFirst, bPrevious, bNext, bLast, bAdd, bModify, bDelete, bOk, bCancel, bClose;
    private JPanel panel = new JPanel(new GridLayout(5, 2));
    private JPanel panel_closeButton = new JPanel();
    private JLabel lId, lLastname, lFirstname, lAfm, lTelephone;
    private JTextField tId, tLastname, tFirstname, tAfm, tTelephone;

    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    private int current = 0;
    private int mode = 0;

    public CustomerModal(JFrame frame) {
        super(frame, "Customer", true);
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
        lLastname = new JLabel("Lastname:", SwingConstants.RIGHT);
        tLastname = new JTextField();
        lFirstname = new JLabel("Firstname:", SwingConstants.RIGHT);
        tFirstname = new JTextField();
        lAfm = new JLabel("AFM:", SwingConstants.RIGHT);
        tAfm = new JTextField();
        lTelephone = new JLabel("Telephone:", SwingConstants.RIGHT);
        tTelephone = new JTextField();

        panel_closeButton.add(bClose);

        panel.add(lId);
        panel.add(tId);
        panel.add(lLastname);
        panel.add(tLastname);
        panel.add(lFirstname);
        panel.add(tFirstname);
        panel.add(lAfm);
        panel.add(tAfm);
        panel.add(lTelephone);
        panel.add(tTelephone);


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
            String query = "select * from customer;";
            rs = stmt.executeQuery(query);

            if (rs.first()) {
                data2Form();
                current = 1;
            } else {
                current = 0;
            }

            tId.setEditable(false);
            tLastname.setEditable(false);
            tFirstname.setEditable(false);
            tTelephone.setEditable(false);
            tAfm.setEditable(false);
            bOk.setEnabled(false);
            bCancel.setEnabled(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void data2Form() {
        try {
            tId.setText(String.valueOf(rs.getInt("idcustomer")));
            tLastname.setText(rs.getString("lastname"));
            tFirstname.setText(rs.getString("firstname"));
            tTelephone.setText(rs.getString("telephone"));
            tAfm.setText(rs.getString("afm"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void space2Form() {
        tId.setText(null);
        tLastname.setText(null);
        tFirstname.setText(null);
        tTelephone.setText(null);
        tAfm.setText(null);
    }

    private void form2DB() {
        try {
            if (mode == 0) { // register
                rs.moveToInsertRow();
            }

            rs.updateString("lastname", tLastname.getText());
            rs.updateString("firstname", tFirstname.getText());
            rs.updateString("telephone", tTelephone.getText());
            rs.updateString("afm", tAfm.getText());

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

        tFirstname.setEditable(true);
        tLastname.setEditable(true);
        tAfm.setEditable(true);
        tTelephone.setEditable(true);
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

        tFirstname.setEditable(true);
        tLastname.setEditable(true);
        tAfm.setEditable(true);
        tTelephone.setEditable(true);

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
        tFirstname.setEditable(false);
        tLastname.setEditable(false);
        tAfm.setEditable(false);
        tTelephone.setEditable(false);
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
        tFirstname.setEditable(false);
        tLastname.setEditable(false);
        tAfm.setEditable(false);
        tTelephone.setEditable(false);
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
