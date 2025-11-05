import javax.swing.SwingUtilities;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new i_HotelManagementSystem(); // Ya new ReceptionDashboard();
        });
    }
}
