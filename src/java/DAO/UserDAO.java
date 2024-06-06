/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class UserDAO extends DBContext{
    public User getUserById(int id) {
        User user = null;
        String sql = "Select * from [User] where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        UserDAO udao = new UserDAO();
        User u = udao.getUserById(1);
        System.out.println(u.getCreatedAt());
    }
}
