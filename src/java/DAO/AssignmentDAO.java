package DAO;

import Model.Assignment;
import java.sql.*;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignmentDAO extends DBContext {

    public Vector<Assignment> displayAllAssignments() {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT * FROM Assignment";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
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

    public Vector<Assignment> getAssignments(String sql) {
        Vector<Assignment> vector = new Vector<>();
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
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

    public Assignment getAssignmentById(long id) {
        String sql = "SELECT * FROM Assignment WHERE id = ?";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setLong(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int lessonId = rs.getInt("lessionId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Assignment assignment = new Assignment(id, lessonDAO.getLessonById(lessonId), fileName, filePath, createdAt, status);
                return assignment;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addAssignment(Assignment assignment) {
        int n = 0;
        String sql = "INSERT INTO Assignment (lessionId, fileName, filePath, createdAt, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, assignment.getLession().getId());
            pre.setString(2, assignment.getFileName());
            pre.setString(3, assignment.getFilePath());
            pre.setDate(4, new java.sql.Date(assignment.getCreatedAt().getTime()));
            pre.setString(5, assignment.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateAssignment(Assignment assignment) {
        int n = 0;
        String sql = "UPDATE Assignment SET lessionId = ?, fileName = ?, filePath = ?, createdAt = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, assignment.getLession().getId());
            pre.setString(2, assignment.getFileName());
            pre.setString(3, assignment.getFilePath());
            pre.setDate(4, new java.sql.Date(assignment.getCreatedAt().getTime()));
            pre.setString(5, assignment.getStatus());
            pre.setLong(6, assignment.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int removeAssignment(long assignmentId) {
        int n = 0;
        String sql = "DELETE FROM Assignment WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setLong(1, assignmentId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<Assignment> getAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lesson l ON a.lessionId = l.id WHERE l.classId = ?";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
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

    public Vector<Assignment> getDoneAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lesson l ON a.lessionId = l.id WHERE l.classId = ? and a.status LIKE 'Done'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
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

    public Vector<Assignment> getToDoAssignmentsByClassId(int classId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a JOIN Lesson l ON a.lessonId = l.id WHERE l.classId = ? and a.status LIKE 'Todo'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
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

    public Vector<Assignment> getAssignmentsByLearnerIdAndStatusDone(int learnerId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a "
                + "JOIN Lesson l ON a.lessionId = l.id "
                + "JOIN Class c ON l.classId = c.id "
                + "WHERE c.learnerId = ? AND a.status = 'Done'";
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

    public Vector<Assignment> getAssignmentsByLearnerIdAndStatusTodo(int learnerId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT a.* FROM Assignment a "
                + "JOIN Lesson l ON a.lessionId = l.id "
                + "JOIN Class c ON l.classId = c.id "
                + "WHERE c.learnerId = ? AND a.status = 'Todo'";
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

    public Vector<Assignment> getTodoAssignmentsByLessonId(int lessonId) {
        Vector<Assignment> vector = new Vector<>();
        String sql = "SELECT * FROM Assignment WHERE lessionId = ? AND status = 'todo'";
        LessonDAO lessonDAO = new LessonDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, lessonId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
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
        Vector<Assignment> assignments = assignmentDAO.getTodoAssignmentsByLessonId(6);
        for (Assignment assignment : assignments) {
            System.out.println(assignment.toString());
        }
    }
}
