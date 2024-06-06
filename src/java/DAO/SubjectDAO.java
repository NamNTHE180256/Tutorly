/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Subject;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TRANG
 */
public class SubjectDAO extends DBContext{
    public Subject getSubjectById(int id) {
        String sql = "SELECT * FROM Subject WHERE id =" + id;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                String name = rs.getString(2);
                Subject subject = new Subject(id, name);
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   public void displayAllSubjects() {
        String sql = "SELECT * FROM Subject";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Subject subject = new Subject(id, name);
                System.out.println(subject.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to get a list of subjects based on a SQL query
    public Vector<Subject> getSubjects(String sql) {
        Vector<Subject> vector = new Vector<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Subject subject = new Subject(id, name);
                vector.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a subject by ID
    public Subject getSubjectById2(int id) {
        String sql = "SELECT * FROM Subject WHERE id =" + id;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");

                Subject subject = new Subject(id, name);
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new subject
    public int addSubject(Subject subject) {
        int n = 0;
        String sql = "INSERT INTO Subject (name) VALUES (?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subject.getName());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a subject
    public int updateSubject(Subject subject) {
        int n = 0;
        String sql = "UPDATE Subject SET name = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subject.getName());
            pre.setInt(2, subject.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a subject
    public int removeSubject(int subjectId) {
        int n = 0;
        String sql = "DELETE FROM Subject WHERE id = " + subjectId;
        try {
            Statement state = connection.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    

}
