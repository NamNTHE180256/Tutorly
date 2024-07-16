/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.SavedTutor;
import Model.Tutor;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
public class SavedTutorDAO extends DBContext {

    public boolean addSavedTutor(int learnerId, int tutorId) {
        String sql = "INSERT INTO SavedTutor (learnerId, tutorId) VALUES (?, ?)";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, learnerId);
            sp.setInt(2, tutorId);
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSavedTutor(int learnerId, int tutorId) {
        String sql = "DELETE FROM SavedTutor WHERE learnerId = ? AND tutorId = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, learnerId);
            sp.setInt(2, tutorId);
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vector<SavedTutor> displayAllSavedTutors() {
        Vector<SavedTutor> savedTutors = new Vector<>();
        String sql = "SELECT * FROM SavedTutor";
        LearnerDAO learnerDAO = new LearnerDAO();
        TutorDAO tutorDAO = new TutorDAO();
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                SavedTutor savedTutor = new SavedTutor();
                savedTutor.setId(rs.getInt("id"));
                savedTutor.setLearner(learnerDAO.getLearnerById(rs.getInt("learnerId")));
                savedTutor.setTutor(tutorDAO.getTutorById(rs.getInt("tutorId")));
                savedTutors.add(savedTutor);
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savedTutors;
    }

    public SavedTutor getSavedTutorById(int id) {
        SavedTutor savedTutor = null;
        LearnerDAO learnerDAO = new LearnerDAO();
        TutorDAO tutorDAO = new TutorDAO();
        String sql = "SELECT * FROM SavedTutor WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            ResultSet rs = sp.executeQuery();
            if (rs.next()) {
                savedTutor = new SavedTutor();
                savedTutor.setId(rs.getInt("id"));
                savedTutor.setLearner(learnerDAO.getLearnerById(rs.getInt("learnerId")));
                savedTutor.setTutor(tutorDAO.getTutorById(rs.getInt("tutorId")));
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savedTutor;
    }

    public boolean updateSavedTutor(SavedTutor savedTutor) {
        String sql = "UPDATE SavedTutor SET learnerId = ?, tutorId = ? WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, savedTutor.getLearner().getId());
            sp.setInt(2, savedTutor.getTutor().getId());
            sp.setInt(3, savedTutor.getId());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeSavedTutor(int id) {
        String sql = "DELETE FROM SavedTutor WHERE id = ?";
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

    public Vector<Tutor> getTutorsByLearnerId(int learnerId) {
        Vector<Tutor> tutors = new Vector<>();
        String sql = "SELECT t.* FROM Tutor t JOIN SavedTutor st ON t.id = st.tutorId WHERE st.learnerId = ?";
        TutorDAO tutorDAO = new TutorDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, learnerId);
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(subjectDAO.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutors.add(tutor);
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutors;
    }

    public static void main(String[] args) {
        SavedTutorDAO dao = new SavedTutorDAO();
        Vector<Tutor> savedTutors = dao.getTutorsByLearnerId(1);
        for (Tutor savedTutor : savedTutors) {
            System.out.println(savedTutor.toString());
        }
    }
}
