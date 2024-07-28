package Controller;

import DAO.QuizDAO;
import Model.Question;
import Model.User;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/SubmitQuizServlet"})
public class SubmitQuizServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        String lessonIdStr = (String) session.getAttribute("lessonId");
        int lessonId = Integer.parseInt(lessonIdStr);
        QuizDAO assignmentDAO = new QuizDAO();
        Long assignmentId = assignmentDAO.getQuizIdByLessonId(lessonId);
        int correctAnswers = 0;

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswerOption = request.getParameter("answer" + (i + 1));
            String userAnswerContent = null;

            // Determine the actual answer content based on the selected option
            if (userAnswerOption != null) {
                switch (userAnswerOption) {
                    case "A":
                        userAnswerContent = question.getAnswerA();
                        break;
                    case "B":
                        userAnswerContent = question.getAnswerB();
                        break;
                    case "C":
                        userAnswerContent = question.getAnswerC();
                        break;
                    case "D":
                        userAnswerContent = question.getAnswerD();
                        break;
                }
            }

            if (userAnswerContent != null && userAnswerContent.equals(question.getCorrectAnswer())) {
                correctAnswers++;
            }
            session.setAttribute("answer" + (i + 1), userAnswerContent);
            assignmentDAO.updateChosenAnswer(assignmentId, i + 1, userAnswerContent);
        }

        double score = (double) correctAnswers / questions.size() * 10;
        DecimalFormat df = new DecimalFormat("#.##");
        session.setAttribute("score", df.format(score));
        session.setAttribute("correctAnswers", correctAnswers);

        long startTime = (long) session.getAttribute("startTime");
        long currentTime = System.currentTimeMillis();
        long timeTakenMillis = currentTime - startTime;
        int timeTakenSeconds = (int) (timeTakenMillis / 1000);
        int minutesTaken = timeTakenSeconds / 60;
        int secondsTaken = timeTakenSeconds % 60;
        String timeTakenFormatted = String.format("%02d:%02d", minutesTaken, secondsTaken);
        Date startedOn = new Date(startTime);
        Date completedOn = new Date(currentTime);
        session.setAttribute("startedOn", startedOn);
        session.setAttribute("completedOn", completedOn);
        session.setAttribute("timeTaken", timeTakenFormatted);

        session.removeAttribute("timeLeft");
        if (assignmentId != null) {
            int numberOfTimeDone = assignmentDAO.getNumberOfTimeDoneByQuizId(assignmentId);
            String formattedScoreStr = String.format("%.1f", score);
            float formattedScore = Float.parseFloat(formattedScoreStr);
            numberOfTimeDone++;
            assignmentDAO.updateQuizDetails(assignmentId, formattedScore, "done", numberOfTimeDone, startedOn, completedOn, timeTakenSeconds);
        }

        request.getRequestDispatcher("View/ReviewQuiz.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
