package DAO;

import Model.Learner;
import Model.Session;
import Model.Tutor;
import Model.TutorSessionChangeRequest;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tung Duong
 */
public class TutorSessionChangeRequestDAO extends DBContext {

    // Thêm yêu cầu đổi buổi học
    public boolean addSessionChangeRequest(int lId, int tId, String from, String to, String reason, String status, String date) {
        String sql = "INSERT INTO TutorSessionChangeRequest (learnerId, tutorId, fromSessionId, toSessionId, reason, status, dateChange) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lId);
            statement.setInt(2, tId);
            statement.setString(3, from);
            statement.setString(4, to);
            statement.setString(5, reason);
            statement.setString(6, status);
            statement.setString(7, date);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TutorSessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Lấy danh sách yêu cầu đổi buổi học cho gia sư
    public List<TutorSessionChangeRequest> getRequestsByTutorId(int tutorId) {
        List<TutorSessionChangeRequest> requests = new ArrayList<>();
        SessionDAO sdao = new SessionDAO();
        LearnerDAO learnerDAO = new LearnerDAO();
        TutorDAO tdao = new TutorDAO();
        String sql = "SELECT * FROM TutorSessionChangeRequest WHERE tutorId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tutorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TutorSessionChangeRequest request = new TutorSessionChangeRequest();
                request.setId(rs.getInt("id"));
                request.setLearnerId(rs.getInt("learnerId"));
                request.setTutorId(rs.getInt("tutorId"));
                request.setFromSessionId(rs.getString("fromSessionId"));
                request.setToSessionId(rs.getString("toSessionId"));
                request.setReason(rs.getString("reason"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("createdAt"));
                Learner l = learnerDAO.getLearnerById(rs.getInt("learnerId"));
                request.setLearner(l);
                Tutor tutor = tdao.getTutorById(rs.getInt("tutorId"));
                request.setTutor(tutor);
                Session fromSession = sdao.getSessionById(rs.getString("fromSessionId"));
                Session toSession = sdao.getSessionById(rs.getString("toSessionId"));
                request.setToSession(toSession);
                request.setFromSession(fromSession);
                request.setDate(rs.getDate("dateChange"));
                requests.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorSessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    // Lấy danh sách yêu cầu đổi buổi học cho học viên
    public List<TutorSessionChangeRequest> getRequestsByLearnerId(int learnerId) {
        List<TutorSessionChangeRequest> requests = new ArrayList<>();
        SessionDAO sdao = new SessionDAO();
        LearnerDAO learnerDAO = new LearnerDAO();
        TutorDAO tdao = new TutorDAO();
        String sql = "SELECT * FROM TutorSessionChangeRequest WHERE learnerId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, learnerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                TutorSessionChangeRequest request = new TutorSessionChangeRequest();
                request.setId(rs.getInt("id"));
                request.setLearnerId(rs.getInt("learnerId"));
                request.setTutorId(rs.getInt("tutorId"));
                request.setFromSessionId(rs.getString("fromSessionId"));
                request.setToSessionId(rs.getString("toSessionId"));
                request.setReason(rs.getString("reason"));
                request.setStatus(rs.getString("status"));
                request.setCreatedAt(rs.getTimestamp("createdAt"));
                Learner l = learnerDAO.getLearnerById(rs.getInt("learnerId"));
                request.setLearner(l);
                Tutor tutor = tdao.getTutorById(rs.getInt("tutorId"));
                request.setTutor(tutor);
                Session fromSession = sdao.getSessionById(rs.getString("fromSessionId"));
                Session toSession = sdao.getSessionById(rs.getString("toSessionId"));
                request.setToSession(toSession);
                request.setFromSession(fromSession);
                request.setDate(rs.getDate("dateChange"));
                requests.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorSessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    // Cập nhật trạng thái yêu cầu đổi buổi học
    public boolean updateRequestStatus(int requestId, String status) {
        String sql = "UPDATE TutorSessionChangeRequest SET status = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, requestId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TutorSessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    // Xóa yêu cầu đổi buổi học theo ID

    public boolean deleteRequest(int requestId) {
        String sql = "DELETE FROM TutorSessionChangeRequest WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, requestId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TutorSessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
