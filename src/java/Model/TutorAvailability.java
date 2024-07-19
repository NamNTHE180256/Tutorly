/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class TutorAvailability {

    private int id;
    private Tutor tutor;
    private Session session;

    private String status;

    // Constructors
    public TutorAvailability() {
    }

    public TutorAvailability(Tutor tutor, Session session, String status) {
        this.tutor = tutor;
        this.session = session;
        this.status = status;
    }

    public TutorAvailability(int id, Tutor tutor, Session session, String status) {
        this.id = id;
        this.tutor = tutor;
        this.session = session;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TutorAvailability{" + "id=" + id + ", tutor=" + tutor + ", session=" + session + ", status=" + status + '}';
    }

}
