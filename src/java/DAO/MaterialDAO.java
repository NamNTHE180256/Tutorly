/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Material;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
public class MaterialDAO extends DBContext {

    private LessonDAO lessonDAO = new LessonDAO();

    public boolean addMaterial(Material material) {
        String sql = "INSERT INTO Material (id, lessionId, fileName, filePath, fileType, uploadedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, material.getId());
            ps.setInt(2, material.getLesson().getId());
            ps.setString(3, material.getFileName());
            ps.setString(4, material.getFilePath());
            ps.setString(5, material.getFileType());
            ps.setDate(6, new java.sql.Date(material.getUploadedAt().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vector<Material> displayAllMaterials() {
        Vector<Material> materials = new Vector<>();
        String sql = "SELECT * FROM Material";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                materials.add(createMaterialFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public Material getMaterialById(int id) {
        String sql = "SELECT * FROM Material WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createMaterialFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateMaterial(Material material) {
        String sql = "UPDATE Material SET lessionId = ?, fileName = ?, filePath = ?, fileType = ?, uploadedAt = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, material.getLesson().getId());
            ps.setString(2, material.getFileName());
            ps.setString(3, material.getFilePath());
            ps.setString(4, material.getFileType());
            ps.setDate(5, new java.sql.Date(material.getUploadedAt().getTime()));
            ps.setInt(6, material.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeMaterial(int id) {
        String sql = "DELETE FROM Material WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vector<Material> getMaterialsByLessonId(int lessonId) {
        Vector<Material> materials = new Vector<>();
        String sql = "SELECT * FROM Material WHERE lessonId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, lessonId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    materials.add(createMaterialFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public Vector<Material> getMaterialsByFileType(String fileType) {
        Vector<Material> materials = new Vector<>();
        String sql = "SELECT * FROM Material WHERE fileType = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fileType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    materials.add(createMaterialFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public Vector<Material> getMaterialsByLessonIdAndFileType(int lessonId, String fileType) {
        Vector<Material> materials = new Vector<>();
        String sql = "SELECT * FROM Material WHERE lessionId = ? AND fileType = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, lessonId);
            ps.setString(2, fileType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    materials.add(createMaterialFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public Vector<Material> getMaterialsByClassIdAndFileType(int classId, String fileType) {
        Vector<Material> materials = new Vector<>();
        String sql = "SELECT m.* FROM Material m JOIN Lession l ON m.lessionId = l.id WHERE l.classId = ? AND m.fileType = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ps.setString(2, fileType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    materials.add(createMaterialFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    private Material createMaterialFromResultSet(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setId(rs.getInt("id"));
        material.setLesson(lessonDAO.getLessonById(rs.getInt("lessionId")));
        material.setFileName(rs.getString("fileName"));
        material.setFilePath(rs.getString("filePath"));
        material.setFileType(rs.getString("fileType"));
        material.setUploadedAt(rs.getDate("uploadedAt"));
        return material;
    }

    public static void main(String[] args) {
        MaterialDAO materialDAO = new MaterialDAO();

        // Test getMaterialsByClassIdAndFileType method
        Vector<Material> materialsByClassIdAndFileType = materialDAO.getMaterialsByClassIdAndFileType(1, "document");
        for (Material material : materialsByClassIdAndFileType) {
            System.out.println(material);
        }
    }
}
