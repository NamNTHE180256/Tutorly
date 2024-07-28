/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.CancelClassDao;
import Model.AClass;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author TRANG
 */
@WebServlet(name = "CancelClassController", urlPatterns = {"/CancelClassController"})
public class CancelClassController extends HttpServlet {

    AClassDAO cDao = new AClassDAO();
    CancelClassDao ccDao = new CancelClassDao();

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
        String id = request.getParameter("id");
        String class_id = request.getParameter("class_id");
        
        
        int class_id_Cancel = Integer.parseInt(class_id);
        AClass classCandel = cDao.getClassById(class_id_Cancel);
      
            int status = ccDao.insertCancelRequest(classCandel);
            if (status > 0) {
                request.setAttribute("error", "Wait for Tutor Response");
            }

            request.getRequestDispatcher("/ViewClassnew").forward(request, response);
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
        CancelClassDao ccdao = new CancelClassDao();
        String reqid = request.getParameter("cancelId");
        int idreq = Integer.parseInt(reqid);
        boolean status = ccdao.deleteCancelRequest(idreq);
        if (status == true) {
            request.getRequestDispatcher("/ViewClassnew").forward(request, response);
        }

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
