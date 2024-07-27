package DAO;

import Model.Question;
import Model.Quiz;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizDAO extends DBContext {

    private static final Logger LOGGER = Logger.getLogger(QuizDAO.class.getName());

    public List<Quiz> displayAllQuiz() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public void saveQuestion(Question question) {
        String sql = "INSERT INTO Question (quizId, questionNumber, questionText, answerA, answerB, answerC, answerD, correctAnswer, chooseAnswer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, question.getQuizId());
            pstmt.setInt(2, question.getNumber());
            pstmt.setString(3, question.getQuestionText());
            pstmt.setString(4, question.getAnswerA());
            pstmt.setString(5, question.getAnswerB());
            pstmt.setString(6, question.getAnswerC());
            pstmt.setString(7, question.getAnswerD());
            pstmt.setString(8, question.getCorrectAnswer());
            pstmt.setString(9, question.getChooseAnswer());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to save question", ex);
        }
    }

    public Long getQuizIdByLessonId(int lessonId) {
        Long quizId = null;
        String sql = "SELECT id FROM Quiz WHERE LessonId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                quizId = rs.getLong("id");
                LOGGER.info("Found quizId: " + quizId + " for lessonId: " + lessonId);
            } else {
                LOGGER.warning("No quiz found for lessonId: " + lessonId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve quiz ID", ex);
        }
        return quizId;
    }

    public Quiz getQuizByLessonId(int lessonId) {
        Quiz quiz = null;
        String sql = "SELECT * FROM Quiz WHERE LessonId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                quiz = new Quiz(
                        rs.getLong("id"),
                        rs.getInt("LessonId"),
                        rs.getString("fileName"),
                        rs.getString("filePath"),
                        rs.getDate("createdAt"),
                        rs.getString("status"),
                        rs.getInt("timeOfQuiz"),
                        rs.getTimestamp("startedOn"),
                        rs.getTimestamp("completedOn"),
                        rs.getInt("timeTaken"),
                        rs.getInt("numberOfTimeDone")
                );
                LOGGER.info("Found quiz: " + quiz + " for lessonId: " + lessonId);
            } else {
                LOGGER.warning("No quiz found for lessonId: " + lessonId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve quiz", ex);
        }
        return quiz;
    }

    public void deleteQuizByLessonId(int lessonId) {
        String sql = "DELETE FROM Quiz WHERE LessonId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("Deleted quiz for lessonId: " + lessonId);
            } else {
                LOGGER.warning("No quiz found to delete for lessonId: " + lessonId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to delete quiz", ex);
        }
    }

    public void saveQuizWithQuestions(Quiz quiz, List<Question> questions) {
        try {
            connection.setAutoCommit(false);

            String sqlQuiz = "INSERT INTO Quiz (LessonId, fileName, filePath, createdAt, status, timeOfQuiz) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmtQuiz = connection.prepareStatement(sqlQuiz, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtQuiz.setInt(1, quiz.getLessonId());
            pstmtQuiz.setString(2, quiz.getFileName());
            pstmtQuiz.setString(3, quiz.getFilePath());
            pstmtQuiz.setDate(4, new java.sql.Date(quiz.getCreatedAt().getTime()));
            pstmtQuiz.setString(5, quiz.getStatus());
            pstmtQuiz.setInt(6, quiz.getQuizTime());

            int affectedRows = pstmtQuiz.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating quiz failed, no rows affected.");
            }

            ResultSet rs = pstmtQuiz.getGeneratedKeys();
            if (rs.next()) {
                long quizId = rs.getLong(1);
                LOGGER.info("Quiz ID: " + quizId);

                String sqlQuestion = "INSERT INTO Question (quizId, questionNumber, questionText, answerA, answerB, answerC, answerD, correctAnswer, chooseAnswer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmtQuestion = connection.prepareStatement(sqlQuestion);
                int questionNumber = 1;
                for (Question question : questions) {
                    pstmtQuestion.setLong(1, quizId);
                    pstmtQuestion.setInt(2, questionNumber++);
                    pstmtQuestion.setString(3, question.getQuestionText());
                    pstmtQuestion.setString(4, question.getAnswerA());
                    pstmtQuestion.setString(5, question.getAnswerB());
                    pstmtQuestion.setString(6, question.getAnswerC());
                    pstmtQuestion.setString(7, question.getAnswerD());
                    pstmtQuestion.setString(8, question.getCorrectAnswer());
                    pstmtQuestion.setString(9, question.getChooseAnswer());
                    pstmtQuestion.addBatch();
                }
                pstmtQuestion.executeBatch();
            } else {
                throw new SQLException("Creating quiz failed, no ID obtained.");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
                LOGGER.severe("Transaction rolled back due to: " + ex.getMessage());
            } catch (SQLException e) {
                LOGGER.severe("Failed to rollback transaction: " + e.getMessage());
            }
            LOGGER.severe("SQL Exception: " + ex.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.severe("Failed to reset auto-commit: " + e.getMessage());
            }
        }
    }

    public void updateQuizTime(long quizId, int quizTime) throws SQLException {
        String sql = "UPDATE Quiz SET timeOfQuiz = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quizTime);
            pstmt.setLong(2, quizId);
            pstmt.executeUpdate();
        }
    }

    public void updateQuizScoreAndStatus(Long quizId, double score, String status) {
        String sql = "UPDATE Quiz SET score = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, score);
            pstmt.setString(2, status);
            pstmt.setLong(3, quizId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("Quiz ID " + quizId + " updated with score " + score + " and status " + status);
            } else {
                LOGGER.warning("No rows affected while updating quiz ID " + quizId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update quiz score and status", ex);
        }
    }

    public List<Question> getQuestionsByLessonId(int lessonId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT q.* FROM Question q INNER JOIN Quiz a ON q.quizId = a.id WHERE a.LessonId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, lessonId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Question question = new Question(
                        rs.getLong("quizId"),
                        rs.getInt("questionNumber"),
                        rs.getString("questionText"),
                        rs.getString("answerA"),
                        rs.getString("answerB"),
                        rs.getString("answerC"),
                        rs.getString("answerD"),
                        rs.getString("correctAnswer"),
                        rs.getString("chooseAnswer")
                );
                questions.add(question);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve questions by lesson ID", ex);
        }
        return questions;
    }

    public void updateQuizCompletionDetails(long quizId, Date startedOn, Date completedOn, String timeTaken, double score) {
        String sql = "UPDATE Quiz SET startedOn = ?, completedOn = ?, timeTaken = ?, score = ?, numberOfTimeDone = numberOfTimeDone + 1 WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setTimestamp(1, new java.sql.Timestamp(startedOn.getTime()));
            pstmt.setTimestamp(2, new java.sql.Timestamp(completedOn.getTime()));
            pstmt.setString(3, timeTaken);
            pstmt.setDouble(4, score);
            pstmt.setLong(5, quizId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update quiz completion details", ex);
        }
    }

    public Quiz getQuizById(long id) {
        String sql = "SELECT * FROM Quiz WHERE id = ?";
        Quiz quiz = null;
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setLong(1, id);
            ResultSet rs = state.executeQuery();
            if (rs.next()) {
                int lessonId = rs.getInt("lessonId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quiz;
    }

    public int removeQuiz(long quizId) {
        int n = 0;
        String sql = "DELETE FROM Quiz WHERE id = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setLong(1, quizId);
            n = state.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void updateQuizTime(int lessonId, int quizTime) {
        String sql = "UPDATE Quiz SET timeOfQuiz = ? WHERE LessonId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, quizTime);
            pstmt.setInt(2, lessonId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("Quiz time updated for lessonId " + lessonId);
            } else {
                LOGGER.warning("No quiz found to update for lessonId: " + lessonId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update quiz time", ex);
        }
    }

    public List<Quiz> getQuizByClassId(int classId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT a.* FROM Quiz a JOIN Lessons l ON a.lessonId = l.id WHERE l.classId = ?";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public List<Quiz> getDoneQuizByClassId(int classId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT a.* FROM Quiz a JOIN Lessons l ON a.lessonId = l.id WHERE l.classId = ? and a.status = 'Done'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public void updateQuizStatus(int lessonId, String status) {
        String query = "UPDATE quizs SET status = ? WHERE lesson_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, lessonId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Quiz> getToDoQuizByClassId(int classId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT a.* FROM Quiz a JOIN Lessons l ON a.lessonId = l.id WHERE l.classId = ? and a.status = 'Todo'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, classId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public Vector<Quiz> getQuizByLearnerIdAndStatusDone(int learnerId) {
        Vector<Quiz> quizzes = new Vector<>();
        String sql = "SELECT a.* FROM Quiz a "
                + "JOIN lesson l ON a.lessonId = l.id "
                + "JOIN Class c ON l.classId = c.id "
                + "WHERE c.learnerId = ? AND a.status = 'Done'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                double score = rs.getDouble("score");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, score, status,createdAt);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public Vector<Quiz> getQuizByLearnerIdAndStatusTodo(int learnerId) {
        Vector<Quiz> quizzes = new Vector<>();
        String sql = "SELECT a.* FROM Quiz a "
                + "JOIN Lesson l ON a.lessonId = l.id "
                + "JOIN Class c ON l.classId = c.id "
                + "WHERE c.learnerId = ? AND a.status = 'Todo'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, learnerId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                int lessonId = rs.getInt("lessonId");
                double score = rs.getDouble("score");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, score, status,createdAt);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public Vector<Quiz> getTodoQuizByLessonId(int lessonId) {
        Vector<Quiz> quizzes = new Vector<>();
        String sql = "SELECT * FROM Quiz WHERE lessonId = ? AND status = 'Todo'";
        try {
            PreparedStatement state = connection.prepareStatement(sql);
            state.setInt(1, lessonId);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                double score = rs.getDouble("score");
                String fileName = rs.getString("fileName");
                String filePath = rs.getString("filePath");
                Date createdAt = rs.getDate("createdAt");
                String status = rs.getString("status");

                Quiz quiz = new Quiz(id, lessonId, fileName, filePath, createdAt, status);
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quizzes;
    }

    public void updateChosenAnswer(long quizId, int questionNumber, String chosenAnswer) {
        String sql = "UPDATE Question SET chooseAnswer = ? WHERE quizId = ? AND questionNumber = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, chosenAnswer);
            pstmt.setLong(2, quizId);
            pstmt.setInt(3, questionNumber);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update chosen answer", ex);
        }
    }

    public int getNumberOfTimeDoneByQuizId(Long quizId) {
        int numberOfTimeDone = 0;
        String sql = "SELECT numberOfTimeDone FROM Quiz WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, quizId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                numberOfTimeDone = rs.getInt("numberOfTimeDone");
                LOGGER.info("Found numberOfTimeDone: " + numberOfTimeDone + " for quizId: " + quizId);
            } else {
                LOGGER.warning("No quiz found for quizId: " + quizId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve numberOfTimeDone", ex);
        }
        return numberOfTimeDone;
    }

    public void updateQuizScoreAndStatus(Long quizId, double score, String status, int numberOfTimeDone) {
        String sql = "UPDATE Quiz SET score = ?, status = ?, numberOfTimeDone = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, score);
            pstmt.setString(2, status);
            pstmt.setInt(3, numberOfTimeDone);
            pstmt.setLong(4, quizId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("Quiz ID " + quizId + " updated with score " + score + ", status " + status + ", and numberOfTimeDone " + numberOfTimeDone);
            } else {
                LOGGER.warning("No rows affected while updating quiz ID " + quizId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update quiz score and status", ex);
        }
    }

    public void updateQuizDetails(Long quizId, double score, String status, int numberOfTimeDone, Date startedOn, Date completedOn, int timeTaken) {
        String sql = "UPDATE Quiz SET score = ?, status = ?, numberOfTimeDone = ?, startedOn = ?, completedOn = ?, timeTaken = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, score);
            pstmt.setString(2, status);
            pstmt.setInt(3, numberOfTimeDone);
            pstmt.setTimestamp(4, new java.sql.Timestamp(startedOn.getTime()));
            pstmt.setTimestamp(5, new java.sql.Timestamp(completedOn.getTime()));
            pstmt.setInt(6, timeTaken);
            pstmt.setLong(7, quizId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                LOGGER.info("Quiz ID " + quizId + " updated with score " + score + ", status " + status + ", numberOfTimeDone " + numberOfTimeDone + ", startedOn " + startedOn + ", completedOn " + completedOn + ", and timeTaken " + timeTaken);
            } else {
                LOGGER.warning("No rows affected while updating quiz ID " + quizId);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Failed to update quiz details", ex);
        }
    }
    public static void main(String[] args) {
        QuizDAO aDAO = new QuizDAO();
        Vector<Quiz> classQuizDone = aDAO.getQuizByLearnerIdAndStatusDone(1);
        System.out.println(classQuizDone);
    }
}
