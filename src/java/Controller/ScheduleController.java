/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AssignmentDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.MaterialDAO;
import Model.Assignment;
import Model.Learner;
import Model.Lesson;
import Model.Material;
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
import java.util.Vector;

/**
 * learner
 *
 * @author TRANG
 */
@WebServlet(name = "ScheduleController", urlPatterns = {"/ScheduleController"})
public class ScheduleController extends HttpServlet {

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
        User user = (User) session1.getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "you dont have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        if (user.getRole().equalsIgnoreCase("learner")) {
            String service = request.getParameter("service");
            response.setContentType("text/html;charset=UTF-8");
            LessonDAO lDAO = new LessonDAO();
            MaterialDAO mDAO = new MaterialDAO();
            AssignmentDAO aDAO = new AssignmentDAO();
            Vector<Lesson> lesson_vector = lDAO.getLessonsByLearnerId(1);
            LearnerDAO leDAO = new LearnerDAO();
            Learner linfo = leDAO.getStudentById(1);

            if (service == null || service.isEmpty()) {
                request.setAttribute("linfo", linfo);
                request.setAttribute("lesson_vector", lesson_vector);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/ScheduleView.jsp");
                dispatcher.forward(request, response);
            }else if (service.equals("viewLessonDetail")){
                int lessonid = Integer.parseInt(request.getParameter("lessonid"));
                Vector<Assignment> assignmentoflesson = aDAO.getTodoAssignmentsByLessonId(lessonid);
                Vector<Material> document = mDAO.getMaterialsByLessonIdAndFileType(lessonid, "document");
                Vector<Material> book = mDAO.getMaterialsByLessonIdAndFileType(lessonid, "book");
                Vector<Material> video = mDAO.getMaterialsByLessonIdAndFileType(lessonid, "video/record");
                Vector<Material> slide = mDAO.getMaterialsByLessonIdAndFileType(lessonid, "slide");
                request.setAttribute("service", "viewLessonDetail");
                request.setAttribute("document", document);
                request.setAttribute("book", book);
                request.setAttribute("video", video);
                request.setAttribute("slide", slide);
                request.setAttribute("assignmentoflesson", assignmentoflesson);
                request.setAttribute("linfo", linfo);
                request.setAttribute("lesson_vector", lesson_vector);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/ScheduleView.jsp");
                dispatcher.forward(request, response);

            } else {
                request.setAttribute("errorMessage", "you dont have permission to access this page");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
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

