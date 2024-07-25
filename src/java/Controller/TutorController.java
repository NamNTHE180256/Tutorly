/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.SaveTutorListDAO;
import DAO.SubjectDAO;
import DAO.TutorAvailabilityDAO;
import DAO.TutorDAO;
import Model.Learner;
import Model.SaveTutorList;
import Model.Subject;
import Model.Tutor;
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
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
@WebServlet(name = "TutorController", urlPatterns = {"/TutorController"})
public class TutorController extends HttpServlet {

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
        HttpSession session1 = request.getSession();
        User user = (User) session1.getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "you dont have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        if (user.getRole().equalsIgnoreCase("learner")) {
            SaveTutorListDAO tDAO = new SaveTutorListDAO();
            SubjectDAO sDAO = new SubjectDAO();
            TutorAvailabilityDAO taDao = new TutorAvailabilityDAO();

            Vector<SaveTutorList> tutor_vector = tDAO.getTop10TutorsByRating();
            Map<Integer, Map<String, Object>> tutorRatings = tDAO.getAllTutorRatings();
            Vector<Subject> subject_vector = sDAO.getSubjects("SELECT * FROM Subject");
            //Vector<TutorAvailability> tutortvailability_vector = taDao.getAvailableSessions();


    String service = request.getParameter("service");
    if (service != null && !service.isEmpty()) {
        // Check for subject filter
        String subject_id = request.getParameter("id");
        if (subject_id != null && !subject_id.isEmpty()) {
            tutor_vector = tDAO.getTutors("SELECT t.*, "
                    + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                    + "FROM Tutor t "
                    + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                    + "WHERE t.subjectId = " + subject_id);
        }

        // Check for price filter
        String price_raw = request.getParameter("price");
        if (price_raw != null && !price_raw.isEmpty()) {
            try {
                int price = Integer.parseInt(price_raw);
                String priceFilter = "";
                switch (price) {
                    case 1:
                        priceFilter = "t.price < 200000";
                        break;
                    case 2:
                        priceFilter = "t.price BETWEEN 200000 AND 400000";
                        break;
                    case 3:
                        priceFilter = "t.price > 400000";
                        break;
                }
                tutor_vector = tDAO.getTutors("SELECT t.*, "
                        + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                        + "FROM Tutor t "
                        + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                        + "WHERE " + priceFilter);
            } catch (NumberFormatException e) {
                // Handle error gracefully
            }
        }

                // Check for session filter
                String session_raw = request.getParameter("session");
                if (session_raw != null && !session_raw.isEmpty()) {
                    try {
                        int session = Integer.parseInt(session_raw);
                        String sql = "";
                        switch (session) {
                            case 1:
                                sql = "SELECT DISTINCT t.id, t.subjectId, t.name, t.gender, t.image, "
                                        + "CAST(t.bio AS nvarchar(max)) AS bio, t.edu, t.price, t.bank, t.status, "
                                        + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                                        + "FROM Tutor t "
                                        + "JOIN TutorAvailability ta ON ta.tutorId = t.id "
                                        + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                                        + "WHERE ta.sessionId LIKE '%1%' OR ta.sessionId LIKE '%2%'";
                                break;
                            case 2:
                                sql = "SELECT DISTINCT t.id, t.subjectId, t.name, t.gender, t.image, "
                                        + "CAST(t.bio AS nvarchar(max)) AS bio, t.edu, t.price, t.bank, t.status, "
                                        + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                                        + "FROM Tutor t "
                                        + "JOIN TutorAvailability ta ON ta.tutorId = t.id "
                                        + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                                        + "WHERE ta.sessionId LIKE '%3%' OR ta.sessionId LIKE '%4%'";
                                break;
                            case 3:
                                sql = "SELECT DISTINCT t.id, t.subjectId, t.name, t.gender, t.image, "
                                        + "CAST(t.bio AS nvarchar(max)) AS bio, t.edu, t.price, t.bank, t.status, "
                                        + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                                        + "FROM Tutor t "
                                        + "JOIN TutorAvailability ta ON ta.tutorId = t.id "
                                        + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                                        + "WHERE ta.sessionId LIKE '%5%'";
                                break;
                        }
                        if (!sql.isEmpty()) {
                            tutor_vector = tDAO.getTutors(sql);
                        }
                    } catch (NumberFormatException e) {
                        // Handle error gracefully
                    }
                }

                // Check for star rating filter
                String star_w = request.getParameter("star");
                if (star_w != null && !star_w.isEmpty()) {
                    try {
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
                    } catch (NumberFormatException e) {
                        // Handle error gracefully
                    }
                }

                // Check for name filter
                String name = request.getParameter("tutorname");
                if (name != null && !name.isEmpty()) {
                    tutor_vector = tDAO.getTutors("SELECT t.*, "
                            + "CASE WHEN st.tutorId IS NOT NULL THEN 'saved' ELSE 'unsaved' END AS save_status "
                            + "FROM Tutor t "
                            + "LEFT JOIN (SELECT DISTINCT tutorId FROM SavedTutor) st ON t.id = st.tutorId "
                            + "WHERE t.name LIKE N'%" + name + "%'");
                }
            }

            request.setAttribute("tutor_vector", tutor_vector);
            request.setAttribute("tutorRatings", tutorRatings);
            request.setAttribute("subject_vector", subject_vector);
            //request.setAttribute("tutortvailability_vector", tutortvailability_vector);
            LearnerDAO leDAO = new LearnerDAO();
            Learner linfo = leDAO.getStudentById(1);
            request.setAttribute("linfo", linfo);
            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorList.jsp");
            dispatcher.forward(request, response);
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
