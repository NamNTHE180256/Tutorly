/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Config.EmailConfig;
import static Controller.RegisterTrialCotroller.getNearestDayOfWeek;
import DAO.AClassDAO;
import DAO.LearnerDAO;
import DAO.LessonDAO;
import DAO.RegisterTrialClassDAO;
import DAO.SessionDAO;
import DAO.SubjectDAO;
import DAO.TutorDAO;
import DAO.UserDAO;
import Model.AClass;
import Model.Lesson;
import Model.RegisterTrialClass;
import Model.Tutor;
import Model.User;
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

    UserDAO udao = new UserDAO();
    AClassDAO aclassDAO = new AClassDAO();
    LearnerDAO lDAO = new LearnerDAO();
    LessonDAO lessonDAO = new LessonDAO();
    SessionDAO sDAO = new SessionDAO();
    TutorDAO tDAO = new TutorDAO();
    SubjectDAO sbDAO = new SubjectDAO();
    EmailConfig config = new EmailConfig();
    RegisterTrialClassDAO rDAO = new RegisterTrialClassDAO();

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

        Vector<RegisterTrialClass> listregister = rDAO.getTrialClassesByTutorId(7);
        request.setAttribute("listregister", listregister);
        String service = request.getParameter("service");
        if (service == null) {
            response.sendRedirect("../Tutorly/View/Logim.jsp");
            return;
        }

        if (!service.isEmpty()) {
            if (service.equals("accept")) {
                try {
                    String learner_id = request.getParameter("learner_id");
                    String responseid = request.getParameter("responseid");
                    String session_id = request.getParameter("session_id");
                    String tutor_id = request.getParameter("tutor_id");
                    String dateStr = request.getParameter("date");

                    // Update the trial class status to 'accepted'
                    rDAO.updateTrialClassStatus(Integer.parseInt(responseid), "accepted");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = formatter.parse(dateStr);

                    AClass aClass = new AClass(
                            lDAO.getLearnerById(Integer.parseInt(learner_id)),
                            tDAO.getTutorById(Integer.parseInt(tutor_id)),
                            1,
                            date,
                            date,
                            "trial",
                            tDAO.getTutorById(Integer.parseInt(tutor_id)).getSubject()
                    );

                    int classId = aclassDAO.addClass(aClass);

                    int lessonResult = 0;
                    Lesson newLesson = null;

                    if (classId != 0) {
                        AClass addedClass = aclassDAO.getClassById(classId);

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

                    if (lessonResult != 0) {
                        // Update the status of other classes with specific criteria
                        int status = rDAO.updateStatusWithSpecificCriteria(Integer.parseInt(tutor_id), session_id, Integer.parseInt(responseid), dateStr);
                        int Number = rDAO.sendEmailsForUpdatedRecords(Integer.parseInt(tutor_id), session_id, Integer.parseInt(responseid), dateStr);
                        // Redirect to the appropriate page
                        response.sendRedirect("../Tutorly/RequestControllersForTutor?requestType=registerTrial&tutorid=" + Integer.parseInt(tutor_id));
                    } else {
                        response.getWriter().write("Failed to add lesson.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().write("Error: " + e.getMessage());
                }

            } else if (service.equals("deny")) {
                try {
                    String responseid = request.getParameter("responseid");
                    String learner_id = request.getParameter("learnerid");
                    User user = udao.getUserById(Integer.parseInt(learner_id));
                    Tutor tutor = tDAO.getTutorById(Integer.parseInt(request.getParameter("tutorid")));

                    int status = rDAO.updateTrialClassStatus(Integer.parseInt(responseid), "denied");
                    if (status > 0) {
                        boolean x = config.MailDenyTrial(user.getEmail(), tutor.getName());
                        response.sendRedirect("../Tutorly/RequestControllersForTutor?requestType=registerTrial&tutorid=" + Integer.parseInt(request.getParameter("tutorid")));
                    } else {
                        response.sendRedirect("../Tutorly/RequestControllersForTutor?requestType=registerTrial&tutorid=" + Integer.parseInt(request.getParameter("tutorid")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().write("Error: " + e.getMessage());
                }
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("View/TutorResponseTrialClassView.jsp");
                dispatcher.forward(request, response);
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
