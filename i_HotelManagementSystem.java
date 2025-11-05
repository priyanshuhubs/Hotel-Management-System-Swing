import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class i_HotelManagementSystem extends JFrame implements ActionListener {
    JLabel text;
    JButton enter;

    i_HotelManagementSystem() {
        getContentPane().setBackground(Color.BLACK);
        // Frame settings
        setBounds(100, 100, 1200, 600); // Slightly smaller, fits on most screens
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/main.png"));
        Image i2 = i1.getImage().getScaledInstance(1200, 600, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(0, 0, 1200, 600);
        add(image);

        // Translucent overlay at bottom
        JPanel overlay = new JPanel();
        overlay.setBackground(new Color(0, 0, 0, 120)); // slightly darker for clarity
        overlay.setBounds(0, 440, 1200, 130);
        image.add(overlay);
        overlay.setLayout(null);

        // Text label
        text = new JLabel("HOTEL MANAGEMENT SYSTEM");
        text.setBounds(80, 55, 1000, 60);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Segoe UI Semibold", Font.BOLD, 44));
        overlay.add(text);

        // "Enter System" button (aligned with text)
        enter = new JButton("Enter System ");
        enter.setBounds(950, 55, 200, 55);
        enter.setBackground(new Color(50, 120, 250));
        enter.setForeground(Color.WHITE);
        enter.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        enter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        enter.setFocusPainted(false);
        enter.setCursor(new Cursor(Cursor.HAND_CURSOR));

        enter.addActionListener(this);
        overlay.add(enter);

        // Start blinking animation in a new thread
        new Thread(() -> {
            try {
                while (true) {
                    text.setVisible(false);
                    Thread.sleep(500);
                    text.setVisible(true);
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        new i_HotelManagementSystem();
    }

}
