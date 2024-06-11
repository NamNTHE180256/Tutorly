/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LearnerDAO;
import DAO.UserDAO;
import GoogleLoginConfig.GoogleAccount;
import GoogleLoginConfig.GoogleLogin;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Acer
 */
@WebServlet(name="GoogleLoginControllers", urlPatterns={"/LoginGoogle"})
public class GoogleLoginControllers extends HttpServlet {

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
        if (uDao.GetUserWithEmail(acc.getEmail()) == null) {
            if (uDao.registerLearnerUsingGoogle(acc) > 0) {
                userLoginGoogle = new User(acc.getEmail(), null, "learner");
                if (lDao.RegisterLearner(userLoginGoogle) > 0) {
                    status = 1;
                }
            }
        } else {
            userLoginGoogle = uDao.GetUserWithEmail(acc.getEmail());
            status = 1;
        }
    }

    if (status > 0) {
        session.setAttribute("LearnerLogin", userLoginGoogle);
        request.getRequestDispatcher("TutorController").forward(request, response);
    } else {
        request.setAttribute("errorMessage", "Google login failed.");
        request.getRequestDispatcher("errorPage.jsp").forward(request, response);
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
