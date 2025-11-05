import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class CustomerInfo extends JFrame {

    JTable table;
    DefaultTableModel model;

    public CustomerInfo() {
        setTitle("Customer Information");
        setSize(950, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 248, 255));

        // === TITLE ===
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(245, 248, 255));
        titlePanel.setPreferredSize(new Dimension(0, 70));

        JLabel heading = new JLabel("Customer Information");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        heading.setForeground(new Color(30, 60, 130));
        titlePanel.add(heading);
        add(titlePanel, BorderLayout.NORTH);

        // === TABLE ===
        String[] columns = { "Name", "Gender", "ID Type", "ID No.", "Country", "Room", "Check-in", "Deposit" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only table
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(34);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 60, 130));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true));
        add(scroll, BorderLayout.CENTER);

        // === BUTTON PANEL ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 248, 255));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 18));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 25, 0)); // spacing

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        backBtn.setBackground(new Color(30, 120, 220));
        backBtn.setForeground(Color.WHITE);
        backBtn.setPreferredSize(new Dimension(120, 42));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load data from DB
        loadData();

        setVisible(true);
    }

    private void loadData() {
        try {
            Conn con = new Conn();
            PreparedStatement ps = con.c.prepareStatement(
                    "SELECT name, gender, id_type, id_number, country, room_no, checkin_time, deposit FROM customers");
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("id_type"),
                        rs.getString("id_number"),
                        rs.getString("country"),
                        rs.getString("room_no"),
                        rs.getString("checkin_time"),
                        "₹" + rs.getString("deposit")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new CustomerInfo();
    }

}
