package DAO;

import Model.Question;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAO.class.getName());

    public List<Question> getQuestionsByClassIdAndLessonId(int classId, int lessonId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Question q JOIN Quiz z ON q.quizId = z.id WHERE z.lessonId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);

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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving questions", e);
        }

        return questions;
    }
}
