/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class DBContext {
    protected Connection connection;
    public DBContext() {

        try {
            String userName = "sa";
            String password = "123";
            String url = "jdbc:sqlserver://ADMINACER\\SQLEXPRESS:1433;databaseName=Tutorly";
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
