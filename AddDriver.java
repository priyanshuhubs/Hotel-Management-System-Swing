import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

import javax.swing.*;

public class AddDriver extends JFrame implements ActionListener {
    JButton addButton, cancelButton;
    JTextField tfname, tfage, tfcompany, tflocation;
    JComboBox<String> GenderCombox, AvailableCombox;

    AddDriver() {
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // Light professional blue-gray

        // Heading
        JLabel heading = new JLabel("Add Drivers");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setBounds(150, 25, 300, 40);
        add(heading);

        // Common font style
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        // Name
        JLabel name = new JLabel("Name");
        name.setFont(labelFont);
        name.setBounds(60, 90, 150, 25);
        add(name);

        tfname = new JTextField();
        tfname.setBounds(230, 90, 200, 30);
        tfname.setFont(fieldFont);
        tfname.setBackground(new Color(225, 235, 245));
        tfname.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tfname);

        // Age
        JLabel Age = new JLabel("Age");
        Age.setFont(labelFont);
        Age.setBounds(60, 130, 150, 25);
        add(Age);

        tfage = new JTextField();
        tfage.setBounds(230, 130, 200, 30);
        tfage.setFont(fieldFont);
        tfage.setBackground(new Color(225, 235, 245));
        tfage.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tfage);

        // Gender
        JLabel Gender = new JLabel("Gender");
        Gender.setFont(labelFont);
        Gender.setBounds(60, 170, 150, 25);
        add(Gender);

        String genderOption[] = { "Male", "Female" };
        GenderCombox = new JComboBox<>(genderOption);
        GenderCombox.setFont(fieldFont);
        GenderCombox.setBounds(230, 170, 200, 30);
        GenderCombox.setBackground(new Color(225, 235, 245));
        GenderCombox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        GenderCombox.setFocusable(false);
        add(GenderCombox);

        // Car Company
        JLabel CarCompany = new JLabel("Car Company");
        CarCompany.setFont(labelFont);
        CarCompany.setBounds(60, 210, 150, 25);
        add(CarCompany);

        tfcompany = new JTextField();
        tfcompany.setBounds(230, 210, 200, 30);
        tfcompany.setFont(fieldFont);
        tfcompany.setBackground(new Color(225, 235, 245));
        tfcompany.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tfcompany);

        // Availability
        JLabel Available = new JLabel("Availability");
        Available.setFont(labelFont);
        Available.setBounds(60, 250, 150, 25);
        add(Available);

        String availableOption[] = { "Available", "Busy" };
        AvailableCombox = new JComboBox<>(availableOption);
        AvailableCombox.setFont(fieldFont);
        AvailableCombox.setBounds(230, 250, 200, 30);
        AvailableCombox.setBackground(new Color(225, 235, 245));
        AvailableCombox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        AvailableCombox.setFocusable(false);
        add(AvailableCombox);

        // Location
        JLabel Location = new JLabel("Location");
        Location.setFont(labelFont);
        Location.setBounds(60, 290, 150, 25);
        add(Location);

        tflocation = new JTextField();
        tflocation.setBounds(230, 290, 200, 30);
        tflocation.setFont(fieldFont);
        tflocation.setBackground(new Color(225, 235, 245));
        tflocation.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220)));
        add(tflocation);

        // Buttons
        addButton = new JButton("ADD");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 102, 204));
        addButton.setFocusPainted(false);
        addButton.setBounds(110, 350, 120, 35);
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(260, 350, 120, 35);
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Image (resized cleanly)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/driver.jpg"));
        Image i2 = i1.getImage().getScaledInstance(330, 230, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(480, 90, 320, 230);
        image.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        add(image);

        // Frame Setup
        setBounds(200, 200, 860, 460);
        setTitle("Add Driver");
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = tfname.getText();
            String ageStr = tfage.getText();
            String gender = (String) GenderCombox.getSelectedItem();
            String company = tfcompany.getText();
            String availability = (String) AvailableCombox.getSelectedItem();
            String location = tflocation.getText();

            // Basic validation (can be expanded)
            if (name.isEmpty() || ageStr.isEmpty() || company.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate age is a positive integer
            int age;
            try {
                age = Integer.parseInt(ageStr);
                if (age < 18 || age > 120) {
                    JOptionPane.showMessageDialog(this, "Age must be a appropriate !",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO driver(name,age ,gender ,company ,available ,location) VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepare(query);
                ps.setString(1, name);
                ps.setString(2, ageStr);
                ps.setString(3, gender);
                ps.setString(4, company);
                ps.setString(5, availability);
                ps.setString(6, location);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Driver '" + name + "' added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                setVisible(true);

            } catch (Exception x) {
                x.printStackTrace();
            }

        } else if (e.getSource() == cancelButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddDriver();
    }
}