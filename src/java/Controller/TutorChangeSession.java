/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.NotificationDAO;
import DAO.SessionDAO;
import DAO.TutorSessionChangeRequestDAO;
import Model.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Tung Duong
 */
@WebServlet(name = "TutorChangeSession", urlPatterns = {"/tutor-change-session"})
public class TutorChangeSession extends HttpServlet {

    private SessionDAO sessionDAO;
    private LearnerDAO studentDAO;
    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        sessionDAO = new SessionDAO();
        studentDAO = new LearnerDAO();
        notificationDAO = new NotificationDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor") || tutor == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<Learner> learners = studentDAO.getLearnersByTutorId(tutor.getId());
        List<LearnerSessionsDTO> learnerSessionsDTOs = new ArrayList<>();
        List<Session> allSessions = sessionDAO.displayAllSessions2();
        for (Learner learner : learners) {
            List<Session> sessions = sessionDAO.getSessionsByLearnerId(learner.getId());
            LearnerSessionsDTO dto = new LearnerSessionsDTO(learner, sessions);
            learnerSessionsDTOs.add(dto);
        }

        request.setAttribute("learnerSessionsDTOs", learnerSessionsDTOs);
        request.setAttribute("allSessions", allSessions);
        request.getRequestDispatcher("/View/TutorChangeSession.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor") || tutor == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        String action = request.getParameter("action");
        String learnerId = request.getParameter("learnerId");

        if (action.equals("change")) {
            String selectedLearnerParam = request.getParameter("selectedLearnerId");

            if (selectedLearnerParam == null) {
                session.setAttribute("errorMessage", "Please select learner first! ");
                request.getRequestDispatcher("/View/TutorChangeSession.jsp").forward(request, response);
                return;
            }
            int selectedLearnerId = Integer.parseInt(selectedLearnerParam);
            String fromSessionParam = request.getParameter("fromSessionId");
            String toSessionId = request.getParameter("toSessionId");
            String fromSessionId = fromSessionParam.split("/")[0];
            String date = fromSessionParam.split("/")[1];
            String reason = request.getParameter("reason");

            TutorSessionChangeRequestDAO requestDAO = new TutorSessionChangeRequestDAO();
            boolean isAdded = requestDAO.addSessionChangeRequest(selectedLearnerId, tutor.getId(), fromSessionId, toSessionId, reason, "Pending", date);
            if (isAdded) {
                Session s1 = sessionDAO.getSessionById(fromSessionId);
                Session s2 = sessionDAO.getSessionById(toSessionId);
                String type = "TutorRequest";
                String message = "Your Tutor request to change session " + s1.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + "  to session " + s2.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + " of  " + date;
                notificationDAO.addNotification(selectedLearnerId, message, type);
                session.setAttribute("successMessage", "Your request have been sent!.");
            } else {
                session.setAttribute("errorMessage", "Failed to update the session.");
            }
            List<Learner> learners = studentDAO.getLearnersByTutorId(tutor.getId());
            List<LearnerSessionsDTO> learnerSessionsDTOs = new ArrayList<>();
            List<Session> allSessions = sessionDAO.displayAllSessions2();
            for (Learner learner : learners) {
                List<Session> sessions = sessionDAO.getSessionsByLearnerId(learner.getId());
                LearnerSessionsDTO dto = new LearnerSessionsDTO(learner, sessions);
                learnerSessionsDTOs.add(dto);
            }
            request.setAttribute("learnerSessionsDTOs", learnerSessionsDTOs);
            request.setAttribute("allSessions", allSessions);
        } else {
            List<Learner> learners = studentDAO.getLearnersByTutorId(tutor.getId());
            List<LearnerSessionsDTO> learnerSessionsDTOs = new ArrayList<>();
            List<Session> allSessions = sessionDAO.displayAllSessions2();
            for (Learner learner : learners) {
                List<Session> sessions = sessionDAO.getSessionsByLearnerId(learner.getId());
                LearnerSessionsDTO dto = new LearnerSessionsDTO(learner, sessions);
                learnerSessionsDTOs.add(dto);
            }
            request.setAttribute("learnerSessionsDTOs", learnerSessionsDTOs);
            request.setAttribute("allSessions", allSessions);
        }

        request.setAttribute("selectedLearnerId", learnerId);
        request.getRequestDispatcher("/View/TutorChangeSession.jsp").forward(request, response);
    }
}
