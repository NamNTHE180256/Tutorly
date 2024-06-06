/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.SubjectCountPercentage;
import java.util.Vector;
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
public class SubjectCountPercentageDAO extends DBContext{
    public Vector<SubjectCountPercentage> getSubjectCountPercentage(int learnerId) {
        Vector<SubjectCountPercentage> vector = new Vector<>();
        String sql = """
            WITH ClassCounts AS (
                SELECT 
                    C.learnerId,
                    LEFT(S.name, CHARINDEX(' ', S.name + ' ') - 1) AS SubjectName,
                    COUNT(C.id) AS SubjectClassCount
                FROM 
                    Class C
                JOIN 
                    Tutor T ON C.tutorId = T.id
                JOIN 
                    Subject S ON T.subjectId = S.id
                WHERE 
                    C.learnerId = ?
                GROUP BY 
                    C.learnerId, 
                    LEFT(S.name, CHARINDEX(' ', S.name + ' ') - 1)
            ),
            TotalCounts AS (
                SELECT 
                    learnerId,
                    COUNT(id) AS TotalClassCount
                FROM 
                    Class
                WHERE 
                    learnerId = ?
                GROUP BY 
                    learnerId
            )
            SELECT 
                CC.learnerId,
                CC.SubjectName,
                CC.SubjectClassCount,
                (CC.SubjectClassCount * 100.0 / TC.TotalClassCount) AS AveragePercentage
            FROM 
                ClassCounts CC
            JOIN 
                TotalCounts TC ON CC.learnerId = TC.learnerId
            ORDER BY 
                CC.learnerId, 
                CC.SubjectName
            """;

        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            state.setInt(2, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                int lId = rs.getInt("learnerId");
                String subjectName = rs.getString("SubjectName");
                int subjectClassCount = rs.getInt("SubjectClassCount");
                double averagePercentage = rs.getDouble("AveragePercentage");

                SubjectCountPercentage stats = new SubjectCountPercentage(lId, subjectName, subjectClassCount, averagePercentage);
                vector.add(stats);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectCountPercentageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public static void main(String[] args) {
        SubjectCountPercentageDAO dao = new SubjectCountPercentageDAO();
        try {
            int learnerId = 1; // Example learnerId
            Vector<SubjectCountPercentage> stats = dao.getSubjectCountPercentage(learnerId);
            for (SubjectCountPercentage stat : stats) {
                System.out.println(stat.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
