package Controller;

import DAO.LearnerDAO;
import DAO.UserDAO;
import GoogleLoginConfig.GoogleAccount;
import GoogleLoginConfig.GoogleLogin;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "GoogleLoginControllers", urlPatterns = {"/LoginGoogle"})
public class GoogleLoginControllers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO uDao = new UserDAO();
        LearnerDAO lDao = new LearnerDAO();
        HttpSession session = request.getSession();
        String code = request.getParameter("code");

        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        GoogleAccount acc = gg.getUserInfo(accessToken);
        User userLoginGoogle = null;
        int status = 0;

        if (acc != null) {
            User existingUser = uDao.GetUserWithEmail(acc.getEmail());
            if (existingUser == null) {
                if (uDao.registerLearnerUsingGoogle(acc) > 0) {
                    userLoginGoogle = new User(acc.getEmail(), null, "learner");
                    status = lDao.RegisterLearner(userLoginGoogle);
                }
            } else {
                userLoginGoogle = existingUser;
                status = 1;
            }
        }

        if (status > 0) {
            session.setAttribute("user", userLoginGoogle);
            response.sendRedirect("TutorController");
        } else {
            request.setAttribute("errorMessage", "Google login failed.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
