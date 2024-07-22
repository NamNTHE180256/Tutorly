package DAO;

import Model.Learner;
import Model.Session;
import Model.SessionChangeRequest;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tung Duong
 */
public class SessionChangeRequestDAO extends DBContext {

    // Thêm yêu cầu đổi buổi học
    public boolean addSessionChangeRequest(int lId, int tId, String from, String to, String reason, String status, String date) {
        String sql = "INSERT INTO SessionChangeRequest (learnerId, tutorId, fromSessionId, toSessionId, reason, status, dateChange) VALUES (?,?, ?, ?, ?, ?, ?)";
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
            Logger.getLogger(SessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Lấy danh sách yêu cầu đổi buổi học cho gia sư
    public List<SessionChangeRequest> getRequestsByTutorId(int tutorId) {
        List<SessionChangeRequest> requests = new ArrayList<>();
        SessionDAO sdao = new SessionDAO();
        LearnerDAO learnerDAO = new LearnerDAO();
        String sql = "SELECT * FROM SessionChangeRequest WHERE tutorId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tutorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SessionChangeRequest request = new SessionChangeRequest();
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
                Session fromSession = sdao.getSessionById(rs.getString("fromSessionId"));
                Session toSession = sdao.getSessionById(rs.getString("toSessionId"));
                request.setToSession(toSession);
                request.setFromSession(fromSession);
                request.setDate(rs.getDate("dateChange"));
                requests.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    // Lấy danh sách yêu cầu đổi buổi học cho học viên
    public List<SessionChangeRequest> getRequestsByLearnerId(int learnerId) {
        List<SessionChangeRequest> requests = new ArrayList<>();
        SessionDAO sdao = new SessionDAO();
        LearnerDAO learnerDAO = new LearnerDAO();
        String sql = "SELECT * FROM SessionChangeRequest WHERE learnerId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, learnerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SessionChangeRequest request = new SessionChangeRequest();
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
                Session fromSession = sdao.getSessionById(rs.getString("fromSessionId"));
                Session toSession = sdao.getSessionById(rs.getString("toSessionId"));
                request.setToSession(toSession);
                request.setDate(rs.getDate("dateChange"));
                request.setFromSession(fromSession);
                requests.add(request);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    // Cập nhật trạng thái yêu cầu đổi buổi học
    public boolean updateRequestStatus(int requestId, String status) {
        String sql = "UPDATE SessionChangeRequest SET status = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setInt(2, requestId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SessionChangeRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
