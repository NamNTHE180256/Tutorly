package DAO;

import Model.AClass;
import Model.Lesson;
import Model.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonDAO extends DBContext {

    // Fetch lessons for a tutor
    public List<Lesson> getLessonForTutor(int tutorId, Integer classId) {
        List<Lesson> listLesson = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("""
                SELECT s.* FROM Lession s 
                JOIN Class c ON c.id = s.classId
                WHERE tutorId = ? AND date < GETDATE()""");

            params.add(tutorId);
            if (classId != null) {
                query.append(" AND s.classId = ?");
                params.add(classId);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, params);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    listLesson.add(mapResultSetToLesson(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLesson;
    }

    // Fetch lessons for a learner
    public List<Lesson> getLessonForLearner(int learnerId, Integer classId) {
        List<Lesson> listLesson = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("""
                SELECT s.* FROM Lession s 
                JOIN Class c ON c.id = s.classId
                WHERE learnerId = ? AND date < GETDATE()""");

            params.add(learnerId);
            if (classId != null) {
                query.append(" AND s.classId = ?");
                params.add(classId);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, params);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    listLesson.add(mapResultSetToLesson(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLesson;
    }

    // Map parameters for PreparedStatement
    private void mapParams(PreparedStatement ps, List<Object> params) throws SQLException {
        int index = 1;
        for (Object param : params) {
            if (param instanceof Date) {
                ps.setTimestamp(index++, new Timestamp(((Date) param).getTime()));
            } else if (param instanceof Integer) {
                ps.setInt(index++, (Integer) param);
            } else if (param instanceof Long) {
                ps.setLong(index++, (Long) param);
            } else if (param instanceof Double) {
                ps.setDouble(index++, (Double) param);
            } else if (param instanceof Float) {
                ps.setFloat(index++, (Float) param);
            } else {
                ps.setString(index++, (String) param);
            }
        }
    }

    // Map ResultSet to Lesson
    private Lesson mapResultSetToLesson(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int classId = rs.getInt("classId");
        String sessionId = rs.getString("sessionId");
        Date date = rs.getDate("date");
        String status = rs.getString("status");

        AClass aClass = new AClassDAO().getClassById(classId);
        Session session = new SessionDAO().getSessionById(sessionId);

        return new Lesson(id, aClass, session, date, status);
    }

    // Display all lessons
    public Vector<Lesson> displayAllLessons() {
        return getLessons("SELECT * FROM Lession");
    }

    // Get lessons based on a SQL query
    public Vector<Lesson> getLessons(String sql) {
        Vector<Lesson> lessons = new Vector<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lessons.add(mapResultSetToLesson(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    // Get lesson by ID
    public Lesson getLessonById(int id) {
        String sql = "SELECT * FROM Lession WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapResultSetToLesson(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Add a new lesson
    public int addLesson(Lesson lesson) {
        String sql = "INSERT INTO Lession (classId, sessionId, date, status) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lesson.getAClass().getId());
            statement.setString(2, lesson.getSession().getId());
            statement.setDate(3, new java.sql.Date(lesson.getDate().getTime()));
            statement.setString(4, lesson.getStatus());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // Update a lesson
    public int updateLesson(Lesson lesson) {
        String sql = "UPDATE Lession SET classId = ?, sessionId = ?, date = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lesson.getAClass().getId());
            statement.setString(2, lesson.getSession().getId());
            statement.setDate(3, new java.sql.Date(lesson.getDate().getTime()));
            statement.setString(4, lesson.getStatus());
            statement.setInt(5, lesson.getId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // Remove a lesson
    public int removeLesson(int lessonId) {
        String sql = "DELETE FROM Lession WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lessonId);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // Get lessons by learner ID
    public Vector<Lesson> getLessonsByLearnerId(int learnerId) {
        String sql = "  SELECT L.* \n"
                + "FROM Lession L \n"
                + "JOIN Class C ON L.classId = C.id \n"
                + "WHERE C.learnerId = ? AND C.status != 'finished' \n"
                + "ORDER BY L.date;";
        return getLessonsWithId(sql, learnerId);
    }

    // Get lessons by class ID
    public Vector<Lesson> getLessonsByClassId(int classId) {
        String sql = "SELECT * FROM Lession WHERE classId = ?";
        return getLessonsWithId(sql, classId);
    }

    // Fetch lessons with an ID parameter
    private Vector<Lesson> getLessonsWithId(String sql, int id) {
        Vector<Lesson> lessons = new Vector<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                lessons.add(mapResultSetToLesson(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lessons;
    }

    public static void main(String[] args) {
        LessonDAO lessonDAO = new LessonDAO();

        // Example: Fetch lessons by class ID
        Vector<Lesson> lessonsByClass = lessonDAO.getLessonsByClassId(1);
        for (Lesson lesson : lessonsByClass) {
            System.out.println(lesson);
        }

        // Example: Fetch lessons for a specific learner
        int learnerId = 1; // Replace with the actual learner ID
        Vector<Lesson> learnerLessons = lessonDAO.getLessonsByLearnerId(learnerId);
        for (Lesson lesson : learnerLessons) {
            System.out.println(lesson.getAClass().getTutor().getName());
        }
    }
}
