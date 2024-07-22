package Controller;

import DAO.LearnerDAO;
import DAO.UserDAO;
import GoogleLoginConfig.GoogleAccount;
import GoogleLoginConfig.GoogleLogin;
import Model.Learner;
import Model.User;
import java.io.IOException;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        System.out.println("11:" + acc);

        if (acc != null) {
            User exUser = uDao.GetUserWithEmail(acc.getEmail());
            if (exUser == null) {
                // Tạo mới User từ thông tin Google
                User userGG = new User(acc.getEmail(), "", "learner");

                int userId = uDao.addUser(userGG.getEmail(), userGG.getPassword(), "learner"); // Đăng ký User mới
                userGG.setId(userId);

                // Convert LocalDate to Date
                LocalDate localDate = LocalDate.now();
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                userGG.setCreatedAt(date); // Set current date

                if (userId > 0) {
                    Learner learner = new Learner();
                    learner.setId(userId);
                    System.out.println("id:" + userId);
                    learner.setName(acc.getFamily_name() + acc.getGiven_name());
                    System.out.println("name:" + acc.getFamily_name() + acc.getGiven_name());
                    learner.setImage(acc.getPicture());
                    System.out.println("image: " + acc.getPicture());
                    System.out.println(learner);
                    int status = lDao.insertLearner(learner);

                    if (status > 0) {
                        Cookie learnerCookie = new Cookie("learnerId", String.valueOf(learner.getId()));
                        learnerCookie.setMaxAge(60 * 60); // 1 giờ

                        Cookie userCookie = new Cookie("userId", String.valueOf(userGG.getId()));
                        userCookie.setMaxAge(60 * 60); // 1 giờ

                        // Thêm cookies vào phản hồi
                        response.addCookie(learnerCookie);
                        response.addCookie(userCookie);
                        // Set session attributes
                        session.setAttribute("learner", learner);
                        session.setAttribute("user", userGG);
                        // Forward to TutorController
                        session.setAttribute("id", userId);
                        request.getRequestDispatcher("TutorController").forward(request, response);
                    } else {
                        request.setAttribute("learner", learner);
                        request.setAttribute("errorMessage", "Learner insertion failed.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "User registration failed.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }

            } else {
                // Người dùng đã tồn tại, sử dụng thông tin User và Learner có sẵn
                User userGG = exUser;
                System.out.println(userGG);
                Learner learner = lDao.getLearnerById(exUser.getId());
                System.out.println(learner);
                Cookie learnerCookie = new Cookie("learnerId", String.valueOf(learner.getId()));
                learnerCookie.setMaxAge(60 * 60); // 1 giờ

                Cookie userCookie = new Cookie("userId", String.valueOf(userGG.getId()));
                userCookie.setMaxAge(60 * 60); // 1 giờ

                // Thêm cookies vào phản hồi
                response.addCookie(learnerCookie);
                response.addCookie(userCookie);
                session.setAttribute("learner", learner);
                session.setAttribute("user", userGG);
                response.sendRedirect("TutorController");
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
