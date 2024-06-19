/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Material;
import java.io.InputStream;
import java.security.Timestamp;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class MaterialDAO extends DBContext {

    public ArrayList<Material> getAllMaterialWithID(int classid, int lessonid) {
        ArrayList<Material> list = new ArrayList<>();

        String query = "SELECT m.*\n"
                + "FROM Material m\n"
                + "JOIN Lession l ON m.lessionId = l.id\n"
                + "JOIN Class c ON l.classId = c.id\n"
                + "WHERE c.id = ? AND m.lessionId = ?;";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, classid);
            st.setInt(2, lessonid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int lessonID = rs.getInt("lessionID");
                String name = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                String fileType = rs.getString("filetype");
                String uploadedAt = rs.getString("uploadedAt");

                Material mate = new Material(id, name, filePath, fileType, uploadedAt, lessonID);
                list.add(mate);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error logging
        }

        return list;
    }

    public Material getAllMaterialWithlesson(int classid, int fileid, int lessonid) {
        Material mate = null;

        String query = "SELECT m.*\n"
                + "FROM Material m\n"
                + "JOIN Lession l ON m.lessionId = l.id\n"
                + "JOIN Class c ON l.classId = c.id\n"
                + "WHERE c.id = ? AND m.lessionId = ? And m.id =? ;";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, classid);
            st.setInt(2, lessonid);
            st.setInt(3, fileid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int lessonID = rs.getInt("lessionID");
                String name = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                String fileType = rs.getString("filetype");
                String uploadedAt = rs.getString("uploadedAt");

                mate = new Material(id, fileType, filePath, fileType, uploadedAt, lessonID);

            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error logging
        }
        return mate;
    }

    public FileData downloadFileFromDatabase(int fileId) {
        FileData fileData = null;
        try {
            // constructs SQL statement
            String sql = "SELECT FileName, FileData FROM Material WHERE Id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fileId);

            // execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fileName = resultSet.getString("FileName");
                InputStream fileContent = resultSet.getBinaryStream("FileData");
                fileData = new FileData(fileName, fileContent);
            }
        } catch (SQLException ex) {

        }
        return fileData;
    }

    public class FileData {

        private String fileName;
        private InputStream fileContent;

        public FileData(String fileName, InputStream fileContent) {
            this.fileName = fileName;
            this.fileContent = fileContent;
        }

        public String getFileName() {
            return fileName;
        }

        public InputStream getFileContent() {
            return fileContent;
        }
    }

    public static void main(String[] args) {
        MaterialDAO dao = new MaterialDAO();
//        for (Material mate : dao.get(1, 1)) {
//            System.out.println(mate);
//        }
System.out.println(dao.getAllMaterialWithlesson(1, 1, 1));
    }
}
