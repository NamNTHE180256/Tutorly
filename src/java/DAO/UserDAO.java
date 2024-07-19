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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public int addUser(String email, String password, String role) {
        String sql = "INSERT INTO [User] (email, [password], [role])\n"
                + "VALUES  (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void setEmailByUserId(int id, String newEmail) {
        String sql = "UPDATE [User] SET email = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newEmail);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getCount(String tableName) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS count FROM " + tableName;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
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
                Date createdAt = rs.getDate("createdAt");
                User user = new User(id, email, password, role, createdAt);
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
            pstmt.setString(1, computeMD5Hash(password));
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
                User user = new User();
                user.setId(id);
                user.setEmail(emailDB);
                user.setPassword(passwordDB);
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                user.setRole(role);
                
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
        UserDAO dao = new UserDAO();
        User u = dao.GetUserWithEmail("learner1@gmail.com");
        System.out.println(u);
    }
//    public static void main(String[] args) {
//<<<<<<< HEAD
//        UserDAO udao = new UserDAO();
//        User u = udao.getUserById(1);
//<<<<<<< HEAD
//        System.out.println(udao.Login("learner5@gmail.com", "1234"));
//        System.out.println(udao.computeMD5Hash("1234"));
//        
//=======
//        System.out.println(udao.Login("learner1@gmail.com", "1"));
//=======
//        UserDAO uDao = new UserDAO();
//        String password = "3";
//        String passAfterMD5 = uDao.computeMD5Hash(password);
//        System.out.println(passAfterMD5);
//        
//>>>>>>> bcf475e5a416004d96226e39b60d957e85a1a7bd
//>>>>>>> 6a4e6a403c1f8ed00a9c9b12aa381ac10eae0541
//    }
}
