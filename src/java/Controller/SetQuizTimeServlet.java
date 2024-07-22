package Controller;

import DAO.QuizDAO;
import DAO.QuestionDAO;
import Model.Question;
import Model.Quiz;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

@WebServlet(name = "SetQuizTimeServlet", urlPatterns = {"/SetQuizTimeServlet"})
public class SetQuizTimeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String lessonIdStr = request.getParameter("lessonId");
        String quizTimeStr = request.getParameter("quizTime");
        String classIdStr = request.getParameter("classId");

        if (lessonIdStr == null || lessonIdStr.isEmpty() || quizTimeStr == null || quizTimeStr.isEmpty() || classIdStr == null || classIdStr.isEmpty()) {
            throw new ServletException("lessonId, quizTime, or classId parameter is missing");
        }

        int lessonId;
        int quizTime;
        int classId;
        try {
            lessonId = Integer.parseInt(lessonIdStr);
            quizTime = Integer.parseInt(quizTimeStr);
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid lessonId, classId, or quizTime parameter", e);
        }

        QuizDAO quizDAO = new QuizDAO();
        Quiz quiz = quizDAO.getQuizByLessonId(lessonId);

        if (quiz == null) {
            // Create a new quiz if it does not exist
            quiz = new Quiz();
            quiz.setLessonId(lessonId);
            quiz.setFileName("defaultFileName"); // You may want to change this
            quiz.setFilePath("defaultFilePath"); // You may want to change this
            quiz.setCreatedAt(new Date());
            quiz.setStatus("Pending");
            quiz.setQuizTime(quizTime);
            quiz.setNumberOfTimeDone(0);

            quizDAO.saveQuizWithQuestions(quiz, new ArrayList<>()); // Assuming no initial questions

            // Retrieve questions and save them to the new quiz
            QuestionDAO questionDAO = new QuestionDAO();
            List<Question> questions = questionDAO.getQuestionsByClassIdAndLessonId(classId, lessonId);
            for (Question question : questions) {
                question.setQuizId(quiz.getId());
                quizDAO.saveQuestion(question);
            }
        } else {
            // Update the quiz time if it already exists
            quizDAO.updateQuizTime(lessonId, quizTime);
        }

        response.sendRedirect("Tutorly/lessonDetailControllers?classid=" + classId + "&lessonId=" + lessonId);
    }
}
