/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.RatingDAO;
import DAO.TutorDAO;
import Model.Learner;
import Model.Rating;
import Model.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * @author asus
 */
@WebServlet(name = "RatingTutorServlet", urlPatterns = {"/RatingTutorServlet"})
public class RatingTutorServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RatingTutorServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RatingTutorServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        int ratingValue = Integer.parseInt(request.getParameter("rating"));
        String review = request.getParameter("review");
        int learnerId = Integer.parseInt(request.getParameter("learnerId"));
        int tutorId = Integer.parseInt(request.getParameter("tutorId"));
        boolean success = false;
        String message = "";
        try {

            if (ratingValue < 1 || ratingValue > 5 || review == null || review.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid input");
            }

            LearnerDAO learnerDAO = new LearnerDAO();
            TutorDAO tutorDAO = new TutorDAO();
            Learner learner = learnerDAO.getLearnerById(learnerId);
            Tutor tutor = tutorDAO.getTutorById(tutorId);

            Rating rating = new Rating(learner, tutor, ratingValue, review);
            rating.setCreatedAt(new Date());

            RatingDAO ratingDAO = new RatingDAO();
            ratingDAO.addRating(rating);

            success = true;
            message = "Rating successful";

        } catch (Exception e) {
            message = "Error: " + e.getMessage();

        }
        request.setAttribute("success", success);
        request.setAttribute("message", message);
        request.getRequestDispatcher("View/RatingTutor.jsp").forward(request, response);

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
