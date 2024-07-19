/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.RatingCountPercentage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TRANG
 */
public class RatingCountPercentageDAO extends DBContext {

    public Vector<RatingCountPercentage> getPercentageByTutorID(int id) {
        Vector<RatingCountPercentage> vector = new Vector<RatingCountPercentage>();
        String sql = "SELECT\n"
                + "    rating,\n"
                + "    COUNT(rating) AS count,\n"
                + "    (COUNT(rating) * 100.0 / total_ratings.total_count) AS percentage\n"
                + "FROM\n"
                + "    Rating\n"
                + "JOIN\n"
                + "    (SELECT COUNT(*) AS total_count FROM Rating WHERE tutorId = ?) AS total_ratings\n"
                + "ON\n"
                + "    1 = 1\n"
                + "WHERE\n"
                + "    tutorId = ?\n"
                + "GROUP BY\n"
                + "    rating, total_ratings.total_count;";
        PreparedStatement state;
        try {
            state = connection.prepareStatement(sql);
            state.setInt(1, id);
            state.setInt(2, id);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int rate = rs.getInt(1);
                int count = rs.getInt(2);
                double percent = rs.getInt(3);
                RatingCountPercentage r = new RatingCountPercentage(rate, count, percent);
                vector.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RatingCountPercentageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vector;
    }

    public static void main(String[] args) {
        RatingCountPercentageDAO r = new RatingCountPercentageDAO();
        System.out.println(r.getPercentageByTutorID(7).toString());;
    }
}
