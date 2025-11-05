import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dashboard extends JFrame implements ActionListener {

    private MusicPlayer player;

    Dashboard() {
        setTitle("Hotel Management Dashboard");
        setBounds(0, 0, 1550, 1000);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/dash.png"));
        // directly scale using fixed size (same as frame)
        Image i2 = i1.getImage().getScaledInstance(1550, 1000, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(0, 0, 1550, 1000);
        add(image);

        // MENU BAR
        JMenuBar mb = new JMenuBar();
        mb.setBackground(new Color(55, 85, 125));
        mb.setPreferredSize(new Dimension(1550, 70));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 20);
        Font itemFont = new Font("Segoe UI", Font.PLAIN, 16);

        JMenu hotel = new JMenu("HOTEL MANAGEMENT");
        hotel.setForeground(Color.WHITE);
        hotel.setFont(menuFont);
        mb.add(Box.createHorizontalStrut(60));
        mb.add(hotel);

        JMenuItem reception = new JMenuItem("RECEPTION");
        reception.setFont(itemFont);
        reception.addActionListener(this);
        hotel.add(reception);

        mb.add(Box.createHorizontalStrut(120));

        JMenu admin = new JMenu("ADMIN");
        admin.setForeground(Color.WHITE);
        admin.setFont(menuFont);
        mb.add(admin);

        JMenuItem addEmployee = new JMenuItem("ADD EMPLOYEE");
        addEmployee.setFont(itemFont);
        addEmployee.addActionListener(this);
        admin.add(addEmployee);

        JMenuItem addrooms = new JMenuItem("ADD ROOMS");
        addrooms.setFont(itemFont);
        addrooms.addActionListener(this);
        admin.add(addrooms);

        JMenuItem addDrivers = new JMenuItem("ADD DRIVERS");
        addDrivers.setFont(itemFont);
        addDrivers.addActionListener(this);
        admin.add(addDrivers);

        // RIGHT SIDE MUSIC CONTROLS
        mb.add(Box.createHorizontalGlue());

        JButton playBtn = new JButton("▶ Play");
        JButton stopBtn = new JButton("⏸ Stop");

        styleButton(playBtn, new Color(0, 128, 255));
        styleButton(stopBtn, new Color(255, 80, 80));

        playBtn.addActionListener(e -> {
            if (player == null)
                player = new MusicPlayer();
            player.playMusic();
        });

        stopBtn.addActionListener(e -> {
            if (player != null)
                player.stopMusic();
        });

        mb.add(playBtn);
        mb.add(Box.createHorizontalStrut(10));
        mb.add(stopBtn);
        mb.add(Box.createHorizontalStrut(20));

        setJMenuBar(mb);

        // TITLE
        JLabel text = new JLabel("Hotel RadhaBallav Welcomes You");
        text.setBounds(200, 40, 1100, 70);
        text.setFont(new Font("Georgia", Font.BOLD, 52));
        text.setForeground(Color.WHITE);
        image.add(text);

        setVisible(true);
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("ADD EMPLOYEE")) {
            new AddEmployee();
        } else if (ae.getActionCommand().equals("ADD ROOMS")) {
            new AddRooms();
        } else if (ae.getActionCommand().equals("ADD DRIVERS")) {
            new AddDriver();
        } else if (ae.getActionCommand().equals("RECEPTION")) {
            new ReceptionDashboard();
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
