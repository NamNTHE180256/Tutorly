/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AClass;
import Model.Learner;
import Model.Lesson;
import Model.Session;
import Model.Subject;
import Model.Tutor;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TRANG
 */
public class LessonDAO extends DBContext {

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

    public Vector<Lesson> getLessonsByTutorIdAndDateRange(int tutorId, String startDate, String endDate) {
        Vector<Lesson> lessons = new Vector<>();
        String sql = """
        SELECT 
                    l.id, 
                    l.classId, 
                    l.sessionId, 
                    l.date, 
                    l.status, 
                    subj.id as subjectId, 
                    c.learnerId, 
                    s.startTime, 
                    s.endTime, 
                    s.dayOfWeek,
                    subj.name AS subjectName, 
                    lrn.name AS learnerName,
                    lrn.image AS learnerImage,
                    t.name AS tutorName
                 FROM 
                    Lession l
                      JOIN 
                         Class c ON l.classId = c.id
                      JOIN 
                         Session s ON l.sessionId = s.id
                           
                      JOIN 
                         Learner lrn ON c.learnerId = lrn.id
                      JOIN 
                         Tutor t ON c.tutorId = t.id
                       JOIN 
                        Subject subj ON t.subjectId = subj.id
                WHERE  
            c.tutorId = ?   AND l.date BETWEEN ? AND ?
        """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tutorId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int classId = rs.getInt("classId");
                String sessionId = rs.getString("sessionId");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                String dayOfWeek = rs.getString("dayOfWeek");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                String subjectName = rs.getString("subjectName");
                String learnerName = rs.getString("learnerName");
                String learnerImage = rs.getString("learnerImage");
                String tutorName = rs.getString("tutorName");

                AClass aClass = new AClass();
                aClass.setId(classId);
                aClass.setSubject(new Subject(rs.getInt("subjectId"), subjectName));
                aClass.setLearner(new Learner(rs.getInt("learnerId"), learnerName, learnerImage));
                aClass.setTutor(new Tutor(tutorId, tutorName));

                Session session = new Session(sessionId, startTime, endTime, dayOfWeek);

                Lesson lesson = new Lesson(id, aClass, session, date, status);
                lessons.add(lesson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public List<Lesson> getLessonForTutor(int tID, Integer class_id) {
        List<Lesson> listLesson = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("""
                     Select s.* from Lession s JOIN Class c
                     ON c.id = s.classId
                     where  tutorId =? and date < GETDATE()""");

            list.add(tID);
            if (class_id != null) {
                query.append(" AND  s.classId = ? ");
                list.add(class_id);
            }
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int classId = rs.getInt("classId");
                    String sessionId = rs.getString("sessionId");
                    Date date = rs.getDate("date");
                    String status = rs.getString("status");

                    AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                    Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                    Lesson lesson = new Lesson(id, aClass, session, date, status);
                    listLesson.add(lesson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLesson;

    }

    public List<Lesson> getLessonForLearner(int lId, Integer class_id) {
        List<Lesson> listLesson = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        try {
            StringBuilder query = new StringBuilder();
            query.append("""
                     Select s.* from Lession s JOIN Class c
                     ON c.id = s.classId
                     where  learnerId = ? and date < GETDATE()""");

            list.add(lId);
            if (class_id != null) {
                query.append(" AND  s.classId = ? ");
                list.add(class_id);
            }
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int classId = rs.getInt("classId");
                    String sessionId = rs.getString("sessionId");
                    Date date = rs.getDate("date");
                    String status = rs.getString("status");

                    AClass aClass = new AClassDAO().getClassById(classId); // Assuming AClassDAO is implemented
                    Session session = new SessionDAO().getSessionById(sessionId); // Assuming SessionDAO is implemented

                    Lesson lesson = new Lesson(id, aClass, session, date, status);
                    listLesson.add(lesson);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLesson;

    }

     public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
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
