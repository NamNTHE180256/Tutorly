package Model;

import java.util.Date;

public class AClass {

    private int id;
    private Learner learner;
    private Tutor tutor;
    private int totalSession;
    private Date startDate;
    private Date endDate;
    private String status;
    private Subject subject;
    // Constructors
    public AClass() {
        this.subject = new Subject(); // Ensure subject is initialized
    }

    public AClass(Learner learner, Tutor tutor, int totalSession, Date startDate, Date endDate, String status, Subject subject) {
        this.learner = learner;
        this.tutor = tutor;
        this.totalSession = totalSession;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.subject = subject;
    }

    public AClass(Learner learner, Tutor tutor, int totalSession, Date startDate, Date endDate, String status) {
        this.learner = learner;
        this.tutor = tutor;
        this.totalSession = totalSession;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.subject = new Subject(); // Ensure subject is initialized
    
    }

 

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public int getTotalSession() {
        return totalSession;
    }

    public void setTotalSession(int totalSession) {
        this.totalSession = totalSession;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "AClass{" + "id=" + id + ", learner=" + learner + ", tutor=" + tutor + ", totalSession=" + totalSession + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", subject=" + subject + '}';
    }

}
