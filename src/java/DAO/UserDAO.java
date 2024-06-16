/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import GoogleLoginConfig.GoogleAccount;
import Model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserDAO extends DBContext {

    public User getUserById(int id) {
        User user = null;
        String sql = "Select * from [User] where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User GetUserWithEmail(String email_login) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email_login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String createdAt = rs.getString("createdAt");
                User user = new User(email, password, role);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching user with email: " + e.getMessage());
        }
        return null;
    }

    public int ChangePassWord(String password, String email) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [password] = ?\n"
                + " WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            int x = pstmt.executeUpdate();
            return x;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating password for user with email: " + e.getMessage());
        }
        return 0;
    }

        public User Login(String email, String password) {
        String sql = "SELECT * FROM [User] WHERE email = ? AND password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, computeMD5Hash(password));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String emailDB = rs.getString("email");
                String passwordDB = rs.getString("password");
                String role = rs.getString("role");
                String createAt = rs.getString("createdAt");
                User user = new User(emailDB, passwordDB, role);
                user.setId(id);
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int register(User user) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO [dbo].[User] ([email], [password], [role], [createdAt]) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());
            st.setString(3, user.getRole());
            st.setString(4, now.format(formatter));
            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error during user registration: " + e.getMessage());
        }
        return 0;
    }

    public String computeMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int registerLearnerUsingGoogle(GoogleAccount acc) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO [dbo].[User] ([email], [password], [role], [createdAt]) VALUES (?, '', ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, acc.getEmail());
            st.setString(2, "learner");
            st.setString(3, now.format(formatter));
            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error during user registration: " + e.getMessage());
        }

        return 0;
    }

    public static void main(String[] args) {
        UserDAO uDao = new UserDAO();
        String password = "3";
        String passAfterMD5 = uDao.computeMD5Hash(password);
        System.out.println(passAfterMD5);
        
    }
}
