  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Manager;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ManagerDAO extends DBContext {
    public ArrayList<Manager> getAllManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM Manager";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getInt("id"));
                manager.setName(rs.getString("name"));
                manager.setApprovedTutor(rs.getInt("approvedTutor"));
                manager.setRejectedTutor(rs.getInt("rejectedTutor"));
                manager.setBlockedTutor(rs.getInt("blockedTutor"));
                manager.setStatus(rs.getString("status"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }
    
    public ArrayList<Manager> searchManagers(String query) {
        ArrayList<Manager> managers = new ArrayList<>();
        String sql = "SELECT * FROM Manager WHERE name LIKE ? OR id LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getInt("id"));
                manager.setName(rs.getString("name"));
                manager.setApprovedTutor(rs.getInt("approvedTutor"));
                manager.setRejectedTutor(rs.getInt("rejectedTutor"));
                manager.setBlockedTutor(rs.getInt("blockedTutor"));
                manager.setStatus(rs.getString("status"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }
    
    public Manager getManagerById(int id) {
        Manager manager = null;
        String sql = "SELECT * FROM Manager WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                manager = new Manager();
                manager.setId(rs.getInt("id"));
                manager.setName(rs.getString("name"));
                manager.setApprovedTutor(rs.getInt("approvedTutor"));
                manager.setRejectedTutor(rs.getInt("rejectedTutor"));
                manager.setBlockedTutor(rs.getInt("blockedTutor"));
                manager.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    public boolean addManager(Manager manager) {
        String sql = "INSERT INTO Manager (id, name, approvedTutor, rejectedTutor, blockedTutor, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, manager.getId());
            ps.setString(2, manager.getName());
            ps.setInt(3, manager.getApprovedTutor());
            ps.setInt(4, manager.getRejectedTutor());
            ps.setInt(5, manager.getBlockedTutor());
            ps.setString(6, manager.getStatus());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateManager(Manager manager) {
        String sql = "UPDATE Manager SET name = ?, approvedTutor = ?, rejectedTutor = ?, blockedTutor = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, manager.getName());
            ps.setInt(2, manager.getApprovedTutor());
            ps.setInt(3, manager.getRejectedTutor());
            ps.setInt(4, manager.getBlockedTutor());
            ps.setString(5, manager.getStatus());
            ps.setInt(6, manager.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteManager(int id) {
        String sql = "DELETE FROM Manager WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ManagerDAO d = new ManagerDAO();
        ArrayList<Manager> m = d.searchManagers("1");
        System.out.println(m);
    }
    
}
