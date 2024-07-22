package Model;

import java.sql.Timestamp;
import java.util.Date;

public class Material {

    private int id;
    private String fileName;
    private String filePath;
    private String fileType;
    private Date uploadedAt;
    private Lesson lesson;

    public Material() {
    }

    @Override
    public String toString() {
        return "Material{" + "id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", fileType=" + fileType + ", uploadedAt=" + uploadedAt + ", lesson=" + lesson + '}';
    }

    public Material(int id, String fileName, String filePath, String fileType, Date uploadedAt, Lesson lesson) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
        this.lesson = lesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}
