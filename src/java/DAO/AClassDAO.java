/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AClass;
import Model.Learner;
import Model.Tutor;
import java.util.Date;
import java.util.Vector;
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
public class AClassDAO extends DBContext {

    public Vector<AClass> displayAllClasses() {
        Vector<AClass> vector = new Vector<>();
        String sql = "SELECT * FROM Class";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int learnerId = rs.getInt("learnerId");
                int tutorId = rs.getInt("tutorId");
                int totalSession = rs.getInt("totalSession");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                String status = rs.getString("status");

                Learner learner = new LearnerDAO().getStudentById(learnerId); // Assuming LearnerDAO is implemented
                Tutor tutor = new TutorDAO().getTutorById(tutorId); // Assuming TutorDAO is implemented

                AClass aClass = new AClass(learner, tutor, totalSession, startDate, endDate, status);
                aClass.setId(id);
                vector.add(aClass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a class by ID
    public AClass getClassById(int id) {
        String sql = "SELECT * FROM Class WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int learnerId = rs.getInt("learnerId");
                int tutorId = rs.getInt("tutorId");
                int totalSession = rs.getInt("totalSession");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                String status = rs.getString("status");

                Learner learner = new LearnerDAO().getStudentById(learnerId); // Assuming LearnerDAO is implemented
                Tutor tutor = new TutorDAO().getTutorById(tutorId); // Assuming TutorDAO is implemented

                AClass aClass = new AClass(learner, tutor, totalSession, startDate, endDate, status);
                aClass.setId(id);
                return aClass;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int countClassByStatus(String status, int id) {
        int n = 0;
        String sql = "Select count(*) as count_class from class where status = ? and tutorId = ? ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, status);
            pre.setInt(2, id);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                n = rs.getInt("count_class");
                return n;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int countClassByStatusLearner(String status, int id) {
        int n = 0;
        String sql = "Select count(*) as count_class from class where status = ? and learnerId = ? ";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, status);
            pre.setInt(2, id);

            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                n = rs.getInt("count_class");
                return n;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    // Method to add a new class
    public int addClass(AClass aClass) {
        int n = 0;
        String sql = "INSERT INTO Class (learnerId, tutorId, totalSession, startDate, endDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, aClass.getLearner().getId());
            pre.setInt(2, aClass.getTutor().getId());
            pre.setInt(3, aClass.getTotalSession());
            pre.setDate(4, new java.sql.Date(aClass.getStartDate().getTime()));
            pre.setDate(5, new java.sql.Date(aClass.getEndDate().getTime()));
            pre.setString(6, aClass.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a class
    public int updateClass(AClass aClass) {
        int n = 0;
        String sql = "UPDATE Class SET learnerId = ?, tutorId = ?, totalSession = ?, startDate = ?, endDate = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, aClass.getLearner().getId());
            pre.setInt(2, aClass.getTutor().getId());
            pre.setInt(3, aClass.getTotalSession());
            pre.setDate(4, new java.sql.Date(aClass.getStartDate().getTime()));
            pre.setDate(5, new java.sql.Date(aClass.getEndDate().getTime()));
            pre.setString(6, aClass.getStatus());
            pre.setInt(7, aClass.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a class
    public int removeClass(int classId) {
        int n = 0;
        String sql = "DELETE FROM Class WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public static void main(String[] args) {
        AClassDAO aClassDAO = new AClassDAO();
        Vector<AClass> classes = aClassDAO.displayAllClasses();
        for (AClass aClass : classes) {
            System.out.println(aClass);
        }
    }
}
