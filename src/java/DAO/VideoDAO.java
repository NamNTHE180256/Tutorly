/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Lesson;
import Model.Material;
import Model.Video;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Acer
 */
public class VideoDAO extends DBContext {

    LessonDAO lessonDAO = new LessonDAO();

    public Video getAllVideoWithLesson(int classId, int fileId, int lessonId) {
        Video video = null;

        String query = "SELECT m.*\n"
                + "FROM Video m\n"
                + "JOIN Lesson l ON m.lessonId = l.id\n"
                + "JOIN Class c ON l.classId = c.id\n"
                + "WHERE c.id = ? AND m.lessonId = ? AND m.id = ?;";

        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setInt(1, classId);
            st.setInt(2, lessonId);
            st.setInt(3, fileId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int lessonID = rs.getInt("lessonId");
                String name = rs.getString("fileName");
               String fileData = rs.getString("filePath"); // Chuyển đổi thành byte[]
                String fileType = rs.getString("fileType");
                Date uploadedAt = rs.getDate("uploadedAt");
                Lesson lesson = lessonDAO.getLessonById(lessonID);
             video = new Video(id, lesson, name, fileData, fileType, uploadedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Bạn có thể thay thế bằng việc ghi log thích hợp
        }
        return video;
    }

    public ArrayList<Video> getAllVideoWithID(int classid, int lessonid) {
        ArrayList<Video> list = new ArrayList<>();

        String query = "SELECT m.*\n"
                + "FROM Video m\n"
                + "JOIN Lesson l ON m.lessonId = l.id\n"
                + "JOIN Class c ON l.classId = c.id\n"
                + "WHERE c.id = ? AND m.lessonId = ?;";

        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setInt(1, classid);
            st.setInt(2, lessonid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int lessonID = rs.getInt("lessonId");
                String name = rs.getString("fileName");
                String fileData = rs.getString("filePath"); // Chuyển đổi thành byte[]
                String fileType = rs.getString("fileType");
                Date uploadedAt = rs.getDate("uploadedAt");
                Lesson lesson = lessonDAO.getLessonById(lessonID);
                Video video = new Video(id, lesson, name, fileData, fileType, uploadedAt);
                list.add(video);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Bạn có thể thay thế bằng việc ghi log thích hợp
        }

        return list;
    }

    public static void main(String[] args) {
        VideoDAO dao = new VideoDAO();
        System.out.println(dao.getAllVideoWithID(3, 17).get(0).getFileName());
    }
}
