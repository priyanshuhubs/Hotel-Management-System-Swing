import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RoomDetails extends JFrame {

    public RoomDetails(String roomNo) {
        setTitle("Room Details - " + roomNo);
        setSize(600, 420);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 245, 255));

        JLabel title = new JLabel("Room Information");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        title.setBounds(190, 20, 300, 40);
        add(title);

        JLabel lblRoomNo = new JLabel("Room No: " + roomNo);
        lblRoomNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRoomNo.setBounds(40, 90, 300, 25);
        add(lblRoomNo);

        JLabel lblType = new JLabel("Type: ");
        lblType.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblType.setBounds(40, 130, 300, 25);
        add(lblType);

        JLabel lblStatus = new JLabel("Status: ");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStatus.setBounds(40, 170, 300, 25);
        add(lblStatus);

        JLabel lblAvailability = new JLabel("Availability: ");
        lblAvailability.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAvailability.setBounds(40, 210, 300, 25);
        add(lblAvailability);

        JLabel lblPrice = new JLabel("Price: ");
        lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPrice.setBounds(40, 250, 300, 25);
        add(lblPrice);

        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(320, 90, 230, 200);
        imgLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true));
        add(imgLabel);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(220, 320, 140, 35);
        closeBtn.setBackground(new Color(180, 60, 60));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.addActionListener(e -> dispose());
        add(closeBtn);

        // Load DB Data
        try {
            Conn con = new Conn();
            String query = "SELECT * FROM room WHERE room_number = ?";
            PreparedStatement ps = con.c.prepareStatement(query);
            ps.setString(1, roomNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblType.setText("Type: " + rs.getString("category"));
                lblStatus.setText("Status: " + rs.getString("status"));
                lblAvailability.setText("Availability: " + rs.getString("availability"));
                lblPrice.setText("Price: ₹" + rs.getString("price"));

                String type = rs.getString("category");
                String imgPath = type.equalsIgnoreCase("Single Bed") ? "icons/single.jpg" : "icons/double.jpg";

                ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource(imgPath));
                Image img = icon.getImage().getScaledInstance(230, 200, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new RoomDetails(null);
    }

}
