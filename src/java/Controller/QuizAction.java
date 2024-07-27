/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.QuestionDAO;
import DAO.QuizDAO;
import Model.Question;
import Model.Quiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
@WebServlet(name = "QuizAction", urlPatterns = {"/QuizAction"})
public class QuizAction extends HttpServlet {

    private final QuizDAO quizDAO = new QuizDAO();
    private final QuestionDAO questionDAO = new QuestionDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        switch (action) {
            case "getQuestions" -> {
                int lessonId = Integer.parseInt(request.getParameter("lessonId"));
                int classId = Integer.parseInt(request.getParameter("classId"));

                Quiz quiz = quizDAO.getQuizByLessonId(lessonId);
                List<Question> questions = questionDAO.getQuestionsByClassIdAndLessonId(classId, lessonId);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                PrintWriter out = response.getWriter();
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(new QuizResponse(questions, quiz.getQuizTime()));
                out.print(jsonResponse);
                out.flush();
            }
            case "saveQuiz" -> {
                try {
                    int quizTime = Integer.parseInt(request.getParameter("quizTime"));
                    String questionsJson = request.getParameter("questions");

                    Gson gson = new Gson();
                    Type questionListType = new TypeToken<List<Question>>() {
                    }.getType();
                    List<Question> questions = gson.fromJson(questionsJson, questionListType);

                    try (Connection connection = quizDAO.getConnection()) {
                        connection.setAutoCommit(false);

                        long quizId = questions.isEmpty() ? 0 : questions.get(0).getQuizId();

                        quizDAO.updateQuizTime(quizId, quizTime);
                        questionDAO.deleteQuestionsByQuizId(quizId);

                        for (Question question : questions) {
                            questionDAO.insertQuestion(connection, question);
                        }

                        connection.commit();
                        response.setStatus(HttpServletResponse.SC_OK);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            case "delete" -> {
                try {
                    int quizId = Integer.parseInt(request.getParameter("quizId"));
                    questionDAO.deleteQuestionsByQuizId(quizId);
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
            default ->
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private static class QuizResponse {

        private List<Question> questions;
        private int quizTime;

        public QuizResponse(List<Question> questions, int quizTime) {
            this.questions = questions;
            this.quizTime = quizTime;
        }

        public QuizResponse() {

        }

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }

        public int getQuizTime() {
            return quizTime;
        }

        public void setQuizTime(int quizTime) {
            this.quizTime = quizTime;
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
