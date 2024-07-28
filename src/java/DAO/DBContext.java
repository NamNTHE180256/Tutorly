/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Admin
 */
public class DBContext {
    protected Connection connection;
    public DBContext() {

        try {
            String userName = "sa";

<<<<<<< HEAD
            String password = "123";
            String url = "jdbc:sqlserver://ADMINACER\\SQLEXPRESS:1433;databaseName=Tutorly";

=======
          
            String password = "17022004";

            String url = "jdbc:sqlserver://localhost:1433;databaseName=Tutorly";
>>>>>>> 951e3e6a1fc140a10343ec1eade9c80223a07c8a
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, userName, password);

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    
    
}


