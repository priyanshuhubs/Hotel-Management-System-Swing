import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Checkout extends JFrame implements ActionListener {

    JComboBox<String> cbCustomerId;
    JTextField tfName, tfRoom, tfCheckin, tfCheckout, tfDeposit;
    JButton btnPreview, btnCheckout, btnBack;

    public Checkout() {
        setTitle("Customer Checkout");
        setBounds(250, 120, 870, 520);
        setLayout(null);
        getContentPane().setBackground(new Color(238, 244, 252));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Customer Checkout");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        heading.setForeground(new Color(40, 70, 150));
        heading.setBounds(300, 25, 400, 40);
        add(heading);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Color labelColor = new Color(50, 50, 70);

        JLabel lblId = new JLabel("Customer ID:");
        lblId.setBounds(120, 100, 150, 25);
        lblId.setFont(labelFont);
        lblId.setForeground(labelColor);
        add(lblId);

        cbCustomerId = new JComboBox<>();
        cbCustomerId.setBounds(280, 100, 180, 25);
        cbCustomerId.addActionListener(e -> loadCustomerDetails());
        add(cbCustomerId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(120, 150, 150, 25);
        lblName.setFont(labelFont);
        lblName.setForeground(labelColor);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(280, 150, 180, 25);
        tfName.setEditable(false);
        add(tfName);

        JLabel lblRoom = new JLabel("Room No:");
        lblRoom.setBounds(120, 200, 150, 25);
        lblRoom.setFont(labelFont);
        lblRoom.setForeground(labelColor);
        add(lblRoom);

        tfRoom = new JTextField();
        tfRoom.setBounds(280, 200, 180, 25);
        tfRoom.setEditable(false);
        add(tfRoom);

        JLabel lblCheckin = new JLabel("Check-in Time:");
        lblCheckin.setBounds(120, 250, 150, 25);
        lblCheckin.setFont(labelFont);
        lblCheckin.setForeground(labelColor);
        add(lblCheckin);

        tfCheckin = new JTextField();
        tfCheckin.setBounds(280, 250, 180, 25);
        tfCheckin.setEditable(false);
        add(tfCheckin);

        JLabel lblDeposit = new JLabel("Deposit:");
        lblDeposit.setBounds(120, 300, 150, 25);
        lblDeposit.setFont(labelFont);
        lblDeposit.setForeground(labelColor);
        add(lblDeposit);

        tfDeposit = new JTextField();
        tfDeposit.setBounds(280, 300, 180, 25);
        tfDeposit.setEditable(false);
        add(tfDeposit);

        JLabel lblCheckout = new JLabel("Checkout Time:");
        lblCheckout.setBounds(120, 350, 150, 25);
        lblCheckout.setFont(labelFont);
        lblCheckout.setForeground(labelColor);
        add(lblCheckout);

        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tfCheckout = new JTextField(currentTime);
        tfCheckout.setBounds(280, 350, 180, 25);
        tfCheckout.setEditable(false);
        add(tfCheckout);

        // Buttons
        btnPreview = new JButton("Preview Receipt");
        btnPreview.setBounds(80, 420, 160, 38);
        btnPreview.setBackground(new Color(70, 110, 180));
        btnPreview.setForeground(Color.WHITE);
        btnPreview.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnPreview.setFocusPainted(false);
        btnPreview.addActionListener(this);
        add(btnPreview);

        btnCheckout = new JButton("Confirm Checkout");
        btnCheckout.setBounds(260, 420, 180, 38);
        btnCheckout.setBackground(new Color(35, 130, 90));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCheckout.setFocusPainted(false);
        btnCheckout.addActionListener(this);
        add(btnCheckout);

        btnBack = new JButton("Back");
        btnBack.setBounds(470, 420, 140, 38);
        btnBack.setBackground(new Color(180, 60, 60));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> setVisible(false));
        add(btnBack);

        // Right Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/checkout.jpg"));
        Image i2 = i1.getImage().getScaledInstance(350, 320, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(540, 100, 300, 300);
        add(image);

        loadCustomerIds();
        setVisible(true);
    }

    private void loadCustomerIds() {
        try {
            Conn conn = new Conn();
            PreparedStatement ps = conn.c.prepareStatement("SELECT id_number FROM customers");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                cbCustomerId.addItem(rs.getString("id_number"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customer IDs: " + e.getMessage());
        }
    }

    private void loadCustomerDetails() {
        String id = (String) cbCustomerId.getSelectedItem();
        if (id == null)
            return;

        try {
            Conn conn = new Conn();
            PreparedStatement ps = conn.c.prepareStatement(
                    "SELECT name, room_no, checkin_time, deposit FROM customers WHERE id_number=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tfName.setText(rs.getString("name"));
                tfRoom.setText(rs.getString("room_no"));
                tfCheckin.setText(rs.getString("checkin_time"));
                tfDeposit.setText(rs.getString("deposit"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading details: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        String id = (String) cbCustomerId.getSelectedItem();
        String name = tfName.getText();
        String room = tfRoom.getText();
        String checkin = tfCheckin.getText();
        String checkout = tfCheckout.getText();
        String deposit = tfDeposit.getText();

        if (ae.getSource() == btnPreview) {
            if (id == null || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a valid customer!");
                return;
            }

            // Receipt Popup
            JTextArea receipt = new JTextArea();
            receipt.setEditable(false);
            receipt.setFont(new Font("Monospaced", Font.PLAIN, 14));
            receipt.setText(
                    "------------------- HOTEL RadhaBallav -------------------\n" +
                            "                      Checkout Receipt\n" +
                            "--------------------------------------------------------\n" +
                            "Customer ID:     " + id + "\n" +
                            "Name:            " + name + "\n" +
                            "Room No:         " + room + "\n" +
                            "Check-in Time:   " + checkin + "\n" +
                            "Check-out Time:  " + checkout + "\n" +
                            "Deposit:         ₹" + deposit + "\n" +
                            "--------------------------------------------------------\n" +
                            "Thank you for staying with us!\nHave a pleasant day 😊\n");

            JOptionPane.showMessageDialog(this, new JScrollPane(receipt), "Preview Receipt",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (ae.getSource() == btnCheckout) {
            if (id == null || room.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a valid customer!");
                return;
            }

            try {
                Conn conn = new Conn();
                PreparedStatement ps = conn.c.prepareStatement("DELETE FROM customers WHERE id_number=?");
                ps.setString(1, id);
                ps.executeUpdate();

                PreparedStatement ps2 = conn.c
                        .prepareStatement("UPDATE room SET availability='Available' WHERE room_number=?");
                ps2.setString(1, room);
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(this, "Checkout successful!");
                setVisible(false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error during checkout: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Checkout();
    }
}
