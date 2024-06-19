/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.MaterialDAO;
import Model.Material;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
@WebServlet(name = "DowloadControllers", urlPatterns = {"/download"})
public class DownloadControllers extends HttpServlet {

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
      MaterialDAO mDao = new MaterialDAO();
    int slotid = Integer.parseInt(request.getParameter("slotid"));
    int fileid = Integer.parseInt(request.getParameter("id"));
    int classid = Integer.parseInt(request.getParameter("classId"));
    Material mate = mDao.getAllMaterialWithlesson(classid, fileid, slotid);
    ArrayList<Material> list = mDao.getAllMaterialWithID(classid, slotid);
    String filePath = mate.getFilePath();
    String downloadLink = generateDownloadLink(request, filePath);
    
    request.setAttribute("downloadLink", downloadLink);
    request.setAttribute("listMaterial", list);
    request.setAttribute("classId", classid);
    request.setAttribute("slotid", slotid);
    
    request.getRequestDispatcher("View/Material.jsp").forward(request, response);
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

 private String generateDownloadLink(HttpServletRequest request, String filePath) {
    String contextPath = request.getContextPath();
    try {
        String encodedFilePath = URLEncoder.encode(filePath, "UTF-8");
        return contextPath + "/downloadFile?filePath=" + encodedFilePath;
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return contextPath + "/downloadFile?filePath=" + filePath;
    }}

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
