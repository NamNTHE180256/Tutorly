/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Session;
import Model.Tutor;
import Model.TutorAvailability;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
public class TutorDAO extends DBContext {

    public ArrayList<Tutor> getAllTutors() {
        ArrayList<Tutor> tutors = new ArrayList<>();
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT * FROM Tutor";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDao.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutors.add(tutor);
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutors;
    }
    
    public ArrayList<Tutor> searchTutors(String searchQuery) {
        ArrayList<Tutor> tutors = new ArrayList<>();
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT Tutor.* FROM Tutor JOIN \n"
                + "	[User] ON Tutor.id = [User].id\n"
                + "	WHERE Tutor.id LIKE ? \n"
                + "		OR Tutor.[name] LIKE ?\n"
                + "		OR [User].email LIKE ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + searchQuery + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDao.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutors.add(tutor);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutors;
    }

    public Tutor getTutorById(int id) {
        Tutor tutor = null;
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT * FROM Tutor WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            ResultSet rs = sp.executeQuery();
            if (rs.next()) {
                tutor = new Tutor();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDao.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutor;
    }

    public boolean insertTutor(Tutor tutor) {
        String sql = "INSERT INTO Tutor (id,subjectId, name, gender, image, bio, edu, price, bank, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, tutor.getId());
            sp.setInt(2, tutor.getSubject().getId());
            sp.setString(3, tutor.getName());
            sp.setBoolean(4, tutor.isGender());
            sp.setString(5, tutor.getImage());
            sp.setString(6, tutor.getBio());
            sp.setString(7, tutor.getEdu());
            sp.setFloat(8 ,tutor.getPrice());
            sp.setString(9, tutor.getBank());
            sp.setString(10, tutor.getStatus());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException ex) {     
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateTutor(Tutor tutor) {
        String sql = "UPDATE Tutor SET subjectId = ?, name = ?, gender = ?, image = ?, bio = ?, edu = ?, price = ?, bank = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, tutor.getSubject().getId());
            sp.setString(2, tutor.getName());
            sp.setBoolean(3, tutor.isGender());
            sp.setString(4, tutor.getImage());
            sp.setString(5, tutor.getBio());
            sp.setString(6, tutor.getEdu());
            sp.setFloat(7, tutor.getPrice());
            sp.setString(8, tutor.getBank());
            sp.setString(9, tutor.getStatus());
            sp.setInt(10, tutor.getId());
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTutor(int id) {
        String sql = "DELETE FROM Tutor WHERE id = ?";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return vector;
    }

    // Method to get a tutor by ID
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return tutorRating;
    }

    public double getAvgRateById(int id) {
        double avgRate = 0;
        String sql = "SELECT AVG(rating) as avg_rate FROM Rating WHERE tutorId = ? GROUP BY tutorId";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                avgRate = rs.getDouble("avg_rate");
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return avgRate;
    }

    public Vector<TutorAvailability> getTutorAvailabilityByTutorId(int tutorId) {
        Vector<TutorAvailability> tutorAvailabilities = new Vector<>();
        String sql = "SELECT \n"
                + "    COALESCE(ta.tutorId, ?) AS tutorId, \n"
                + "    s.id AS sessionId, \n"
                + "    COALESCE(ta.status, 'Unavailable') AS status\n"
                + "FROM [Session] s\n"
                + "LEFT JOIN TutorAvailability ta ON s.id = ta.sessionId AND ta.tutorId = ?\n"
                + "ORDER BY \n"
                + "    CASE \n"
                + "        WHEN s.id LIKE '%1' THEN 1\n"
                + "        WHEN s.id LIKE '%2' THEN 2\n"
                + "        WHEN s.id LIKE '%3' THEN 3\n"
                + "        WHEN s.id LIKE '%4' THEN 4\n"
                + "        WHEN s.id LIKE '%5' THEN 5\n"
                + "    END, \n"
                + "    CASE \n"
                + "        WHEN s.id LIKE 'M%' THEN 1\n"
                + "        WHEN s.id LIKE 'T%' THEN 2\n"
                + "        WHEN s.id LIKE 'W%' THEN 3\n"
                + "        WHEN s.id LIKE 'R%' THEN 4\n"
                + "        WHEN s.id LIKE 'F%' THEN 5\n"
                + "        WHEN s.id LIKE 'SA%' THEN 6\n"
                + "        WHEN s.id LIKE 'SU%' THEN 7\n"
                + "    END,\n"
                + "    s.startTime;";
        TutorDAO tutorDAO = new TutorDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, tutorId);
            state.setInt(2, tutorId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("tutorId");
                String sessionId = rs.getString("sessionId");
                String status = rs.getString("status");
                Tutor tutor = tutorDAO.getTutorById(tutorId);
                Session session = sessionDAO.getSessionById(sessionId);
                TutorAvailability tutorAvailability = new TutorAvailability(id, tutor, session, status);
                tutorAvailabilities.add(tutorAvailability);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tutorAvailabilities;
    }

    public void setStatus(int tutorId, String status) {
        String sql = "UPDATE Tutor SET status = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, tutorId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                + "    HAVING AVG(rating) BETWEEN " + start + " AND " + end + "\n"
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
            ex.printStackTrace();
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
                + "WHERE subjectId =" + subjectId
                + "AND id <> " + id);
        for (Tutor tu : v) {
            System.out.println(t);
        }
        System.out.println(t.searchTutors("1"));
    }
}
