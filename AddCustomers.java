import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCustomers extends JFrame implements ActionListener {

    JComboBox<String> idTypeBox, countryBox, roomBox;
    JTextField tfNumber, tfName, tfCheckin, tfDeposit;
    JRadioButton rbMale, rbFemale;
    JButton submit, cancel;
    ButtonGroup genderGroup;

    public AddCustomers() {

        setTitle("Add Guest Details");
        setBounds(200, 120, 850, 530);
        setLayout(null);
        getContentPane().setBackground(new Color(238, 244, 252)); // subtle sky grey

        // Title
        JLabel heading = new JLabel("Add Guest Details");
        heading.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        heading.setForeground(new Color(40, 90, 160));
        heading.setBounds(290, 20, 400, 35);
        add(heading);

        // ---------- Left labels ----------
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color labelColor = new Color(40, 40, 55);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel lblID = new JLabel("ID Type:");
        lblID.setBounds(50, 80, 130, 25);
        lblID.setFont(labelFont);
        lblID.setForeground(labelColor);
        add(lblID);

        JLabel lblNumber = new JLabel("ID Number:");
        lblNumber.setBounds(50, 120, 130, 25);
        lblNumber.setFont(labelFont);
        lblNumber.setForeground(labelColor);
        add(lblNumber);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 160, 130, 25);
        lblName.setFont(labelFont);
        lblName.setForeground(labelColor);
        add(lblName);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(50, 200, 130, 25);
        lblGender.setFont(labelFont);
        lblGender.setForeground(labelColor);
        add(lblGender);

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(50, 240, 130, 25);
        lblCountry.setFont(labelFont);
        lblCountry.setForeground(labelColor);
        add(lblCountry);

        JLabel lblRoom = new JLabel("Allocated Room No:");
        lblRoom.setBounds(50, 280, 130, 25);
        lblRoom.setFont(labelFont);
        lblRoom.setForeground(labelColor);
        add(lblRoom);

        JLabel lblCheckIn = new JLabel("Check-in Time:");
        lblCheckIn.setBounds(50, 320, 130, 25);
        lblCheckIn.setFont(labelFont);
        lblCheckIn.setForeground(labelColor);
        add(lblCheckIn);

        JLabel lblDeposit = new JLabel("Deposit:");
        lblDeposit.setBounds(50, 360, 130, 25);
        lblDeposit.setFont(labelFont);
        lblDeposit.setForeground(labelColor);
        add(lblDeposit);

        // ---------- Input Fields ----------
        idTypeBox = new JComboBox<>(new String[] { "Aadhar Card", "Passport", "Voter ID", "Driving License", "Other" });
        idTypeBox.setBounds(200, 80, 150, 25);
        idTypeBox.setFont(fieldFont);
        add(idTypeBox);

        tfNumber = new JTextField();
        tfNumber.setBounds(200, 120, 150, 25);
        add(tfNumber);

        tfName = new JTextField();
        tfName.setBounds(200, 160, 150, 25);
        add(tfName);

        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        rbMale.setBackground(new Color(238, 244, 252));
        rbFemale.setBackground(new Color(238, 244, 252));
        rbMale.setBounds(200, 200, 60, 25);
        rbFemale.setBounds(270, 200, 80, 25);
        rbMale.setFont(fieldFont);
        rbFemale.setFont(fieldFont);
        genderGroup = new ButtonGroup(); // group to select one from both
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        add(rbMale);
        add(rbFemale);

        countryBox = new JComboBox<>(new String[] { "India", "USA", "UK", "Canada", "Australia", "Other" });
        countryBox.setBounds(200, 240, 150, 25);
        countryBox.setFont(fieldFont);
        add(countryBox);

        // Room number as JComboBox populated from DB (available rooms only)
        roomBox = new JComboBox<>();
        roomBox.setBounds(200, 280, 150, 25);
        roomBox.setFont(fieldFont);
        add(roomBox);

        // Fetch available rooms from database
        try {
            Conn conn = new Conn();
            String roomQuery = "SELECT room_number FROM room WHERE availability = 'Available'";
            PreparedStatement roomPs = conn.c.prepareStatement(roomQuery);
            ResultSet rs = roomPs.executeQuery();
            while (rs.next()) {
                roomBox.addItem(rs.getString("room_number"));
            }
            rs.close();
            roomPs.close();
            if (roomBox.getItemCount() == 0) {
                roomBox.addItem("No rooms available");
                roomBox.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage());
        }

        // Auto fill current date-time
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        tfCheckin = new JTextField(currentDate);
        tfCheckin.setEditable(false);
        tfCheckin.setBounds(200, 320, 150, 25);
        tfCheckin.setFont(fieldFont);
        add(tfCheckin);

        tfDeposit = new JTextField();
        tfDeposit.setBounds(200, 360, 150, 25);
        add(tfDeposit);

        // ---------- Buttons ----------
        submit = new JButton("Submit");
        submit.setBounds(100, 420, 120, 32);
        submit.setBackground(new Color(50, 110, 190));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setFocusPainted(false);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(240, 420, 120, 32);
        cancel.setBackground(new Color(150, 60, 60));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel.setFocusPainted(false);
        cancel.addActionListener(e -> setVisible(false));
        add(cancel);

        // ---------- Right Image ----------
        ImageIcon imgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/guest.png"));
        Image img = imgIcon.getImage().getScaledInstance(400, 350, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        imgLabel.setBounds(420, 90, 380, 320);
        add(imgLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String idType = (String) idTypeBox.getSelectedItem();
        String number = tfNumber.getText();
        String name = tfName.getText();
        String gender = rbMale.isSelected() ? "Male" : rbFemale.isSelected() ? "Female" : null;
        String country = (String) countryBox.getSelectedItem();
        String roomNo = (String) roomBox.getSelectedItem();
        String checkin = tfCheckin.getText();
        String deposit = tfDeposit.getText();

        if (number.isEmpty() || name.isEmpty() || gender == null
                || (roomNo == null || roomNo.trim().isEmpty() || roomNo.equals("No rooms available"))
                || deposit.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields! (Select a valid room if available)");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO customers (id_type, id_number, name, gender, country, room_no, checkin_time, deposit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.c.prepareStatement(query);
            ps.setString(1, idType);
            ps.setString(2, number);
            ps.setString(3, name);
            ps.setString(4, gender);
            ps.setString(5, country);
            ps.setString(6, roomNo);
            ps.setString(7, checkin);
            ps.setString(8, deposit);
            ps.executeUpdate();

            String query2 = "UPDATE room SET availability = 'Occupied' WHERE room_number = ?";
            PreparedStatement ps2 = conn.c.prepareStatement(query2);
            ps2.setString(1, roomNo);
            ps2.executeUpdate();
            ps.close();
            ps2.close();
            JOptionPane.showMessageDialog(null, "Customer Added Successfully!");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddCustomers();
    }

}