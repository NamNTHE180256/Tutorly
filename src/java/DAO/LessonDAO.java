package DAO;

import Model.AClass;
import Model.Learner;
import Model.Lesson;
import Model.Session;
import Model.Subject;
import Model.Tutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonDAO extends DBContext {

    SessionDAO sdao = new SessionDAO();
    ClassDAO cdao = new ClassDAO();

    public Lesson getLessonById(int LessonId, int classId) {
        Lesson lesson = null;
        String query = "SELECT Lesson.* FROM Lesson JOIN Class ON Lesson.classId = Class.id WHERE Lesson.id = ? AND Class.id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);

            st.setInt(1, LessonId);
            st.setInt(2, classId);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                lesson = new Lesson();
                int id = rs.getInt("id");
                System.out.println(id);
                AClass aclass = cdao.getClassById(rs.getInt("classId"));
                System.out.println(aclass);
                Session session = sdao.getSessionById(rs.getString("sessionId"));
                System.out.println(session);
                Date date = rs.getDate("date");
                System.out.println(date);
                String status = rs.getString("status");
                System.out.println(status);

                lesson = new Lesson(id, aclass, session, date, status);

                // Set other fields of Lesson object here as per your database schema
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lesson;
    }

    // Fetch lessons for a tutor
    public List<Lesson> getLessonForTutor(int tutorId, Integer classId) {
        List<Lesson> listLesson = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        try {
            StringBuilder query = new StringBuilder();
            query.append("""
                SELECT s.* FROM Lesson s 
                JOIN Class c ON c.id = s.classId
                WHERE tutorId = ? """);

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
                SELECT s.* FROM Lesson s 
                JOIN Class c ON c.id = s.classId
                WHERE learnerId = ? """);

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
        return getLessons("SELECT * FROM Lesson");
    }

    // Get lessons based on a SQL query
    public Vector<Lesson> getLessons(String sql) {
        Vector<Lesson> vector = new Vector<>();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                vector.add(mapResultSetToLesson(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a lesson by ID
    public Lesson getLessonById(int id) {
        String sql = "SELECT * FROM Lesson WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                return mapResultSetToLesson(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to get lessons by tutor ID and date range
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
                    Lesson l
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

    // Method to add a new lesson
    public int addLesson(Lesson lesson) {
        int n = 0;
        String sql = "INSERT INTO Lesson (classId, sessionId, date, status) VALUES (?, ?, ?, ?)";
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
        String sql = "UPDATE Lesson SET classId = ?, sessionId = ?, date = ?, status = ? WHERE id = ?";
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
        String sql = "DELETE FROM Lesson WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, lessonId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Get lessons by learner ID
    public Vector<Lesson> getLessonsByLearnerId(int learnerId) {
        String sql = "  SELECT L.* \n"
                + "FROM Lesson L \n"
                + "JOIN Class C ON L.classId = C.id \n"
                + "WHERE C.learnerId = ? AND C.status != 'finished' \n"
                + "ORDER BY L.date;";
        return getLessonsWithId(sql, learnerId);
    }

    public Vector<Lesson> getLessonsByTutorId(int tutorId) {

        String sql = "  SELECT L.* \n"
                + "FROM Lesson L \n"
                + "JOIN Class C ON L.classId = C.id \n"
                + "WHERE C.tutorId = ? AND C.status != 'finished' \n"
                + "ORDER BY L.date;";

        return getLessonsWithId(sql, tutorId);

    }

    // Get lessons by class ID
    public Vector<Lesson> getLessonsByClassId(int classId) {
        String sql = "SELECT * FROM Lesson WHERE classId = ?";
        return getLessonsWithId(sql, classId);
    }

    public int getFinishedLessonCount(int classId) {
        String sql = "SELECT COUNT(*) AS finishedCount FROM Lesson WHERE classId = ? AND status = 'Finished'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                return rs.getInt("finishedCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    // Method to get the total lesson count for a class
    public int getTotalLessonCount(int classId) {
        String sql = "SELECT COUNT(*) AS totalCount FROM Lesson WHERE classId = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalCount");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
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

//    public Vector<Lesson> getLessonsByTutorId(int learnerId) {
//        String sql = "  SELECT L.* \n"
//                + "FROM Lesson L \n"
//                + "JOIN Class C ON L.classId = C.id \n"
//                + "WHERE C.tutorId = ? AND C.status != 'finished' \n"
//                + "ORDER BY L.date;";
//        return getLessonsWithId(sql, learnerId);
//    }
    public static void main(String[] args) {
        LessonDAO lessonDAO = new LessonDAO();

        // Example: Fetch lessons by class ID
        Vector<Lesson> lessonsByClass = lessonDAO.getLessonsByClassId(3);
        for (Lesson lesson : lessonsByClass) {
            System.out.println(lesson);
        }

        // Example: Fetch lessons by class ID
//        Vector<Lesson> lessonsByClass = lessonDAO.getLessonsByClassId(1);
//        for (Lesson lesson : lessonsByClass) {
//            System.out.println(lesson);
//        }
//
//        // Example: Fetch lessons for a specific learner
//        int learnerId = 1; // Replace with the actual learner ID
//        Vector<Lesson> learnerLessons = lessonDAO.getLessonsByLearnerId(learnerId);
//        for (Lesson lesson : learnerLessons) {
//            System.out.println(lesson.getAClass().getTutor().getName());
        System.out.println("1:" + lessonDAO.getLessonsByTutorId(8));
    }

}
