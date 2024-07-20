/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Session;
import java.util.Vector;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionDAO extends DBContext {

    public SessionDAO() {
        // Initialize your database connection here
        // For example:
        // this.connection = DriverManager.getConnection("your_database_url", "username", "password");
    }

    // Method to display all sessions
    public Vector<Session> displayAllSessions() {
        Vector<Session> vector = new Vector<>();
        String sql = "SELECT * FROM Session";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Session session = new Session(id, startTime, endTime, dayOfWeek);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public List<Session> displayAllSessions2() {
        List<Session> vector = new ArrayList<>();
        String sql = "SELECT  distinct s.* FROM Session s  \n"
                + "                      JOIN Lesson l ON s.id = l.sessionId  \n"
                + "                    JOIN Class c ON l.classId = c.id  ";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Session session = new Session(id, startTime, endTime, dayOfWeek);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a list of sessions based on a SQL query
    public Vector<Session> getSessions(String sql) {
        Vector<Session> vector = new Vector<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Session session = new Session(id, startTime, endTime, dayOfWeek);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a session by ID
    public Session getSessionById(String id) {
        String sql = "SELECT * FROM Session WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Session session = new Session(id, startTime, endTime, dayOfWeek);
                return session;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Session getSessionByClassId(int classId) {
        String sql = "SELECT top 1 s.id, s.startTime, s.endTime, s.dayOfWeek "
                + "FROM Session s "
                + "JOIN Lesson l ON s.id = l.sessionId "
                + "WHERE l.classId = ? ";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                return new Session(id, startTime, endTime, dayOfWeek);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new session
    public int addSession(Session session) {
        int n = 0;
        String sql = "INSERT INTO Session (id, startTime, endTime, dayOfWeek) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, session.getId());
            pre.setString(2, session.getStartTime());
            pre.setString(3, session.getEndTime());
            pre.setString(4, session.getDayOfWeek());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a session
    public int updateSession(Session session) {
        int n = 0;
        String sql = "UPDATE Session SET startTime = ?, endTime = ?, dayOfWeek = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, session.getStartTime());
            pre.setString(2, session.getEndTime());
            pre.setString(3, session.getDayOfWeek());
            pre.setString(4, session.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a session
    public int removeSession(String sessionId) {
        int n = 0;
        String sql = "DELETE FROM Session WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, sessionId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public List<Session> getAvailableSessions(String currentSessionId) {
        List<Session> vector = new ArrayList<>();
        String sql = "SELECT * FROM Session WHERE id != ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, currentSessionId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Session session = new Session(id, startTime, endTime, dayOfWeek);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public List<Session> getSessionsByLearnerId(int learnerId) {
        List<Session> vector = new ArrayList<>();
        String sql = "SELECT  l.date as ldate, s.* FROM Session s JOIN Lesson l ON s.id = l.sessionId JOIN Class c ON l.classId = c.id "
                + "WHERE c.learnerId = ? and l.status = 'Scheduled'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");

                Date date = rs.getDate("ldate");
                Session session = new Session(id, startTime, endTime, dayOfWeek, date);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public List<Session> getSessionsByTutorId(String tutorId) {
        List<Session> vector = new ArrayList<>();
        String sql = "SELECT l.date as ldate,  s.* FROM Session s "
                + "JOIN Lesson l ON s.id = l.sessionId "
                + "JOIN Class c ON l.classId = c.id "
                + "WHERE c.tutorId = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, tutorId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String dayOfWeek = rs.getString("dayOfWeek");
                Date date = rs.getDate("ldate");
                Session session = new Session(id, startTime, endTime, dayOfWeek, date);
                vector.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public boolean updateLearnerSession(int learnerId, String fromSessionId, String toSessionId, String date) {
        String sql = "UPDATE Lesson SET sessionId = ? WHERE sessionId = ? AND classId IN (SELECT id FROM Class WHERE learnerId = ?) AND date = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, toSessionId);
            state.setString(2, fromSessionId);
            state.setInt(3, learnerId);
            state.setString(4, date);
            int rowsUpdated = state.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SessionDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void main(String[] args) {
        SessionDAO s = new SessionDAO();
        Date date = new Date(2024, 05, 13);
        s.updateLearnerSession(1, "M1", "W5", "2024-05-13");
    }
}
