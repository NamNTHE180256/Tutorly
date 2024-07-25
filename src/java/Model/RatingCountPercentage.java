/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TRANG
 */
public class RatingCountPercentage {

    private int rate;
    private int count;
    private double percentage;

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public RatingCountPercentage() {
    }

    public RatingCountPercentage(int rate, int count, double percentage) {
        this.rate = rate;
        this.count = count;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "RatingCountPercentage{" + "rate=" + rate + ", count=" + count + ", percentage=" + percentage + '}';
    }

}
