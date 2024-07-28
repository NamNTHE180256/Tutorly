/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.AClassDAO;
import Model.AClass;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="LearnerModalController", urlPatterns={"/LearnerModal"})
public class LearnerModalController extends HttpServlet {
   
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LearnerModalController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LearnerModalController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int learnerId = Integer.parseInt(request.getParameter("learnerId"));

        AClassDAO classDAO = new AClassDAO();
        List<AClass> classes = classDAO.getClassesByLearnerId(learnerId);

        StringBuilder html = new StringBuilder();
        for (AClass aClass : classes) {
            html.append("<div class='card'>")
                    .append("<div class='card-header' style='background-color: #0E3C6E; color: white;'>")
                    .append(aClass.getTutor().getName()).append(" - ").append(aClass.getTutor().getSubject().getName())
                    .append("</div>")
                    .append("<div class='card-body'>")
                    .append("<p>Total Lessons: ").append(aClass.getTotalSession()).append("</p>")
                    .append("<p>Start Date: ").append(aClass.getStartDate()).append("</p>")
                    .append("<p>End Date: ").append(aClass.getEndDate()).append("</p>")
                    .append("<p>Status: ").append(aClass.getStatus()).append("</p>")
                    .append("</div>")
                    .append("</div>");
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(html.toString());
        out.close();
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
