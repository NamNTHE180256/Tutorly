/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.AClass;
import Model.RequestCancel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public class CancelClassDao extends DBContext {

    AClassDAO aClassDAO = new AClassDAO();

    public int updateClassStatusDenied(int excludedId, String targetDate) {
        String sql = "UPDATE CancelClass cce "
                + "SET status = 'denied' "
                + "FROM CancelClass cce "
                + "JOIN Class c ON cce.classId = c.id "
                + "WHERE cce.id <> ? AND c.startDate = ?";

        int updatedRows = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, excludedId);
            pstmt.setString(2, targetDate);

            updatedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            
        }

        return updatedRows;
    }
   public int updateClassStatusAccp(int excludedId, String targetDate) {
        String sql = "UPDATE CancelClass cce "
                + "SET status = 'accepted' "
                + "FROM CancelClass cce "
                + "JOIN Class c ON cce.classId = c.id "
                + "WHERE cce.id <> ? AND c.startDate = ?";

        int updatedRows = 0;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, excludedId);
            pstmt.setString(2, targetDate);

            updatedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            
        }

        return updatedRows;
    }

    public ArrayList<RequestCancel> getALlRequestWithTutorID(int tutorID) {
        ArrayList<RequestCancel> list = new ArrayList<>();
        String sql = "select * from CancelClass ce JOIN Class c on ce.classId = c.id where tutorId=?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, tutorID);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int classId = rs.getInt("classId");
                    AClass aclass = aClassDAO.getClassById(classId);
                    String status = rs.getString("status");
                    String readed = rs.getString("readed");
                    RequestCancel req = new RequestCancel(id, aclass, status, readed);
                    list.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Bạn nên xử lý lỗi đúng cách
        }
        return list;
    }

    public ArrayList<RequestCancel> getALlRequestWithLearnerID(int learnerId) {
        ArrayList<RequestCancel> list = new ArrayList<>();
        String sql = "select * from CancelClass ce JOIN Class c on ce.classId = c.id where learnerId=?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, learnerId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int classId = rs.getInt("classId");
                    AClass aclass = aClassDAO.getClassById(classId);
                    String status = rs.getString("status");
                    String readed = rs.getString("readed");
                    RequestCancel req = new RequestCancel(id, aclass, status, readed);
                    list.add(req);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Bạn nên xử lý lỗi đúng cách
        }
        return list;
    }

    public int countRequestsByClassId(int classId, int learnerid) {
        String sql = "SELECT COUNT(*) \n"
                + "FROM [dbo].[CancelClass] \n"
                + "JOIN Class ON Class.id = CancelClass.classId \n"
                + "WHERE CancelClass.classId = ? \n"
                + "  AND Class.learnerId = ?\n"
                + "  AND CancelClass.status = 'trial'";
        int count = 0;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, classId);
            st.setInt(2, learnerid);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi để có thể theo dõi dễ dàng hơn
        }

        return count;
    }

    public RequestCancel getRequestWithID(int id) {

        String sql = "select * from CancelClass where id=?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int idrqc = rs.getInt("id");
                    int classId = rs.getInt("classId");
                    AClass aclass = aClassDAO.getClassById(classId);
                    String status = rs.getString("status");
                    String readed = rs.getString("readed");
                    RequestCancel req = new RequestCancel(id, aclass, status, readed);
                    return req;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Bạn nên xử lý lỗi đúng cách
        }
        return null;
    }

    public int insertCancelRequest(AClass aclass) {
        String sql = "INSERT INTO [dbo].[CancelClass] ([classId], [status], [readed]) VALUES (?, ?, ?)";
        int generatedId = 0;
        System.out.println(aclass);
        try (PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, aclass.getId());
            st.setString(2, "wait");
            st.setString(3, "unread");

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi để có thể theo dõi dễ dàng hơn
        }

        return generatedId;
    }

    public boolean deleteCancelRequest(int cancelId) {
        String sql = "DELETE FROM [dbo].[CancelClass] WHERE id = ?";
        boolean rowDeleted = false;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, cancelId);

            int affectedRows = st.executeUpdate();
            rowDeleted = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi để có thể theo dõi dễ dàng hơn
        }

        return rowDeleted;
    }

    public boolean updateCancelRequestStatus(int cancelId, String status) {
        String sql = "UPDATE [dbo].[CancelClass] SET status = ? WHERE id = ?";
        boolean rowUpdated = false;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, cancelId);

            int affectedRows = st.executeUpdate();
            rowUpdated = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi để có thể theo dõi dễ dàng hơn
        }

        return rowUpdated;
    }

    public static void main(String[] args) {
        CancelClassDao cdao = new CancelClassDao();
        AClassDAO adao = new AClassDAO();
        System.out.println(cdao.insertCancelRequest(adao.getClassById(12)));
        System.out.println(cdao.getALlRequestWithTutorID(10));
        System.out.println(cdao.getRequestWithID(3));
    }
}
