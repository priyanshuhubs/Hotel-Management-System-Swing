import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;

public class AddEmployee extends JFrame implements ActionListener {

    JTextField tfname, tfage, tfsalary, tfphoneNo, tfeMail, tfaadhar;
    JRadioButton rbmale, rbfemale;
    JButton submit, cancel;
    JComboBox<String> cbjob;

    AddEmployee() {
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // Light sky tone

        JLabel heading = new JLabel("Add Employee Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBounds(250, 20, 300, 40);
        add(heading);

        JLabel lblname = new JLabel("Full Name");
        lblname.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblname.setBounds(80, 80, 120, 25);
        add(lblname);

        tfname = new JTextField();
        tfname.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfname.setBounds(210, 80, 180, 28);
        tfname.setBackground(new Color(224, 240, 255));
        add(tfname);

        JLabel lblage = new JLabel("Age");
        lblage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblage.setBounds(80, 120, 120, 25);
        add(lblage);

        tfage = new JTextField();
        tfage.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfage.setBounds(210, 120, 180, 28);
        tfage.setBackground(new Color(224, 240, 255));
        add(tfage);

        JLabel gender = new JLabel("Gender");
        gender.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gender.setBounds(80, 160, 120, 25);
        add(gender);

        rbmale = new JRadioButton("Male");
        rbmale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbmale.setBackground(new Color(240, 248, 255));
        rbmale.setBounds(210, 160, 80, 25);
        add(rbmale);

        rbfemale = new JRadioButton("Female");
        rbfemale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbfemale.setBackground(new Color(240, 248, 255));
        rbfemale.setBounds(300, 160, 90, 25);
        add(rbfemale);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbmale);
        bg.add(rbfemale);

        JLabel job = new JLabel("Job Title");
        job.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        job.setBounds(80, 200, 120, 25);
        add(job);

        String str[] = { "Front Desk Clerks", "Porters", "HouseKeepers", "Kitchen Staff",
                "Room Servicer", "Chefs", "Waiter/Waitress", "Manager", "Accountant" };
        cbjob = new JComboBox<>(str);
        cbjob.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbjob.setBounds(210, 200, 180, 28);
        cbjob.setBackground(new Color(224, 240, 255));
        add(cbjob);

        JLabel salary = new JLabel("Salary");
        salary.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        salary.setBounds(80, 240, 120, 25);
        add(salary);

        tfsalary = new JTextField();
        tfsalary.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfsalary.setBounds(210, 240, 180, 28);
        tfsalary.setBackground(new Color(224, 240, 255));
        add(tfsalary);

        JLabel phoneNo = new JLabel("Phone No");
        phoneNo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        phoneNo.setBounds(80, 280, 120, 25);
        add(phoneNo);

        tfphoneNo = new JTextField();
        tfphoneNo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfphoneNo.setBounds(210, 280, 180, 28);
        tfphoneNo.setBackground(new Color(224, 240, 255));
        add(tfphoneNo);

        JLabel eMail = new JLabel("Email");
        eMail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        eMail.setBounds(80, 320, 120, 25);
        add(eMail);

        tfeMail = new JTextField();
        tfeMail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfeMail.setBounds(210, 320, 180, 28);
        tfeMail.setBackground(new Color(224, 240, 255));
        add(tfeMail);

        JLabel aadhar = new JLabel("Aadhar No");
        aadhar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        aadhar.setBounds(80, 360, 120, 25);
        add(aadhar);

        tfaadhar = new JTextField();
        tfaadhar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tfaadhar.setBounds(210, 360, 180, 28);
        tfaadhar.setBackground(new Color(224, 240, 255));
        add(tfaadhar);

        submit = new JButton("Submit");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setBounds(120, 420, 120, 32);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setBounds(270, 420, 120, 32);
        cancel.addActionListener(e -> setVisible(false));
        add(cancel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/tenth.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 350, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(440, 90, 360, 300);
        add(image);

        setBounds(200, 120, 850, 530);
        // setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String name = tfname.getText();
        String age = tfage.getText();
        String salary = tfsalary.getText();
        String phone = tfphoneNo.getText();
        String email = tfeMail.getText();
        String aadhar = tfaadhar.getText();
        String gender = null;
        if (rbmale.isSelected())
            gender = "Male";
        else if (rbfemale.isSelected())
            gender = "Female";
        String job = (String) cbjob.getSelectedItem();

        if (name.isEmpty() || age.isEmpty() || salary.isEmpty() || phone.isEmpty()
                || email.isEmpty() || aadhar.isEmpty() || gender == null || job.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Missing information detected. Kindly check and fill all inputs!");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO employee (name, age, gender, job, salary, phone, email, aadhar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepare(query);
            ps.setString(1, name);
            ps.setString(2, age);
            ps.setString(3, gender);
            ps.setString(4, job);
            ps.setString(5, salary);
            ps.setString(6, phone);
            ps.setString(7, email);
            ps.setString(8, aadhar);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Employee added successfully!");
            setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
