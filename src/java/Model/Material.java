/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class Material {
    private long id;
    private Lesson lession;
    private String fileName;
    private String filePath;
    private String fileType;
    private Date uploadedAt;

    // Constructors
    public Material() {}

    public Material(Lesson lession, String fileName, String filePath, String fileType) {
        this.lession = lession;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lesson getLession() {
        return lession;
    }

    public void setLession(Lesson lession) {
        this.lession = lession;
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
