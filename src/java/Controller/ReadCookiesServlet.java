package Controller;

import DAO.LearnerDAO;
import DAO.ManagerDAO;
import DAO.TutorDAO;
import DAO.UserDAO;
import Model.Learner;
import Model.Manager;
import Model.Tutor;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ReadCookiesServlet
 */
@WebServlet(name = "ReadCookiesServlet", urlPatterns = {"/ReadCookiesServlet"})
public class ReadCookiesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        ManagerDAO mDao = new ManagerDAO();
        LearnerDAO ldao = new LearnerDAO();
        UserDAO udao = new UserDAO();
        Learner learner = null;
        TutorDAO tdao = new TutorDAO();
        Tutor tutor = null;
        User user = null;

        if (cookies != null) {
            // Iterate through all cookies
            for (Cookie cookie : cookies) {
                // Check and handle learnerId cookie
                if ("learnerId".equals(cookie.getName())) {
                    String learnerId_raw = cookie.getValue();
                    int learnerId = Integer.parseInt(learnerId_raw);
                    learner = ldao.getLearnerById(learnerId);

                    // Save learner information in the session
                    HttpSession session = request.getSession();
                    session.setAttribute("learner", learner);
                }

                // Check and handle tutorId cookie
                if ("tutorId".equals(cookie.getName())) {
                    String tutorId_raw = cookie.getValue();
                    int tutorId = Integer.parseInt(tutorId_raw);
                    tutor = tdao.getTutorById(tutorId);

                    // Save tutor information in the session
                    HttpSession session = request.getSession();
                    session.setAttribute("tutor", tutor);
                }
                if ("userId".equals(cookie.getName())) {
                    String userId = cookie.getValue();
                    int uid = Integer.parseInt(userId);
                    user = udao.getUserById(uid);
                    System.out.println(user);
                    // Save user information in the session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                }
                if ("managerId".equals(cookie.getName())) {
                    String managerId = cookie.getValue();
                    int uid = Integer.parseInt(managerId);
                    Manager manager = mDao.getManagerById(uid);

                    // Save user information in the session
                    HttpSession session = request.getSession();
                    session.setAttribute("manager", manager);
                }

                // Check and handle userId cookie
            }
        }

        if (learner != null && tutor == null) {
            user = udao.getUserById(learner.getId());
        } else if (tutor != null && learner == null) {
            user = udao.getUserById(tutor.getId());
        }
        // Debugging prints
        System.out.println("Learner: " + learner);
        System.out.println("Tutor: " + tutor);

        if (user == null) {

            request.getRequestDispatcher("View/Login.jsp").forward(request, response);
        }
        // Redirect to the appropriate page based on u  ser role
        if (user != null && user.getRole() != null) {
            if (user.getRole().equalsIgnoreCase("Learner")) {
                response.sendRedirect("DashboardController?type=learner" + "&learnerid=" + learner.getId());
            } else if (user.getRole().equalsIgnoreCase("tutor")) {
                response.sendRedirect("../Tutorly/DashboardController?type=tutor&tutorid=" + tutor.getId());
            } else if (user.getRole().equalsIgnoreCase("admin") && tutor == null && learner == null) {
                response.sendRedirect("AdminController?action=dashboard");
            } else if (user.getRole().equalsIgnoreCase("manager")) {
                response.sendRedirect("AdminController?action=tutor");    
            } else {
                request.setAttribute("messageError", "Unknown role!");
                request.getRequestDispatcher("View/Login.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
