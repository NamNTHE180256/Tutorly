/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.TutorDAO;
import DAO.UserDAO;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class Tutor {

    private int id;
    private Subject subject;
    private String name;
    private boolean gender;
    private String image;
    private String bio;
    private String edu;
    private float price;
    private String bank;
    private String status;
    private String Linkmeet;

    // Constructors
    public Tutor() {
    }

    public Tutor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tutor(int id, Subject subject, String name, boolean gender, String image, String bio, String edu, float price, String bank, String status) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.bio = bio;
        this.edu = edu;
        this.price = price;
        this.bank = bank;
        this.status = status;
    }
    

    public Tutor(int id, Subject subject, String name, boolean gender, String image, String bio, String edu, float price, String bank, String status, String linkmeet) {
        this.id = id;
        this.subject = subject;
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.bio = bio;
        this.edu = edu;
        this.price = price;
        this.bank = bank;
        this.status = status;
        this.Linkmeet = linkmeet;
    }

    public String getLinkmeet() {
        return Linkmeet;
    }

    public void setLinkmeet(String Linkmeet) {
        this.Linkmeet = Linkmeet;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserInfo() {
        UserDAO uDao = new UserDAO();
        return uDao.getUserById(id);
    }

    public double getAvgRating() {
        TutorDAO tDao = new TutorDAO();
        return tDao.getAvgRateById(id);
    }

    public Vector<TutorAvailability> getAvailabilities() {
        TutorDAO tDao = new TutorDAO();
        return tDao.getTutorAvailabilityByTutorId(id);
    }

    @Override
    public String toString() {
        return "Tutor{" + "id=" + id + ", subject=" + subject + ", name=" + name + ", gender=" + gender + ", image=" + image + ", bio=" + bio + ", edu=" + edu + ", price=" + price + ", bank=" + bank + ", status=" + status + '}';
    }

}
