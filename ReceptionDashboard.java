import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReceptionDashboard extends JFrame implements ActionListener {

    JButton[] buttons;
    String[] btnNames = {
            "New Customer Form", "Rooms Details", "Department", "All Employees",
            "Customer Info", "Manager Info", "Checkout", "Update Room Availbility",
            "Update Room Status", "Pickup Service", "Back", "Logout"
    };

    public ReceptionDashboard() {
        setTitle("Hotel Reception Dashboard");

        setBounds(150, 95, 900, 580);
        // setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 25)); // Slightly darker

        // Left menu panel with refined premium styling
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(15, 15, 22)); // Deeper charcoal
        menuPanel.setBounds(0, 0, 260, 580);
        menuPanel.setLayout(null);
        // Subtle border for premium separation
        menuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 50, 70)));
        add(menuPanel);

        JLabel title = new JLabel("HOTEL RECEPTION");
        title.setBounds(50, 25, 200, 35);
        title.setForeground(new Color(100, 200, 255)); // Soft blue accent
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        // Drop shadow effect
        JLabel titleShadow = new JLabel("HOTEL RECEPTION");
        titleShadow.setBounds(50, 29, 200, 35);
        titleShadow.setForeground(new Color(0, 0, 0, 100));
        titleShadow.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        menuPanel.add(titleShadow);
        menuPanel.add(title);

        // Buttons list - ActionListeners attached here for all buttons
        buttons = new JButton[btnNames.length];
        int y = 75; // Slightly lower start for better fit
        for (int i = 0; i < btnNames.length; i++) {
            buttons[i] = new JButton(btnNames[i]);
            buttons[i].setBounds(20, y, 220, 32); // Slightly wider to fit in panel (260 width)
            buttons[i].setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Smaller font to match
            buttons[i].setForeground(new Color(220, 220, 230)); // Softer white for readability
            buttons[i].setBackground(new Color(35, 35, 50)); // Muted gray for sophistication
            buttons[i].setFocusPainted(false);
            buttons[i].setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
            // Subtle rounded corners via custom border
            buttons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(6, 10, 6, 10),
                    BorderFactory.createLineBorder(new Color(60, 60, 80), 1, true)));
            buttons[i].addActionListener(this);

            buttons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    e.getComponent().setBackground(new Color(60, 90, 200)); // Refined blue hover
                    e.getComponent().setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    e.getComponent().setBackground(new Color(35, 35, 50));
                    e.getComponent().setForeground(new Color(220, 220, 230));
                }
            });
            menuPanel.add(buttons[i]);
            y += 38; // Tighter spacing to ensure all 12 fit (total ~75 + 12*38 = 531 < 580)
        }

        // Right-side image panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/reception.png"));
        Image img = i1.getImage().getScaledInstance(640, 580, Image.SCALE_SMOOTH);

        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(260, 0, 640, 580);
        add(imageLabel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New Customer Form":
                new AddCustomers();
                break;
            case "Rooms Details":
                new RoomBrowser();
                break;
            case "Department":
                new Department();
                break;
            case "All Employees":
                new EmployeeInfo();
                break;
            case "Customer Info":
                new CustomerInfo();
                break;
            case "Manager Info":
                new ManagerInfo();
                break;
            case "Checkout":
                new Checkout();
                break;
            case "Update Room Availbility":
                new UpdateRoomAvailability();
                break;
            case "Update Room Status":
                new UpdateRoomStatus();
                break;
            case "Pickup Service":
                new PickupService();
                break;
            case "Back":
                dispose(); // Sirf dashboard band
                break;
            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are You Sure ?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                }
                break;

            default:
                JOptionPane.showMessageDialog(this, "Button clicked: " + command, "Action",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ReceptionDashboard();
    }

}