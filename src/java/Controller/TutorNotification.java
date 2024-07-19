package Controller;

import DAO.NotificationDAO;
import DAO.TutorNotificationDAO;
import DAO.TutorSessionChangeRequestDAO;
import Model.Learner;
import Model.Tutor;
import Model.User;
import java.io.IOException;
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
@WebServlet(name = "TutorNotification", urlPatterns = {"/tutor-noti"})
public class TutorNotification extends HttpServlet {

    private TutorNotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        notificationDAO = new TutorNotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (tutor == null || !user.getRole().equalsIgnoreCase("tutor")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        if (action.equals("request")) {
            notificationDAO.readNotification(id);
            response.sendRedirect("manage-change-request");
        }

        if (action.equals("response")) {
            notificationDAO.readNotification(id);
            response.sendRedirect("tutor-requests");
        }
    }
}
