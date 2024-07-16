/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.LearnerDAO;
import DAO.SavedTutorDAO;
import Model.Learner;
import Model.SavedTutor;
import Model.Tutor;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
@WebServlet(name="SavedTutorController", urlPatterns={"/SavedTutorController"})
public class SavedTutorController extends HttpServlet {
   
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
        LearnerDAO leDAO = new LearnerDAO();
        Learner linfo = leDAO.getStudentById(1);
        SavedTutorDAO stDAO = new SavedTutorDAO();
        String service = request.getParameter("service");
        if(service == null || service.isEmpty()){
            //String tutorId = request.getParameter("tutorId");
            //boolean addTutortoSavelist = stDAO.addSavedTutor(linfo.getId() ,Integer.parseInt(tutorId));
            Vector<Tutor> savedTutors = stDAO.getTutorsByLearnerId(1);
            request.setAttribute("linfo", linfo);
            request.setAttribute("savedTutors", savedTutors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/SavedTutorView.jsp");
            dispatcher.forward(request, response);
        }else if (service.equals("add")){
            String tutorId = request.getParameter("tutor_id");
            String learnId = request.getParameter("learn_id");
            boolean addTutortoSavelist = stDAO.addSavedTutor(Integer.parseInt(learnId) ,
                                                            Integer.parseInt(tutorId));
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/SavedTutorView.jsp");
            dispatcher.forward(request, response);
        }else if (service.equals("remove")){
            String tutorId = request.getParameter("tutor_id");
            String learnId = request.getParameter("learn_id");
            boolean addTutortoSavelist = stDAO.removeSavedTutor(Integer.parseInt(learnId) ,
                                                            Integer.parseInt(tutorId));
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/SavedTutorView.jsp");
            dispatcher.forward(request, response);
        }
        
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
