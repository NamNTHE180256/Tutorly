/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Learner;
import Model.User;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TRANG
 */
public class LearnerDAO extends DBContext {
     public Vector<Learner> displayAllStudents() {
        Vector<Learner> vector = new Vector<>();
        String sql = "SELECT * FROM Learner";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");

                Learner student = new Learner(id, name, image);
                vector.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a student by ID
    public Learner getStudentById(int id) {
        String sql = "SELECT * FROM Learner WHERE id = " + id;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String image = rs.getString("image");

                Learner student = new Learner(id, name, image);
                return student;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new student
    public int addStudent(Learner student) {
        int n = 0;
        String sql = "INSERT INTO Learner (name, image) VALUES (?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, student.getName());
            pre.setString(2, student.getImage());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a student
    public int updateStudent(Learner student) {
        int n = 0;
        String sql = "UPDATE Learner SET name = ?, image = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, student.getName());
            pre.setString(2, student.getImage());
            pre.setInt(3, student.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public int updateStudentName(int studentId, String newName) {
    int n = 0;
    String sql = "UPDATE Learner SET name = ? WHERE id = ?";
    try {
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, newName);
        pre.setInt(2, studentId);
        n = pre.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return n;
}


    // Method to delete a student
    public int removeStudent(int studentId) {
        int n = 0;
        String sql = "DELETE FROM Learner WHERE id = " + studentId;
        try {
            Statement state = connection.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public static void main(String[] args) {
        LearnerDAO l = new LearnerDAO();
//        Learner le = l.getStudentById(1);
//        System.out.println(le.toString());
//        System.out.println(le.getUserInfo().toString());
        System.out.println(l.displayAllStudents().toString());
    }
}
