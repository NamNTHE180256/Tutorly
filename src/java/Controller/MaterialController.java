/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.MaterialDAO;
import DAO.VideoDAO;
import Model.AClass;
import Model.Learner;
import Model.Material;
import Model.Video;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
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
        VideoDAO vdao = new VideoDAO();
        LearnerDAO leDAO = new LearnerDAO();
        Learner linfo = leDAO.getStudentById(1);
        Vector<AClass> class_vector = cDAO.getClassesByLearnerId(linfo.getId());
        String service = request.getParameter("service");
        String classid = request.getParameter("classid");
        Vector<Material> doc = new Vector<>();
        Vector<Material> slide = new Vector<>();
        Vector<Material> book = new Vector<>();
        Vector<Material> listmate = new Vector<>();
        Vector<Video> video = new Vector<>();
        try {
            int classIdInt = Integer.parseInt(classid);
            slide = mDAO.getSlide(classIdInt, "pdf");
            doc = mDAO.getDocument(classIdInt, "pdf");
            video = vdao.getAllVideoWithClassID(classIdInt);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (service == null || service.isEmpty()) {
            request.setAttribute("class_vector", class_vector);
            request.setAttribute("linfo", linfo);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/MaterialView.jsp");
            dispatcher.forward(request, response);
        } else if (service.equals("viewClassMaterial") && classid != null && !classid.isEmpty()) {

            request.setAttribute("classid", classid);
            request.setAttribute("doc", doc);
            request.setAttribute("slide", slide);
            request.setAttribute("book", book);
            request.setAttribute("video", video);
            request.setAttribute("class_vector", class_vector);
            request.setAttribute("linfo", linfo);
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/MaterialView.jsp");
            dispatcher.forward(request, response);

        } else if (service.equalsIgnoreCase("download")) {
            try {
                int lessonid = Integer.parseInt(request.getParameter("lessonid"));
                int id = Integer.parseInt(request.getParameter("id"));
                int classIdInt = Integer.parseInt(classid);
                Material mate = mDAO.getAllMaterialWithLesson(classIdInt, id, lessonid);
                String appPath = getServletContext().getRealPath("/") + "uploads";
                File uploads = new File(appPath);
                if (!uploads.exists()) {
                    uploads.mkdirs();
                }

                File file = new File(uploads, mate.getFileName());
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(mate.getFilePath());
                }

                // Set attributes to forward to JSP
                String fileUrl = request.getContextPath() + "/uploads/" + mate.getFileName();
                request.setAttribute("fileUrl", fileUrl);
                request.setAttribute("classid", classid);
                request.setAttribute("doc", doc);
                request.setAttribute("slide", slide);
                request.setAttribute("book", book);
                request.setAttribute("video", video);
                request.setAttribute("class_vector", class_vector);
                request.setAttribute("linfo", linfo);
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/MaterialView.jsp");
                dispatcher.forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (service.equalsIgnoreCase("downloadVideo")) {
            int lessonid = Integer.parseInt(request.getParameter("lessonid"));
            int id = Integer.parseInt(request.getParameter("id"));
            int classIdInt = Integer.parseInt(classid);
            Video video1 = vdao.getAllVideoWithLesson(classIdInt, id, lessonid);
            String videoUrl = video1.getFilePath();
            String videoId = videoUrl.substring(videoUrl.lastIndexOf("v=") + 2); // Trích xuất ID video từ URL
            String embedUrl = "https://www.youtube.com/embed/" + videoId + "?controls=0"; // Tạo URL nhúng
            request.setAttribute("fileUrlYtb", embedUrl);
            request.setAttribute("classid", classid);
            request.setAttribute("doc", doc);
            request.setAttribute("slide", slide);
            request.setAttribute("book", book);
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
