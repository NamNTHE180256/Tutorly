/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.UserDAO;

/**
 *
 * @author Admin
 */
public class Manager {

    private int id;
    private String name;
    private int approvedTutor;
    private int rejectedTutor;
    private int blockedTutor;
    private String status;

    // Constructor
    public Manager() {
    }

    public Manager(int id, String name, int approvedTutor, int rejectedTutor, int blockedTutor, String status) {
        this.id = id;
        this.name = name;
        this.approvedTutor = approvedTutor;
        this.rejectedTutor = rejectedTutor;
        this.blockedTutor = blockedTutor;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getApprovedTutor() {
        return approvedTutor;
    }

    public void setApprovedTutor(int approvedTutor) {
        this.approvedTutor = approvedTutor;
    }

    public int getRejectedTutor() {
        return rejectedTutor;
    }

    public void setRejectedTutor(int rejectedTutor) {
        this.rejectedTutor = rejectedTutor;
    }

    public int getBlockedTutor() {
        return blockedTutor;
    }

    public void setBlockedTutor(int blockedTutor) {
        this.blockedTutor = blockedTutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Manager{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", approvedTutor=" + approvedTutor
                + ", rejectedTutor=" + rejectedTutor
                + ", blockedTutor=" + blockedTutor
                + ", status='" + status + '\''
                + '}';
    }

    public User getUserInfo() {
        UserDAO uDAO = new UserDAO();
        return uDAO.getUserById(id);
    }
}
