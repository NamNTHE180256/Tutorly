package Model;
import java.sql.Timestamp;





public class Material {

    private int id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String uploadedAt;
    private int lessionID;

    // Constructor
    public Material(int id, String fileName, String filePath, String fileType, String uploadedAt, int lessionID) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedAt = uploadedAt;
        this.lessionID = lessionID;
    }

    // Getters and setters
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

    public String getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(String uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setLessionID(int lessionID) {
        this.lessionID = lessionID;
    }

    public int getLessionID() {
        return lessionID;
    }

    @Override
    public String toString() {
        return "Material{" + "id=" + id + ", fileName=" + fileName + ", filePath=" + filePath + ", fileType=" + fileType + ", uploadedAt=" + uploadedAt + ", lessionID=" + lessionID + '}';
    }

}
