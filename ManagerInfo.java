import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ManagerInfo extends JFrame {

    JTable table;
    DefaultTableModel model;

    public ManagerInfo() {
        setTitle("Manager Information");
        setBounds(70, 40, 1150, 650);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 248, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title
        JLabel heading = new JLabel("Manager Information");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 32));
        heading.setForeground(new Color(30, 60, 130));
        heading.setBounds(0, 15, getWidth(), 50);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading);

        // Table setup
        String[] columns = { "Name", "Age", "Gender", "Salary", "Phone", "Email", "Aadhar " };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table read-only
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(38);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Sab columns fit

        // Header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 60, 130));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(100, 42));

        // Center all cells
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // ScrollPane - POORA FRAME TAK
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 80, 1050, 460); // Full width, full height
        scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true));
        add(scroll);

        // BACK BUTTON - CENTER MEIN
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(510, 560, 130, 45);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(200, 70, 70));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        add(backBtn);

        loadData();
        setVisible(true);
    }

    private void loadData() {
        try {
            Conn con = new Conn();
            String query = "SELECT name, age, gender, salary, phone, email, aadhar FROM employee WHERE job = 'Manager'";
            PreparedStatement ps = con.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        "₹" + rs.getString("salary"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("aadhar")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ManagerInfo();
    }
}