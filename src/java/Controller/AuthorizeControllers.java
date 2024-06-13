
package Controller;
//
//import Model.User;
//import DAO.UserDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.util.Random;
//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
//
///**
// *
// * @author Acer
// */
//@WebServlet(name = "AuthorizeControllers", urlPatterns = {"/authorize"})
//public class AuthorizeControllers extends HttpServlet {
//
//    private static int doGetCounter = 0;
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AuthorizeControllers</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AuthorizeControllers at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        UserDAO dao = new UserDAO();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("LearnerRegister");
//        String action = request.getParameter("action");
//        User userForgotPassword = dao.GetUserWithEmail(request.getParameter("email_for_get_new_password"));
//        User userForgotPasswordFromSession = (User) session.getAttribute("User_Forgotpassword");
//
//        if ((user != null || (userForgotPassword != null || userForgotPasswordFromSession != null)) && action != null) {
//            try {
//                Random random = new Random();
//                int rand = random.nextInt(999999);
//                String covert = String.format("%06d", rand); // Ensure a 6-digit code
//
//                boolean emailSent = false;
//                if (user != null) {
//                    emailSent = sendEmail(user.getEmail(), covert);
//                }
//                boolean emailGetNewPassword = false;
//                if (userForgotPassword != null) {
//                    emailGetNewPassword = sendEmail(userForgotPassword.getEmail(), covert);
//                }
//                boolean emailGetNewPasswordFromSession = false;
//                if (userForgotPasswordFromSession != null) {
//                    emailGetNewPasswordFromSession = sendEmail(userForgotPasswordFromSession.getEmail(), covert);
//                }
//
//                if (emailSent || emailGetNewPassword || emailGetNewPasswordFromSession) {
//                    session.setAttribute("code", covert); // Set verification code in session
//
//                    if (action.equalsIgnoreCase("register")) {
//                        doGetCounter++; // Increment counter
//                        request.setAttribute("action", "register");
//                        if (doGetCounter > 1) { // Check if resend
//                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
//                        }
//                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//                    } else if (action.equalsIgnoreCase("forgotPassword")) {
//                        if (userForgotPasswordFromSession == null) {
//                            session.setAttribute("User_Forgotpassword", userForgotPassword);
//                        }
//                        doGetCounter++; // Increment counter
//                        request.setAttribute("action", "forgotPassword");
//                        if (doGetCounter > 1) { // Check if resend
//                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
//                        }
//                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//                    }
//                } else {
//                    request.setAttribute("errorMessage", "Failed to send email. Please try again later.");
//                    request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//                }
//            } catch (Exception e) {
//                request.setAttribute("errorMessage", "Failed to send email. Error: " + e.getMessage());
//                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//                e.printStackTrace();
//            }
//        } else {
//            request.setAttribute("errorMessage", "User not found in session or invalid action.");
//            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//        }
//    }
//
//    private boolean sendEmail(String recipientEmail, String code) {
//        String emailUsername = "anhndhe182179@fpt.edu.vn"; // email cua ban than
//        String emailPassword = "bven xitn hlno foix"; // mat khau ung dung
//
//        try {
//            Email email = new SimpleEmail();
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(587);
//            email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
//            email.setStartTLSEnabled(true);
//            email.setFrom(emailUsername, "Tutorly.com"); // Nguoi gui
//            email.setSubject("Xác nhận Email"); // Tieu de
//            email.setMsg("Mã xác nhận của bạn là: " + code); // Noi dung
//            email.addTo(recipientEmail); // Dia chi email can gui toi
//            email.send(); // gui
//            return true; // tra ve true
//        } catch (EmailException e1) {
//            e1.printStackTrace();
//            System.err.println("Failed to send email via port 587. Attempting via port 465.");
//            try {
//                Email email = new SimpleEmail();
//                email.setHostName("smtp.gmail.com");
//                email.setSmtpPort(465);
//                email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
//                email.setSSLOnConnect(true);
//                email.setFrom(emailUsername, "Tutorly.com");
//                email.setSubject("Xác nhận Email");
//                email.setMsg("Mã xác nhận của bạn là: " + code);
//                email.addTo(recipientEmail);
//                email.send();
//                return true;
//            } catch (EmailException e2) {
//                e2.printStackTrace();
//                System.err.println("Failed to send email via port 465. Error: " + e2.getMessage());
//                return false;
//            }
//        }
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        UserDAO udao = new UserDAO();
//        String codeAuthorize = request.getParameter("codeAuthorize");
//        HttpSession session = request.getSession();
//        String covert = (String) session.getAttribute("code");
//        User user = (User) session.getAttribute("LearnerRegister");
//
//        // Check for null values
//        if (codeAuthorize == null || covert == null || user == null) {
//            request.setAttribute("errorMessage", "Có lỗi xảy ra! Vui lòng thử lại.");
//            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//            return;
//        }
//
//        // Compare authorization code
//        if (codeAuthorize.equalsIgnoreCase(covert)) {
//            // Register the user
//            int x = udao.register(user);
//            if (x > 0) {  // Ensure that register method returns the correct number of affected rows
//                session.setAttribute("LearnerLogin", user);
//                session.removeAttribute("LearnerRegister");
//                request.getRequestDispatcher("/TutorController").forward(request, response); // 
//            } else {
//                request.setAttribute("errorMessage", "Đăng ký thất bại. " + x);// gui tam neu co gi thi mai sau chuyen den 1 trang khac
//                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("action", "register");
//            request.setAttribute("messageError", "Verifications code is not correct !! Enter again.");// gui tam neu co gi thi mai sau chuyen den 1 trang khac
//            request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
//package Controller;
//
//import Config.EmailConfig;
//import DAO.LearnerDAO;
//import Model.User;
//import DAO.UserDAO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.util.Random;
//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.Email;
//import org.apache.commons.mail.EmailException;
//import org.apache.commons.mail.SimpleEmail;
//
///**
// *
// * @author Acer
// */
//@WebServlet(name = "AuthorizeControllers", urlPatterns = {"/authorize"})
//public class AuthorizeControllers extends HttpServlet {
//
//    EmailConfig email = new EmailConfig();
//    private static int doGetCounter = 0;
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AuthorizeControllers</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AuthorizeControllers at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
import Config.EmailConfig;
import DAO.LearnerDAO;
import Model.User;
import DAO.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        UserDAO dao = new UserDAO();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("userRegister");
//        String action = request.getParameter("action");
//        User userForgotPassword = dao.GetUserWithEmail(request.getParameter("email_for_get_new_password"));
//        User userForgotPasswordFromSession = (User) session.getAttribute("User_Forgotpassword");
//
//        if ((user != null || (userForgotPassword != null || userForgotPasswordFromSession != null)) && action != null) {
//            try {
//                Random random = new Random();
//                int rand = random.nextInt(999999);
//                String covert = String.format("%06d", rand); // Ensure a 6-digit code
//
//                boolean emailSent = false;
//                if (user != null) {
//                    emailSent = email.sendEmail(user.getEmail(), covert);
//                }
//                boolean emailGetNewPassword = false;
//                if (userForgotPassword != null) {
//                    emailGetNewPassword = email.sendEmail(userForgotPassword.getEmail(), covert);
//                }
//                boolean emailGetNewPasswordFromSession = false;
//                if (userForgotPasswordFromSession != null) {
//                    emailGetNewPasswordFromSession = email.sendEmail(userForgotPasswordFromSession.getEmail(), covert);
//                }
//
//                if (emailSent || emailGetNewPassword || emailGetNewPasswordFromSession) {
//                    session.setAttribute("code", covert); // Set verification code in session
//
//                    if (action.equalsIgnoreCase("register")) {
//                        doGetCounter++; // Increment counter
//                        request.setAttribute("action", "register");
//                        if (doGetCounter > 1) { // Check if resend
//                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
//                        }
//                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//                    } else if (action.equalsIgnoreCase("forgotPassword")) {
//                        if (userForgotPasswordFromSession == null) {
//                            session.setAttribute("User_Forgotpassword", userForgotPassword);
//                        }
//                        doGetCounter++; // Increment counter
//                        request.setAttribute("action", "forgotPassword");
//                        if (doGetCounter > 1) { // Check if resend
//                            request.setAttribute("sendAgain", "Verification code sent again. Please check your email!");
//                        }
//                        request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//                    }
//                } else {
//                    request.setAttribute("errorMessage", "Failed to send email. Please try again later.");
//                    request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//                }
//            } catch (Exception e) {
//                request.setAttribute("errorMessage", "Failed to send email. Error: " + e.getMessage());
//                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//                e.printStackTrace();
//            }
//        } else {
//            request.setAttribute("errorMessage", "User not found in session or invalid action.");
//            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//        }
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        LearnerDAO ldao = new LearnerDAO();
//        UserDAO udao = new UserDAO();
//        String codeAuthorize = request.getParameter("codeAuthorize");
//        HttpSession session = request.getSession();
//        String covert = (String) session.getAttribute("code");
//        User user = (User) session.getAttribute("userRegister");
//
//        // Check for null values
//        if (codeAuthorize == null || covert == null || user == null) {
//            request.setAttribute("errorMessage", "Có lỗi xảy ra! Vui lòng thử lại.");
//            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//            return;
//        }
//
//        // Compare authorization code
//        if (codeAuthorize.equalsIgnoreCase(covert)) {
//            // Register the user
//            int x = udao.register(user);
//            if (x > 0) {  // Ensure that register method returns the correct number of affected rows
//
//                if (user.getRole().equalsIgnoreCase("learner")) {
//                    int status = ldao.RegisterLearner(user);
//                    if (status > 0) {
//                        session.setAttribute("LearnerLogin", user);
//                        session.removeAttribute("userRegister");
//                        request.getRequestDispatcher("/TutorController").forward(request, response);
//                    }
//                }// 
//            } else {
//                request.setAttribute("errorMessage", "Đăng ký thất bại. " + x);// gui tam neu co gi thi mai sau chuyen den 1 trang khac
//                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("action", "register");
//            request.setAttribute("messageError", "Verifications code is not correct !! Enter again.");// gui tam neu co gi thi mai sau chuyen den 1 trang khac
//            request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
//        }
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

