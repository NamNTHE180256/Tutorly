/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.SubjectCountPercentageDAO;
import Model.Learner;
import Model.SubjectCountPercentage;
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
@WebServlet(name = "StudentProfileController", urlPatterns = {"/StudentProfileController"})
public class StudentProfileController extends HttpServlet {

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
            request.setAttribute("errorMessage", "you dont have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        if (user.getRole().equalsIgnoreCase("learner")) {
            response.setContentType("text/html;charset=UTF-8");
            //String id_student = request.getParameter("id");
            String service = request.getParameter("service");
            LearnerDAO lDAO = new LearnerDAO();
            Learner linfo = lDAO.getStudentById(1);
            SubjectCountPercentageDAO scpDAO = new SubjectCountPercentageDAO();
            Vector<SubjectCountPercentage> scp_vector = scpDAO.getSubjectCountPercentage(1);
            if (service == null || service.isEmpty()) {
                request.setAttribute("linfo", linfo);
                request.setAttribute("scp_vector", scp_vector);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/LearnerProfile.jsp");
                dispatcher.forward(request, response);
            } else if (service.equals("updateRequest")) {
                request.setAttribute("linfo", linfo);
                request.setAttribute("scp_vector", scp_vector);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/UpdateLearner.jsp");
                dispatcher.forward(request, response);
            } else if (service.equals("update")) {
                String newName = request.getParameter("name");
                if (newName != null && !newName.isEmpty()) {
                    linfo.setName(newName);
                    lDAO.updateStudent(linfo);

                }
                request.setAttribute("linfo", linfo);
                request.setAttribute("scp_vector", scp_vector);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/LearnerProfile.jsp");
                dispatcher.forward(request, response);
            }
            // Forward to JSP

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
