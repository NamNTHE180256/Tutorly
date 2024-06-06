/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Learner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Admin
 */
public class LearnerDAO extends DBContext {
    public ArrayList<Learner> getAllLearners() {
        ArrayList<Learner> learners = new ArrayList<>();
        String sql = "SELECT * FROM Learner";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                Learner learner = new Learner();
                learner.setId(rs.getInt("id"));
                learner.setName(rs.getString("name"));
                learner.setImage(rs.getString("image"));
                learners.add(learner);
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return learners;
    }
    
    public Learner getLearnerById(int id) {
        Learner learner = null;
        String sql = "SELECT * FROM Learner WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            ResultSet rs = sp.executeQuery();
            if (rs.next()) {
                learner = new Learner();
                learner.setId(rs.getInt("id"));
                learner.setName(rs.getString("name"));
                learner.setImage(rs.getString("image"));
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return learner;
    }

    // Method to insert a new learner into the database
    public boolean insertLearner(Learner learner) {
        String sql = "INSERT INTO Learner (name, image) VALUES (?, ?)";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setString(1, learner.getName());
            sp.setString(2, learner.getImage());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing learner in the database
    public boolean updateLearner(Learner learner) {
        String sql = "UPDATE Learner SET name = ?, image = ? WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setString(1, learner.getName());
            sp.setString(2, learner.getImage());
            sp.setInt(3, learner.getId());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a learner from the database
    public boolean deleteLearner(int id) {
        String sql = "DELETE FROM Learner WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
