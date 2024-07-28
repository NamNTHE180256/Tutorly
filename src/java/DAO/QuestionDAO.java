package DAO;

import Model.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class QuestionDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    public List<Question> getQuestionsByClassIdAndLessonId(int classId, int lessonId) {
        List<Question> questions = new ArrayList<>();
        String sql = """
            SELECT q.* FROM Question q 
            JOIN Quiz z ON q.quizId = z.id 
            JOIN Lesson l ON z.lessonId = l.id
            WHERE l.id = ? AND l.classId = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, classId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("questionNumber"),
                            rs.getString("questionText"),
                            rs.getString("answerA"),
                            rs.getString("answerB"),
                            rs.getString("answerC"),
                            rs.getString("answerD"),
                            rs.getString("correctAnswer")
                    );
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving questions", e);
        }

        return questions;
    }

    public void deleteQuestionsByQuizId(long quizId) throws SQLException {
        String sql = "DELETE FROM Question WHERE quizId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, quizId);
            pstmt.executeUpdate();
        }
    }

    public void insertQuestion(Connection connection, Question question) throws SQLException {
        String sql = "INSERT INTO Question (quizId, questionNumber, questionText, answerA, answerB, answerC, answerD, correctAnswer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, question.getQuizId());
            pstmt.setInt(2, question.getNumber());
            pstmt.setString(3, question.getQuestionText());
            pstmt.setString(4, question.getAnswerA());
            pstmt.setString(5, question.getAnswerB());
            pstmt.setString(6, question.getAnswerC());
            pstmt.setString(7, question.getAnswerD());
            pstmt.setString(8, question.getCorrectAnswer());
            pstmt.executeUpdate();
        }
    }

    public void deleteQuestionsByLessonId(int lessonId) throws SQLException {
        String sql = """
            DELETE q FROM Question q
            JOIN Quiz z ON q.quizId = z.id
            JOIN Lesson l ON z.lessonId = l.id
            WHERE l.id = ?
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, lessonId);
            pstmt.executeUpdate();
        }
    }
    public static void main(String[] args) {
        QuestionDAO qDAO = new QuestionDAO();
        List<Question> l = qDAO.getQuestionsByClassIdAndLessonId(4, 28);
        for ( Question q : l){
            System.out.println(q.toString());
        }
    }
}
