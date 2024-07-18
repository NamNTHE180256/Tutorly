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
public class Lesson {

    private int id;
    private AClass aClass;
    private Session session;
    private Date date;
    private String status;

    // Constructors
    public Lesson() {
    }

    public Lesson(int id, AClass aClass, Session session, Date date, String status) {
        this.id = id;
        this.aClass = aClass;
        this.session = session;
        this.date = date;
        this.status = status;
    }

    public Lesson(AClass aClass, Session session, Date date, String status) {
        this.aClass = aClass;
        this.session = session;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AClass getaClass() {
        return aClass;
    }

    public void setaClass(AClass aClass) {
        this.aClass = aClass;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lesson{" + "id=" + id + ", aClass=" + aClass + ", session=" + session + ", date=" + date + ", status=" + status + '}';
    }

}
