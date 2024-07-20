package Controller;

import DAO.SessionChangeRequestDAO;
import Model.Learner;
import Model.User;
import Model.SessionChangeRequest;
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
@WebServlet(name = "LearnerManageChangeRequests", urlPatterns = {"/learner-change-requests"})
public class LearnerManageChangeRequests extends HttpServlet {

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
        Learner learner = (Learner) session.getAttribute("learner");
        if (user == null || !user.getRole().equalsIgnoreCase("learner")) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        List<SessionChangeRequest> requests = requestDAO.getRequestsByLearnerId(learner.getId());
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/View/LearnerManageChangeRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        if (learner == null || user == null) {
            request.setAttribute("errorMessage", "You don't have permission to access this page");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        String action = request.getParameter("action");
        if (action.equals("change-status")) {
            int requestId = Integer.parseInt(request.getParameter("requestId"));
            String status = request.getParameter("status");
            if (status.equals("Cancel")) {
                boolean isUpdated = requestDAO.updateRequestStatus(requestId, status);
                if (isUpdated) {
                    session.setAttribute("successMessage", "Reject succeessfully! ");
                } else {
                    session.setAttribute("errorMessage", "Failed.");
                }
            }
            response.sendRedirect("learner-change-requests");
        }

    }
}
