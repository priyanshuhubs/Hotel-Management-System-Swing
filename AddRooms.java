import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import javax.swing.*;

public class AddRooms extends JFrame implements ActionListener {
    JButton addButton, cancelButton;
    JTextField tfroom, tfprice;
    JComboBox<String> CategoryBox, RoomCombox, CleanCombox;

    AddRooms() {
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // Light professional blue-gray

        // Heading
        JLabel heading = new JLabel("Add Rooms");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setBounds(150, 25, 300, 40);
        add(heading);

        // Common Fonts
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Room Number
        JLabel roomNo = new JLabel("Room Number");
        roomNo.setFont(labelFont);
        roomNo.setBounds(60, 90, 150, 25);
        add(roomNo);

        tfroom = new JTextField();
        tfroom.setBounds(230, 90, 200, 30);
        tfroom.setFont(fieldFont);
        tfroom.setBackground(new Color(225, 235, 245));
        tfroom.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tfroom);

        // Room Availability
        JLabel roomAvailable = new JLabel("Room Available");
        roomAvailable.setFont(labelFont);
        roomAvailable.setBounds(60, 130, 150, 25);
        add(roomAvailable);

        String availableOption[] = { "Available", "Occupied" };
        RoomCombox = new JComboBox<>(availableOption);
        RoomCombox.setFont(fieldFont);
        RoomCombox.setBounds(230, 130, 200, 30);
        RoomCombox.setBackground(new Color(225, 235, 245));
        RoomCombox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        RoomCombox.setFocusable(false);
        add(RoomCombox);

        // Room Status
        JLabel Roomstatus = new JLabel("Room Status");
        Roomstatus.setFont(labelFont);
        Roomstatus.setBounds(60, 170, 150, 25);
        add(Roomstatus);

        String cleanOption[] = { "Clean", "Dirty" };
        CleanCombox = new JComboBox<>(cleanOption);
        CleanCombox.setFont(fieldFont);
        CleanCombox.setBounds(230, 170, 200, 30);
        CleanCombox.setBackground(new Color(225, 235, 245));
        CleanCombox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        CleanCombox.setFocusable(false);
        add(CleanCombox);

        // Room Rate
        JLabel RoomPrice = new JLabel("Room Rate");
        RoomPrice.setFont(labelFont);
        RoomPrice.setBounds(60, 210, 150, 25);
        add(RoomPrice);

        tfprice = new JTextField();
        tfprice.setBounds(230, 210, 200, 30);
        tfprice.setFont(fieldFont);
        tfprice.setBackground(new Color(225, 235, 245));
        tfprice.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tfprice);

        // Room Category
        JLabel Roomtype = new JLabel("Room Category");
        Roomtype.setFont(labelFont);
        Roomtype.setBounds(60, 250, 150, 25);
        add(Roomtype);

        String RoomCategory[] = { "Single Bed", "Double Bed" };
        CategoryBox = new JComboBox<>(RoomCategory);
        CategoryBox.setFont(fieldFont);
        CategoryBox.setBounds(230, 250, 200, 30);
        CategoryBox.setBackground(new Color(225, 235, 245));
        CategoryBox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        CategoryBox.setFocusable(false);
        add(CategoryBox);

        // Buttons
        addButton = new JButton("ADD");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setFocusPainted(false);
        addButton.setBounds(110, 320, 120, 35);
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(260, 320, 120, 35);
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/twelve.jpg"));
        Image i2 = i1.getImage().getScaledInstance(340, 240, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(480, 80, 320, 240);
        image.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        add(image);

        // Frame Setup
        setBounds(200, 200, 860, 460);
        setTitle("Add Rooms");
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addButton) {
            String roomNumber = tfroom.getText();
            String availability = (String) RoomCombox.getSelectedItem();
            String status = (String) CleanCombox.getSelectedItem();
            String price = tfprice.getText();
            String type = (String) CategoryBox.getSelectedItem();

            if (roomNumber.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing information detected. Kindly check and fill all inputs!");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO room(room_number,category,status,price,availability) VALUES (?,?,?,?,?)";
                PreparedStatement ps = conn.prepare(query);
                ps.setString(1, roomNumber);
                ps.setString(2, type);
                ps.setString(3, status);
                ps.setString(4, price);
                ps.setString(5, availability);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Room added successfully!");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddRooms();
    }
}
