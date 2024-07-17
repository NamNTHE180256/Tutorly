/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import static Controller.RegisterTrialCotroller.getNearestDayOfWeek;
import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.RegisterTrialClassDAO;
import DAO.SessionDAO;
import DAO.SubjectDAO;
import DAO.TutorDAO;
import Model.AClass;
import Model.Lesson;
import Model.RegisterTrialClass;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author TRANG
 */
@WebServlet(name = "TutorResponseTrialClassController", urlPatterns = {"/TutorResponseTrialClassController"})
public class TutorResponseTrialClassController extends HttpServlet {

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
        RegisterTrialClassDAO rDAO = new RegisterTrialClassDAO();
        Vector<RegisterTrialClass> listregister = rDAO.getTrialClassesByTutorId(7);
        request.setAttribute("listregister", listregister);
        String service = request.getParameter("service");
        if (service != null && !service.isEmpty()) {
            if (service.equals("accept")) {
                try {
                    String learner_id = request.getParameter("learner_id");
                    String responseid = request.getParameter("responseid");
                    String session_id = request.getParameter("session_id");
                    String tutor_id = request.getParameter("tutor_id");
                    String dateStr = request.getParameter("date");

                    rDAO.updateTrialClassStatus(Integer.parseInt(responseid), "accepted");
                    
                    
                    AClassDAO aclassDAO = new AClassDAO();
                    LearnerDAO lDAO = new LearnerDAO();
                    LessonDAO lessonDAO = new LessonDAO();
                    SessionDAO sDAO = new SessionDAO();
                    TutorDAO tDAO = new TutorDAO();
                    SubjectDAO sbDAO = new SubjectDAO();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(dateStr);

                    AClass aClass = new AClass(
                            lDAO.getLearnerById(Integer.parseInt(learner_id)),
                            tDAO.getTutorById(Integer.parseInt(tutor_id)),
                            1,
                            date,
                            date,
                            "trial",
                            sbDAO.getSubjectById(tDAO.getTutorById(Integer.parseInt(tutor_id)).getSubject().getId())
                    );

                    int classId = aclassDAO.addClass(aClass);

                    int lessonResult = 0;
                    Lesson newLesson = null;

                    if (classId != 0) {
                        // Retrieve the newly added AClass using the classId directly
                        AClass addedClass = aclassDAO.getClassById(aclassDAO.getLatestClassId());

                        if (addedClass != null) {
                            newLesson = new Lesson(
                                    addedClass,
                                    sDAO.getSessionById(session_id),
                                    addedClass.getStartDate(),
                                    "Scheduled"
                            );
                            lessonResult = lessonDAO.addLesson(newLesson);
                        }
                    }

                    // Optionally, you can send a response back to the client
                    if (lessonResult != 0) {
                        response.getWriter().write("Class and lesson added successfully.");
                    } else {
                        response.getWriter().write("Failed to add lesson.");
                    }
                    response.sendRedirect("TutorResponseTrialClassController");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().write("Error: " + e.getMessage());
                }
            } else if (service.equals("deny")) {
                String responseid = request.getParameter("responseid");
                rDAO.updateTrialClassStatus(Integer.parseInt(responseid), "denied");
                response.sendRedirect("TutorResponseTrialClassController");
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorResponseTrialClassView.jsp");
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
