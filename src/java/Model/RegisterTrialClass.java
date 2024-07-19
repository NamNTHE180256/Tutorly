/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author TRANG
 */
public class RegisterTrialClass {

    private int id;
    private Learner learner;
    private Tutor tutor;
    private Session session;
    private int totalSession;
    private Date startDate;
    private Date endDate;
    private String status;
    private Subject subject;
    private String readed;

    public RegisterTrialClass(Learner learner, Tutor tutor, Session session, int totalSession, Date startDate, Date endDate, String status, Subject subject, String readed) {
        this.learner = learner;
        this.tutor = tutor;
        this.session = session;
        this.totalSession = totalSession;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.subject = subject;
        this.readed = readed;
    }

    public RegisterTrialClass(int id, Learner learner, Tutor tutor, Session session, int totalSession, Date startDate, Date endDate, String status, Subject subject, String readed) {
        this.id = id;
        this.learner = learner;
        this.tutor = tutor;
        this.session = session;
        this.totalSession = totalSession;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.subject = subject;
        this.readed = readed;
    }

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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    @Override
    public String toString() {
        return "RegisterTrialClass{"
                + "id=" + id
                + ", learner=" + learner
                + ", tutor=" + tutor
                + ", totalSession=" + totalSession
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", status='" + status + '\''
                + ", subject=" + subject
                + ", readed='" + readed + '\''
                + '}';
    }
}
