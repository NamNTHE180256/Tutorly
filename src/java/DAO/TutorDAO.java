/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Tutor;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author TRANG
 */
public class TutorDAO extends DBContext{
    

    // Constructor to initialize the database connection
    public TutorDAO() {
        // Initialize your database connection here
        // For example:
        // this.conn = DriverManager.getConnection("your_database_url", "username", "password");
    }

    // Method to display all tutors
     public Vector<Tutor> displayAllTutors() {
        Vector<Tutor> vector = new Vector<>();
        String sql = "SELECT * FROM Tutor";
        SubjectDAO sDAO = new SubjectDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectId = rs.getInt("subjectId");
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String image = rs.getString("image");
                String bio = rs.getString("bio");
                String edu = rs.getString("edu");
                float price = rs.getFloat("price");
                String bank = rs.getString("bank");
                String status = rs.getString("status");

                Tutor tutor = new Tutor(id, sDAO.getSubjectById(subjectId), name, gender, image, bio, edu, price, bank, status);
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a list of tutors based on a SQL query
    public Vector<Tutor> getTutors(String sql) {
        Vector<Tutor> vector = new Vector<>();
        SubjectDAO sDAO = new SubjectDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectId = rs.getInt("subjectId");
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String image = rs.getString("image");
                String bio = rs.getString("bio");
                String edu = rs.getString("edu");
                float price = rs.getFloat("price");
                String bank = rs.getString("bank");
                String status = rs.getString("status");

                Tutor tutor = new Tutor(id, sDAO.getSubjectById(subjectId), name, gender, image, bio, edu, price, bank, status);
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a tutor by ID
    public Tutor getTutorById(int id) {
        String sql = "SELECT * FROM Tutor WHERE id =" + id;
        SubjectDAO sDAO = new SubjectDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String image = rs.getString("image");
                String bio = rs.getString("bio");
                String edu = rs.getString("edu");
                float price = rs.getFloat("price");
                String bank = rs.getString("bank");
                String status = rs.getString("status");

                Tutor tutor = new Tutor(id, sDAO.getSubjectById(subjectId), name, gender, image, bio, edu, price, bank, status);
                return tutor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new tutor
    public int addTutor(Tutor tutor) {
        int n = 0;
        String sql = "INSERT INTO Tutor (subjectId, name, gender, image, bio, edu, price, bank, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, tutor.getSubject().getId());
            pre.setString(2, tutor.getName());
            pre.setBoolean(3, tutor.isGender());
            pre.setString(4, tutor.getImage());
            pre.setString(5, tutor.getBio());
            pre.setString(6, tutor.getEdu());
            pre.setFloat(7, tutor.getPrice());
            pre.setString(8, tutor.getBank());
            pre.setString(9, tutor.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a tutor
    public int updateTutor(Tutor tutor) {
        int n = 0;
        String sql = "UPDATE Tutor SET subjectId = ?, name = ?, gender = ?, image = ?, bio = ?, edu = ?, price = ?, bank = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, tutor.getSubject().getId());
            pre.setString(2, tutor.getName());
            pre.setBoolean(3, tutor.isGender());
            pre.setString(4, tutor.getImage());
            pre.setString(5, tutor.getBio());
            pre.setString(6, tutor.getEdu());
            pre.setFloat(7, tutor.getPrice());
            pre.setString(8, tutor.getBank());
            pre.setString(9, tutor.getStatus());
            pre.setInt(10, tutor.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a tutor
    public int removeTutor(int tutorId) {
        int n = 0;
        String sql = "DELETE FROM Tutor WHERE id = " + tutorId;
        try {
            Statement state = connection.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }


     public Map<Integer, Map<String, Object>> getAllTutorRatings() {
        Map<Integer, Map<String, Object>> tutorRatings = new HashMap<>();
        String sql = "SELECT tutorId, AVG(rating) as avg_rate, COUNT(rating) as rate_count FROM Rating GROUP BY tutorId";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int tutorId = rs.getInt("tutorId");
                double avgRate = rs.getDouble("avg_rate");
                int rateCount = rs.getInt("rate_count");

                Map<String, Object> rate = new HashMap<>();
                if (rateCount == 0) {
                    rate.put("Newtutor", rateCount);
                } else {
                    rate.put("avgRate", avgRate);
                    rate.put("rateCount", rateCount);
                }
                tutorRatings.put(tutorId, rate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutorRatings;
    }
    
    public Map<String, Object> getTutorRatingsById(int tutorId) {
    Map<String, Object> tutorRating = new HashMap<>();
    String sql = "SELECT tutorId, AVG(rating) as avg_rate, COUNT(rating) as rate_count FROM Rating WHERE tutorId = ? GROUP BY tutorId";
    try {
        PreparedStatement state = connection.prepareStatement(sql);
        state.setInt(1, tutorId);
        ResultSet rs = state.executeQuery();
        if (rs.next()) {
            double avgRate = rs.getDouble("avg_rate");
            int rateCount = rs.getInt("rate_count");

            if (rateCount == 0) {
                tutorRating.put("NewTutor", rateCount);
            } else {
                tutorRating.put("avgRate", avgRate);
                tutorRating.put("rateCount", rateCount);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return tutorRating;
}

     
     public Vector<Tutor> RateTutors(double start, double end) {
        Vector<Tutor> vector = new Vector<>();
        SubjectDAO sDAO = new SubjectDAO();
         String sql = "SELECT t.*\n"
                 + "FROM Tutor t\n"
                 + "JOIN (\n"
                 + "    SELECT tutorId, AVG(rating) as avg_rate, COUNT(rating) as rate_count\n"
                 + "    FROM Rating\n"
                 + "    GROUP BY tutorId\n"
                 + "    HAVING AVG(rating) BETWEEN "+start+" AND "+end+"\n"
                 + ") r ON t.id = r.tutorId;";
         try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectId = rs.getInt("subjectId");
                String name = rs.getString("name");
                boolean gender = rs.getBoolean("gender");
                String image = rs.getString("image");
                String bio = rs.getString("bio");
                String edu = rs.getString("edu");
                float price = rs.getFloat("price");
                String bank = rs.getString("bank");
                String status = rs.getString("status");

                Tutor tutor = new Tutor(id, sDAO.getSubjectById(subjectId), name, gender, image, bio, edu, price, bank, status);
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
     
    public static void main(String[] args) {
        TutorDAO t = new TutorDAO();
        String name = "hien";
        int subjectId = 15;
        int id = 9;
        Vector<Tutor> v = t.getTutors("SELECT *\n"
                    + "FROM Tutor\n"
                    + "WHERE subjectId ="+ subjectId 
                    + "AND id <> "+id);
        for(Tutor tu : v){
            System.out.println(t);
        }
    }
}
