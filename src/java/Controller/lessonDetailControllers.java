package Controller;

import DAO.LessonDAO;
import Model.Lesson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class lessonDetailControllers
 */
@WebServlet(name = "lessonDetailControllers", urlPatterns = {"/lessonDetailControllers"})
public class lessonDetailControllers extends HttpServlet {

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
        LessonDAO ldao = new LessonDAO();
        response.setContentType("text/html;charset=UTF-8");

        String classIdStr = request.getParameter("classid");
        String lessonIdStr = request.getParameter("lessonId");

        if (classIdStr != null && lessonIdStr != null) {
            int classid = Integer.parseInt(classIdStr);
            int lessonId = Integer.parseInt(lessonIdStr);

            Lesson lesson = ldao.getLessonById(lessonId, classid);
            System.out.println("1: "+lesson);
            if (lesson != null) {
                request.setAttribute("lesson", lesson);
                request.getRequestDispatcher("View/lessonDetails.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "No lesson found for the provided class ID and lesson ID.");
                request.getRequestDispatcher("View/lessonDetails.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid class ID or lesson ID");
            request.getRequestDispatcher("View/lessonDetails.jsp").forward(request, response);
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
