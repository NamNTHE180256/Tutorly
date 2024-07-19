package Model;

import java.sql.Timestamp;
import java.sql.Date;

public class SessionChangeRequest {

    private int id;
    private int learnerId;
    private Learner learner;
    private int tutorId;
    private String fromSessionId;
    private String toSessionId;
    private Session fromSession;
    private Session toSession;
    private String reason;
    private Date date;
    private String status;
    private Timestamp createdAt;

    public SessionChangeRequest() {
    }

    public SessionChangeRequest(int id, int learnerId, int tutorId, String fromSessionId, String toSessionId, String reason, String status, Timestamp createdAt) {
        this.id = id;
        this.learnerId = learnerId;
        this.tutorId = tutorId;
        this.fromSessionId = fromSessionId;
        this.toSessionId = toSessionId;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    public SessionChangeRequest(int id, int learnerId, Learner learner, int tutorId, String fromSessionId, String toSessionId, Session fromSession, Session toSession, String reason, Date date, String status, Timestamp createdAt) {
        this.id = id;
        this.learnerId = learnerId;
        this.learner = learner;
        this.tutorId = tutorId;
        this.fromSessionId = fromSessionId;
        this.toSessionId = toSessionId;
        this.fromSession = fromSession;
        this.toSession = toSession;
        this.reason = reason;
        this.date = date;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Session getFromSession() {
        return fromSession;
    }

    public void setFromSession(Session fromSession) {
        this.fromSession = fromSession;
    }

    public Session getToSession() {
        return toSession;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public SessionChangeRequest(int learnerId, int tutorId, String fromSessionId, String toSessionId, String reason, String status) {
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
