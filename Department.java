import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Department extends JFrame {

    JTable table;
    DefaultTableModel model;

    public Department() {
        setTitle("Department");
        setBounds(150, 80, 1000, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 248, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title - Changed
        JLabel heading = new JLabel("Hotel Department ");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        heading.setForeground(new Color(30, 60, 130));
        heading.setBounds(200, 20, 400, 40);
        add(heading);

        // Table setup
        String[] columns = { "Department", "Budget" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        // Header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 60, 130));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(100, 40));

        // Center cells
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 80, 500, 420);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true));
        add(scroll);

        // RIGHT SIDE IMAGE
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/department.png"));
        if (icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(430, 460, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setBounds(560, 80, 430, 460);
            add(imgLabel);
        }

        // BACK BUTTON
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(205, 510, 160, 40); // Center mein (neeche)
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(200, 70, 70));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backBtn);

        // Action
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });

        loadData();

        setVisible(true);
    }

    private void loadData() {
        try {
            Conn con = new Conn();
            PreparedStatement ps = con.c.prepareStatement(
                    "SELECT department, budget FROM department");
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("department"),
                        "₹" + rs.getString("budget"),
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Department();
    }

}