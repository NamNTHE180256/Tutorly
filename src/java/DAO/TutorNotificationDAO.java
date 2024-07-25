package DAO;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Notification;

/**
 *
 * @author Tung Duong
 */
public class TutorNotificationDAO extends DBContext {

    // Phương thức để thêm thông báo mới
    public boolean addNotification(int tutorId, String message, String type) {
        String sql = "INSERT INTO TutorNotification (tutorId, message, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tutorId);
            statement.setString(2, message);
            statement.setString(3, type);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(TutorNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Phương thức để lấy thông báo theo tutorId
    public List<Notification> getNotificationsByTutorId(int tutorId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM TutorNotification WHERE tutorId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tutorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                String type = rs.getString("type");
                boolean isRead = rs.getBoolean("isRead");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                notifications.add(new Notification(id, tutorId, message, isRead, createdAt, type));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorNotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;
    }

    public boolean readNotification(int notificationId) {
        String sql = "UPDATE TutorNotification SET isRead = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setInt(2, notificationId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int countUnreadNotifications(int learnerId) {
        String sql = "SELECT COUNT(*) FROM TutorNotification WHERE tutorId = ? AND isRead = 0";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, learnerId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
