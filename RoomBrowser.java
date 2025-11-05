import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RoomBrowser extends JFrame {

    JTable table;
    DefaultTableModel model;

    public RoomBrowser() {
        setTitle("Hotel Room Browser");
        setBounds(180, 90, 1000, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(243, 247, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title
        JLabel heading = new JLabel("Room Details");
        heading.setFont(new Font("Tahoma Bold", Font.BOLD, 26));
        heading.setForeground(new Color(30, 60, 130));
        heading.setBounds(200, 12, 300, 40);
        add(heading);

        // Search Bar
        JLabel searchLabel = new JLabel("Search Room:");
        searchLabel.setBounds(60, 65, 120, 25);
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(170, 65, 60, 25);
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(283, 65, 100, 25);
        searchBtn.setBackground(new Color(40, 90, 160));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchBtn.setFocusPainted(false);
        add(searchBtn);

        // Filter panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(null);
        filterPanel.setBackground(new Color(243, 247, 255));
        filterPanel.setBounds(60, 100, 520, 35); // ⬅️ Reduced width to stop before image
        add(filterPanel);

        JLabel lblFilter = new JLabel("Filter By:");
        lblFilter.setBounds(0, 5, 80, 25);
        lblFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterPanel.add(lblFilter);

        JComboBox<String> bedTypeFilter = new JComboBox<>(new String[] { "All", "Single Bed", "Double Bed" });
        bedTypeFilter.setBounds(70, 5, 100, 25);
        bedTypeFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterPanel.add(bedTypeFilter);

        JCheckBox availableOnly = new JCheckBox("Available Only");
        availableOnly.setBounds(220, 5, 150, 25);
        availableOnly.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        availableOnly.setBackground(new Color(243, 247, 255));
        filterPanel.add(availableOnly);

        // Table setup
        String[] columns = { "Room No", "Category", "Availibility", "Price", "Status" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 230));
        table.setSelectionBackground(new Color(220, 230, 250));
        table.setSelectionForeground(Color.BLACK);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 60, 130));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        header.setPreferredSize(new Dimension(100, 40));

        int[] colWidths = { 90, 120, 120, 90, 130 };
        for (int i = 0; i < colWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // ScrollPane — reduced width to stop before image
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 140, 520, 370);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 1, true));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scroll);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/bedroom.jpg"));
        Image img = icon.getImage().getScaledInstance(380, 550, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(580, 25, 400, 520);
        imgLabel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        add(imgLabel);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(230, 520, 160, 38);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(200, 70, 70));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(backBtn);

        backBtn.addActionListener(e -> dispose());

        // Table click → open details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String roomNo = model.getValueAt(row, 0).toString();
                    new RoomDetails(roomNo);
                }
            }
        });

        // Filters
        searchBtn.addActionListener(e -> {
            String room = searchField.getText().trim();
            if (room.isEmpty())
                loadData((String) bedTypeFilter.getSelectedItem(), availableOnly.isSelected());
            else
                loadSpecificRoom(room);
        });

        bedTypeFilter
                .addActionListener(e -> loadData((String) bedTypeFilter.getSelectedItem(), availableOnly.isSelected()));
        availableOnly
                .addActionListener(e -> loadData((String) bedTypeFilter.getSelectedItem(), availableOnly.isSelected()));

        loadData("All", false);
        setVisible(true);
    }

    private void loadData(String bedType, boolean showAvailableOnly) {
        try {
            Conn con = new Conn();
            String query = "SELECT room_number, category, status, price, availability FROM room WHERE 1=1";
            if (!bedType.equals("All"))
                query += " AND category = ?";
            if (showAvailableOnly)
                query += " AND availability = 'Available'";

            PreparedStatement ps = con.c.prepareStatement(query);
            int param = 1;
            if (!bedType.equals("All"))
                ps.setString(param++, bedType);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("room_number"),
                        rs.getString("category"),
                        rs.getString("availability"),
                        "₹" + rs.getString("price"),
                        rs.getString("status")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadSpecificRoom(String roomNo) {
        try {
            Conn con = new Conn();
            String query = "SELECT room_number, category, status, price, availability FROM room WHERE room_number = ?";
            PreparedStatement ps = con.c.prepareStatement(query);
            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("room_number"),
                        rs.getString("category"),
                        rs.getString("status"),
                        "₹" + rs.getString("price"),
                        rs.getString("availability")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new RoomBrowser();
    }

}
