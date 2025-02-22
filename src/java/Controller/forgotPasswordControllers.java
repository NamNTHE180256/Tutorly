/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.TutorDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DAO.UserDAO;
import Model.Learner;
import Model.Tutor;
import Model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Acer
 */
@WebServlet(name = "forgotPasswordControllers", urlPatterns = {"/forgotPassword"})
public class forgotPasswordControllers extends HttpServlet {

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
            out.println("<title>Servlet forgotPasswordControllers</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet forgotPasswordControllers at " + request.getContextPath() + "</h1>");
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
        String codeAuthorize = request.getParameter("codeAuthorize");
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");

        // Ensure codeAuthorize and code are not null before comparing
        if (codeAuthorize != null && code != null && codeAuthorize.equalsIgnoreCase(code)) {
            response.sendRedirect(request.getContextPath() + "/View/makeNewPasswordForgot.jsp");
        } else {
            request.setAttribute("messageError", "Verification code is not correct");
            request.setAttribute("action", "forgotPassword");
            request.getRequestDispatcher("/View/authorize.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet responseS
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TutorDAO tdao = new TutorDAO();
        LearnerDAO ldao = new LearnerDAO();
        UserDAO udao = new UserDAO();
        int status = 0;
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String passwordConfirm = request.getParameter("passwordConfirm");
        User user = null;
        if (password.equals(passwordConfirm)) {
            user = (User) session.getAttribute("User_Forgotpassword");
            status = udao.ChangePassWord(computeMD5Hash(password), user.getEmail());

        } else {
            request.setAttribute("messageError", "Password Confirm must be same with password");
            request.getRequestDispatcher("/View/makeNewPasswordForgot.jsp").forward(request, response);

        }
        if (status > 0) {
            if (user.getRole().equalsIgnoreCase("learner")) {
                session.setAttribute("user", user);
                Learner learner = ldao.getLearnerById(user.getId());
                session.setAttribute("learner", learner);
                Cookie learnerCookie = new Cookie("learnerId", String.valueOf(learner.getId()));
                learnerCookie.setMaxAge(60 * 60); // 1 giờ

                Cookie userCookie = new Cookie("userId", String.valueOf(user.getId()));
                userCookie.setMaxAge(60 * 60); // 1 giờ

                // Thêm cookies vào phản hồi
                response.addCookie(learnerCookie);
                response.addCookie(userCookie);
            response.sendRedirect("TutorController");
            } else if (user.getRole().equalsIgnoreCase("tutor")) {
                session.setAttribute("user", user);
                Tutor tutor = tdao.getTutorById(user.getId());
                session.setAttribute("tutor", tutor);

                Cookie tutorCookie = new Cookie("tutorId", String.valueOf(tutor.getId()));
                tutorCookie.setMaxAge(60 * 60); // 1 hour

                Cookie userCookie = new Cookie("userId", String.valueOf(user.getId()));
                userCookie.setMaxAge(60 * 60); // 1 hour

                // Add cookies to the response
                response.addCookie(tutorCookie);
                response.addCookie(userCookie);
                 response.sendRedirect("tutor-dashboard");
            } else {
                request.setAttribute("errorMessage", "Change Password faileddd!!!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    private String computeMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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
