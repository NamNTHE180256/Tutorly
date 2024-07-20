package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection connection;

    public DBContext() {
        try {
            String userName = "sa";
            String password = "123";
            String url = "jdbc:sqlserver://ADMINACER\\SQLEXPRESS:1433;databaseName=Tutorly";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            connection = null; // Ensure connection is null if an exception occurs
        }
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        if (db.connection != null) {
            System.out.println("Database connected: " + db.connection);
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
