package DAO;

import Model.SaveTutorList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SaveTutorListDAO extends DBContext {

    public ArrayList<SaveTutorList> getAllTutors() {
        ArrayList<SaveTutorList> tutors = new ArrayList<>();
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT t.*, "
                + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                + "FROM Tutor t "
                + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                + "WHERE t.status = 'Active'";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            ResultSet rs = sp.executeQuery();
            while (rs.next()) {
                SaveTutorList tutor = new SaveTutorList();
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
                tutor.setSaveStatus(rs.getString("save_status"));
                tutors.add(tutor);
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutors;
    }

    public SaveTutorList getTutorById(int id) {
        SaveTutorList tutor = null;
        SubjectDAO sDao = new SubjectDAO();
        String sql = "SELECT t.*, "
                + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                + "FROM Tutor t "
                + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                + "WHERE t.id = ? AND t.status = 'Active'";
        try {
            PreparedStatement sp = connection.prepareStatement(sql);
            sp.setInt(1, id);
            ResultSet rs = sp.executeQuery();
            if (rs.next()) {
                tutor = new SaveTutorList();
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
                tutor.setSaveStatus(rs.getString("save_status"));
            }
            rs.close();
            sp.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutor;
    }

    public boolean insertTutor(SaveTutorList tutor) {
        String sql = "INSERT INTO Tutor (subjectId, name, gender, image, bio, edu, price, bank, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            int rowsAffected = sp.executeUpdate();
            sp.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTutor(SaveTutorList tutor) {
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

    public Vector<SaveTutorList> displayAllTutors() {
        Vector<SaveTutorList> vector = new Vector<>();
        String sql = "SELECT t.*, "
                + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                + "FROM Tutor t "
                + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                + "WHERE t.status = 'Active'";
        SubjectDAO sDAO = new SubjectDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                SaveTutorList tutor = new SaveTutorList();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDAO.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutor.setSaveStatus(rs.getString("save_status"));
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<SaveTutorList> getTutors(String sql) {
        Vector<SaveTutorList> vector = new Vector<>();
        SubjectDAO sDAO = new SubjectDAO();
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                SaveTutorList tutor = new SaveTutorList();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDAO.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutor.setSaveStatus(rs.getString("save_status"));
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int addTutor(SaveTutorList tutor) {
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
                    rate.put("NewTutor", rateCount);
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

    public Vector<SaveTutorList> RateTutors(double start, double end) {
        Vector<SaveTutorList> vector = new Vector<>();
        SubjectDAO sDAO = new SubjectDAO();
        String sql = "SELECT t.*, "
                + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                + "FROM Tutor t "
                + "JOIN ( "
                + "    SELECT tutorId, AVG(rating) as avg_rate, COUNT(rating) as rate_count "
                + "    FROM Rating "
                + "    GROUP BY tutorId "
                + "    HAVING AVG(rating) BETWEEN ? AND ? "
                + ") r ON t.id = r.tutorId "
                + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                + "WHERE t.status = 'Active'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setDouble(1, start);
            state.setDouble(2, end);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                SaveTutorList tutor = new SaveTutorList();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDAO.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutor.setSaveStatus(rs.getString("save_status"));
                vector.add(tutor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<SaveTutorList> getTop10TutorsByRating() {
        Vector<SaveTutorList> topTutors = new Vector<>();
        SubjectDAO sDAO = new SubjectDAO();
        String sql = "SELECT TOP 10 t.*, \n"
                + "       CASE WHEN t.status = 'Active' AND st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status, \n"
                + "       r.avg_rating\n"
                + "FROM Tutor t\n"
                + "JOIN (\n"
                + "    SELECT tutorId, AVG(rating) AS avg_rating\n"
                + "    FROM Rating\n"
                + "    GROUP BY tutorId\n"
                + ") r ON t.id = r.tutorId\n"
                + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId\n"
                + "WHERE t.status = 'Active'\n"
                + "ORDER BY r.avg_rating DESC;";

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                SaveTutorList tutor = new SaveTutorList();
                tutor.setId(rs.getInt("id"));
                tutor.setSubject(sDAO.getSubjectById(rs.getInt("subjectId")));
                tutor.setName(rs.getString("name"));
                tutor.setGender(rs.getBoolean("gender"));
                tutor.setImage(rs.getString("image"));
                tutor.setBio(rs.getString("bio"));
                tutor.setEdu(rs.getString("edu"));
                tutor.setPrice(rs.getFloat("price"));
                tutor.setBank(rs.getString("bank"));
                tutor.setStatus(rs.getString("status"));
                tutor.setSaveStatus(rs.getString("save_status"));
                topTutors.add(tutor);
            }
            rs.close();
            state.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return topTutors;
    }

    public static void main(String[] args) {
        SaveTutorListDAO t = new SaveTutorListDAO();
        Vector<SaveTutorList> v = t.getTop10TutorsByRating();
        for (SaveTutorList tu : v) {
            System.out.println(tu.toString());
        }
    }
}
