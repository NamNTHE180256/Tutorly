/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.LessonDAO;
import Model.Learner;
import Model.Lesson;
import Model.Tutor;
import Model.User;
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
 * @author ADMIN
 */
@WebServlet(name = "HistoryController", urlPatterns = {"/history"})
public class HistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Learner learner = (Learner) session.getAttribute("learner");
        Tutor tutor = (Tutor) session.getAttribute("tutor");
        String majorParam = request.getParameter("class");

        Integer classId = (majorParam != null && !majorParam.isEmpty()) ? Integer.valueOf(majorParam) : null;

        if (user == null) {
            response.sendRedirect("View/Login.jsp");
        } else {
            List<Lesson> lessons = new ArrayList<>();
            LessonDAO ldao = new LessonDAO();
            if (tutor != null) {
                lessons = ldao.getLessonForTutor(tutor.getId(), classId);
            } else if (learner != null) {
                lessons = ldao.getLessonForLearner(learner.getId(), classId);
            }
            request.setAttribute("lessons", lessons);
            request.getRequestDispatcher("View/History.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
