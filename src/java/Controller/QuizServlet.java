/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.QuizDAO;
import Model.Question;
import Model.Quiz;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import jakarta.servlet.annotation.MultipartConfig;

/**
 *
 * @author asus
 */
@MultipartConfig
@WebServlet(name = "QuizServlet", urlPatterns = {"/QuizServlet"})
public class QuizServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(QuizServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        String lessonIdStr = request.getParameter("lessonId");
        String classIdStr = request.getParameter("classId");
        String action = request.getParameter("action");

        LOGGER.info("doGet - Lesson ID: " + lessonIdStr);
        if (lessonIdStr == null || lessonIdStr.isEmpty() || classIdStr == null || classIdStr.isEmpty()) {
            throw new ServletException("lessonId or classId parameter is missing");
        }

        int lessonId = Integer.parseInt(lessonIdStr);
        int classId = Integer.parseInt(classIdStr);
        session.setAttribute("lessonId", lessonIdStr); // Store lessonId as string
        session.setAttribute("classId", classIdStr); // Store classId as string

        QuizDAO quizDAO = new QuizDAO();
        Quiz quiz = quizDAO.getQuizByLessonId(lessonId);

        if ("createNew".equals(action) && "tutor".equalsIgnoreCase(role)) {
            // Delete existing quiz
            quizDAO.deleteQuizByLessonId(lessonId);
            // Reset action parameter
            request.removeAttribute("action");
            // Redirect to UploadQuiz.jsp for creating a new quiz
            response.sendRedirect("View/UploadQuiz.jsp?lessonId=" + lessonIdStr + "&classId=" + classIdStr);
        } else if (quiz != null) {
            if ("learner".equalsIgnoreCase(role)) {
                // Learner views quiz
                List<Question> questions = quizDAO.getQuestionsByLessonId(lessonId);
                session.setAttribute("questions", questions);

                session.setAttribute("quizTime", quiz.getQuizTime()); // Set quizTime in session
                request.setAttribute("lessonId", lessonIdStr);
                request.setAttribute("classId", classIdStr);
                request.getRequestDispatcher("View/DoQuiz.jsp").forward(request, response);
            } else if ("tutor".equalsIgnoreCase(role)) {
                // If quiz exists, show prompt to create new
                request.setAttribute("lessonId", lessonIdStr);
                request.setAttribute("classId", classIdStr);
                request.getRequestDispatcher("View/UploadQuiz.jsp").forward(request, response);
            }
        } else {
            if ("tutor".equalsIgnoreCase(role)) {
                // Tutor views questions to set quiz time
                request.setAttribute("lessonId", lessonIdStr);
                request.setAttribute("classId", classIdStr);
                request.getRequestDispatcher("View/UploadQuiz.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String role = request.getParameter("role");

        if (role == null) {
            role = user.getRole(); // Fallback to user role if not provided
        }

        String lessonIdStr = request.getParameter("lessonId");
        String classIdStr = request.getParameter("classId");
        if (lessonIdStr == null || lessonIdStr.isEmpty() || classIdStr == null || classIdStr.isEmpty()) {
            throw new ServletException("lessonId or classId parameter is missing");
        }

        int lessonId;
        int classId;
        try {
            lessonId = Integer.parseInt(lessonIdStr);
            classId = Integer.parseInt(classIdStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid lessonId or classId parameter", e);
        }

        QuizDAO quizDAO = new QuizDAO();

        if ("tutor".equalsIgnoreCase(role)) {
            Part filePart = request.getPart("file");
            if (filePart == null) {
                throw new ServletException("File part is missing");
            }

            // Process the file and create quiz
            List<Question> questions = processFile(filePart);

            Quiz quiz = new Quiz();
            quiz.setLessonId(lessonId);
            quiz.setFileName(filePart.getSubmittedFileName());
            quiz.setFilePath("path/to/save/" + filePart.getSubmittedFileName());
            quiz.setCreatedAt(new Date());
            quiz.setStatus("Pending");
            quiz.setQuizTime(0); // Initially set to 0, will be updated later
            quiz.setNumberOfTimeDone(0); // Ensure it's initialized to 0

            quizDAO.saveQuizWithQuestions(quiz, questions);

            request.setAttribute("questions", questions);
            request.setAttribute("lessonId", lessonIdStr);
            request.setAttribute("classId", classIdStr);
            request.getRequestDispatcher("View/QuestionList.jsp").forward(request, response);
        } else if ("learner".equalsIgnoreCase(role)) {
            response.sendRedirect("QuizServlet?lessonId=" + lessonId + "&classId=" + classId);
        }
    }

    private List<Question> processFile(Part filePart) throws IOException {
        List<Question> questions = new ArrayList<>();
        InputStream fileContent = filePart.getInputStream();
        XWPFDocument document = new XWPFDocument(fileContent);
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        int questionNumber = 1;
        for (XWPFParagraph para : paragraphs) {
            String line = para.getText();
            LOGGER.info("Processing line: " + line);
            String[] parts = line.split(";");
            LOGGER.info("Parts length: " + parts.length);
            if (parts.length == 6) {
                Question question = new Question(
                        questionNumber++,
                        parts[0].trim(),
                        parts[1].trim().substring(2),
                        parts[2].trim().substring(2),
                        parts[3].trim().substring(2),
                        parts[4].trim().substring(2),
                        parts[5].trim().substring(2)
                );
                questions.add(question);
                LOGGER.info("Question added: " + question.getQuestionText());
            } else {
                LOGGER.warning("Invalid line format: " + line);
            }
        }
        LOGGER.info("Total questions processed: " + questions.size());
        return questions;
    }

    @Override
    public String getServletInfo() {
        return "QuizServlet handles quiz-related tasks.";
    }

}
