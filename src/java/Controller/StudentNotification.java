package Controller;

import DAO.NotificationDAO;
import DAO.TutorSessionChangeRequestDAO;
import Model.Learner;
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
@WebServlet(name = "StudentNotification", urlPatterns = {"/student-noti"})
public class StudentNotification extends HttpServlet {

    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        if (learner == null || !user.getRole().equalsIgnoreCase("learner")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        if (action.equals("request")) {
            notificationDAO.readNotification(id);
            response.sendRedirect("manage-tutor-request");
        }

        if (action.equals("response")) {
            notificationDAO.readNotification(id);
            response.sendRedirect("learner-change-requests");
        }
    }
}
