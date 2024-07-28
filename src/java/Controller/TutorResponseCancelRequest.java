/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.CancelClassDao;
import Model.RequestCancel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
@WebServlet(name = "TutorResponseCancelRequest", urlPatterns = {"/TutorResponseCancelRequest"})
public class TutorResponseCancelRequest extends HttpServlet {

    CancelClassDao ccDao = new CancelClassDao();
    AClassDAO acdao = new AClassDAO();

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

        String action = request.getParameter("action");
        String cancelIdParam = request.getParameter("cancelId");
        String tutorId = request.getParameter("tutorId");
        String startDate = request.getParameter("startDate");
        try {
            int cancelId = Integer.parseInt(cancelIdParam);

            if (action.equalsIgnoreCase("approve")) {
                ArrayList<RequestCancel> listCancel = ccDao.getALlRequestWithTutorID(Integer.parseInt(tutorId));

                boolean status = ccDao.updateCancelRequestStatus(cancelId, "accepted");
                if (status == true) {
                    int total = acdao.UpdateStatusClass("finished", ccDao.getRequestWithID(cancelId).getAClass().getId());
                    if (total > 0) {
                        int x = ccDao.updateClassStatusAccp(cancelId, startDate);
                        request.setAttribute("cancel", listCancel);
                        response.sendRedirect("../Tutorly/RequestControllersForTutor?requestType=cancelClass&tutorid=" + Integer.parseInt(tutorId));
                    }
                }
            } else if (action.equalsIgnoreCase("deny")) {
                ArrayList<RequestCancel> listCancel = ccDao.getALlRequestWithTutorID(Integer.parseInt(tutorId));
                boolean status = ccDao.updateCancelRequestStatus(cancelId, "denied");
                if (status == true) {
                    int y = ccDao.updateClassStatusDenied(cancelId, startDate);
                    request.setAttribute("cancel", listCancel);
                    response.sendRedirect("../Tutorly/RequestControllersForTutor?requestType=cancelClass&tutorid=" + Integer.parseInt(tutorId));
                }
            }

            // Reload the list of requests after the action
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Cancel ID format");
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
