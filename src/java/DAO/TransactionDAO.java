/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Transaction;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class TransactionDAO extends DBContext{
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transactions (userId, transactionDate, amount, payment_method, transaction_type, status, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, transaction.getUser().getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(transaction.getTransactionDate().getTime()));
            stmt.setInt(3, transaction.getAmount());
            stmt.setString(4, transaction.getPaymentMethod());
            stmt.setString(5, transaction.getTransactionType());
            stmt.setString(6, transaction.getStatus());
            stmt.setString(7, transaction.getDescription());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions";
        UserDAO udao = new UserDAO();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("id"),
                    udao.getUserById(rs.getInt("userId")),
                    rs.getTimestamp("transactionDate"),
                    rs.getInt("amount"),
                    rs.getString("payment_method"),
                    rs.getString("transaction_type"),
                    rs.getString("status"),
                    rs.getString("description")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
    
    public static void main(String[] args) {
        // Create a new Transaction object
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date transactionDate;
        try {
            transactionDate = dateFormat.parse("24072024072600"); // Example date
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Parsed Date: " + transactionDate);
        UserDAO udao = new UserDAO();
        User user = udao.getUserById(1);

        Transaction transaction = new Transaction(
                user, // userId (example user id)
                transactionDate, // transactionDate
                2000000, // amount (example amount)
                "VNPay", // paymentMethod
                "Payment", // transactionType
                "Completed", // status
                "Payment for tutoring service" // description
        );
        TransactionDAO tdao = new TransactionDAO();
        tdao.addTransaction(transaction);
        System.out.println(tdao.getAllTransactions());
}}
