package DAO;
import Model.AClass;
import Model.Learner;
import Model.Tutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassDAO extends DBContext {
    TutorDAO tdao = new TutorDAO();
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
                Tutor tutor = tdao.getTutorById(tutorId); // Assuming TutorDAO is implemented

                AClass aClass = new AClass(learner, tutor, totalSession, startDate, endDate, status);
                aClass.setId(id);
                return aClass;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AClassDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Vector<AClass> getClassesByTutorId(int tutorId) {
        Vector<AClass> classes = new Vector<>();
        LearnerDAO lDao = new LearnerDAO();
        TutorDAO tDao = new TutorDAO();
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT c.id, c.learnerId, c.totalSession, c.startDate "
                + "FROM Class c "
                + "WHERE c.tutorId = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tutorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AClass aClass = new AClass();
                aClass.setId(rs.getInt("id"));
                aClass.setLearner(lDao.getLearnerById(rs.getInt("learnerId")));
                aClass.setTutor(tDao.getTutorById(tutorId));
//                aClass.setSubject(sDao.getSubjectById(rs.getInt("subjectId")));
                aClass.setTotalSession(rs.getInt("totalSession"));
                aClass.setStartDate(rs.getDate("startDate"));
                
                classes.add(aClass);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classes;
    }
    
    public Vector<AClass> getClassesBySTudentId(int studentId) {
        Vector<AClass> classes = new Vector<>();
        LearnerDAO lDao = new LearnerDAO();
        TutorDAO tDao = new TutorDAO();
        SubjectDAO sDao = new SubjectDAO();
        String sql = """
                    SELECT c.id, c.learnerId, c.tutorId, c.totalSession, c.startDate  
                      FROM Class c    
                      WHERE c.learnerId = ?""";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AClass aClass = new AClass();
                aClass.setId(rs.getInt("id"));
                aClass.setLearner(lDao.getLearnerById(rs.getInt("learnerId")));
                aClass.setTutor(tDao.getTutorById(rs.getInt("tutorId")));
//                aClass.setSubject(sDao.getSubjectById(rs.getInt("subjectId")));
                aClass.setTotalSession(rs.getInt("totalSession"));
                aClass.setStartDate(rs.getDate("startDate"));
                
                classes.add(aClass);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return classes;
    }
    
    public int getFinishedSessions(int classId) {
        int finishedSessions = 0;
        String sql = "SELECT COUNT(*) AS finishedSessions FROM Lesson WHERE classId = ? AND status = 'Finished'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                finishedSessions = rs.getInt("finishedSessions");
            }
        } catch (SQLException e) {
            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return finishedSessions;
    }

    public static void main(String[] args) {
        ClassDAO cdao = new ClassDAO();
        System.out.println(cdao.getClassById(3));
    }
}

//    public Vector<AClass> getClassesByLearnerId(int learnerId) {
//        Vector<AClass> classes = new Vector<>();
//        String sql = "SELECT c.*, l.name as learnerName, t.name as tutorName, s.name as subjectName, se.startTime, se.endTime "
//                   + "FROM Class c "
//                   + "JOIN Learner l ON c.learnerId = l.id "
//                   + "JOIN Tutor t ON c.tutorId = t.id "
//                   + "JOIN Subject s ON c.subjectId = s.id "
//                   + "JOIN Lesson le ON c.id = le.classId "
//                   + "JOIN Session se ON le.sessionId = se.id "
//                   + "WHERE c.learnerId = ?";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, learnerId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                AClass aClass = new AClass();
//                aClass.setId(rs.getInt("id"));
//                Learner learner = new Learner();
//                learner.setName(rs.getString("learnerName"));
//                aClass.setLearner(learner);
//                Tutor tutor = new Tutor();
//                tutor.setName(rs.getString("tutorName"));
//                aClass.setTutor(tutor);
//                Subject subject = new Subject();
//                subject.setName(rs.getString("subjectName"));
//                aClass.setSubject(subject);
//                aClass.setTotalSession(rs.getInt("totalSession"));
//                aClass.setStartDate(rs.getDate("startDate"));
//                aClass.setEndDate(rs.getDate("endDate"));
//                aClass.setStatus(rs.getString("status"));
//                aClass.setStartTime(rs.getString("startTime"));
//                aClass.setEndTime(rs.getString("endTime"));
//                aClass.setProgressPercentage((int) ((getFinishedSessions(rs.getInt("id")) / (double) rs.getInt("totalSession")) * 100));
//                classes.add(aClass);
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return classes;
//    }
//    private int getFinishedSessions(int classId) {
//        int finishedSessions = 0;
//        String sql = "SELECT COUNT(*) AS finishedCount FROM Lesson WHERE classId = ? AND status = 'Finished'";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, classId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                finishedSessions = rs.getInt("finishedCount");
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(ClassDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return finishedSessions;
//    }
//    public static void main(String[] args) {
//        ClassDAO classDAO = new ClassDAO();
//        
//        // Kiểm tra hàm getClassesByLearnerId với learnerId = 1
//        int learnerId = 1;
//        System.out.println("Classes for Learner ID: " + learnerId);
//        Vector<AClass> learnerClasses = classDAO.getClassesByLearnerId(learnerId);
//        for (AClass aClass : learnerClasses) {
//            System.out.println("Class ID: " + aClass.getId());
//            System.out.println("Learner Name: " + aClass.getLearner().getName());
//            System.out.println("Tutor Name: " + aClass.getTutor().getName());
//            System.out.println("Subject Name: " + aClass.getSubject().getName());
//            System.out.println("Total Sessions: " + aClass.getTotalSession());
//            System.out.println("Start Date: " + aClass.getStartDate());
//            System.out.println("End Date: " + aClass.getEndDate());
//            System.out.println("Start Time: " + aClass.getStartTime());
//            System.out.println("End Time: " + aClass.getEndTime());
//            System.out.println("Status: " + aClass.getStatus());
//            System.out.println("Progress Percentage: " + aClass.getProgressPercentage() + "%");
//            System.out.println("----");
//        }
//
//        // Kiểm tra hàm getClassesByTutorId với tutorId = 7
//        int tutorId = 7;
//        System.out.println("Classes for Tutor ID: " + tutorId);
//        Vector<AClass> tutorClasses = classDAO.getClassesByTutorId(tutorId);
//        for (AClass aClass : tutorClasses) {
//            System.out.println("Class ID: " + aClass.getId());
//            System.out.println("Learner Name: " + aClass.getLearner().getName());
//            System.out.println("Tutor Name: " + aClass.getTutor().getName());
//            System.out.println("Subject Name: " + aClass.getSubject().getName());
//            System.out.println("Total Sessions: " + aClass.getTotalSession());
//            System.out.println("Start Date: " + aClass.getStartDate());
//            System.out.println("End Date: " + aClass.getEndDate());
//            System.out.println("Status: " + aClass.getStatus());
////            System.out.println("Progress Percentage: " + aClass.getProgressPercentage() + "%");
//            System.out.println("----");
//        }
//        for (Session s : tutorClasses) {
//            
//        }
//    }
//}
