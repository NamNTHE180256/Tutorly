/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Acer
 */
public class Income {
  private int id;
  private float tax;
  private float amount;
  private Date createdAt;
  private Date DayPaid;
  private String status;
  private float Total;
  private Tutor tutor;
  
 public Income() {
   
    }

    public Income(int id, float tax, float amount, Date createdAt, Date DayPaid, String status, float Total, Tutor tutor) {
        this.id = id;
        this.tax = tax;
        this.amount = amount;
        this.createdAt = createdAt;
        this.DayPaid = DayPaid;
        this.status = status;
        this.Total = Total;
        this.tutor = tutor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDayPaid() {
        return DayPaid;
    }

    public void setDayPaid(Date DayPaid) {
        this.DayPaid = DayPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

  

    @Override
    public String toString() {
        return "Income{" + "id=" + id + ", tax=" + tax + ", amount=" + amount + ", createdAt=" + createdAt + ", status=" + status + ", tutor=" + tutor + '}';
    }
  
}
