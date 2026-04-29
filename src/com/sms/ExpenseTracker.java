package com.sms;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

@SuppressWarnings("serial")
public class ExpenseTracker extends JFrame {

    JTextField titleField, amountField, categoryField;
    JTextArea area;

    public ExpenseTracker() {
        setTitle("Smart Expense Tracker");
        setSize(500, 500);
        setLayout(new FlowLayout());

        add(new JLabel("Title:"));
        titleField = new JTextField(20);
        add(titleField);

        add(new JLabel("Amount:"));
        amountField = new JTextField(20);
        add(amountField);

        add(new JLabel("Category:"));
        categoryField = new JTextField(20);
        add(categoryField);

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View");
        JButton deleteBtn = new JButton("Delete");

        add(addBtn);
        add(viewBtn);
        add(deleteBtn);

        area = new JTextArea(15, 40);
        add(new JScrollPane(area));

        // ADD
        addBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO expenses(title, amount, category) VALUES (?, ?, ?)"
                );

                ps.setString(1, titleField.getText());
                ps.setDouble(2, Double.parseDouble(amountField.getText()));
                ps.setString(3, categoryField.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Expense Added!");
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // VIEW
        viewBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM expenses");

                area.setText("");

                while (rs.next()) {
                    area.append(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getDouble("amount") + " | " +
                        rs.getString("category") + "\n"
                    );
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter ID to delete:");

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM expenses WHERE id=?"
                );

                ps.setInt(1, Integer.parseInt(id));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Deleted!");
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}