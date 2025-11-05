import java.sql.*;

public class Conn {
    Connection c;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hotelmanagement",
                    "root",
                    "Priyanshu@12");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement return karne ke liye method
    public PreparedStatement prepare(String sql) throws SQLException {
        return c.prepareStatement(sql);
    }
}
