/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Learner;
import Model.Rating;
import Model.Tutor;
import java.util.Vector;
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
public class RatingDAO extends DBContext{
    public RatingDAO() {
        // Initialize your database connection here
        // For example:
        // this.connection = DriverManager.getConnection("your_database_url", "username", "password");
    }

    // Method to get all ratings
    public Vector<Rating> getAllRatings() {
        Vector<Rating> vector = new Vector<>();
        String sql = "SELECT * FROM Rating";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Learner learner = getLearnerById(rs.getInt("learnerId")); // Implement getLearnerById method
                Tutor tutor = getTutorById(rs.getInt("tutorId")); // Implement getTutorById method
                int rating = rs.getInt("rating");
                String review = rs.getString("review");
                Date createdAt = rs.getDate("createdAt");

                Rating ratingObj = new Rating(learner, tutor, rating, review);
                ratingObj.setId(id);
                ratingObj.setCreatedAt(createdAt);
                vector.add(ratingObj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    // Method to get a rating by ID
    public Rating getRatingById(int id) {
        String sql = "SELECT * FROM Rating WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                Learner learner = getLearnerById(rs.getInt("learnerId")); // Implement getLearnerById method
                Tutor tutor = getTutorById(rs.getInt("tutorId")); // Implement getTutorById method
                int rating = rs.getInt("rating");
                String review = rs.getString("review");
                Date createdAt = rs.getDate("createdAt");

                Rating ratingObj = new Rating(learner, tutor, rating, review);
                ratingObj.setId(id);
                ratingObj.setCreatedAt(createdAt);
                return ratingObj;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Method to add a new rating
    public int addRating(Rating rating) {
        int n = 0;
        String sql = "INSERT INTO Rating (learnerId, tutorId, rating, review, createdAt) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, rating.getLearner().getId());
            pre.setInt(2, rating.getTutor().getId());
            pre.setInt(3, rating.getRating());
            pre.setString(4, rating.getReview());
            pre.setDate(5, new java.sql.Date(rating.getCreatedAt().getTime()));
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to update a rating
    public int updateRating(Rating rating) {
        int n = 0;
        String sql = "UPDATE Rating SET learnerId = ?, tutorId = ?, rating = ?, review = ?, createdAt = ? WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, rating.getLearner().getId());
            pre.setInt(2, rating.getTutor().getId());
            pre.setInt(3, rating.getRating());
            pre.setString(4, rating.getReview());
            pre.setDate(5, new java.sql.Date(rating.getCreatedAt().getTime()));
            pre.setInt(6, rating.getId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Method to delete a rating
    public int removeRating(int ratingId) {
        int n = 0;
        String sql = "DELETE FROM Rating WHERE id = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, ratingId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    // Placeholder method to get a learner by ID
    private Learner getLearnerById(int learnerId) {
        // Implement the method to get a Learner by its ID
        // For example, you might have a LearnerDAO class with a similar method
        return new Learner(); // Replace with actual implementation
    }

    // Placeholder method to get a tutor by ID
    private Tutor getTutorById(int tutorId) {
        // Implement the method to get a Tutor by its ID
        // For example, you might have a TutorDAO class with a similar method
        return new Tutor(); // Replace with actual implementation
    }
    
     public Vector<Rating> getRatingsByTutorId(int tutorId) {
        Vector<Rating> vector = new Vector<>();
        String sql = "SELECT * FROM Rating WHERE tutorId = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, tutorId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Learner learner = getLearnerById(rs.getInt("learnerId")); // Implement getLearnerById method
                Tutor tutor = getTutorById(tutorId); // You can reuse the existing method to get tutor details
                int rating = rs.getInt("rating");
                String review = rs.getString("review");
                Date createdAt = rs.getDate("createdAt");

                Rating ratingObj = new Rating(learner, tutor, rating, review);
                ratingObj.setId(id);
                ratingObj.setCreatedAt(createdAt);
                vector.add(ratingObj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
     
     public static void main(String[] args) {
        RatingDAO r = new RatingDAO();
         System.out.println(r.getRatingsByTutorId(7));
    }
}
