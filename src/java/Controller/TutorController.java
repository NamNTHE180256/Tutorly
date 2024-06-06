/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.SubjectDAO;
import DAO.TutorDAO;
import Model.Subject;
import Model.Tutor;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
@WebServlet(name="TutorController", urlPatterns={"/TutorController"})
public class TutorController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        TutorDAO tDAO = new TutorDAO();
        SubjectDAO sDAO = new SubjectDAO();
        Vector<Tutor> tutor_vector = tDAO.displayAllTutors(); // Method to get all tutors
        Map<Integer, Map<String, Object>> tutorRatings = tDAO.getAllTutorRatings(); // Method to get all ratings
        Vector<Subject> subject_vector = sDAO.getSubjects("SELECT * FROM Subject"); // Method to get all subjects

        // Check for subject filter
        String subject_id = request.getParameter("id");
        if (subject_id != null && !subject_id.isEmpty()) {
            tutor_vector = tDAO.getTutors("SELECT * FROM Tutor WHERE subjectId = " + subject_id);
        }
        String price_raw = request.getParameter("price");
        if (price_raw != null && !price_raw.isEmpty()){
            int price = Integer.parseInt(price_raw);
            switch (price) {
                case 1:
                    tutor_vector = tDAO.getTutors("SELECT * FROM Tutor WHERE price < 200");
                    break;
                case 2:
                    tutor_vector = tDAO.getTutors("SELECT * FROM Tutor WHERE price BETWEEN 200 AND 400");
                    break;
                case 3:
                    tutor_vector = tDAO.getTutors("SELECT * FROM Tutor WHERE price > 400");
                    break;
            }
            
        }
        
        String session_raw = request.getParameter("session");
        if (session_raw != null && !session_raw.isEmpty()){
            int session = Integer.parseInt(session_raw);
            switch (session) {
                case 1:
                    tutor_vector = tDAO.getTutors("SELECT DISTINCT t.*\n"
                            + "FROM \n"
                            + "    Tutor t\n"
                            + "JOIN \n"
                            + "    TutorAvailability ta ON ta.tutorId = t.id\n"
                            + "WHERE \n"
                            + "    ta.sessionId LIKE '%1%' OR ta.sessionId LIKE '%2%';");
                    break;
                case 2:
                    tutor_vector = tDAO.getTutors("SELECT DISTINCT t.*\n"
                            + "FROM \n"
                            + "    Tutor t\n"
                            + "JOIN \n"
                            + "    TutorAvailability ta ON ta.tutorId = t.id\n"
                            + "WHERE \n"
                            + "    ta.sessionId LIKE '%3%' OR ta.sessionId LIKE '%4%';");
                    break;
                case 3:
                    tutor_vector = tDAO.getTutors("SELECT DISTINCT t.*\n"
                            + "FROM \n"
                            + "    Tutor t\n"
                            + "JOIN \n"
                            + "    TutorAvailability ta ON ta.tutorId = t.id\n"
                            + "WHERE \n"
                            + "    ta.sessionId LIKE '%5%';");
                    break;
            }

        }
        
        String star_w = request.getParameter("star");
        if (star_w != null && !star_w.isEmpty()){
            int star = Integer.parseInt(star_w);
            switch (star) {
                case 1:
                    tutor_vector = tDAO.RateTutors(0.1, 1);
                    break;
                case 2:
                    tutor_vector = tDAO.RateTutors(2, 2.9);
                    break;
                case 3:
                     tutor_vector = tDAO.RateTutors(3, 3.9);
                    break;
                case 4:
                     tutor_vector = tDAO.RateTutors(4, 4.9);
                    break;
                case 5:
                     tutor_vector = tDAO.RateTutors(5, 5);
                    break;
            }
        }
        
        String name = request.getParameter("tutorname");
        if(name != null && !name.isEmpty()){
            tutor_vector = tDAO.getTutors("SELECT *\n"
                    + "FROM Tutor\n"
                    + "WHERE name LIKE N'%"+name+"%';");
        }
        // Set attributes
        request.setAttribute("tutor_vector", tutor_vector);
        request.setAttribute("tutorRatings", tutorRatings);
        request.setAttribute("subject_vector", subject_vector);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorList.jsp");
        dispatcher.forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
