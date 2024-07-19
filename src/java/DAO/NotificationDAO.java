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
public class NotificationDAO extends DBContext {

    // Phương thức để thêm thông báo mới
    public boolean addNotification(int learnerId, String message, String type) {
        String sql = "INSERT INTO Notification (learnerId, message, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, learnerId);
            statement.setString(2, message);
            statement.setString(3, type);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Phương thức để lấy thông báo theo learnerId
    public List<Notification> getNotificationsByLearnerId(int learnerId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notification WHERE learnerId = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, learnerId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String message = rs.getString("message");
                String type = rs.getString("type");
                boolean isRead = rs.getBoolean("isRead");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                notifications.add(new Notification(id, learnerId, message, isRead, createdAt, type));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;
    }

    public boolean readNotification(int notificationId) {
        String sql = "UPDATE Notification SET isRead = ? WHERE id = ?";
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
        String sql = "SELECT COUNT(*) FROM Notification WHERE learnerId = ? AND isRead = 0";
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
