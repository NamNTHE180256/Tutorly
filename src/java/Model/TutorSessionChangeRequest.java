package Model;

import java.sql.Timestamp;
import java.sql.Date;

public class TutorSessionChangeRequest {

    private int id;
    private int learnerId;
    private Learner learner;
    private int tutorId;
    private Tutor tutor;
    private String fromSessionId;
    private String toSessionId;
    private Session fromSession;
    private Session toSession;
    private String reason;
    private Date date;
    private String status;
    private Timestamp createdAt;

    public TutorSessionChangeRequest() {
    }

    public TutorSessionChangeRequest(int id, int learnerId, int tutorId, String fromSessionId, String toSessionId, String reason, String status, Timestamp createdAt) {
        this.id = id;
        this.learnerId = learnerId;
        this.tutorId = tutorId;
        this.fromSessionId = fromSessionId;
        this.toSessionId = toSessionId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public TutorSessionChangeRequest(int learnerId, int tutorId, String fromSessionId, String toSessionId, String reason, Date date, String status) {
        this.learnerId = learnerId;
        this.tutorId = tutorId;
        this.fromSessionId = fromSessionId;
        this.toSessionId = toSessionId;
        this.reason = reason;
        this.date = date;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Session getFromSession() {
        return fromSession;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public void setFromSession(Session fromSession) {
        this.fromSession = fromSession;
    }

    public Session getToSession() {
        return toSession;
    }

    public void setToSession(Session toSession) {
        this.toSession = toSession;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public TutorSessionChangeRequest(int learnerId, int tutorId, String fromSessionId, String toSessionId, String reason, String status) {
        this.learnerId = learnerId;
        this.tutorId = tutorId;
        this.fromSessionId = fromSessionId;
        this.toSessionId = toSessionId;
        this.reason = reason;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(int learnerId) {
        this.learnerId = learnerId;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public String getFromSessionId() {
        return fromSessionId;
    }

    public void setFromSessionId(String fromSessionId) {
        this.fromSessionId = fromSessionId;
    }

    public String getToSessionId() {
        return toSessionId;
    }

    public void setToSessionId(String toSessionId) {
        this.toSessionId = toSessionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SessionChangeRequest{" + "id=" + id + ", learnerId=" + learnerId + ", learner=" + learner + ", tutorId=" + tutorId + ", fromSessionId=" + fromSessionId + ", toSessionId=" + toSessionId + ", fromSession=" + fromSession + ", toSession=" + toSession + ", reason=" + reason + ", status=" + status + ", createdAt=" + createdAt + '}';
    }

}
