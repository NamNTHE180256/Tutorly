/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Session;
import Model.Tutor;
import Model.TutorAvailability;
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
public class TutorAvailabilityDAO extends DBContext {

    public TutorAvailabilityDAO() {
        // Initialize your database connection here
        // For example:
        // this.connection = DriverManager.getConnection("your_database_url", "username", "password");
    }

    // Method to display all tutor availabilities
    public Vector<TutorAvailability> displayAllTutorAvailabilities() {
        Vector<TutorAvailability> vector = new Vector<>();
        String sql = "SELECT * FROM TutorAvailability";
        TutorDAO tutorDAO = new TutorDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int tutorId = rs.getInt("tutorId");
                String sessionId = rs.getString("sessionId");
                String status = rs.getString("status");

                Tutor tutor = tutorDAO.getTutorById(tutorId);
                Session session = sessionDAO.getSessionById(sessionId);
                TutorAvailability tutorAvailability = new TutorAvailability(id, tutor, session, status);
                vector.add(tutorAvailability);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a list of tutor availabilities based on a SQL query
    public Vector<TutorAvailability> getTutorAvailabilities(String sql) {
        Vector<TutorAvailability> vector = new Vector<>();
        TutorDAO tutorDAO = new TutorDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int tutorId = rs.getInt("tutorId");
                String sessionId = rs.getString("sessionId");
                String status = rs.getString("status");

                Tutor tutor = tutorDAO.getTutorById(tutorId);
                Session session = sessionDAO.getSessionById(sessionId);
                TutorAvailability tutorAvailability = new TutorAvailability(id, tutor, session, status);
                vector.add(tutorAvailability);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a tutor availability by ID
    public TutorAvailability getTutorAvailabilityById(int id) {
        String sql = "SELECT * FROM TutorAvailability WHERE id =" + id;
        TutorDAO tutorDAO = new TutorDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int tutorId = rs.getInt("tutorId");
                String sessionId = rs.getString("sessionId");
                String status = rs.getString("status");

                Tutor tutor = tutorDAO.getTutorById(tutorId);
                Session session = sessionDAO.getSessionById(sessionId);
                TutorAvailability tutorAvailability = new TutorAvailability(id, tutor, session, status);
                return tutorAvailability;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new tutor availability
    public int addTutorAvailability(TutorAvailability tutorAvailability) {
        int n = 0;
        String sql = "INSERT INTO TutorAvailability (tutorId, sessionId, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, tutorAvailability.getTutor().getId());
            pre.setString(2, tutorAvailability.getSession().getId());
            pre.setString(3, tutorAvailability.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a tutor availability
    public int updateTutorAvailability(TutorAvailability tutorAvailability) {
        int n = 0;
        String sql = "UPDATE TutorAvailability SET tutorId = ?, sessionId = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, tutorAvailability.getTutor().getId());
            pre.setString(2, tutorAvailability.getSession().getId());
            pre.setString(3, tutorAvailability.getStatus());
            pre.setInt(4, tutorAvailability.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a tutor availability
    public int removeTutorAvailability(int tutorAvailabilityId) {
        int n = 0;
        String sql = "DELETE FROM TutorAvailability WHERE id = " + tutorAvailabilityId;
        try {
            Statement state = connection.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TutorAvailabilityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<TutorAvailability> getTutorAvailabilityByTutorId(int tutorId) {
        Vector<TutorAvailability> tutorAvailabilities = new Vector<>();
        String sql = "SELECT \n"
            + " COALESCE(ta.tutorId, ?) AS tutorId, \n"
            + " s.id AS sessionId, \n"
            + " CASE \n"
            + " WHEN EXISTS ("
            + "    SELECT 1 FROM [Lesson] l "
            + "    JOIN [Class] c ON l.classId = c.id "
            + "    WHERE l.sessionId = s.id AND c.tutorId = ?"
            + " ) THEN 'Unavailable' \n"
            + " ELSE COALESCE(ta.status, 'Unavailable') \n"
            + " END AS status\n"
            + "FROM [Session] s\n"
            + "LEFT JOIN TutorAvailability ta ON s.id = ta.sessionId AND ta.tutorId = ?\n"
            + "ORDER BY \n"
            + " CASE \n"
            + " WHEN s.id LIKE '%1' THEN 1\n"
            + " WHEN s.id LIKE '%2' THEN 2\n"
            + " WHEN s.id LIKE '%3' THEN 3\n"
            + " WHEN s.id LIKE '%4' THEN 4\n"
            + " WHEN s.id LIKE '%5' THEN 5\n"
            + " END, \n"
            + " CASE \n"
            + " WHEN s.id LIKE 'M%' THEN 1\n"
            + " WHEN s.id LIKE 'T%' THEN 2\n"
            + " WHEN s.id LIKE 'W%' THEN 3\n"
            + " WHEN s.id LIKE 'R%' THEN 4\n"
            + " WHEN s.id LIKE 'F%' THEN 5\n"
            + " WHEN s.id LIKE 'SA%' THEN 6\n"
            + " WHEN s.id LIKE 'SU%' THEN 7\n"
            + " END,\n"
            + " s.startTime;";

        TutorDAO tutorDAO = new TutorDAO();
        SessionDAO sessionDAO = new SessionDAO();

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, tutorId);
            state.setInt(2, tutorId);
            state.setInt(3, tutorId);

            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("tutorId");
                String sessionId = rs.getString("sessionId");
                String status = rs.getString("status");
                Tutor tutor = tutorDAO.getTutorById(tutorId);
                Session session = sessionDAO.getSessionById(sessionId);
                TutorAvailability tutorAvailability = new TutorAvailability(id, tutor, session, status);
                tutorAvailabilities.add(tutorAvailability);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tutorAvailabilities;
    }
    
    public Vector<TutorAvailability> getAvailableSessions() {
    Vector<TutorAvailability> availableSessions = new Vector<>();

    String sql = "SELECT ta.tutorId, s.id AS sessionId, 'Available' AS availabilityStatus " +
                 "FROM TutorAvailability ta " +
                 "JOIN [Session] s ON ta.sessionId = s.id " +
                 "WHERE NOT EXISTS ( " +
                 "    SELECT 1 " +
                 "    FROM Lesson l " +
                 "    JOIN Class c ON l.classId = c.Id " +
                 "    WHERE l.sessionId = ta.sessionId " +
                 "      AND c.tutorId = ta.tutorId " +
                 ") " +
                 "GROUP BY ta.tutorId, s.id";

    try (
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Cache tutors and sessions to avoid repeated database calls
        Map<Integer, Tutor> tutorCache = new HashMap<>();
        Map<String, Session> sessionCache = new HashMap<>();
        
        SessionDAO sDAO = new SessionDAO();
        TutorDAO tDAO = new TutorDAO();

        while (resultSet.next()) {
            int tutorId = resultSet.getInt("tutorId");
            String sessionId = resultSet.getString("sessionId");
            String availabilityStatus = resultSet.getString("availabilityStatus");

            Tutor tutor = tutorCache.computeIfAbsent(tutorId, tDAO::getTutorById);
            Session session = sessionCache.computeIfAbsent(sessionId, sDAO::getSessionById);

            availableSessions.add(new TutorAvailability(tutor, session, availabilityStatus));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception properly in a real application
    }

    return availableSessions;
}

    
     public static void main(String[] args) {
        TutorAvailabilityDAO t = new TutorAvailabilityDAO();
        Vector<TutorAvailability> v = t.getAvailableSessions();
        for(TutorAvailability d : v){
            System.out.println(d);
        }
    }

}
