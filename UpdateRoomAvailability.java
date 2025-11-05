import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateRoomAvailability extends JFrame implements ActionListener {

    JComboBox<String> roomBox;
    JLabel lblCurrentAvailability;
    JButton btnToggle, btnBack;
    Conn conn;

    public UpdateRoomAvailability() {
        setTitle("Update Room Availability");
        setBounds(250, 120, 600, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 247, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Heading
        JLabel heading = new JLabel("Change Room Availability");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        heading.setForeground(new Color(40, 70, 150));
        heading.setBounds(140, 30, 400, 30);
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

        // Current Availability label
        JLabel lblAvailability = new JLabel("Current Availability:");
        lblAvailability.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblAvailability.setBounds(90, 150, 150, 25);
        add(lblAvailability);

        lblCurrentAvailability = new JLabel("—");
        lblCurrentAvailability.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        lblCurrentAvailability.setForeground(new Color(60, 100, 160));
        lblCurrentAvailability.setBounds(250, 150, 200, 25);
        add(lblCurrentAvailability);

        // Buttons
        btnToggle = new JButton("Toggle Availability");
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

        // When a room is selected, show current availability
        roomBox.addActionListener(e -> showCurrentAvailability());

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

    private void showCurrentAvailability() {
        try {
            String roomNo = (String) roomBox.getSelectedItem();
            PreparedStatement ps = conn.c.prepareStatement("SELECT availability FROM room WHERE room_number = ?");
            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblCurrentAvailability.setText(rs.getString("availability"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching availability: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String roomNo = (String) roomBox.getSelectedItem();
            String current = lblCurrentAvailability.getText();
            String newStatus = current.equalsIgnoreCase("Available") ? "Occupied" : "Available";

            PreparedStatement ps = conn.c.prepareStatement("UPDATE room SET availability = ? WHERE room_number = ?");
            ps.setString(1, newStatus);
            ps.setString(2, roomNo);
            ps.executeUpdate();
            ps.close();

            lblCurrentAvailability.setText(newStatus);
            JOptionPane.showMessageDialog(this, "Room availability updated to: " + newStatus);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating availability: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new UpdateRoomAvailability();
    }
}
