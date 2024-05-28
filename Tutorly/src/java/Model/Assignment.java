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
public class Assignment {
    private long id;
    private Lesson lession;
    private String fileName;
    private String filePath;
    private Date createdAt;

    // Constructors
    public Assignment() {}

    public Assignment(Lesson lession, String fileName, String filePath) {
        this.lession = lession;
        this.fileName = fileName;
        this.filePath = filePath;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