//}
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
        String codeAuthorize = request.getParameter("codeAuthorize");
        HttpSession session = request.getSession();
        String covert = (String) session.getAttribute("code");
        User user = (User) session.getAttribute("userRegister");

        // Check for null values
        if (codeAuthorize == null || covert == null || user == null) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra! Vui lòng thử lại.");
            request.getRequestDispatcher("/View/error.jsp").forward(request, response);
            return;
        }

        // Compare authorization code
        if (codeAuthorize.equalsIgnoreCase(covert)) {
            // Register the user
            int x = udao.register(user);
            if (x > 0) {  // Ensure that register method returns the correct number of affected rows

                if (user.getRole().equalsIgnoreCase("learner")) {
                    int status = ldao.RegisterLearner(user);
                    if (status > 0) {
                        session.setAttribute("LearnerLogin", user);
                        session.removeAttribute("userRegister");
                        request.getRequestDispatcher("/TutorController").forward(request, response);
                    }
                }// 
            } else {
                request.setAttribute("errorMessage", "Đăng ký thất bại. " + x);// gui tam neu co gi thi mai sau chuyen den 1 trang khac
                request.getRequestDispatcher("/View/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("action", "register");
            request.setAttribute("messageError", "Verifications code is not correct !! Enter again.");// gui tam neu co gi thi mai sau chuyen den 1 trang khac
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

