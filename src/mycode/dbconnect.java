package mycode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {

    public static Connection connect() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cartaxi?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "vihandu2003";

            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}


