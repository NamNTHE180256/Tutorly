/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Acer
 */
@WebServlet(name = "changePasswordControllers", urlPatterns = {"/changePassword"})
public class changePasswordControllers extends HttpServlet {

    UserDAO udao = new UserDAO();

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
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String Currentpass = request.getParameter("Currentpass");
        System.out.println(Currentpass);
        String newpass = request.getParameter("newpass");
        System.out.println(newpass);
        User user = udao.Login(email, Currentpass);
        String newpass2 = request.getParameter("newpass2");
        System.out.println(newpass2);
        User user2 = (User) session.getAttribute("user");
        if (user != null) {
            if (newpass.equals(newpass2)) {
                user2.setPassword(newpass);
                if (udao.ChangePassWord(user2.getPassword(), user2.getEmail()) > 0) {
                    request.setAttribute("error", "Change password successfull");
                    user2.setPassword(newpass);
                    System.out.println(user2);
                    session.setAttribute("user", user2);
                    if (user2.getRole().equalsIgnoreCase("learner")) {
                        request.setAttribute("error", "Change Password Successfull");
                        request.getRequestDispatcher("StudentProfileController").forward(request, response);
                    } else if (user2.getRole().equalsIgnoreCase("tutor")) {
                        response.sendRedirect("../Tutorly/TutorProfileController?service=&error=Change Password Successfull");
                    }
                }

            } else {
                if (user2.getRole().equalsIgnoreCase("learner")) {
                    request.setAttribute("error", "Password Confirmation does not match");
                    request.getRequestDispatcher("StudentProfileController").forward(request, response);
                } else if (user2.getRole().equalsIgnoreCase("tutor")) {
                    response.sendRedirect("../Tutorly/TutorProfileController?service=&error=Password Confirmation does not match");
                }
            }
        } else {
            if (user2.getRole().equalsIgnoreCase("learner")) {
                request.setAttribute("error", "Wrong Password");
                request.getRequestDispatcher("StudentProfileController").forward(request, response);
            } else if (user2.getRole().equalsIgnoreCase("tutor")) {
                response.sendRedirect("../Tutorly/TutorProfileController?service=&error=Wrong Password");
            }
        }
    }

    public String computeMD5Hash(String input) {
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
        processRequest(request, response);
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
        processRequest(request, response);
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
