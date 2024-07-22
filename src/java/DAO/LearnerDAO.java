/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Learner;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
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

    public int RegisterLearner(User user) {
        String sql = "INSERT INTO [dbo].[Learner] ([id], [name], [image]) VALUES (?, ?, ' ')";
        int newId = generateNewId();

        try (PreparedStatement sp = connection.prepareStatement(sql)) {
            sp.setInt(1, newId);
            sp.setString(2, user.getEmail());
            sp.executeUpdate();
            return newId;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error during learner registration: " + e.getMessage());
        }
        return 0;
    }

    public int generateNewId() {
        String query = "SELECT MAX(id) AS max_id FROM [dbo].[Learner]";
        try (PreparedStatement sp = connection.prepareStatement(query); ResultSet rs = sp.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error generating new ID: " + e.getMessage());
        }
        return 1; // Default to 1 if there are no records
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
    public int insertLearner(Learner learner) {
        String sql = "INSERT INTO [dbo].[Learner] ([id], [name], [image]) VALUES (?, ?, ?)";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, learner.getId());
            sp.setString(2, learner.getName());
            sp.setString(3, learner.getImage());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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

    public List<Learner> getLearnersByTutorId(int tutorId) {
        List<Learner> vector = new ArrayList<>();
        String sql = "SELECT l.* FROM Learner l JOIN Class c ON l.id = c.learnerId WHERE c.tutorId = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, tutorId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");

                Learner learner = new Learner(id, name, image);
                vector.add(learner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LearnerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public static void main(String[] args) {
        LearnerDAO l = new LearnerDAO();
        Learner learner = new Learner(200, "ducanh", "dsadada");
//        Learner le = l.getStudentById(1);
//        System.out.println(le.toString());
//        System.out.println(le.getUserInfo().toString());
        System.out.println(l.insertLearner(learner));
        System.out.println(l.getLearnerById(3));
    }
}
