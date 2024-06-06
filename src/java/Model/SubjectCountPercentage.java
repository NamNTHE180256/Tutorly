/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author TRANG
 */
public class SubjectCountPercentage {
    private int learnerId;
    private String subjectName;
    private int subjectClassCount;
    private double averagePercentage;

    // Constructors
    public SubjectCountPercentage() {}

    public SubjectCountPercentage(int learnerId, String subjectName, int subjectClassCount, double averagePercentage) {
        this.learnerId = learnerId;
        this.subjectName = subjectName;
        this.subjectClassCount = subjectClassCount;
        this.averagePercentage = averagePercentage;
    }

    // Getters and setters
    public int getLearnerId() {
        return learnerId;
    }

    public void setLearnerId(int learnerId) {
        this.learnerId = learnerId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectClassCount() {
        return subjectClassCount;
    }

    public void setSubjectClassCount(int subjectClassCount) {
        this.subjectClassCount = subjectClassCount;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    @Override
    public String toString() {
        return "ClassStatistics{" +
                "learnerId=" + learnerId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectClassCount=" + subjectClassCount +
                ", averagePercentage=" + averagePercentage +
                '}';
    }
}
