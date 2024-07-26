/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LessonDAO;
import DAO.VideoDAO;
import Model.Video;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
@WebServlet(name = "addVideoControllers", urlPatterns = {"/addVideoControllers"})
public class addVideoControllers extends HttpServlet {

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
        LessonDAO dao = new LessonDAO();
        VideoDAO vdao = new VideoDAO();
        int slotid = Integer.parseInt(request.getParameter("slotid"));
        int classid = Integer.parseInt(request.getParameter("classid"));
        String filename = request.getParameter("fileName");
        String linkYtb = request.getParameter("linkYtb");
        Video video = new Video(dao.getLessonById(slotid, classid), filename, linkYtb, "video");
        int status = vdao.insertVideoWithCondition(video, classid, slotid);
        if (status > 0) {
            response.sendRedirect("lessonDetailControllers?classid=" + classid + "&lessonId=" + slotid + "&error=upload Success");
        } else {
            request.setAttribute("error", "upload failed");
            response.sendRedirect("lessonDetailControllers?classid=" + classid + "&lessonId=" + slotid + "&error=upload failed");
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
