/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Video {

    private int id;
    private Lesson lesson;
    private String fileName;
    private String filePath;
    private String fileType;
    private Date uploadedAt;

    public Video(Lesson lesson, String fileName, String filePath, String fileType) {

        this.lesson = lesson;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;

    }

    public Video(int id, Lesson lesson, String fileName, String filePath, String fileType, Date uploadedAt) {
        this.id = id;
        this.lesson = lesson;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", lesson=" + lesson + ", fileName=" + fileName + ", filePath=" + filePath + ", fileType=" + fileType + ", uploadedAt=" + uploadedAt + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

}
