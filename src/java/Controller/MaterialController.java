/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.MaterialDAO;
import Model.AClass;
import Model.Assignment;
import Model.Learner;
import Model.Material;
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
@WebServlet(name = "MaterialController", urlPatterns = {"/MaterialController"})
public class MaterialController extends HttpServlet {

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
        AClassDAO cDAO = new AClassDAO();
        MaterialDAO mDAO = new MaterialDAO();
        LearnerDAO leDAO = new LearnerDAO();
        Learner linfo = leDAO.getStudentById(1);
        Vector<AClass> class_vector = cDAO.getClassesByLearnerId(linfo.getId());
        String service = request.getParameter("service");
        String type = request.getParameter("type");
        String classid = request.getParameter("classid");

        Vector<Material> doc = new Vector<>(); // Initialize an empty list
        Vector<Material> slide = new Vector<>();
        Vector<Material> book = new Vector<>();
        Vector<Material> video = new Vector<>();
        if (service == null || service.isEmpty()) {
            request.setAttribute("class_vector", class_vector);
            request.setAttribute("linfo", linfo);
//        request.setAttribute("materials", materials); // Set the empty list
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/MaterialView.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("viewClassMaterial") && classid != null && !classid.isEmpty()) {

            try {
                int classIdInt = Integer.parseInt(classid);
                doc = mDAO.getMaterialsByClassIdAndFileType(classIdInt, "document");
                slide = mDAO.getMaterialsByClassIdAndFileType(classIdInt, "slide");
                book = mDAO.getMaterialsByClassIdAndFileType(classIdInt, "book");
                video = mDAO.getMaterialsByClassIdAndFileType(classIdInt, "video/record");
            } catch (NumberFormatException e) {
                // Handle the exception, e.g., log it and set an error message
            }

        request.setAttribute("doc", doc);
        request.setAttribute("slide", slide);
        request.setAttribute("book", book);
        request.setAttribute("video", video);
        request.setAttribute("linfo", linfo);
        request.setAttribute("video", video);
         request.setAttribute("class_vector", class_vector);
        request.setAttribute("linfo", linfo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("View/MaterialView.jsp");
        dispatcher.forward(request, response);
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
