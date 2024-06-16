/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Assignment;
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
public class AssignmentDAO extends DBContext{
    public Vector<Assignment> displayAllAssignments() {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT * FROM Assignment";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a list of assignments based on a SQL query
    public Vector<Assignment> getAssignments(String sql) {
        Vector<Assignment> vector = new Vector<>();
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get an assignment by ID
    public Assignment getAssignmentById(long id) {
        String sql = "SELECT * FROM Assignment WHERE id =" + id;
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                return assignment;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new assignment
//    public int addAssignment(Assignment assignment) {
//        int n = 0;
//        String sql = "INSERT INTO Assignment (lessionId, fileName, filePath, createdAt, status) VALUES (?, ?, ?, ?, ?)";
//        try {
//            PreparedStatement pre = connection.prepareStatement(sql);
//            pre.setInt(1, assignment.getLesson().getId());
//            pre.setString(2, assignment.getFileName());
//            pre.setString(3, assignment.getFilePath());
//            pre.setDate(4, new java.sql.Date(assignment.getCreatedAt().getTime()));
//            pre.setString(5, assignment.getStatus());
//            n = pre.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return n;
//    }
//
//    // Method to update an assignment
//    public int updateAssignment(Assignment assignment) {
//        int n = 0;
//        String sql = "UPDATE Assignment SET lessionId = ?, fileName = ?, filePath = ?, createdAt = ?, status = ? WHERE id = ?";
//        try {
//            PreparedStatement pre = connection.prepareStatement(sql);
//            pre.setInt(1, assignment.getLesson().getId());
//            pre.setString(2, assignment.getFileName());
//            pre.setString(3, assignment.getFilePath());
//            pre.setDate(4, new java.sql.Date(assignment.getCreatedAt().getTime()));
//            pre.setString(5, assignment.getStatus());
//            pre.setLong(6, assignment.getId());
//            n = pre.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return n;
//    }

    // Method to delete an assignment
    public int removeAssignment(long assignmentId) {
        int n = 0;
        String sql = "DELETE FROM Assignment WHERE id = " + assignmentId;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
     public Vector<Assignment> getAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lession l ON a.lessionId = l.id WHERE l.classId = ?";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
     
     public Vector<Assignment> getDoneAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lession l ON a.lessionId = l.id WHERE l.classId = ? and a.[status] like 'Done'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
     
     public Vector<Assignment> getToDoAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lession l ON a.lessionId = l.id WHERE l.classId = ? and a.[status] like 'Todo'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessionId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessionId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
     
    public Vector<Assignment> getAssignmentsByLearnerIdAndStatusDone(int learnerId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a " +
                     "JOIN Lession l ON a.lessionId = l.id " +
                     "JOIN Class c ON l.classId = c.id " +
                     "WHERE c.learnerId = ? AND a.[status] = 'Done'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessonId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get assignments by learner ID with status "Todo"
    public Vector<Assignment> getAssignmentsByLearnerIdAndStatusTodo(int learnerId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a " +
                     "JOIN Lession l ON a.lessionId = l.id " +
                     "JOIN Class c ON l.classId = c.id " +
                     "WHERE c.learnerId = ? AND a.[status] = 'Todo'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessonId), fileName, filePath, createdAt, status);
                vector.add(assignment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    
    public static void main(String[] args) {
        AssignmentDAO assignmentDAO = new AssignmentDAO();
//        Vector<Assignment> assignments = assignmentDAO.displayAllAssignments();
//        for (Assignment assignment : assignments) {
//            System.out.println(assignment);
//        }

        // Example usage of getAssignmentsByClassId
        Vector<Assignment> classAssignments = assignmentDAO.getAssignmentsByLearnerIdAndStatusTodo(1);
        for (Assignment assignment : classAssignments) {
            System.out.println(assignment.toString());
        }
    }

    public Vector<Assignment> getTodoAssignmentsByLessonId(int lessonid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
