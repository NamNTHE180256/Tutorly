/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AssignmentDAO;
import DAO.LearnerDAO;
import Model.Assignment;
import Model.Learner;
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
 * @author TRANG
 */
@WebServlet(name = "AssignmentController", urlPatterns = {"/AssignmentController"})
public class AssignmentController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            request.setAttribute("errorMessage", "you dont have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        if (user.getRole().equalsIgnoreCase("learner")) {
            response.setContentType("text/html;charset=UTF-8");
            String service = request.getParameter("service");
            AssignmentDAO aDAO = new AssignmentDAO();
            LearnerDAO lDAO = new LearnerDAO();
            Learner linfo = lDAO.getStudentById(1);
            if (service == null || service.isEmpty()) {
                Vector<Assignment> classAssignmentsToDo = aDAO.getAssignmentsByLearnerIdAndStatusTodo(1);
                Vector<Assignment> classAssignmentsDone = aDAO.getAssignmentsByLearnerIdAndStatusDone(1);
                request.setAttribute("classAssignments", classAssignmentsToDo);
                request.setAttribute("todoassignment", classAssignmentsToDo.size());
                request.setAttribute("linfo", linfo);

                //request.setAttribute("classAssignmentsDone", classAssignmentsDone);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/Assigment.jsp");
                dispatcher.forward(request, response);
            } else if (service.equals("done")) {
                Vector<Assignment> classAssignmentsToDo = aDAO.getAssignmentsByLearnerIdAndStatusTodo(1);
                Vector<Assignment> classAssignmentsDone = aDAO.getAssignmentsByLearnerIdAndStatusDone(1);
                request.setAttribute("classAssignments", classAssignmentsDone);
                request.setAttribute("todoassignment", classAssignmentsToDo.size());
                request.setAttribute("linfo", linfo);
                request.setAttribute("classAssignmentsDone", classAssignmentsDone);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/Assigment.jsp");
                dispatcher.forward(request, response);
            } else {
                Vector<Assignment> classAssignmentsToDo = aDAO.getAssignmentsByLearnerIdAndStatusTodo(1);
                Vector<Assignment> classAssignmentsDone = aDAO.getAssignmentsByLearnerIdAndStatusDone(1);
                request.setAttribute("classAssignments", classAssignmentsToDo);
                request.setAttribute("todoassignment", classAssignmentsToDo.size());
                request.setAttribute("linfo", linfo);
                //request.setAttribute("classAssignmentsDone", classAssignmentsDone);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/Assigment.jsp");
                dispatcher.forward(request, response);
            }

        } else {
            request.setAttribute("errorMessage", "you dont have permission to access this page");
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
