/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.AClass;
import Model.Lesson;
import Model.Session;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TRANG
 */
public class LessonDAO extends DBContext{
     public Vector<Lesson> displayAllLessons() {
        Vector<Lesson> vector = new Vector<>();
        String sql = "SELECT * FROM Lession";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int classId = rs.getInt("classId");
                String sessionId = rs.getString("sessionId");
                Date date = rs.getDate("date");
                String status = rs.getString("status");

                AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                Lesson lesson = new Lesson(id, aClass, session, date, status);
                vector.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get lessons based on a SQL query
    public Vector<Lesson> getLessons(String sql) {
        Vector<Lesson> vector = new Vector<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int classId = rs.getInt("classId");
                String sessionId = rs.getString("sessionId");
                Date date = rs.getDate("date");
                String status = rs.getString("status");

                AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                Lesson lesson = new Lesson(id, aClass, session, date, status);
                vector.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a lesson by ID
    public Lesson getLessonById(int id) {
        String sql = "SELECT * FROM Lession WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int classId = rs.getInt("classId");
                String sessionId = rs.getString("sessionId");
                Date date = rs.getDate("date");
                String status = rs.getString("status");

                AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                Lesson lesson = new Lesson(id, aClass, session, date, status);
                return lesson;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new lesson
    public int addLesson(Lesson lesson) {
        int n = 0;
        String sql = "INSERT INTO Lession (classId, sessionId, date, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, lesson.getAClass().getId());
            pre.setString(2, lesson.getSession().getId());
            pre.setDate(3, new java.sql.Date(lesson.getDate().getTime()));
            pre.setString(4, lesson.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a lesson
    public int updateLesson(Lesson lesson) {
        int n = 0;
        String sql = "UPDATE Lession SET classId = ?, sessionId = ?, date = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, lesson.getAClass().getId());
            pre.setString(2, lesson.getSession().getId());
            pre.setDate(3, new java.sql.Date(lesson.getDate().getTime()));
            pre.setString(4, lesson.getStatus());
            pre.setInt(5, lesson.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a lesson
    public int removeLesson(int lessonId) {
        int n = 0;
        String sql = "DELETE FROM Lession WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, lessonId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<Lesson> getLessonsByLearnerId(int learnerId) {
        Vector<Lesson> vector = new Vector<>();
        String sql = "SELECT L.* FROM Lession L JOIN Class C ON L.classId = C.id WHERE C.learnerId = ? ORDER BY L.date";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int classId = rs.getInt("classId");
                String sessionId = rs.getString("sessionId");
                Date date = rs.getDate("date");
                String status = rs.getString("status");

                AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                Lesson lesson = new Lesson(id, aClass, session, date, status);
                vector.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public static void main(String[] args) {
        LessonDAO lDAO = new LessonDAO();
        Vector<Lesson> lessons = lDAO.displayAllLessons();
        for (Lesson lesson : lessons) {
            System.out.println(lesson);
        }

        // Example: Fetch lessons for a specific learner
        int learnerId = 1; // Replace with the actual learner ID
        Vector<Lesson> learnerLessons = lDAO.getLessonsByLearnerId(learnerId);
        for (Lesson lesson : learnerLessons) {
            System.out.println(lesson.getAClass().getTutor().getName());
        }
    }
}
