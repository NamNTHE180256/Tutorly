package Controller;

import Config.EmailConfig;
import DAO.LearnerDAO;
import Model.User;
import DAO.UserDAO;
import Model.Learner;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.Random;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Acer
 */
@WebServlet(name = "AuthorizeControllers", urlPatterns = {"/authorize"})
public class AuthorizeControllers extends HttpServlet {
    
    EmailConfig email = new EmailConfig();
    private static int doGetCounter = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AuthorizeControllers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthorizeControllers at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userRegister");
        String action = request.getParameter("action");
        User userForgotPassword = dao.GetUserWithEmail(request.getParameter("email_for_get_new_password"));
        User userForgotPasswordFromSession = (User) session.getAttribute("User_Forgotpassword");
        
        if ((user != null || (userForgotPassword != null || userForgotPasswordFromSession != null)) && action != null) {
            try {
                Random random = new Random();
                int rand = random.nextInt(999999);
                String covert = String.format("%06d", rand); // Ensure a 6-digit code

                boolean emailSent = false;
                if (user != null) {
                    emailSent = email.sendEmail(user.getEmail(), covert);
                }
                boolean emailGetNewPassword = false;
                if (userForgotPassword != null) {
                    emailGetNewPassword = email.sendEmail(userForgotPassword.getEmail(), covert);
                }
                boolean emailGetNewPasswordFromSession = false;
                if (userForgotPasswordFromSession != null) {
                    emailGetNewPasswordFromSession = email.sendEmail(userForgotPasswordFromSession.getEmail(), covert);
                }
                
                if (emailSent || emailGetNewPassword || emailGetNewPasswordFromSession) {
                    session.setAttribute("code", covert); // Set verification code in session

                    if (action.equalsIgnoreCase("register")) {
                        doGetCounter++; // Increment counter
                        request.setAttribute("action", "register");
                        if (doGetCounter > 1) { // Check if resend
                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
                        }
                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
                    } else if (action.equalsIgnoreCase("forgotPassword")) {
                        if (userForgotPasswordFromSession == null) {
                            session.setAttribute("User_Forgotpassword", userForgotPassword);
                        }
                        doGetCounter++; // Increment counter
                        request.setAttribute("action", "forgotPassword");
                        if (doGetCounter > 1) { // Check if resend
                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
                        }
                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Failed to send email. Please try again later.");
                    request.getRequestDispatcher("/View/error.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Failed to send email. Error: " + e.getMessage());
                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
                e.printStackTrace();
            }
        } else {
            request.setAttribute("errorMessage", "User not found in session or invalid action.");
            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LearnerDAO ldao = new LearnerDAO();
        UserDAO udao = new UserDAO();
        HttpSession session = request.getSession();

        // Retrieve parameters and session attributes
        String codeAuthorize = request.getParameter("codeAuthorize");
        String covert = (String) session.getAttribute("code");
        User user_raw = (User) session.getAttribute("userRegister");

        // Check for null values
        if (codeAuthorize == null || covert == null || user_raw == null) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra! Vui lòng thử lại.");
            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
            return;
        }

        // Compare authorization code
        if (codeAuthorize.equalsIgnoreCase(covert)) {
            // Register the user
            int userId = udao.addUser(user_raw.getEmail(), user_raw.getPassword(), user_raw.getRole());
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            user_raw.setId(userId);
            user_raw.setCreatedAt(date); // Set current date
            if (userId > 0) {
                // Successfully registered user, proceed with learner registration
                Learner learner = new Learner(userId, "New User", " "); // Adjust learner data as needed
                int status = ldao.insertLearner(learner);
                
                if (status > 0) {
                    Cookie learnerCookie = new Cookie("learnerId", String.valueOf(userId));
                    learnerCookie.setMaxAge(60 * 60); // 1 giờ

                    Cookie userCookie = new Cookie("userId", String.valueOf(userId));
                    userCookie.setMaxAge(60 * 60); // 1 giờ

                    // Thêm cookies vào phản hồi
                    response.addCookie(learnerCookie);
                    response.addCookie(userCookie);
                    // Set session attributes
                    session.setAttribute("learner", learner);
                    session.setAttribute("user", user_raw);
                    // Forward to TutorController or appropriate destination
                    response.sendRedirect("TutorController"); // Assuming TutorController is a servlet or JSP
                } else {
                    request.setAttribute("learner", learner);
                    request.setAttribute("errorMessage", "Learner insertion failed.");
                    request.getRequestDispatcher("/View/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "User registration failed.");
                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("action", "register");
            request.setAttribute("messageError", "Mã xác minh không chính xác!! Vui lòng thử lại.");
            request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
