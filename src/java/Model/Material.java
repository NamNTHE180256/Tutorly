package Model;
import java.sql.Timestamp;





public class Material {

     private int id;
    private String fileName;
    private byte[] fileData;
    private String fileType;
    private String uploadedAt;
    private int lessonID; // Corrected variable name

    // Constructor

    public Material(int id, String fileName, byte[] fileData, String fileType, String uploadedAt, int lessonID) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
        this.lessonID = lessonID;
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

 

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }
   

}
