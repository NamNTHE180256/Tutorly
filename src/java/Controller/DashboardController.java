/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.QuizDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import Model.Quiz;
import Model.Learner;
import Model.Lesson;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 * learner
 *
 * @author TRANG
 */
@WebServlet(name = "DashboardController", urlPatterns = {"/DashboardController"})
public class DashboardController extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        LessonDAO lDAO = new LessonDAO();
        QuizDAO aDAO = new QuizDAO();
        String type = request.getParameter("type");

        if ("learner".equalsIgnoreCase(type)) {
            try {
                int learnerid = Integer.parseInt(request.getParameter("learnerid"));
                Vector<Quiz> classQuizToDo = aDAO.getQuizByLearnerIdAndStatusTodo(learnerid);
                Vector<Lesson> lesson_vector = lDAO.getLessonsByLearnerId(learnerid);
                LearnerDAO leDAO = new LearnerDAO();
                Learner linfo = leDAO.getStudentById(learnerid);

                request.setAttribute("linfo", linfo);
                request.setAttribute("todoQuiz", classQuizToDo.size());
                request.setAttribute("lesson_vector", lesson_vector);

                request.getRequestDispatcher("View/Dashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
            }
        } else if ("tutor".equalsIgnoreCase(type)) {
            try {
                int tutorid = Integer.parseInt(request.getParameter("tutorid"));
                Vector<Lesson> lesson_vector = lDAO.getLessonsByTutorId(tutorid);

                request.setAttribute("lesson_vector", lesson_vector);
                request.getRequestDispatcher("View/Dashboard.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
