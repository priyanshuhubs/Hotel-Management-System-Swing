import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateRoomStatus extends JFrame implements ActionListener {

    JComboBox<String> roomBox;
    JLabel lblCurrentStatus;
    JButton btnToggle, btnBack;
    Conn conn;

    UpdateRoomStatus() {
        setTitle("Update Room Status");
        setBounds(250, 120, 600, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 247, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Heading
        JLabel heading = new JLabel("Change Room Clean Status");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        heading.setForeground(new Color(40, 70, 150));
        heading.setBounds(130, 30, 400, 30);
        add(heading);

        // Room Number label
        JLabel lblRoom = new JLabel("Select Room:");
        lblRoom.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblRoom.setBounds(90, 100, 150, 25);
        add(lblRoom);

        // Dropdown to select room
        roomBox = new JComboBox<>();
        roomBox.setBounds(220, 100, 200, 28);
        roomBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(roomBox);

        // Current Status label
        JLabel lblStatus = new JLabel("Current Status:");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblStatus.setBounds(90, 150, 150, 25);
        add(lblStatus);

        lblCurrentStatus = new JLabel("—");
        lblCurrentStatus.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        lblCurrentStatus.setForeground(new Color(60, 100, 160));
        lblCurrentStatus.setBounds(230, 150, 200, 25);
        add(lblCurrentStatus);

        // Buttons
        btnToggle = new JButton("Toggle Status");
        btnToggle.setBounds(100, 230, 180, 40);
        btnToggle.setBackground(new Color(40, 90, 160));
        btnToggle.setForeground(Color.WHITE);
        btnToggle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnToggle.setFocusPainted(false);
        btnToggle.addActionListener(this);
        add(btnToggle);

        btnBack = new JButton("Back");
        btnBack.setBounds(320, 230, 120, 40);
        btnBack.setBackground(new Color(170, 60, 60));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBack.addActionListener(e -> setVisible(false));
        add(btnBack);

        // Load room numbers
        loadRoomNumbers();

        // When a room is selected, show current status
        roomBox.addActionListener(e -> showCurrentStatus());

        setVisible(true);
    }

    private void loadRoomNumbers() {
        try {
            conn = new Conn();
            PreparedStatement ps = conn.c.prepareStatement("SELECT room_number FROM room");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roomBox.addItem(rs.getString("room_number"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage());
        }
    }

    private void showCurrentStatus() {
        try {
            String roomNo = (String) roomBox.getSelectedItem();
            PreparedStatement ps = conn.c.prepareStatement("SELECT status FROM room WHERE room_number = ?");
            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblCurrentStatus.setText(rs.getString("status"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching status: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String roomNo = (String) roomBox.getSelectedItem();
            String currentStatus = lblCurrentStatus.getText();
            String newStatus = currentStatus.equalsIgnoreCase("Clean") ? "Unclean" : "Clean";

            PreparedStatement ps = conn.c.prepareStatement("UPDATE room SET status = ? WHERE room_number = ?");
            ps.setString(1, newStatus);
            ps.setString(2, roomNo);
            ps.executeUpdate();
            ps.close();

            lblCurrentStatus.setText(newStatus);
            JOptionPane.showMessageDialog(this, "Room status updated to: " + newStatus);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UpdateRoomStatus();
    }

}
