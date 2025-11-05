import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;
    JButton login, cancel;

    Login() {
        // Window setup
        setTitle("Employee Management System - Login");
        setLayout(null);
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background color and main panel
        getContentPane().setBackground(new Color(245, 248, 255));

        // Left panel (for form)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBounds(30, 30, 280, 250);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        add(formPanel);

        // Font setup
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI Semibold", Font.PLAIN, 14);

        // Username label and field
        JLabel user = new JLabel("Username");
        user.setFont(labelFont);
        user.setBounds(25, 30, 100, 25);
        formPanel.add(user);

        username = new JTextField();
        username.setFont(fieldFont);
        username.setBounds(25, 55, 220, 30);
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(username);

        // Password label and field
        JLabel pass = new JLabel("Password");
        pass.setFont(labelFont);
        pass.setBounds(25, 100, 100, 25);
        formPanel.add(pass);

        password = new JPasswordField();
        password.setFont(fieldFont);
        password.setBounds(25, 125, 220, 30);
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(password);

        // Buttons
        login = new JButton("Login");
        login.setFont(buttonFont);
        login.setBackground(new Color(50, 100, 200));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setBounds(25, 180, 100, 30);
        login.addActionListener(this);
        formPanel.add(login);

        cancel = new JButton("Cancel");
        cancel.setFont(buttonFont);
        cancel.setBackground(new Color(120, 120, 120));
        cancel.setForeground(Color.WHITE);
        cancel.setFocusPainted(false);
        cancel.setBounds(145, 180, 100, 30);
        cancel.addActionListener(this);
        formPanel.add(cancel);

        // Right-side image (brand or theme image)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(340, 30, 250, 250);
        add(image);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String user = username.getText();
            String pass = new String(password.getPassword());

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";
                PreparedStatement ps = c.prepare(query);

                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }

}
