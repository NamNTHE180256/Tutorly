package Controller;

import DAO.SessionChangeRequestDAO;
import DAO.TutorSessionChangeRequestDAO;
import Model.Learner;
import Model.User;
import Model.SessionChangeRequest;
import Model.Tutor;
import Model.TutorSessionChangeRequest;
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
@WebServlet(name = "TutorRequest", urlPatterns = {"/tutor-requests"})
public class TutorRequest extends HttpServlet {

    private TutorSessionChangeRequestDAO requestDAO;

    @Override
    public void init() throws ServletException {
        requestDAO = new TutorSessionChangeRequestDAO();
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

        List<TutorSessionChangeRequest> requests = requestDAO.getRequestsByTutorId(tutor.getId());
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/View/TutorManageChangeRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        if (tutor == null || !user.getRole().equalsIgnoreCase("tutor")) {
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
            response.sendRedirect("tutor-requests");
        }
        if (action.equals("delete")) {
            int requestId = Integer.parseInt(request.getParameter("requestId"));

            boolean isDelete = requestDAO.deleteRequest(requestId);
            if (isDelete) {
                session.setAttribute("successMessage", "Delete succeessfully! ");
            } else {
                session.setAttribute("errorMessage", "Failed.");
            }

            response.sendRedirect("tutor-requests");
        }

    }
}
