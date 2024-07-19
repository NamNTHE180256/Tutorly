/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TRANG
 */
public class SaveTutorList extends Tutor {

    private String saveStatus;

    public SaveTutorList() {
    }

    public SaveTutorList(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    public SaveTutorList(String saveStatus, int id, Subject subject, String name, boolean gender, String image, String bio, String edu, float price, String bank, String status) {
        super(id, subject, name, gender, image, bio, edu, price, bank, status);
        this.saveStatus = saveStatus;
    }

    public String getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(String saveStatus) {
        this.saveStatus = saveStatus;
    }

    @Override
    public String toString() {
        return "SaveTutorList{" + "saveStatus=" + saveStatus + '}';
    }

}
