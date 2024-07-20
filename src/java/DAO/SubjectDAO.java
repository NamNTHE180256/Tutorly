/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Subject;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SubjectDAO extends DBContext {

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

    public int addSubject(String subjectName) {
        int n = 0;
        String sql = "INSERT INTO Subject (name) VALUES (?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subjectName);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Map<String, Integer> getTutorsPerSubject() {
        Map<String, Integer> subjectTutorCounts = new HashMap<>();
        String sql = "SELECT [Subject].[name] AS subject_name, COUNT(Tutor.id) AS tutor_count \n"
                + "                     FROM [Subject] \n"
                + "                     LEFT JOIN Tutor ON [Subject].id = tutor.subjectId \n"
                + "                     GROUP BY [Subject].[name]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                int tutorCount = rs.getInt("tutor_count");
                subjectTutorCounts.put(subjectName, tutorCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectTutorCounts;
    }

    public Map<Subject, Integer> getNumberOfLearnersBySubject() {
        String sql = "SELECT s.id, s.name, COUNT(DISTINCT c.learnerId) AS NumberOfLearners "
                + "FROM Subject s "
                + "LEFT JOIN Tutor t ON s.id = t.subjectId "
                + "LEFT JOIN Class c ON t.id = c.tutorId "
                + "GROUP BY s.id, s.name";
        Map<Subject, Integer> result = new HashMap<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int numberOfLearners = rs.getInt("NumberOfLearners");
                Subject subject = new Subject(id, name);
                result.put(subject, numberOfLearners);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Method to get number of classes by subject
    public Map<Subject, Integer> getNumberOfClassesBySubject() {
        String sql = "SELECT s.id, s.name, COUNT(c.id) AS NumberOfClasses "
                + "FROM Subject s "
                + "LEFT JOIN Tutor t ON s.id = t.subjectId "
                + "LEFT JOIN Class c ON t.id = c.tutorId "
                + "GROUP BY s.id, s.name";
        Map<Subject, Integer> result = new HashMap<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int numberOfClasses = rs.getInt("NumberOfClasses");
                Subject subject = new Subject(id, name);
                result.put(subject, numberOfClasses);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Method to get number of tutors by subject
    public Map<Subject, Integer> getNumberOfTutorsBySubject() {
        String sql = "SELECT s.id, s.name, COUNT(t.id) AS NumberOfTutors "
                + "FROM Subject s "
                + "LEFT JOIN Tutor t ON s.id = t.subjectId "
                + "GROUP BY s.id, s.name";
        Map<Subject, Integer> result = new HashMap<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int numberOfTutors = rs.getInt("NumberOfTutors");
                Subject subject = new Subject(id, name);
                result.put(subject, numberOfTutors);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static void main(String[] args) {
        SubjectDAO d = new SubjectDAO();
        Map<String, Integer> subjectTutorCounts = d.getTutorsPerSubject();
        System.out.println(subjectTutorCounts);
    }

}
