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

          
            String password = "17022004";

            String url = "jdbc:sqlserver://localhost:1433;databaseName=Tutorly";
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


