package DAO;

import Model.Material;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaterialDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(MaterialDAO.class.getName());

    public ArrayList<Material> getAllMaterialWithID(int classid, int lessonid) {
        ArrayList<Material> list = new ArrayList<>();

        String query = "SELECT m.* " +
                "FROM Material m " +
                "JOIN Lession l ON m.lessionId = l.id " +
                "JOIN Class c ON l.classId = c.id " +
                "WHERE c.id = ? AND m.lessionId = ?;";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, classid);
            st.setInt(2, lessonid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int lessonID = rs.getInt("lessionID");
                String name = rs.getString("fileName");
              byte[] fileData = rs.getBytes("filePath");
                String fileType = rs.getString("filetype");
                String uploadedAt = rs.getString("uploadedAt");

                Material mate = new Material(id, name, fileData, fileType, uploadedAt, lessonID);
                list.add(mate);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching materials", e);
        }

        return list;
    }

    public Material getAllMaterialWithlesson(int classid, int fileid, int lessonid) {
        Material mate = null;

        String query = "SELECT m.* " +
                "FROM Material m " +
                "JOIN Lession l ON m.lessionId = l.id " +
                "JOIN Class c ON l.classId = c.id " +
                "WHERE c.id = ? AND m.lessionId = ? AND m.id = ?;";

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
               byte[] fileData = rs.getBytes("filePath");
                String fileType = rs.getString("filetype");
                String uploadedAt = rs.getString("uploadedAt");

                mate = new Material(id, name, fileData, fileType, uploadedAt, lessonID);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching material with lesson", e);
        }
        return mate;
    }

    public FileData getFileAsBinary(int fileId) {
        FileData fileData = null;
        String query = "SELECT fileName, filePath FROM Material WHERE id = ?";

        try {
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, fileId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String fileName = rs.getString("fileName");
                InputStream fileContent = rs.getBinaryStream("filePath");
                fileData = new FileData(fileName, fileContent);
            } else {
                LOGGER.log(Level.WARNING, "No file found with ID: {0}", fileId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching file as binary", e);
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
        System.out.println(dao.getFileAsBinary(3));
    }
}
