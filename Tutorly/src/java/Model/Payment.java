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
public class Payment {
    private int id;
    private AClass aClass;
    private float amount;
    private Date date;

    // Constructors
    public Payment() {}

    public Payment(AClass aClass, float amount, Date date) {
        this.aClass = aClass;
        this.amount = amount;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AClass getAClass() {
        return aClass;
    }

    public void setAClass(AClass aClass) {
        this.aClass = aClass;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
