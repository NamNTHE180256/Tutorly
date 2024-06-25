/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.AssignmentDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.TutorDAO;
import Model.AClass;
import Model.Assignment;
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
 *
 * @author asus
 */
@WebServlet(name = "ClassDetail", urlPatterns = {"/ClassDetail"})
public class ClassDetail extends HttpServlet {

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
        HttpSession session1 = request.getSession();
        User user = (User) session1.getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return; // Ensure no further processing happens
        }

        if (user.getRole().equalsIgnoreCase("learner") || user.getRole().equalsIgnoreCase("tutor")) {
            response.setContentType("text/html;charset=UTF-8");

            int classId = Integer.parseInt(request.getParameter("classId"));

            LessonDAO lDAO = new LessonDAO();
            AssignmentDAO aDAO = new AssignmentDAO();
            Vector<Assignment> classAssignmentsToDo = aDAO.getAssignmentsByLearnerIdAndStatusTodo(classId);
            Vector<Lesson> lesson_vector = lDAO.getLessonsByLearnerId(classId);

            LearnerDAO leDAO = new LearnerDAO();
            Learner linfo = leDAO.getStudentById(classId);

            AClassDAO classDAO = new AClassDAO();
            AClass aClass = classDAO.getClassById(classId);

            request.setAttribute("linfo", linfo);
            request.setAttribute("todoassignment", classAssignmentsToDo.size());
            request.setAttribute("lesson_vector", lesson_vector);
            request.setAttribute("aClass", aClass);

            RequestDispatcher dispatcher = request.getRequestDispatcher("View/ClassDetail.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
