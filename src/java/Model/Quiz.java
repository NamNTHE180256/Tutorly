package Model;

import java.util.Date;
import java.util.List;

public class Quiz {

    private long id;
    private int lessonId;
    private String fileName;
    private String filePath;
    private double score;
    private String status;
    private Date createdAt;
    private int quizTime; // New attribute
    private Date startedOn;
    private Date completedOn;
    private int timeTaken;
    private int numberOfTimeDone;
    private List<Question> questions;

    // Constructors, getters, and setters
    public Quiz() {
    }

    public Quiz(long id, int lessonId, String fileName, String filePath, Date createdAt, String status) {
        this.id = id;
        this.lessonId = lessonId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Quiz(long id, int lessonId, String fileName, String filePath, Date createdAt, String status, int quizTime, Date startedOn, Date completedOn, int timeTaken, int numberOfTimeDone) {
        this.id = id;
        this.lessonId = lessonId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.createdAt = createdAt;
        this.status = status;
        this.quizTime = quizTime;
        this.startedOn = startedOn;
        this.completedOn = completedOn;
        this.timeTaken = timeTaken;
        this.numberOfTimeDone = numberOfTimeDone;
    }

    // Getters and Setters for all fields including new field
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(int quizTime) {
        this.quizTime = quizTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getNumberOfTimeDone() {
        return numberOfTimeDone;
    }

    public void setNumberOfTimeDone(int numberOfTimeDone) {
        this.numberOfTimeDone = numberOfTimeDone;
    }

    @Override
    public String toString() {
        return "Quiz{"
                + "id=" + id
                + ", lessonId=" + lessonId
                + ", fileName='" + fileName + '\''
                + ", filePath='" + filePath + '\''
                + ", createdAt=" + createdAt
                + ", status='" + status + '\''
                + ", timeOfQuiz=" + quizTime
                + '}';
    }
}
