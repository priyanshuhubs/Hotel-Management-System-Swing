import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class PickupService extends JFrame {

    JTable table;
    DefaultTableModel model;

    public PickupService() {
        setTitle("Pickup Service - Drivers");
        setBounds(150, 100, 1000, 550);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 247, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Heading
        JLabel heading = new JLabel("Drivers Information");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        heading.setForeground(new Color(30, 60, 130));
        heading.setBounds(220, 25, 400, 40);
        add(heading);

        // Table setup
        String[] columns = { "Name", "Age", "Gender", "Car Name", "Available", "Location" };
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 230, 250));
        table.setFocusable(false);
        table.setIntercellSpacing(new Dimension(10, 5)); // 🧩 Padding inside cells

        // Header design
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(40, 70, 150));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(100, 38));
        header.setReorderingAllowed(false); // prevent column drag

        // Center align data properly
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // ScrollPane for table
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 80, 560, 380);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true));
        add(scroll);

        // Right Side Static Image
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/driver.png"));
        Image img = icon.getImage().getScaledInstance(330, 380, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(640, 80, 330, 380);
        imageLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(imageLabel);

        // Back Button
        JButton back = new JButton("Back");
        back.setBounds(340, 470, 160, 40);
        back.setFont(new Font("Segoe UI", Font.BOLD, 15));
        back.setBackground(new Color(200, 70, 70));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> setVisible(false));
        add(back);

        // Load Driver Data
        loadDriverData();

        setVisible(true);
    }

    private void loadDriverData() {
        try {
            Conn conn = new Conn();
            PreparedStatement ps = conn.c.prepareStatement("SELECT * FROM driver");
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        rs.getString("company"),
                        rs.getString("available"),
                        rs.getString("location")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading driver data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PickupService();
    }

}
