/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.ManagerDAO;
import DAO.TutorDAO;
import Model.ManageTutor;
import Model.Manager;
import Model.Tutor;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Admin
 */
@WebServlet(name="BLockTutor", urlPatterns={"/BLockTutor"})
public class BLockTutor extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet BLockTutor</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BLockTutor at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int tutorId = Integer.parseInt(request.getParameter("id"));
        String reason = request.getParameter("reason");
        TutorDAO tutorDao = new TutorDAO();
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");

        try {
            if (manager != null) {
                ManagerDAO mDao = new ManagerDAO();
                ManageTutor mt = new ManageTutor(tutorId, manager.getId(), "block");
                mDao.AddManageTutor(mt);
            }
            tutorDao.setStatus(tutorId, "Blocked");
            Tutor tutor = tutorDao.getTutorById(tutorId);

            // Email Configuration
            String emailUsername = "ducanhqbz@gmail.com";
            String emailPassword = "pfeb gdjy vrnu tiva";
            String recipientEmail = tutor.getUserInfo().getEmail();

            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
            email.setStartTLSEnabled(true);
            email.setFrom(emailUsername, "Tutorly.com");
            email.setSubject("[Tutorly] Your Tutor Account has been Blocked.");
            email.setCharset("UTF-8");

            String emailContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; color: black; }"
                    + ".header { font-size: 18px; font-weight: bold; }"
                    + ".content { margin-top: 20px; color: black; }"
                    + ".footer { margin-top: 30px; font-size: 14px; color: black; }"
                    + ".footer p { margin: 0; color: black; }"
                    + ".footer .address { margin-top: 10px; color: black; }"
                    + ".footer .logo { margin-top: 20px; color: black; }"
                    + ".red { color: red; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<p class='header'>Dear <strong>" + tutor.getName() + "</strong>,</p>"
                    + "<div class='content'>"
                    + "<p>We regret to inform you that your account on <strong>Tutorly</strong> has been <i class='red'>BLOCKED</i> due to the following reason:</p>"
                    + "<p><i>" + reason + "</i></p>"
                    + "<p>If you have any questions or need further clarification, please contact our support team.</p>"
                    + "<p><i>Note: This is an auto-generated email, please do not reply.</i></p>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>Sincerely,</p>"
                    + "<p><strong>Tutorly Team</strong></p>"
                    + "<div class='address'>"
                    + "<p>Phone: +84 123 456 789</p>"
                    + "</div>"
                    + "<div class='logo'>"
                    + "<img src='cid:logo' />"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            email.setHtmlMsg(emailContent);

            // Correctly specify the path to the logo image
            File logoFile = new File(getServletContext().getRealPath("/image/LOGO_Main.png"));
            if (!logoFile.exists()) {
                throw new RuntimeException("Logo file does not exist at path: " + logoFile.getAbsolutePath());
            }
            email.embed(logoFile, "logo");

            email.addTo(recipientEmail);
            email.send();

            // If email is sent successfully, send a success response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\":\"success\"}");

        } catch (EmailException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"Failed to send email.\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\", \"message\":\"An unexpected error occurred.\"}");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
