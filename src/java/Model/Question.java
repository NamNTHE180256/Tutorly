/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Question {

    private long quizId;
    private int number;
    private String questionText;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private String chooseAnswer;

    public Question() {
    }

    public Question(int number, String questionText, String answerA, String answerB, String answerC, String answerD, String correctAnswer) {
        this.number = number;
        this.questionText = questionText;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.chooseAnswer = null; // Default to null
    }

    public Question(long quizId, int number, String questionText, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String chooseAnswer) {
        this.quizId = quizId;
        this.number = number;
        this.questionText = questionText;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.chooseAnswer = chooseAnswer;
    }

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    // Getters and Setters for all fields
    public long getAssignmentId() {
        return quizId;
    }

    public void setAssignmentId(long quizId) {
        this.quizId = quizId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getChooseAnswer() {
        return chooseAnswer;
    }

    public void setChooseAnswer(String chooseAnswer) {
        this.chooseAnswer = chooseAnswer;
    }

    @Override
    public String toString() {
        return "Question{"
                + "quizId=" + quizId
                + ", number=" + number
                + ", questionText='" + questionText + '\''
                + ", answerA='" + answerA + '\''
                + ", answerB='" + answerB + '\''
                + ", answerC='" + answerC + '\''
                + ", answerD='" + answerD + '\''
                + ", correctAnswer='" + correctAnswer + '\''
                + ", chooseAnswer='" + chooseAnswer + '\''
                + '}';
    }
}
