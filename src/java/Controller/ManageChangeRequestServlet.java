package Controller;

import DAO.NotificationDAO;
import DAO.SessionChangeRequestDAO;
import DAO.SessionDAO;
import Model.Session;
import Model.User;
import Model.SessionChangeRequest;
import Model.Tutor;
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
@WebServlet(name = "ManageChangeRequestServlet", urlPatterns = {"/manage-change-request"})
public class ManageChangeRequestServlet extends HttpServlet {

    private SessionChangeRequestDAO requestDAO;

    @Override
    public void init() throws ServletException {
        requestDAO = new SessionChangeRequestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<SessionChangeRequest> requests = requestDAO.getRequestsByTutorId(tutor.getId());
        requests.forEach(System.out::println);
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/View/ManageChangeRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (user == null || !user.getRole().equalsIgnoreCase("tutor")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        NotificationDAO notificationDAO = new NotificationDAO();
        String action = request.getParameter("action");
        if (action.equals("change-status")) {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            int lid = Integer.parseInt(request.getParameter("lid"));
            String from_session = request.getParameter("from_session");
            String to_session = request.getParameter("to_session");
            String date = request.getParameter("date");
            String status = request.getParameter("status");

            SessionDAO sessionDAO = new SessionDAO();
            Session s1 = sessionDAO.getSessionById(from_session);
            Session s2 = sessionDAO.getSessionById(to_session);
            if (status.equals("Approved")) {
                boolean isUpdated = requestDAO.updateRequestStatus(requestId, status);
                if (isUpdated) {
                    sessionDAO.updateLearnerSession(lid, from_session, to_session, date);
                    String type = "TurorResponse";
                    String message = "Your request have been approved change " + s1.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + "  to session " + s2.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + " of  " + date;
                    notificationDAO.addNotification(lid, message, type);

                    request.setAttribute("successMessage", "Approve succeessfully! ");
                } else {
                    request.setAttribute("errorMessage", "Failed.");
                }
            }
            if (status.equals("Rejected")) {
                boolean isUpdated = requestDAO.updateRequestStatus(requestId, status);
                if (isUpdated) {
                    String message = "Your request have been rejected to change " + s1.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + "  to session " + s2.getStartTime().split("\\.")[0] + " in " + s1.getDayOfWeek() + " of  " + date;
                    String type = "TurorResponse";
                    notificationDAO.addNotification(lid, message, type);

                    request.setAttribute("successMessage", "Reject succeessfully! ");
                } else {
                    request.setAttribute("errorMessage", "Failed.");
                }
            }

            response.sendRedirect("manage-change-request");
        }

    }
}
