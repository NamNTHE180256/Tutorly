package Controller;

import DAO.SessionChangeRequestDAO;
import DAO.SessionDAO;
import DAO.TutorDAO;
import DAO.TutorNotificationDAO;
import Model.Learner;
import Model.User;
import Model.Tutor;
import Model.Session;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Tung Duong
 */
@WebServlet(name = "RequestChangeSessionServlet", urlPatterns = {"/submit-change-session-request"})
public class RequestChangeSessionServlet extends HttpServlet {

    private SessionChangeRequestDAO requestDAO;
    private SessionDAO sessionDAO;
    private TutorDAO tutorDAO;
    private TutorNotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        requestDAO = new SessionChangeRequestDAO();
        sessionDAO = new SessionDAO();
        tutorDAO = new TutorDAO();
        notificationDAO = new TutorNotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        if (user == null || !user.getRole().equalsIgnoreCase("learner")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<Tutor> tutors = tutorDAO.getTutorsByLearnerId(learner.getId());
        List<Session> sessions = sessionDAO.getSessionsByLearnerId(learner.getId());
        List<Session> availableSessions = sessionDAO.displayAllSessions2();

        request.setAttribute("tutors", tutors);
        request.setAttribute("sessions", sessions);
        request.setAttribute("availableSessions", availableSessions);
        request.getRequestDispatcher("/View/RequestChangeSession.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        if (learner == null || !user.getRole().equalsIgnoreCase("learner")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        int learnerId = learner.getId();
        int tutorId = Integer.parseInt(request.getParameter("tutorId"));
        String fromSessionParam = request.getParameter("fromSessionId");
        String toSessionId = request.getParameter("toSessionId");
        String fromSessionId = fromSessionParam.split("/")[0];
        String date = fromSessionParam.split("/")[1];
        String reason = request.getParameter("reason");

        boolean isAdded = requestDAO.addSessionChangeRequest(learnerId, tutorId, fromSessionId, toSessionId, reason, "Pending", date);

        if (isAdded) {
            Session s1 = sessionDAO.getSessionById(fromSessionId);
            Session s2 = sessionDAO.getSessionById(toSessionId);
            String type = "StudentRequest";
            String message = "Your Student request to change session " + s1.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + "  to session " + s2.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + " of  " + date;
            notificationDAO.addNotification(tutorId, message, type);
            session.setAttribute("successMessage", "Your request have been sent!.");
        } else {
            session.setAttribute("errorMessage", "Gửi yêu cầu đổi buổi học thất bại.");
        }

        response.sendRedirect("submit-change-session-request");
    }
}
