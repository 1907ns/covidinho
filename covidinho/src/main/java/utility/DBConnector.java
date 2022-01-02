package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public DBConnector() throws SQLException {
    }

    public static Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/covidinho"; //MySQL URL followed by the database name
        String username = "admin"; //MySQL username
        String password = "password"; //MySQL password
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/covidinho", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }




}

