/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
@WebServlet(name = "UpdateTimeServlet", urlPatterns = {"/UpdateTimeServlet"})
public class UpdateTimeServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateTimeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateTimeServlet at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("updateTime".equals(action)) {
            int timeLeft = Integer.parseInt(request.getParameter("timeLeft"));
            session.setAttribute("timeLeft", timeLeft);
        } else if ("saveAnswer".equals(action)) {
            int questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
            String answer = request.getParameter("answer");
            session.setAttribute("answer" + questionNumber, answer);

            List<Integer> completedQuestions = (List<Integer>) session.getAttribute("completedQuestions");
            if (completedQuestions == null) {
                completedQuestions = new ArrayList<>();
            }
            if (!completedQuestions.contains(questionNumber)) {
                completedQuestions.add(questionNumber);
            }
            session.setAttribute("completedQuestions", completedQuestions);
        } else if ("resetQuiz".equals(action)) {
            session.removeAttribute("timeLeft");
            Integer questionCount = (Integer) session.getAttribute("questionCount");
            if (questionCount != null) {
                for (int i = 1; i <= questionCount; i++) {
                    session.removeAttribute("answer" + i);
                }
            }
            session.removeAttribute("completedQuestions");
            session.setAttribute("quizStatus", "todo");
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
