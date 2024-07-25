/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.RatingCountPercentageDAO;
import DAO.RatingDAO;
import DAO.SaveTutorListDAO;
import DAO.TutorAvailabilityDAO;
import DAO.TutorDAO;
import Model.Learner;
import Model.Rating;
import Model.RatingCountPercentage;
import Model.Tutor;
import Model.TutorAvailability;
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
 * learner
 *
 * @author TRANG
 */
@WebServlet(name = "TutorDetailController", urlPatterns = {"/TutorDetailController"})
public class TutorDetailController extends HttpServlet {

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

        TutorDAO tuDAO = new TutorDAO();
        RatingDAO rDAO = new RatingDAO();
        SaveTutorListDAO tDAO = new SaveTutorListDAO();
        TutorAvailabilityDAO taDao = new TutorAvailabilityDAO();
        RatingCountPercentageDAO ratecountDAO = new RatingCountPercentageDAO();
        Tutor t = new Tutor();
        Vector<Tutor> suggesttutor_vector = null;
        Vector<Rating> rateofstudent = null;
        Map<String, Object> tutorRatings = null;
        Vector<RatingCountPercentage> ratecount = null;
        String id_raw = request.getParameter("id");
        String subjectId_raw = request.getParameter("idsub");
        if(id_raw!= null & !id_raw.isEmpty()){
            int id = Integer.parseInt(id_raw);
            int subjectId = Integer.parseInt(subjectId_raw);
            t = tDAO.getTutorById(id);
            rateofstudent = rDAO.getRatingsByTutorId(id);
            tutorRatings = tDAO.getTutorRatingsById(id);
            ratecount = ratecountDAO.getPercentageByTutorID(id);
            suggesttutor_vector = tuDAO.getTutors("SELECT *\n"
                    + "FROM Tutor\n"
                    + "WHERE subjectId ="+ subjectId 
                    + "AND id <> "+id);
        }
        
        Vector<TutorAvailability> tutorAvailabilities = taDao.getTutorAvailabilityByTutorId(Integer.parseInt(id_raw));
        request.setAttribute("tutorAvailabilities", tutorAvailabilities);
        request.setAttribute("ratings", rateofstudent);
        request.setAttribute("tutorRatings", tutorRatings);
        request.setAttribute("ratecount", ratecount);
        request.setAttribute("tutor", t);
        request.setAttribute("suggesttutor_vector", suggesttutor_vector);
        LearnerDAO leDAO = new LearnerDAO();
        Learner linfo = leDAO.getStudentById(1);
        request.setAttribute("linfo", linfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorDetail.jsp");
        dispatcher.forward(request, response);
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
